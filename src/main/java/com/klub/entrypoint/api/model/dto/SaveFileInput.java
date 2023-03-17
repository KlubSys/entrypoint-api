package com.klub.entrypoint.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveFileInput {

    private Boolean isDir;
    private String filename;
    private String fileType;
}
