package com.pluxurydolo.instagram.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ContainerStatusResponse(

    @JsonProperty("id")
    String id,

    @JsonProperty("status_code")
    String statusCode,

    @JsonProperty("status")
    String status
) {
}
