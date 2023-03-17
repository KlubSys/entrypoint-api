package com.klub.entrypoint.api.service.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.klub.entrypoint.api.exception.ErrorOccurredException;
import com.klub.entrypoint.api.exception.NotFoundException;
import com.klub.entrypoint.api.model.KlubFileEntity;
import com.klub.entrypoint.api.model.message.FileMetadataUpdatePayload;
import com.klub.entrypoint.api.repository.KlubFileRepositoryInterface;
import com.klub.entrypoint.api.service.resource.KlubFileServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class KafkaFileMetadataListener {

    private final ObjectMapper defaultMapper;
    private final KlubFileRepositoryInterface klubFileRepository;
    private final KlubFileServiceInterface klubFileService;

    @Autowired
    public KafkaFileMetadataListener(ObjectMapper defaultMapper,
                                     KlubFileRepositoryInterface klubFileRepository,
                                     KlubFileServiceInterface klubFileService) {
        this.defaultMapper = defaultMapper;
        this.klubFileRepository = klubFileRepository;
        this.klubFileService = klubFileService;
    }

    @KafkaListener(topics = "file_metadata", groupId = "group0")
    void handle(String data){
        System.out.println("Received Data " + data);

        try {
            FileMetadataUpdatePayload payload = defaultMapper
                    .readValue(data, FileMetadataUpdatePayload.class);

            Optional<KlubFileEntity> fileCtn = klubFileRepository.findById(payload.getFileId());
            if (fileCtn.isEmpty()) throw new NotFoundException("No file found");

            klubFileService.update(fileCtn.get(), payload.getData());
        } catch (JsonProcessingException | NotFoundException | ErrorOccurredException e) {
            throw new RuntimeException(e);
        }
    }
}
