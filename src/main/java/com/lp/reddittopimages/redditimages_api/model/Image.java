package com.lp.reddittopimages.redditimages_api.model;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Image {

    
    @JsonProperty("url")
    private String url;
    
    
    @JsonProperty("score")
    private int score;
    
    @JsonProperty("num_comments")
    private int numberOfComments;

}
