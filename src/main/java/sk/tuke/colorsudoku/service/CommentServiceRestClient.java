package sk.tuke.colorsudoku.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import sk.tuke.colorsudoku.entity.Comment;
import sk.tuke.colorsudoku.entity.Score;

import java.util.Arrays;
import java.util.List;

public class CommentServiceRestClient implements CommentService{
    private final String url = "http://localhost:8080/api/comment";

    @Autowired
    private RestTemplate restTemplate;
    //private RestTemplate restTemplate = new RestTemplate();

    @Override
    public void addComment(Comment comment) throws CommentException {
        restTemplate.postForEntity(url, comment, Comment.class);
    }

    @Override
    public List<Comment> getComments(String game) throws CommentException {
        return Arrays.asList(restTemplate.getForEntity(url + "/" + game, Comment[].class).getBody());
    }

    @Override
    public void reset() throws CommentException {
        throw new UnsupportedOperationException("Not supported via web service");
    }
}
