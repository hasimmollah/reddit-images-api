package com.lp.reddittopimages.redditimages_api.exception;

public class RedditIntegrationException extends RedditImagesApiBaseException{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public RedditIntegrationException() {
        super();
    }

    public RedditIntegrationException(String message) {
        super(message);
    }

    public RedditIntegrationException(String message, Throwable cause) {
        super(message, cause);
    }

    public RedditIntegrationException(Throwable cause) {
        super(cause);
    }
}
