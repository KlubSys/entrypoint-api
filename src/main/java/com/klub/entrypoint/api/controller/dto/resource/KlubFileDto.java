package com.klub.entrypoint.api.controller.dto.resource;

import com.klub.entrypoint.api.model.KlubFileEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KlubFileDto {

    private String id;
    private Boolean isDir;
    private String filename;
    private String fileType;
    private Integer level = 0;
    private Boolean uploaded = true;
    private String firstBlockGroupId;
    private Integer dataBlocCount;
    private String parentId;

    public static KlubFileDto from(KlubFileEntity file){
        if (file == null) return null;

        return new KlubFileDto(file.getId(), file.getDir(), file.getFilename(), file.getFileType(),
                file.getLevel(), file.getUploaded(), file.getFirstBlockGroupId(), file.getDataBlocCount(),
                file.getParent() != null ? file.getParent().getId() : null);
    }
}
