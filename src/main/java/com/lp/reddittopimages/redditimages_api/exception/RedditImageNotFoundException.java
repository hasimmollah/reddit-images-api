package com.lp.reddittopimages.redditimages_api.exception;

public class RedditImageNotFoundException extends RedditImagesApiBaseException{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public RedditImageNotFoundException() {
        super();
    }

    public RedditImageNotFoundException(String message) {
        super(message);
    }

    public RedditImageNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public RedditImageNotFoundException(Throwable cause) {
        super(cause);
    }
}
