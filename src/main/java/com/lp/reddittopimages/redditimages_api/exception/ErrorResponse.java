package com.lp.reddittopimages.redditimages_api.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@JsonTypeName("Error")                                                                                         
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT ,use = JsonTypeInfo.Id.NAME)
public class ErrorResponse {
    @JsonProperty(value = "Message")
    private String message;
    
    @JsonProperty(value = "Code")
    private String code;
    

    public ErrorResponse() {
        super();
    }

    public ErrorResponse(String code, String message) {
        super();
        this.code = code;
        this.message = message;
    }

    @Override
    public String toString() {
        return "ErrorResponse Code:" + code + " ErrorResponse Message: " + message;
    }
}