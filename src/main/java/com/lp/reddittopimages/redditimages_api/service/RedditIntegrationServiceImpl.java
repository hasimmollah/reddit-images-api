
package com.lp.reddittopimages.redditimages_api.service;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.lp.reddittopimages.redditimages_api.constants.CommonConstants;
import com.lp.reddittopimages.redditimages_api.exception.RedditImageNotFoundException;
import com.lp.reddittopimages.redditimages_api.exception.RedditDataParsingException;
import com.lp.reddittopimages.redditimages_api.exception.RedditIntegrationException;
import com.lp.reddittopimages.redditimages_api.model.Image;


/**
 * class to integrate with reddit service
 * @author hasmolla
 *
 */
@Service
public class RedditIntegrationServiceImpl implements RedditIntegrationService{
    Logger logger = LoggerFactory.getLogger(RedditIntegrationServiceImpl.class);

    @Value("${redditBaseUrl:}")
    private String redditBaseUrl;

    @Value("${finalImageCount:}")
    private int finalImageCount;

    @Autowired
    RedditImageServiceHelper imageComparator;

    private String fetchResponseFromUrl(String url) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = null;
        try {
            final HttpHeaders headers = new HttpHeaders();
            headers.set("User-Agent", "Mozilla");

            final HttpEntity<String> entity = new HttpEntity<String>(
                headers);
            response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        } catch (HttpClientErrorException e) {
            logger.error(e.getMessage(), e);
            if (e.getRawStatusCode() == HttpStatus.NOT_FOUND.value()) {
                throw new RedditImageNotFoundException(
                    e);
            } else {

                throw new RedditIntegrationException(
                    e);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RedditIntegrationException(
                e);
        }
        return response.getBody();

    }

    /**
     * Method to fetch top shared images based on topic.
     *  (non-Javadoc)
     * @see com.lp.reddittopimages.redditimages_api.service.RedditIntegrationService#fetchImages(java.lang.String)
     */
    @Override
    public List<Image> fetchImages(String topic) {
        List<Image> result = null;
        String finalUrl = redditBaseUrl + topic + "/.json";

        JsonParser parser = new JsonParser();

        JsonElement jsonElement = null;
        String outputJson=fetchResponseFromUrl(finalUrl);
        try {
            jsonElement = parser.parse(outputJson);
        } catch (JsonIOException e) {
            logger.error(e.getMessage(), e);
            throw new RedditDataParsingException(
                e);
        } catch (JsonSyntaxException e) {
            logger.error(e.getMessage(), e);
            throw new RedditDataParsingException(
                e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RedditIntegrationException(
                e);
        }
        result = parseJsonData(jsonElement);
        return result;
    }

    private List<Image> parseJsonData(JsonElement jsonElement) {
        List<Image> result = null;
        Image[] images = new Image[finalImageCount];
        boolean isPresentData = false;

        try {
            JsonObject jsonObject = jsonElement.getAsJsonObject();

            JsonArray jsonElementData = jsonObject.get(CommonConstants.DATA).getAsJsonObject()
                .get(CommonConstants.CHILDREN).getAsJsonArray();

            for (JsonElement je : jsonElementData) {
                JsonObject jo = je.getAsJsonObject();
                JsonObject joDataObject = jo.get(CommonConstants.DATA).getAsJsonObject();
                String url = joDataObject.get(CommonConstants.URL).getAsString();
                String postHint = joDataObject.get(CommonConstants.POST_HINT) != null
                    ? joDataObject.get(CommonConstants.POST_HINT).getAsString() : null;
                int score = joDataObject.get(CommonConstants.SCORE) != null
                    ? joDataObject.get(CommonConstants.SCORE).getAsInt() : 0;
                int numberOfComments = joDataObject.get(CommonConstants.NUM_COMMENTS) != null
                    ? joDataObject.get(CommonConstants.NUM_COMMENTS).getAsInt() : 0;

                if (CommonConstants.IMAGE.equalsIgnoreCase(postHint)) {
                    isPresentData = true;

                    Image image = new Image().builder().url(url).score(score)
                        .numberOfComments(numberOfComments)

                        .build()

                    ;
                    imageComparator.updateImages(image, images);
                }

            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RedditDataParsingException(
                e);
        }
        if (isPresentData) {
            result = Arrays.asList(images);
        } else {
            throw new RedditImageNotFoundException(
                "No data found");
        }

        return result;
    }

}
