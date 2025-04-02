package sk.tuke.app.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import sk.tuke.app.entity.Rating;

import java.util.Optional;

@Transactional
public class RatingServiceJPA implements RatingService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void setRating(Rating rating) {
        entityManager.persist(rating);
    }

    @Override
    public int getRating(String game, String player) {
        Integer rating = entityManager.createNamedQuery("Rating.getRating", Integer.class)
                .setParameter("game", game)
                .setParameter("player", player)
                .getSingleResult();
        return Optional.ofNullable(rating).orElse(0);
    }

    @Override
    public int getAverageRating(String game) {
        Double averageRating = entityManager.createNamedQuery("Rating.getAverageRating", Double.class)
                .setParameter("game", game)
                .getSingleResult();

        return Optional.ofNullable(averageRating).map(Double::intValue).orElse(0);
    }

    @Override
    public void reset() {
        entityManager.createNamedQuery("Rating.resetRatings").executeUpdate();
    }
}
