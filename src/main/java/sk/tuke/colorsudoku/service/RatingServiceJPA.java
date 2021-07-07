package sk.tuke.colorsudoku.service;

import sk.tuke.colorsudoku.entity.Rating;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;


@Transactional
public class RatingServiceJPA implements RatingService{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void setRating(Rating rating) throws RatingException{
        entityManager.createNamedQuery("Rating.delRating").setParameter("game", rating.getGame()).setParameter("player", rating.getPlayer()).executeUpdate();
        entityManager.persist(rating);


    }

    @Override
    public int getAverageRating(String game) throws RatingException {
        return (int) Math.round((Double) entityManager.createQuery("select avg(rating) from Rating where game like :game and rating > 0").setParameter("game", game).getSingleResult());
    }

    @Override
    public int getRating(String game, String player) throws RatingException {
        return ((Rating)entityManager.createNamedQuery("Rating.getRating").setParameter("game", game).setParameter("player", player).getSingleResult()).getRating();
    }

    @Override
    public void reset() {
        entityManager.createNativeQuery("delete from rating").executeUpdate();
    }
}
