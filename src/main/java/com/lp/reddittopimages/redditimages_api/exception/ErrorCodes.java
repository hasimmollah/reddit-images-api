package com.lp.reddittopimages.redditimages_api.exception;

public enum ErrorCodes {

    //Application Error Codes
    GENERIC_ERR("ERR_000", "Application Exception occurred. Please contact system administrator."),
    REDDIT_INTEGRATION_ERR("ERR_001", "Error occurred while fetching data from Reddit. Please contact system administrator."),
    MISSING_OR_EMPTY_HEADER(
        "ERR_002",
        "Missing or Empty request header"),
    REDDIT_PARSING_ERR("ERR_003", "Error occurred while parsing reddit data from Reddit. Please contact system administrator.")
    ,IMAGE_NOT_FOUND_ERR("ERR_004", "No images found for the given inputs"),
    
    
   
;

    private final String code;
    private final String description;

    private ErrorCodes(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getCode() {
        return code;
    }

    public ErrorResponse getErrorResponse() {
        return new ErrorResponse(code, description);
    }

    @Override
    public String toString() {
        return code + ": " + description;
    }
    
    public static final String missingOrEmptyHeaderErrorMsg(String headerName) {
        return "Missing or Empty request header '" + headerName + "'";
    }

   
}