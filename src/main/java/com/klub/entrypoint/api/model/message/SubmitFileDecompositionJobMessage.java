package com.klub.entrypoint.api.model.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.klub.entrypoint.api.helper.JobActionEnum;
import com.klub.entrypoint.api.helper.JobTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SubmitFileDecompositionJobMessage {

    @JsonProperty("job_type")
    private JobTypeEnum jobType;

    @JsonProperty("action")
    private JobActionEnum action;

    @JsonProperty("job_interval")
    private Integer jobInterval;

    @JsonProperty("result_location")
    private String resultLocation;
    private String payload;


    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class SubmitFileDecompositionJobPayload {
        @JsonProperty("file_id")
        private String fileId;
        /**
         * The name of the file in the temporary storage
         */
        @JsonProperty("filename")
        private String fileName;
    }
}
