package sk.tuke.colorsudoku.game.consoleui;

import org.springframework.beans.factory.annotation.Autowired;
import sk.tuke.colorsudoku.game.core.Field;
import sk.tuke.colorsudoku.game.core.GameState;
import sk.tuke.colorsudoku.game.core.Tile;
import sk.tuke.colorsudoku.game.core.TileColor;
import sk.tuke.colorsudoku.entity.Comment;
import sk.tuke.colorsudoku.entity.Rating;
import sk.tuke.colorsudoku.entity.Score;
import sk.tuke.colorsudoku.service.*;

import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConsoleUI {

    private static final String GAME_NAME = "color_sudoku";
    private Field field;
    private static final Pattern INPUT_PATTERN = Pattern.compile("([1-9])([A-I])([BCDGMOPRY])");
    private final int FIELD_DIMENSION = 9;

    //private final ScoreService scoreService1 = new ScoreServiceJDBC();
    //private final CommentService commentService = new CommentServiceJDBC();
    //private final RatingService ratingService = new RatingServiceJDBC();

    @Autowired
    private ScoreService scoreService;
    @Autowired
    private RatingService ratingService;
    @Autowired
    private CommentService commentService;

    public ConsoleUI(Field field, ScoreService scoreService, RatingService ratingService, CommentService commentService){
        this.scoreService = scoreService;
        this.commentService = commentService;
        this.ratingService = ratingService;
        this.field = field;
    }

    public ConsoleUI(Field field){
        this.field = field;
    }

    public void play(){
        Scanner scannerYesNo;
        Scanner input1 = new Scanner(System.in);
        char yesNo;
        System.out.println("Welcome to Color Sudoku please choose difficulty: ");
        System.out.print("1 - Easy, 2 - Medium, 3 - Hard: ");
        int i = input1.nextInt();
        while ((i < 1 || i > 3) && i != 99){
            System.out.println("Chosen wrong difficulty. Choose again: ");
            i = input1.nextInt();
        }
        System.out.println("Format for filling the sudoku XYC where X=1-9, Y=A-H and \n C= B,C,D,G,M,O,P,R,Y   type 'X' for exit");
        field.setDifficulty(i);
        do {
            printField();
            processInput();
        } while (field.getGameState() == GameState.PLAYING);
        printField();
        if (field.getGameState() == GameState.SOLVED){
            System.out.println("You solved the sudoku");
            scoreService.addScore(
                    new Score(GAME_NAME, System.getProperty("user.name"), field.getScore(), new Date())
            );
        }
        else {
            System.out.println("You failed");
            scoreService.addScore(
                    new Score(GAME_NAME, System.getProperty("user.name"), 0, new Date())
            );
        }

        System.out.print("Would you like to add a comment? [Y/N]: ");
        if (yesOrNo()){
            System.out.print("Add comment (max. 1024 chars.): ");
            String commentScanner = new Scanner(System.in).nextLine().trim();
            while (commentScanner.length()>1024){
                System.out.print("Maximum limit exceeded. Please type comment again(max. 1024 chars.): ");
            }
            commentService.addComment(
                    new Comment(GAME_NAME, System.getProperty("user.name"), commentScanner, new Date())
            );
        }

        System.out.print("Would you like to rate this game? [Y/N]: ");
        if (yesOrNo()){
            System.out.print("Set rating 1-5(very bad - best): ");
            Scanner input = new Scanner(System.in);
            int rating = input.nextInt();
            while (rating < 1 || rating > 5){
                System.out.print("Rating out of range. Please set rating again[1-5]: ");
                rating = input.nextInt();
            }
            ratingService.setRating(
                    new Rating(GAME_NAME, System.getProperty("user.name"), rating, new Date())
            );
        }
        char inputChar = 'R';
        while (inputChar == 'R' || inputChar == 'C' || inputChar == 'S' || inputChar == 'M'){
            System.out.println("Would you like to see top scores or avg. rating or comments? ");
            System.out.print("Type 'S' for scores, 'R' for rating, 'C' for comments, 'M' for your rating or any other char. for exit: ");
            Scanner input = new Scanner(System.in);
            inputChar = input.next(".").charAt(0);
            inputChar = Character.toUpperCase(inputChar);
            getDataFromDatabase(inputChar);

        }

        do {
            System.out.println("Would you like to play another game? [Y/N]");
            scannerYesNo = new Scanner(System.in);
            yesNo = scannerYesNo.next(".").charAt(0);
            yesNo = Character.toUpperCase(yesNo);
        } while (yesNo != 'Y' && yesNo != 'N');

        if (yesNo == 'Y'){
            this.field = new Field();
            play();
        }


    }

    private void getDataFromDatabase(char input){
        if (input == 'S') printScores();
        if (input == 'R') printAverageRating();
        if (input == 'C') printComments();
        if (input == 'M') printRating();
    }

    private boolean yesOrNo(){
        Scanner scannerYesNo = new Scanner(System.in);
        char yesNo = scannerYesNo.next(".").charAt(0);
        yesNo = Character.toUpperCase(yesNo);
        return yesNo == 'Y';
    }

    private void printField(){
        printFieldHeader();
        printFieldBody();
    }

    private void printFieldHeader(){
        System.out.println("Number of hints: " + field.getNumberOfHints());
        System.out.print(" ");
        for (int column = 0; column < FIELD_DIMENSION; column++){
            System.out.print(" ");
            System.out.print((char) ('A' + column));
            if (column == 2 || column == 5)
                System.out.print("  ");
        }
        System.out.println();
    }

    private void printFieldBody(){
        for (int row = 0; row < FIELD_DIMENSION; row++){
            System.out.print(row + 1);
            for (int column = 0; column < FIELD_DIMENSION; column++){
                System.out.print(" ");
                printTile(row, column);
                if (column == 2 || column == 5)
                    System.out.print(" |");
            }
            if (row == 2 || row == 5){
                System.out.println();
                for (int i = 0; i <= FIELD_DIMENSION+2; i++){
                    System.out.print("_ ");
                }
            }
            System.out.println();
        }
    }

    private void printTile(int row, int column){
        final Tile tile = field.getGameTile(row, column);
        switch (tile.getTileColor()){
            case BLUE:
                System.out.print("\u001B[34m" + "B" + "\u001B[0m");
                break;
            case CYAN:
                System.out.print("\u001B[36m" + "C" + "\u001B[0m");
                break;
            case DARKGRAY:
                System.out.print("\u001b[38;5;240m" + "D" + "\u001B[0m");
                break;
            case GREEN:
                System.out.print("\u001B[32m" + "G" + "\u001B[0m");
                break;
            case MAGENTA:
                System.out.print("\u001B[35m" + "M" + "\u001B[0m");
                break;
            case ORANGE:
                System.out.print("\u001B[38;5;208m" + "O" + "\u001B[0m");
                break;
            case PINK:
                System.out.print("\u001B[95m" + "P" + "\u001B[0m");
                break;
            case RED:
                System.out.print("\u001B[31m" + "R" + "\u001B[0m");
                break;
            case YELLOW:
                System.out.print("\u001B[38;5;226m" + "Y" + "\u001B[0m");
                break;
            case WHITE:
                System.out.print("W");
                break;
            default:
                throw new IllegalArgumentException("Unsupported tile color " + tile.getTileColor());
        }
    }

    protected void processInput(){
        boolean correctInput = false;
        while (!correctInput){

            System.out.print("Enter input (e.g. 1AP, 5DD, X)");
            String input = new Scanner(System.in).nextLine().trim().toUpperCase();

            if ("X".equals(input)){
                System.exit(0);
            }
            Matcher matcher = INPUT_PATTERN.matcher(input);
            if (matcher.matches()){
                try {
                    int row = Integer.parseInt(matcher.group(1)) - 1;
                    int column = matcher.group(2).charAt(0) - 'A';
                    if (row >= 0 && row < FIELD_DIMENSION && column >= 0 && column < FIELD_DIMENSION){
                        int fillHandlerInt;
                        fillHandlerInt = field.fillTile(row,column, TileColor.getColorFromChar(matcher.group(3)));
                        fillHandler(fillHandlerInt);
                        correctInput = true;
                    }
                    //return;

                } catch (NumberFormatException e){
                    System.out.println("Wrong input");
                }
            }
        }
    }

    private void fillHandler(int i){
        if (i == 1){
            System.out.println("Filled wrong color");
        }
        if (i == 2){
            System.out.println("Error");
        }
        if (i == 3){
            System.out.println("Tile already filled");
        }
    }

    private void printScores() {
        List<Score> scores = scoreService.getTopScores(GAME_NAME);

        for (Score score : scores) {
            System.out.printf("%s %d\n", score.getPlayer(), score.getPoints());
        }
    }

    private void printRating(){
        int myRating = ratingService.getRating(GAME_NAME,System.getProperty("user.name"));
        System.out.println(myRating);
    }

    private void printAverageRating(){
        int averageRating = ratingService.getAverageRating(GAME_NAME);
        System.out.println(averageRating);
    }

    private void printComments(){
        List<Comment> comments = commentService.getComments(GAME_NAME);

        for (Comment comment : comments){
            System.out.printf("%s: %s\n", comment.getPlayer(), comment.getComment());
        }
    }

}
