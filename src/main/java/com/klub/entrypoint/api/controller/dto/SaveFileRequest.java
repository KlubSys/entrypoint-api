package com.klub.entrypoint.api.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveFileRequest {

    //@JsonProperty("is_dir")
    private Boolean isDir;
    private String filename;
    //@JsonProperty("file_type")
    private String fileType;

    private String parent;
}
