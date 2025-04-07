package sk.tuke.app.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import sk.tuke.app.entity.Score;
import java.util.List;

@Transactional
public class ScoreServiceJPA implements ScoreService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addScore(Score score) {
        entityManager.persist(score);
    }

    @Override
    public List<Score> getTopScores(String game) {
        return entityManager.createNamedQuery("Score.getTopScores", Score.class)
                .setParameter("game", game)
                .setMaxResults(10)
                .getResultList();
    }

    @Override
    public void reset() {
        throw new UnsupportedOperationException("Not supported via web service");
    }
}