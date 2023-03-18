package com.klub.entrypoint.api.service.resource;

import com.klub.entrypoint.api.exception.ErrorOccurredException;
import com.klub.entrypoint.api.model.KlubFileEntity;
import com.klub.entrypoint.api.model.dto.SaveFileInput;

import java.util.HashMap;

public interface KlubFileServiceInterface {

    KlubFileEntity create(byte[] bytes, SaveFileInput input, KlubFileEntity parent) throws ErrorOccurredException;

    void update(KlubFileEntity file, HashMap<String, Object> data) throws ErrorOccurredException;
}