package com.klub.entrypoint.api.controller.restapi.file;

import com.klub.entrypoint.api.configs.ftp.CustomFtpClient;
import com.klub.entrypoint.api.configs.ftp.FtpUploadMessagingGateway;
import com.klub.entrypoint.api.controller.dto.SaveFileRequest;
import com.klub.entrypoint.api.controller.dto.resource.KlubFileDto;
import com.klub.entrypoint.api.exception.NotFoundException;
import com.klub.entrypoint.api.model.KlubFileEntity;
import com.klub.entrypoint.api.model.dto.SaveFileInput;
import com.klub.entrypoint.api.service.resource.KlubFileServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

import static org.springframework.integration.graph.LinkNode.Type.input;

@RestController
@Validated
@RequestMapping("/api/file")
public class FileController {

    private final FtpUploadMessagingGateway ftpUploadMessagingGateway;
    private final CustomFtpClient ftpClient;

    private final KlubFileServiceInterface fileServiceInterface;

    @Autowired
    public FileController(FtpUploadMessagingGateway ftpUploadMessagingGateway,
                          CustomFtpClient ftpClient, KlubFileServiceInterface fileServiceInterface) {
        this.ftpUploadMessagingGateway = ftpUploadMessagingGateway;
        this.ftpClient = ftpClient;
        this.fileServiceInterface = fileServiceInterface;
    }

    @PostMapping
    public String createFile(@RequestBody SaveFileRequest body) throws IOException {
        /*
        KlubFileEntity parent = null;
        if (body.getParent() != null) {
            Optional<KlubFileEntity> fileCtn = klubFileRepository.findById(body.getParent());
            if (fileCtn.isEmpty())
                throw new NotFoundException("Parent file not found");
            parent = fileCtn.get();
        }

        SaveFileInput input = new SaveFileInput();
        input.setFileType(body.getFileType());
        input.setFilename(body.getFilename());
        input.setIsDir(body.getIsDir());

        KlubFileEntity file = klubFileService.create(input,
                body.getParent() == null ? null : parent);
        KlubFileDto data = KlubFileDto.from(file);
        return new ResponseEntity<>(data, HttpStatus.OK);*/
        return "OK";
    }

    @PatchMapping("{fileId}")
    public void renameFile() {}

    @DeleteMapping("{fileId}")
    public void deleteFile() {}

}
