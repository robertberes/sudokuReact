package sk.tuke.kpi.kp.colorsudoku.service;

import org.junit.jupiter.api.Test;
import sk.tuke.colorsudoku.entity.Score;
import sk.tuke.colorsudoku.service.*;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ScoreServiceTest {

    private ScoreService createService() {
        return new ScoreServiceJDBC();
    }

    @Test
    public void testReset() {
        ScoreService service = createService();
        service.reset();
        assertEquals(0, service.getTopScores("color_sudoku").size());
    }

    @Test
    public void testAddScore() {
        ScoreService service = createService();
        service.reset();
        Date date = new Date();
        service.addScore(new Score("color_sudoku", "Jaro", 200, date,"1"));

        List<Score> scores = service.getTopScores("color_sudoku");

        assertEquals(1, scores.size());

        assertEquals("color_sudoku", scores.get(0).getGame());
        assertEquals("Jaro", scores.get(0).getPlayer());
        assertEquals(200, scores.get(0).getPoints());
        assertEquals(date, scores.get(0).getPlayedOn());
    }

    @Test
    public void testAddScore3() {
        ScoreService service = createService();
        service.reset();
        Date date = new Date();
        service.addScore(new Score("color_sudoku", "Jaro", 200, date,"1"));
        service.addScore(new Score("color_sudoku", "Fero", 400, date,"1"));
        service.addScore(new Score("color_sudoku", "Jozo", 100, date,"1"));

        List<Score> scores = service.getTopScores("color_sudoku");

        assertEquals(3, scores.size());

        assertEquals("color_sudoku", scores.get(0).getGame());
        assertEquals("Fero", scores.get(0).getPlayer());
        assertEquals(400, scores.get(0).getPoints());
        assertEquals(date, scores.get(0).getPlayedOn());

        assertEquals("color_sudoku", scores.get(1).getGame());
        assertEquals("Jaro", scores.get(1).getPlayer());
        assertEquals(200, scores.get(1).getPoints());
        assertEquals(date, scores.get(1).getPlayedOn());

        assertEquals("color_sudoku", scores.get(2).getGame());
        assertEquals("Jozo", scores.get(2).getPlayer());
        assertEquals(100, scores.get(2).getPoints());
        assertEquals(date, scores.get(2).getPlayedOn());
    }

    @Test
    public void testAddScore10() {
        ScoreService service = createService();
        for (int i = 0; i < 20; i++)
            service.addScore(new Score("color_sudoku", "Jaro", 200, new Date(),"1"));
        assertEquals(10, service.getTopScores("color_sudoku").size());
    }

    @Test
    public void testPersistance() {
        ScoreService service = createService();
        service.reset();
        service.addScore(new Score("color_sudoku", "Jaro", 200, new Date(),"1"));

        service = createService();
        assertEquals(1, service.getTopScores("color_sudoku").size());
    }
}

