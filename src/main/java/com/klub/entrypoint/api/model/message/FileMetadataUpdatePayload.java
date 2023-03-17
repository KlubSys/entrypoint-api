package com.klub.entrypoint.api.model.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileMetadataUpdatePayload {

    private String fileId;
    private HashMap<String, Object> data = new HashMap<>();
}
