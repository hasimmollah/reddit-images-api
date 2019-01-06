
package com.lp.reddittopimages.redditimages_api.service;

import org.springframework.stereotype.Component;

import com.lp.reddittopimages.redditimages_api.model.Image;


/**
 * Class to reorder and identify top shared images
 * @author hasmolla
 *
 */
@Component
public class RedditImageServiceHelperImpl implements RedditImageServiceHelper{

    /**
     * Method to reorder and identify top shared images.
     * (non-Javadoc)
     * @see com.lp.reddittopimages.redditimages_api.service.RedditImageServiceHelper#updateImages(com.lp.reddittopimages.redditimages_api.model.Image, com.lp.reddittopimages.redditimages_api.model.Image[])
     */
    @Override
    public void updateImages(Image image, Image[] images) {
        int counter = images.length - 1;
        boolean hastoReorder = true;
        if (images[counter] == null) {
            images[counter] = image;
        } else {
            Image currentImage = images[counter];
            if (compareToSwap(currentImage, image)) {
                images[counter] = image;
            } else {
                hastoReorder = false;
            }
        }
        if (hastoReorder) {
            reorderImages(images);
        }

    }

    private void reorderImages(Image[] images) {
        for (int i = images.length - 1; i > 0; i--) {
            if (images[i - 1] == null) {
                images[i - 1] = images[i];
                images[i] = null;
            } else if (compareToSwap(images[i - 1], images[i])) {
                Image tempImage = images[i - 1];
                images[i - 1] = images[i];
                images[i] = tempImage;
            } else {
                break;
            }

        }
    }

    private boolean compareToSwap(Image image1, Image image2) {
        boolean flag = false;

        if (image2.getScore() > image1.getScore() || (image2.getScore() == image1.getScore()
            && image2.getNumberOfComments() > image1.getNumberOfComments())) {
            flag = true;
        }

        return flag;
    }

}
