package com.klub.entrypoint.api.controller.restapi.test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.klub.entrypoint.api.controller.dto.SaveFileRequest;
import com.klub.entrypoint.api.controller.dto.resource.KlubFileDto;
import com.klub.entrypoint.api.exception.ErrorOccurredException;
import com.klub.entrypoint.api.exception.NotFoundException;
import com.klub.entrypoint.api.model.KlubFileEntity;
import com.klub.entrypoint.api.model.dto.SaveFileInput;
import com.klub.entrypoint.api.repository.KlubFileRepositoryInterface;
import com.klub.entrypoint.api.service.api.CentralLoggerServerApi;
import com.klub.entrypoint.api.service.api.dto.CentralServerLogMessage;
import com.klub.entrypoint.api.service.resource.KlubFileServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@Validated
@RequestMapping("/api_test/file")
public class TestFileController {

    private final KlubFileServiceInterface klubFileService;
    private final KlubFileRepositoryInterface klubFileRepository;
    private final ObjectMapper defaultMapper;
    private final CentralLoggerServerApi centralLoggerServerApi;

    @Autowired
    public TestFileController(KlubFileServiceInterface klubFileService,
                              KlubFileRepositoryInterface klubFileRepository, ObjectMapper defaultMapper, CentralLoggerServerApi centralLoggerServerApi) {
        this.klubFileService = klubFileService;
        this.klubFileRepository = klubFileRepository;
        this.defaultMapper = defaultMapper;
        this.centralLoggerServerApi = centralLoggerServerApi;
    }

    @PostMapping
    ResponseEntity<KlubFileDto> createFile(@RequestParam("file") MultipartFile uploadedFile,
                                           @RequestParam("data") String data)
            throws Exception {
        SaveFileRequest body = defaultMapper.readValue(data, new TypeReference<>() {});

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

        centralLoggerServerApi.dispatchLog(CentralServerLogMessage.builder()
                .text("Uploading File").build());
        KlubFileEntity file = klubFileService.create(uploadedFile.getBytes(), input,
                body.getParent() == null ? null : parent);
        KlubFileDto data2 = KlubFileDto.from(file);
        return new ResponseEntity<>(data2, HttpStatus.OK);
    }

    @GetMapping("files")
    ResponseEntity<List<KlubFileDto>> getFiles() {
        List<KlubFileDto> res = klubFileRepository.findAll()
                .stream().map(KlubFileDto::from).collect(Collectors.toList());
        return new ResponseEntity<>(res, HttpStatus.OK);
    }


}
