package sk.tuke.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;
import sk.tuke.app.entity.Rating;

public class RatingServiceRestClient implements RatingService {
    @Value("${remote.server.api}")
    private String url;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void setRating(Rating rating) {
        restTemplate.postForEntity(url + "/rating", rating, Rating.class);
    }

    @Override
    public int getAverageRating(String game)  {
        return restTemplate.getForObject(url + "/rating/" + game + "/average", Integer.class);
    }

    @Override
    public int getRating(String game, String player) {
        return restTemplate.getForObject(url + "/rating/" + game + "/" + player, Integer.class);
    }

    @Override
    public void reset()  {
        throw new UnsupportedOperationException("Not supported via web service");
    }
}
