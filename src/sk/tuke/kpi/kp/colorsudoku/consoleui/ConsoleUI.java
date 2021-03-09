package sk.tuke.kpi.kp.colorsudoku.consoleui;

import sk.tuke.kpi.kp.colorsudoku.core.Field;
import sk.tuke.kpi.kp.colorsudoku.core.GameState;
import sk.tuke.kpi.kp.colorsudoku.core.Tile;
import sk.tuke.kpi.kp.colorsudoku.core.TileColor;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConsoleUI {

    private final Field field;
    private static final Pattern INPUT_PATTERN = Pattern.compile("([1-9])([A-I])([BCDGMOPRY])");
    private final int FIELD_DIMENSION = 9;

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
        }
        else {
            System.out.println("You failed");
        }
    }

    public void printField(){
        printFieldHeader();
        printFieldBody();
    }

    public void printFieldHeader(){
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
                System.out.print("B");
                break;
            case CYAN:
                System.out.print("C");
                break;
            case DARKGRAY:
                System.out.print("D");
                break;
            case GREEN:
                System.out.print("G");
                break;
            case MAGENTA:
                System.out.print("M");
                break;
            case ORANGE:
                System.out.print("O");
                break;
            case PINK:
                System.out.print("P");
                break;
            case RED:
                System.out.print("R");
                break;
            case YELLOW:
                System.out.print("Y");
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
}
