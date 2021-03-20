package sk.tuke.kpi.kp.colorsudoku.core;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

import static sk.tuke.kpi.kp.colorsudoku.core.TileColor.*;

public class Field {


    private GameState gameState = GameState.PLAYING;
    private final Tile[][] finalTiles;
    private final Tile[][] gameTiles;
    private final int difficulty;
    private final int FIELD_DIMENSION = 9;
    private int numberOfFilledTiles;
    private int numberOfHints;
    private long startMillis;



    public Field(int difficulty) {
        this.difficulty = difficulty;
        finalTiles = new Tile[FIELD_DIMENSION][FIELD_DIMENSION];
        gameTiles = new Tile[FIELD_DIMENSION][FIELD_DIMENSION];
        setNumberOfFilledTiles(81);
        if(difficulty > 3) {
            throw new IllegalArgumentException("Unsupported game difficulty");
        }
        generate();
    }



    private void generate(){

        generateFinalField();
        setDifficulty(difficulty);
        startMillis = System.currentTimeMillis();
    }

    private void generateFinalField(){
        for (int i = 0; i < FIELD_DIMENSION; i++){
            for (int j = 0; j < FIELD_DIMENSION; j++){
                finalTiles[i][j] = new EmptyTile();
                addColorsToField( i, j);
                if (finalTiles[i][j].getTileColor()==WHITE){
                    j=0;
                }
            }
        }
    }

    private void addColorsToField(int i, int j){
        TileColor tileColor;
        List<TileColor> colorPalette= Arrays.asList(TileColor.values());
        TileColor.getRandom();
        List<TileColor> listOfColors= checkField(finalTiles, i, j);

        List<TileColor> listOfColorsWithoutDuplicates = listOfColors.stream()
                .distinct()
                .collect(Collectors.toList());

        List<TileColor> missingColors = new ArrayList<>(colorPalette);
        missingColors.removeAll(listOfColorsWithoutDuplicates);

        if (missingColors.size() == 0 ){
            missingColors.add(WHITE);
        }

        tileColor = TileColor.getRandomFromList(missingColors);
        TileColor firstColor = TileColor.getRandomFromList(missingColors);

        if (j == 9){
            firstColor = tileColor;
        }
        if (j == 0 && i > 0){
            tileColor = firstColor;
        }

        listOfColors.clear();
        listOfColorsWithoutDuplicates.clear();
        missingColors.clear();
        finalTiles[i][j] = new FilledTile(tileColor);
        gameTiles[i][j] = new FilledTile(tileColor);
    }

    private @NotNull List<TileColor> checkField(Tile[][] tiles, int row, int column){
        List<TileColor> colors;
        colors = getColorsFromRow(tiles, row, column);
        colors.addAll(getColorsFromColumn(tiles, row, column, colors));
        colors.addAll(getColorsFromBox(tiles, row, column, colors));
        return colors;
    }

    private @NotNull List<TileColor> getColorsFromRow(Tile[][] tiles, int row, int column){
        List<TileColor> colors = new ArrayList<>();
        for (int i = 0; i <= column; i++){
            colors.add(tiles[row][i].getTileColor());
        }
        return colors;
    }

    private List<TileColor> getColorsFromColumn(Tile[][] tiles, int row, int column, List<TileColor> colors){
        for (int i = 0; i <= row; i++){
            colors.add(tiles[i][column].getTileColor());
        }
        return colors;
    }

    private List<TileColor> getColorsFromBox(Tile[][] tiles, int row, int column, List<TileColor> colors){
        int x = (row / 3) * 3;
        int y = (column / 3) * 3;
        for (int i = x; i <= x + 2; i++){
            for (int j = y; j <= y + 2; j++){
                if (tiles[i][j] != null){
                    colors.add(tiles[i][j].getTileColor());
                }
            }
        }
        return colors;
    }

    public int getDifficulty() {
        return difficulty;
    }

    private void setDifficulty(int difficulty){

        int minimumWhiteTiles;
        int maximumWhiteTiles;

        if (difficulty == 1) {
            minimumWhiteTiles = 3;
            maximumWhiteTiles = 5;
            setNumberOfHints(5);
            hideColors(minimumWhiteTiles,maximumWhiteTiles);
        }
        if (difficulty == 2){
            minimumWhiteTiles = 4;
            maximumWhiteTiles = 6;
            setNumberOfHints(4);
            hideColors(minimumWhiteTiles,maximumWhiteTiles);
        }
        if (difficulty == 3){
            minimumWhiteTiles = 5;
            maximumWhiteTiles = 7;
            setNumberOfHints(3);
            hideColors(minimumWhiteTiles,maximumWhiteTiles);
        }
    }

    private void hideColors(int minimumWhiteTiles, int maximumWhiteTiles){
        ArrayList<Integer> listOfColumnNumbers = new ArrayList<>();
        for (int i = 0; i < FIELD_DIMENSION; i++){
            listOfColumnNumbers.add(i);
        }
        for (int i = 0; i < FIELD_DIMENSION; i++) {
            int randomNumFromRange = minimumWhiteTiles + (int) (Math.random() * ((maximumWhiteTiles - minimumWhiteTiles) + 1));
            Collections.shuffle(listOfColumnNumbers);
            setNumberOfFilledTiles(getNumberOfFilledTiles() - randomNumFromRange);
            for (int j = 0; j < randomNumFromRange; j++) {
                gameTiles[i][listOfColumnNumbers.get(j)] = new EmptyTile();
            }
        }
    }

    public GameState getGameState() {
        return gameState;
    }

    private boolean isSolved(){
            int counter = 0;
            for (int i = 0; i < FIELD_DIMENSION; i++) {
                for (int j = 0; j < FIELD_DIMENSION; j++) {
                    if (gameTiles[i][j].getTileColor() == finalTiles[i][j].getTileColor()){
                        counter++;
                    }
                    else return false;
                }
            }
            return counter == 81;
    }

    public int fillTile(int row, int column, TileColor color){
        if (gameState == GameState.PLAYING){
            final Tile tile = gameTiles[row][column];
            if (tile.getTileState() == TileState.EMPTY || tile.getTileState() == TileState.NOTED){
                if (color == finalTiles[row][column].getTileColor()){
                    gameTiles[row][column] = new FilledTile(color);
                    setNumberOfFilledTiles(getNumberOfFilledTiles() + 1);
                }
                if (gameTiles[row][column].getTileColor() != finalTiles[row][column].getTileColor()){
                    setNumberOfHints(getNumberOfHints() - 1);
                    if (getNumberOfHints() == 0){
                        gameState = GameState.FAILED;
                    }
                    return 1; // 1 - filled wrong color

                }
                if (isSolved())
                    gameState = GameState.SOLVED;
            }
            else {
                setNumberOfHints(getNumberOfHints()-1);
                if (getNumberOfHints() == 0){
                    gameState = GameState.FAILED;
                }
                return 3; // 3 - trying to fill already filled tile
            }
            return 0; // filled color successfully
        }
        return 2; //error
    }

    public int getNumberOfHints() {
        return numberOfHints;
    }

    private void setNumberOfHints(int numberOfHints) {
        this.numberOfHints = numberOfHints;
    }

    public int getNumberOfFilledTiles() {
        return numberOfFilledTiles;
    }

    private void setNumberOfFilledTiles(int numberOfFilledTiles) {
        this.numberOfFilledTiles = numberOfFilledTiles;
    }

    public Tile getGameTile(int row, int column){
        return gameTiles[row][column];
    }

    public Tile[][] getFinalField(){
        return finalTiles;
    }

    private int getPlayingTime(){
        return ((int) (System.currentTimeMillis() - startMillis)) / 1000;
    }

    public int getScore(){
        return 4000 - getPlayingTime();
    }

}
