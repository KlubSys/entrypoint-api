package com.klub.entrypoint.api.service.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.klub.entrypoint.api.exception.ErrorOccurredException;
import com.klub.entrypoint.api.model.message.SubmitFileDecompositionJobMessage;
import com.klub.entrypoint.api.service.api.dto.CentralServerLogMessage;
import com.klub.entrypoint.api.service.api.dto.SubmitDecompositionJobApiResponse;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class CentralLoggerServerApi {

    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");

    @Value("${log.server}")
    private String logServer;

    @Value("${klub.logger.name}")
    private String loggerName;

    private final ObjectMapper defaultMapper;

    final OkHttpClient client = new OkHttpClient();

    @Autowired
    public CentralLoggerServerApi(ObjectMapper defaultMapper) {
        this.defaultMapper = defaultMapper;
    }

    public boolean dispatchLog(CentralServerLogMessage message) throws Exception {
        message.setFrom(loggerName);
        String ctn = defaultMapper.writeValueAsString(message);
        RequestBody body = RequestBody.create(ctn, JSON);
        System.out.println("Central Server log Message Request body " + ctn);

        Request request = new Request.Builder()
                .url(logServer + "/logs")
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new ErrorOccurredException("Request failed with " + response.code());
            if (response.body() == null) throw new ErrorOccurredException("Submit server response body ius null");

            String bdy = response.body().string();
            System.out.println("Body Logs server request api " + bdy + "" + response.body().contentLength());
            return true;
        } catch (IOException e) {
            System.err.println("Calling Server Log API " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }
}
