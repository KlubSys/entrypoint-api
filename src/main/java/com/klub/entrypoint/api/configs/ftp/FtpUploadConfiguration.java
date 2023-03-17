package com.klub.entrypoint.api.configs.ftp;

import org.apache.commons.net.ftp.FTPFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.expression.common.LiteralExpression;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.file.remote.session.CachingSessionFactory;
import org.springframework.integration.file.remote.session.SessionFactory;
import org.springframework.integration.ftp.outbound.FtpMessageHandler;
import org.springframework.integration.ftp.session.DefaultFtpSessionFactory;
import org.springframework.integration.ftp.session.FtpFileInfo;
import org.springframework.messaging.MessageHandler;

import java.time.LocalDateTime;

@Configuration
public class FtpUploadConfiguration {

    @Value("${ftp.port}")
    private Integer ftpPort;

    @Value("${ftp.username}")
    private String username;

    @Value("${ftp.password}")
    private String password;

    @Bean
    public SessionFactory<FTPFile> ftpSessionFactory() {
        DefaultFtpSessionFactory sf = new DefaultFtpSessionFactory();
        sf.setHost("localhost");
        sf.setPort(ftpPort);
        sf.setUsername(username);
        sf.setPassword(password);

        return new CachingSessionFactory<FTPFile>(sf);
    }

    @Bean
    @ServiceActivator(inputChannel = "ftpUploadChannel")
    public MessageHandler uploadHandler() {
        FtpMessageHandler handler = new FtpMessageHandler(ftpSessionFactory());
        handler.setRemoteDirectoryExpression(new LiteralExpression("/klub/temp_storage"));
        handler.setUseTemporaryFileName(false);
        handler.setFileNameGenerator(message -> String.format("klub_data_%d.klub", LocalDateTime.now().getNano()));
        return handler;
    }
}
