package sk.tuke.colorsudoku.server.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;
import sk.tuke.colorsudoku.entity.Score;
import sk.tuke.colorsudoku.game.core.*;
import sk.tuke.colorsudoku.service.CommentService;
import sk.tuke.colorsudoku.service.RatingService;
import sk.tuke.colorsudoku.service.ScoreService;
import sk.tuke.colorsudoku.service.UserService;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/colorsudoku")
@Scope(WebApplicationContext.SCOPE_SESSION)
public class ColorSudokuController {
    @Autowired
    private ScoreService scoreService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private RatingService ratingService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserController userController;

    private Field field;
    private final int FIELD_DIMENSION = 9;
    private final String GAME_NAME = "color_sudoku";
    private boolean noting = false;
    private List colors;
    private String savedColor = null;
    private String stringDifficulty = null;

    @RequestMapping
    public String colorSudoku(@RequestParam(required = false) String color, @RequestParam(required = false) String row,
                              @RequestParam(required = false) String column, Model model, @RequestParam(required = false) String difficulty){
        if (field == null){
            if (difficulty.equals("1") || difficulty.equals("2") || difficulty.equals("3")){
                newGame(difficulty, model);
            }
            else {
                newGame("1", model);
            }


        }
        if (color != null){
            savedColor = color;
        }

        if(row != null && column != null){
            try {
                if (noting){
                    field.noteTile(Integer.parseInt(row), Integer.parseInt(column), TileColor.getTileColor(savedColor));
                }
                else{
                    if (field.getGameState() == GameState.PLAYING) {
                        field.fillTile(Integer.parseInt(row), Integer.parseInt(column),  TileColor.getTileColor(savedColor));
                        if (userController.isLogged() && field.getGameState() == GameState.SOLVED) {
                            scoreService.addScore(new Score(
                                    GAME_NAME,
                                    userController.getLoggedUsers().getUsername(),
                                    field.getScore(),
                                    new Date(),
                                    getStringDifficulty()
                            ));
                        }
                    }

                }

            } catch (Exception e) {

            }
        }
        prepareModel(model, stringDifficulty);
        return "colorsudoku";
    }

    @RequestMapping("/new")
    public String newGame(String difficulty, Model model){
        field = new Field();
        if (difficulty == null){
            difficulty = "1";
        }
        setStringDifficulty(difficulty);
        field.setDifficulty(Integer.parseInt(difficulty));
        prepareModel(model, difficulty);
        return "colorsudoku";
    }

    @RequestMapping("/note")
    public String changeNoting(Model model){
        noting = !noting;
        prepareModel(model, stringDifficulty);
        return "colorsudoku";
    }


    public String getNumberOfHints(){
        int hints = field.getNumberOfHints() - 1;
        if (hints < 0){
            return String.valueOf(0);
        }
        return String.valueOf(hints);
    }

    public String getHtmlField(){
        StringBuilder sb = new StringBuilder();
        sb.append("<table>\n");
        List<TileColor> notedColors;

        for (int row = 0; row < FIELD_DIMENSION; row++) {
            sb.append("<tr>\n");
            for (int column = 0; column < FIELD_DIMENSION; column++) {
                Tile tile = field.getGameTile(row, column);
                notedColors=getNotedColors(tile);
                if (notedColors != null){
                    sb.append("<td align=\"center\" valign=\"middle\" style = \"background= rgb(255, 255, 255) none repeat scroll 0% 0%;\" max-width=\"58px\" bgcolor =\"#FFFFFF\" width=\"58px\">");
                }
                else {
                    sb.append("<td>");
                }

                sb.append(String.format("<a class=\"sudoku\" href='/colorsudoku?row=%d&column=%d'>\n", row, column));
                if (!getImageName(tile).equals("noted")){
                    sb.append("<img id=\"sud\" src='/images/tiles/" + getImageName(tile) + ".png'>");
                }

                if (notedColors != null){
                    for(TileColor temp : notedColors){
                        sb.append("<img src='/images/tiles/" + temp + ".png' width=\"18\" height=\"18\" border=\"none\">");
                    }
                }
                sb.append("</a>\n");
                sb.append("</td>\n");

                if ((column == 2 || column == 5)){
                    sb.append("<td width=\"1\">");
                    sb.append("</td>");
                }
            }
            sb.append("</tr>\n");
            if (row == 2 || row == 5){
                sb.append("<tr>\n");
                for(int column = 0; column < FIELD_DIMENSION; column++){
                    sb.append("<td height=\"2\">");
                    sb.append("</td>");
                }
                sb.append("</tr>\n");
            }
        }

        sb.append("</table>\n");
        return sb.toString();


    }

    private String getImageName(Tile tile) {
        switch (tile.getTileState()){
            case EMPTY:
                return "WHITE";
            case NOTED:
                return "noted";
            case FILLED:
                return tile.getTileColor().toString();
            default:
                throw new IllegalArgumentException("Unsupported tile state " + tile.getTileState());
        }
    }

    private List<TileColor> getNotedColors(Tile tile){
        List<TileColor> notedColors;
        if (tile instanceof NotedTile){
            if (((NotedTile) tile).getNotedColors() == null){
                return null;
            }
            notedColors = ((NotedTile) tile).getNotedColors();
        }
        else {
            getImageName(tile);
            return null;
        }

        return notedColors;
    }

    private void prepareModel(Model model, String diff){

        model.addAttribute("scores", scoreService.getTopScoresDiff(GAME_NAME, diff));
        model.addAttribute("comments", commentService.getComments(GAME_NAME));
        model.addAttribute("rating", ratingService.getAverageRating(GAME_NAME));
        model.addAttribute("myRating", ratingService.getRating(GAME_NAME,userController.getLoggedUsers().getUsername()));

    }

    public int getRating(){
        return ratingService.getAverageRating(GAME_NAME);
    }

    public int getUserRating(){
        return ratingService.getRating(GAME_NAME, userController.getLoggedUsers().getUsername());
    }

    public UserService getUserService() {
        return userService;
    }

    public String getStringDifficulty() {
        return stringDifficulty;
    }

    private void setStringDifficulty(String stringDifficulty) {
        this.stringDifficulty = stringDifficulty;
    }

    public void setSavedColor(String color){
        savedColor = color;
    }

    public List getColors(){
        return colors;
    }

    public boolean isNoting() {
        return noting;
    }

    public GameState getGameState() {
        return field.getGameState();
    }

    public String getScore() {
        return String.valueOf(field.getScore());
    }

    public boolean isSolved(){
        return field.getGameState() == GameState.SOLVED;
    }

    public boolean isFailed() { return field.getGameState() == GameState.FAILED;}
}
