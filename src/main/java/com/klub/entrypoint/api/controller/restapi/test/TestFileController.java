package com.klub.entrypoint.api.controller.restapi.test;

import com.klub.entrypoint.api.controller.dto.SaveFileRequest;
import com.klub.entrypoint.api.controller.dto.resource.KlubFileDto;
import com.klub.entrypoint.api.exception.ErrorOccurredException;
import com.klub.entrypoint.api.exception.NotFoundException;
import com.klub.entrypoint.api.model.KlubFileEntity;
import com.klub.entrypoint.api.model.dto.SaveFileInput;
import com.klub.entrypoint.api.repository.KlubFileRepositoryInterface;
import com.klub.entrypoint.api.service.resource.KlubFileServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@Validated
@RequestMapping("/api_test/file")
public class TestFileController {

    private final KlubFileServiceInterface klubFileService;
    private final KlubFileRepositoryInterface klubFileRepository;

    @Autowired
    public TestFileController(KlubFileServiceInterface klubFileService,
                              KlubFileRepositoryInterface klubFileRepository) {
        this.klubFileService = klubFileService;
        this.klubFileRepository = klubFileRepository;
    }

    @PostMapping
    ResponseEntity<KlubFileDto> createFile(@RequestBody @Valid SaveFileRequest body)
            throws NotFoundException,
            ErrorOccurredException {
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
        return new ResponseEntity<>(data, HttpStatus.OK);
    }


}
