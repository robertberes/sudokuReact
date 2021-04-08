package sk.tuke.colorsudoku.service;

import sk.tuke.colorsudoku.entity.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class CommentServiceJPA implements CommentService{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addComment(Comment comment) throws CommentException {
        entityManager.persist(comment);
    }

    @Override
    public List<Comment> getComments(String game) throws CommentException {
        return entityManager.createQuery("select c from Comment c")
                .getResultList();
    }

    @Override
    public void reset() throws CommentException {
        entityManager.createNativeQuery("delete from comment").executeUpdate();
    }
}
