package sk.tuke.kpi.kp.colorsudoku.service;

import org.junit.jupiter.api.Test;
import sk.tuke.kpi.kp.colorsudoku.entity.Rating;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class RatingServiceTest {

    private RatingService createService() {
        return new RatingServiceJDBC();
    }

    @Test
    public void testReset() {
        RatingService service = createService();
        service.reset();
        assertEquals(0, service.getAverageRating("color_sudoku"));
    }

    @Test
    public void testAddRating() {
        RatingService service = createService();
        service.reset();
        Date date = new Date();
        service.setRating(new Rating("color_sudoku", "Jaro", 5, date));

        int averageRating = service.getAverageRating("color_sudoku");
        assertEquals(5, averageRating);

        assertEquals(5, service.getRating("color_sudoku", "Jaro"));

    }

    @Test
    public void testAddRating3() {
        RatingService service = createService();
        service.reset();
        Date date = new Date();
        service.setRating(new Rating("color_sudoku", "Jaro", 5, date));
        service.setRating(new Rating("color_sudoku", "Fero", 3, date));
        service.setRating(new Rating("color_sudoku", "Jozo", 4, date));

        int averageRating = service.getAverageRating("color_sudoku");

        assertEquals(4, averageRating);

    }

    @Test
    public void testAddRatingWithSamePlayer() {
        RatingService service = createService();
        service.reset();
        service.setRating(new Rating("color_sudoku", "Robo", 2, new Date()));
        service.setRating(new Rating("color_sudoku", "Robo", 4, new Date()));

        assertEquals(4, service.getAverageRating("color_sudoku"));
    }

    @Test
    public void testPersistance() {
        RatingService service = createService();
        service.reset();
        service.setRating(new Rating("color_sudoku", "Jaro", 5, new Date()));

        service = createService();
        assertEquals(5, service.getAverageRating("color_sudoku"));
    }
}
