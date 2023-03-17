package com.klub.entrypoint.api.configs.ftp;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import java.io.File;

@MessagingGateway
public interface FtpUploadMessagingGateway {

    @Gateway(requestChannel = "ftpUploadChannel")
    public void uploadFile(File file);
}
