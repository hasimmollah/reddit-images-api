package com.lp.reddittopimages.redditimages_api.exception;

public class RedditImagesApiBaseException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private ErrorResponse validationErrorResponse;

    public RedditImagesApiBaseException() {
        super();
    }

    public RedditImagesApiBaseException(String message) {
        super(message);
    }

    public RedditImagesApiBaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public RedditImagesApiBaseException(Throwable cause) {
        super(cause);
    }

    public void setValidationErrorResponse(ErrorResponse validationErrorResponse) {
        this.validationErrorResponse = validationErrorResponse;

    }

    public ErrorResponse getValidationErrorResponse() {
        return validationErrorResponse;

    }
}
