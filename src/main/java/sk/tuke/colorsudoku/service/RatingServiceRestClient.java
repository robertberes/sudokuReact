package sk.tuke.colorsudoku.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import sk.tuke.colorsudoku.entity.Rating;


public class RatingServiceRestClient implements RatingService{
    private final String url = "http://localhost:8080/api/rating";

    @Autowired
    private RestTemplate restTemplate;
    //private RestTemplate restTemplate = new RestTemplate();

    @Override
    public void setRating(Rating rating) throws RatingException{
        restTemplate.postForEntity(url , rating, Rating.class);

    }

    @Override
    public int getAverageRating(String gameName) throws RatingException{
        return restTemplate.getForEntity(url + "/" + gameName, Integer.class).getBody();
    }

    @Override
    public int getRating(String gameName, String player) throws RatingException{
        return restTemplate.getForEntity(url + "/" + gameName + "/" + player, Integer.class).getBody();
    }

    @Override
    public void reset() {
        throw new UnsupportedOperationException("Not supported via web service");
    }
}

