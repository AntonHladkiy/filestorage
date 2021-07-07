package com.ah.filestorage.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse extends SuccessResponse {
    @JsonProperty("error")
    private String errorMessage;

    public ErrorResponse(String message) {
        super( false );
        this.errorMessage = message;
    }
}
