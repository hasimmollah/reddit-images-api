
package com.lp.reddittopimages.redditimages_api.service;

import org.springframework.stereotype.Component;

import com.lp.reddittopimages.redditimages_api.model.Image;


/**
 * Interface to help service class to reorder images and identify top shared images
 * @author hasmolla
 *
 */
@Component
public interface RedditImageServiceHelper {

    /**
     * Method to reorder images and identify top shared images
     * @param image
     * @param images
     */
    public void updateImages(Image image, Image[] images) ;
}
