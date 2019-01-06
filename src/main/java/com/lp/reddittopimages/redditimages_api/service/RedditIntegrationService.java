
package com.lp.reddittopimages.redditimages_api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lp.reddittopimages.redditimages_api.model.Image;


/**
 * Service interface to integrate reddit service
 * @author hasmolla
 *
 */
@Service
public interface RedditIntegrationService {
    /**
     * Method to fetch top shared images based on topic specified
     * @param topic
     * @return
     */
    public List<Image> fetchImages(String topic);
}
