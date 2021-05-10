package sk.tuke.colorsudoku.server.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sk.tuke.colorsudoku.entity.Score;
import sk.tuke.colorsudoku.service.ScoreService;

import java.util.List;

@RestController
@RequestMapping("/api/score")
public class ScoreServiceRest {

    @Autowired
    private ScoreService scoreService;

    @GetMapping("/{game}")
    public List<Score> getTopScores(@PathVariable String game) {
        return scoreService.getTopScores(game);
    }

    @PostMapping
    public void addScore(@RequestBody Score score) {
        scoreService.addScore(score);
    }

    @GetMapping("/{game}/{difficulty}")
    public List<Score> getTopScoresDiff(@PathVariable String game, @PathVariable String difficulty){
        return scoreService.getTopScoresDiff(game, difficulty);
    }
}
