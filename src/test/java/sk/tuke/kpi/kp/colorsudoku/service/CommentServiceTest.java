package sk.tuke.kpi.kp.colorsudoku.service;

import org.junit.jupiter.api.Test;
import sk.tuke.colorsudoku.entity.Comment;
import sk.tuke.colorsudoku.service.*;


import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommentServiceTest {

    private CommentService createService() {
        return new CommentServiceJDBC();
    }

    @Test
    public void testReset() {
        CommentService service = createService();
        service.reset();
        assertEquals(0, service.getComments("color_sudoku").size());
    }

    @Test
    public void testAddComment() {
        CommentService service = createService();
        service.reset();
        Date date = new Date();
        service.addComment(new Comment("color_sudoku", "Jaro", "200", date));

        List<Comment> comments = service.getComments("color_sudoku");

        assertEquals(1, comments.size());

        assertEquals("color_sudoku", comments.get(0).getGame());
        assertEquals("Jaro", comments.get(0).getPlayer());
        assertEquals("200", comments.get(0).getComment());
        assertEquals(date, comments.get(0).getCommentedOn());
    }

    @Test
    public void testAddComment3() {
        CommentService service = createService();
        service.reset();
        Date date = new Date();
        service.addComment(new Comment("color_sudoku", "Jaro", "This is comment1", date));
        service.addComment(new Comment("color_sudoku", "Fero", "This is comment2", date));
        service.addComment(new Comment("color_sudoku", "Jozo", "This is comment3", date));

        List<Comment> comments = service.getComments("color_sudoku");

        assertEquals(3, comments.size());

        assertEquals("color_sudoku", comments.get(0).getGame());
        assertEquals("Jaro", comments.get(0).getPlayer());
        assertEquals("This is comment1", comments.get(0).getComment());
        assertEquals(date, comments.get(0).getCommentedOn());

        assertEquals("color_sudoku", comments.get(1).getGame());
        assertEquals("Fero", comments.get(1).getPlayer());
        assertEquals("This is comment2", comments.get(1).getComment());
        assertEquals(date, comments.get(1).getCommentedOn());

        assertEquals("color_sudoku", comments.get(2).getGame());
        assertEquals("Jozo", comments.get(2).getPlayer());
        assertEquals("This is comment3", comments.get(2).getComment());
        assertEquals(date, comments.get(2).getCommentedOn());
    }

    @Test
    public void testAddComment10() {
        CommentService service = createService();
        for (int i = 0; i < 20; i++)
            service.addComment(new Comment("color_sudoku", "Jaro", "This is comment", new Date()));
        assertEquals(20, service.getComments("color_sudoku").size());
    }

    @Test
    public void testPersistance() {
        CommentService service = createService();
        service.reset();
        service.addComment(new Comment("color_sudoku", "Jaro", "This is comment", new Date()));

        service = createService();
        assertEquals(1, service.getComments("color_sudoku").size());
    }
}

