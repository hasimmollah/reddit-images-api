
package com.lp.reddittopimages.redditimages_api.exception.handler;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.lp.reddittopimages.redditimages_api.exception.ErrorCodes;
import com.lp.reddittopimages.redditimages_api.exception.ErrorResponse;
import com.lp.reddittopimages.redditimages_api.exception.RedditImageNotFoundException;
import com.lp.reddittopimages.redditimages_api.exception.RedditDataParsingException;
import com.lp.reddittopimages.redditimages_api.exception.RedditIntegrationException;


/**
 * Advice to handle exception
 * 
 * @author hasmolla
 *
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice(basePackages = { "com.lp.reddittopimages.redditimages_api" })
public class RedditImagesApiExceptionHandler {

    Logger logger = LoggerFactory.getLogger(RedditImagesApiExceptionHandler.class);

    @ExceptionHandler(RedditIntegrationException.class)
    public ResponseEntity<ErrorResponse> handleRedditIntegrationException(
        RedditIntegrationException e) {
        logger.error(e.getMessage(), e);
        ErrorResponse error = ErrorCodes.REDDIT_INTEGRATION_ERR.getErrorResponse();
        return new ResponseEntity<>(
            error,
            HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RedditDataParsingException.class)
    public ResponseEntity<ErrorResponse> handleRedditDataParsingException(
        RedditDataParsingException e) {
        logger.error(e.getMessage(), e);
        ErrorResponse error = ErrorCodes.REDDIT_PARSING_ERR.getErrorResponse();
        return new ResponseEntity<>(
            error,
            HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        logger.error(e.getMessage(), e);
        ErrorResponse error = ErrorCodes.GENERIC_ERR.getErrorResponse();
        return new ResponseEntity<>(
            error,
            HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RedditImageNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleImageNotFoundException(RedditImageNotFoundException e) {
        logger.error(e.getMessage(), e);
        ErrorResponse error = ErrorCodes.IMAGE_NOT_FOUND_ERR.getErrorResponse();
        return new ResponseEntity<>(
            error,
            HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ServletRequestBindingException.class)
    public ResponseEntity<ErrorResponse> handleServletRequestBindingException(
        HttpServletRequest request, ServletRequestBindingException ex) {
        logger.error(ex.getMessage(), ex);
        ErrorResponse error = new ErrorResponse(
            ErrorCodes.MISSING_OR_EMPTY_HEADER.getCode(),
            ex.getMessage());
        return new ResponseEntity<>(
            error,
            HttpStatus.BAD_REQUEST);
    }
}
