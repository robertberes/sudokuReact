package sk.tuke.kpi.kp.colorsudoku.consoleui;

import sk.tuke.kpi.kp.colorsudoku.core.Field;
import sk.tuke.kpi.kp.colorsudoku.core.GameState;
import sk.tuke.kpi.kp.colorsudoku.core.Tile;
import sk.tuke.kpi.kp.colorsudoku.core.TileColor;
import sk.tuke.kpi.kp.colorsudoku.entity.Comment;
import sk.tuke.kpi.kp.colorsudoku.entity.Rating;
import sk.tuke.kpi.kp.colorsudoku.entity.Score;
import sk.tuke.kpi.kp.colorsudoku.service.*;

import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConsoleUI {

    private static final String GAME_NAME = "color_sudoku";
    private final Field field;
    private static final Pattern INPUT_PATTERN = Pattern.compile("([1-9])([A-I])([BCDGMOPRY])");
    private final int FIELD_DIMENSION = 9;

    private final ScoreService scoreService = new ScoreServiceJDBC();
    private final CommentService commentService = new CommentServiceJDBC();
    private final RatingService ratingService = new RatingServiceJDBC();

    public ConsoleUI(Field field){
        this.field = field;
    }

    public void play(){
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
            commentService.addComment(
                    new Comment(GAME_NAME, System.getProperty("user.name"), commentScanner, new Date())
            );
        }

        System.out.print("Would you like to rate this game? [Y/N]: ");
        if (yesOrNo()){
            System.out.println("Set rating 1-5(very bad - best): ");
            Scanner input = new Scanner(System.in);
            int rating = input.nextInt();
            while (rating < 1 || rating > 5){
                System.out.println("Rating out of range. Please set rating again[1-5]: ");
                rating = input.nextInt();
            }
            ratingService.setRating(
                    new Rating(GAME_NAME, System.getProperty("user.name"), rating, new Date())
            );
        }
        char inputChar = 'R';
        while (inputChar == 'R' || inputChar == 'C' || inputChar == 'S'){
            System.out.println("Would you like to see top scores or avg. rating or comments?");
            System.out.print("Type 'S' for scores, 'R' for rating, 'C' for comments or any other char. for exit: ");
            Scanner input = new Scanner(System.in);
            inputChar = input.next(".").charAt(0);
            inputChar = Character.toUpperCase(inputChar);
            getDataFromDatabase(inputChar);

        }
    }

    private void getDataFromDatabase(char input){
        if (input == 'S') printScores();
        if (input == 'R') printAverageRating();
        if (input == 'C') printComments();
    }

    private boolean yesOrNo(){
        Scanner scannerYesNo = new Scanner(System.in);
        char yesNo = scannerYesNo.next(".").charAt(0);
        yesNo = Character.toUpperCase(yesNo);
        return yesNo == 'Y';
    }

    public void printField(){
        printFieldHeader();
        printFieldBody();
    }

    public void printFieldHeader(){
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

    public void printFieldBody(){
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

    public void printTile(int row, int column){
        final Tile tile = field.getGameTile(row, column);
        switch (tile.getTileColor()){
            case BLACK:
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

    public void fillHandler(int i){
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
