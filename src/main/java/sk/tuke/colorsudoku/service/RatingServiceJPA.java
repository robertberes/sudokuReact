package sk.tuke.colorsudoku.service;

import sk.tuke.colorsudoku.entity.Rating;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;


@Transactional
public class RatingServiceJPA implements RatingService{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void setRating(Rating rating) throws ScoreException{
        entityManager.persist(rating);
    }

    @Override
    public int getAverageRating(String game) throws RatingException {
        return (int) Math.round((Double) entityManager.createQuery("select avg(rating) from Rating where game like :game").setParameter("game", game).getSingleResult());
    }

    @Override
    public int getRating(String game, String player) throws RatingException {
        return entityManager.createQuery("select rating from Rating where player like :player").setParameter("player", player).getFirstResult();
    }

    @Override
    public void reset() {
        entityManager.createNativeQuery("delete from rating").executeUpdate();
    }
}
