package com.klub.entrypoint.api.service.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.klub.entrypoint.api.configs.ftp.CustomFtpClient;
import com.klub.entrypoint.api.exception.ErrorOccurredException;
import com.klub.entrypoint.api.helper.JobActionEnum;
import com.klub.entrypoint.api.helper.JobTypeEnum;
import com.klub.entrypoint.api.model.KlubFileEntity;
import com.klub.entrypoint.api.model.dto.SaveFileInput;
import com.klub.entrypoint.api.model.message.SubmitFileDecompositionJobMessage;
import com.klub.entrypoint.api.repository.KlubFileRepositoryInterface;
import com.klub.entrypoint.api.service.BaseEntityCrudService;
import com.klub.entrypoint.api.service.api.JobSchedulerApi;
import com.klub.entrypoint.api.service.api.dto.SubmitDecompositionJobApiResponse;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;

@Service
@Transactional
public class KlubFileService extends
        BaseEntityCrudService<KlubFileEntity, String, KlubFileRepositoryInterface>
        implements KlubFileServiceInterface {

    private final CustomFtpClient ftpClient;
    private final ObjectMapper defaultMapper;
    private final JobSchedulerApi jobSchedulerApi;

    @Autowired
    public KlubFileService(KlubFileRepositoryInterface daoRepository, CustomFtpClient ftpClient,
                           ObjectMapper defaultMapper, JobSchedulerApi jobSchedulerApi) {
        super(daoRepository);
        this.ftpClient = ftpClient;
        this.defaultMapper = defaultMapper;
        this.jobSchedulerApi = jobSchedulerApi;
    }

    @Override
    public KlubFileEntity create(SaveFileInput input, KlubFileEntity parent)
            throws ErrorOccurredException {
        try {
            KlubFileEntity file = new KlubFileEntity();
            file.setDir(input.getIsDir());
            file.setFileType(input.getFileType());
            file.setFilename(input.getFilename());

            file.setParent(parent);

            if (parent != null) {
                file.setLevel(parent.getLevel() + 1);
            }

            file = daoRepository.save(file);

            //Upload the file via FTP
            File fileData = new File("file.dat");// File.createTempFile("temp", "file.txt");
            FileUtils.writeStringToFile(fileData, "Data test", "UTF-8");

            //ftpUploadMessagingGateway.uploadFile(file);
            final String temporaryStorageFilename = String.format("%s.klub", file.getId());

            boolean res = ftpClient.getInstance().storeFile(
                    //TODO path must be in a constant
                    String.format("/klub/temp_storage/%s", temporaryStorageFilename),
                    new FileInputStream(fileData)
            );
            if (res) {
                System.out.println("File uploaded");
                file.setUploaded(true);
                file.setTemporaryStorageFilename(temporaryStorageFilename);
                file = daoRepository.save(file);
            } else {
                daoRepository.delete(file);
                throw new ErrorOccurredException("File not uploaded");
            }

            //Construct payload to create a decomposition job
            SubmitFileDecompositionJobMessage decompositionJobMessage = new SubmitFileDecompositionJobMessage();
            decompositionJobMessage.setJobInterval(0);
            decompositionJobMessage.setResultLocation("");
            decompositionJobMessage.setJobType(JobTypeEnum.ONCE);
            decompositionJobMessage.setAction(JobActionEnum.FILE_DECOMPOSITION);

            SubmitFileDecompositionJobMessage.SubmitFileDecompositionJobPayload payload =
                    new SubmitFileDecompositionJobMessage.SubmitFileDecompositionJobPayload(
                            file.getId(), temporaryStorageFilename
                    );
            decompositionJobMessage.setPayload(defaultMapper.writeValueAsString(payload));

            try {
                //TODO uncomment;
                SubmitDecompositionJobApiResponse ctn = jobSchedulerApi.submitJob(decompositionJobMessage);
                file.setJobId(ctn.getJobId());
                file.setJobScheduled(true);
                file = daoRepository.save(file);
            } catch (Exception e) {
                System.err.println(e.getMessage());
                e.printStackTrace();
                throw new ErrorOccurredException("Can't submit the decomposition job");
            }

            return file;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ErrorOccurredException("Message " + e.getMessage());
        }
    }

    @Override
    public void update(KlubFileEntity file, HashMap<String, Object> data) throws ErrorOccurredException {
        try {
            update(file, data, new ArrayList<>());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ErrorOccurredException("Message " + e.getMessage());
        }
    }
}
