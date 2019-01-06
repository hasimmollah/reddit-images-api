package com.lp.reddittopimages.redditimages_api.exception;

public class RedditDataParsingException extends RedditImagesApiBaseException{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public RedditDataParsingException() {
        super();
    }

    public RedditDataParsingException(String message) {
        super(message);
    }

    public RedditDataParsingException(String message, Throwable cause) {
        super(message, cause);
    }

    public RedditDataParsingException(Throwable cause) {
        super(cause);
    }
}
