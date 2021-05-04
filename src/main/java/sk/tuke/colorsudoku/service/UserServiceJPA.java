package sk.tuke.colorsudoku.service;

import sk.tuke.colorsudoku.entity.Users;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class UserServiceJPA implements UserService{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addUser(Users users) throws UserException {
        entityManager.persist(users);
    }

    @Override
    public String getPassword(String username) throws UserException {
        return ((Users)entityManager.createNamedQuery("Users.getPassword").setParameter("username", username).getSingleResult()).getPassword();
    }

    @Override
    public void reset() {

    }

    @Override
    public List<String> getUsernames() throws UserException {
        return entityManager.createNamedQuery("Users.getUsernames").getResultList();
    }

}
