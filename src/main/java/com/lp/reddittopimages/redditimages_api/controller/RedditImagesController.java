package com.lp.reddittopimages.redditimages_api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lp.reddittopimages.redditimages_api.model.Image;
import com.lp.reddittopimages.redditimages_api.service.RedditIntegrationService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
public class RedditImagesController {
    @Autowired
    RedditIntegrationService redditIntegrationService;

    @RequestMapping("/")
    String healthCheck() {
        return "Application is up";
    }

    @RequestMapping(path = "/images", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "This api endpoint fecilitates fetch images from reddit.")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
    public ResponseEntity<List<Image>> fetchImages(
        @ApiParam(value = "Provides information about the topic for which images to be search") @RequestHeader(name = "topic", required=true)  String topic
       

    ) {
       List<Image> images= redditIntegrationService.fetchImages(topic);
        return new ResponseEntity<List<Image>>(
            images,
            HttpStatus.OK);
    }
}
