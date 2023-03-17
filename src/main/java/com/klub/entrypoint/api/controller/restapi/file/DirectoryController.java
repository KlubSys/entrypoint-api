package com.klub.entrypoint.api.controller.restapi.file;

import com.klub.entrypoint.api.configs.ftp.FtpUploadMessagingGateway;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;

@RestController
@Validated
@RequestMapping("/api/directory")
public class DirectoryController {

    @PostMapping
    public void createDirectory() {
    }

    @PatchMapping("{directoryId}")
    public void renameDirectory() {}

}
