package sk.tuke.colorsudoku.game.core;

import org.jetbrains.annotations.NotNull;
import java.util.*;
import java.util.stream.Collectors;
import static sk.tuke.colorsudoku.game.core.TileColor.*;

public class Field {


    private GameState gameState = GameState.PLAYING;
    private final Tile[][] finalTiles;
    private final Tile[][] gameTiles;
    private final int FIELD_DIMENSION = 9;
    private final int EASY = 1;
    private final int MEDIUM = 2;
    private final int HARD = 3;
    private final int TEST = 99;
    private int numberOfFilledTiles;
    private int numberOfHints;
    private long startMillis;



    public Field() {
        finalTiles = new Tile[FIELD_DIMENSION][FIELD_DIMENSION];
        gameTiles = new Tile[FIELD_DIMENSION][FIELD_DIMENSION];
        setNumberOfFilledTiles(81);
        generate();
    }



    private void generate(){

        generateFinalField();

    }

    private void generateFinalField(){
        int counter;
        emptyAllTiles();
        outerLoop:
        for (int i = 0; i < FIELD_DIMENSION; i++){
            counter = 0;
            for (int j = 0; j < FIELD_DIMENSION; j++){
                //finalTiles[i][j] = new EmptyTile();
                addColorsToField( i, j);
                if (counter > 15){
                    i = -1;
                    emptyAllTiles();
                    continue outerLoop;
                }
                if (finalTiles[i][j].getTileColor()==WHITE){
                    j=0;
                    counter++;
                }
            }
        }
    }

    private void emptyAllTiles(){
        for (int i = 0; i < FIELD_DIMENSION; i++){
            for (int j = 0; j < FIELD_DIMENSION; j++){
                finalTiles[i][j] = new EmptyTile();
                gameTiles[i][j] = new EmptyTile();
            }
        }
    }

    private void addColorsToField(int row, int column){
        TileColor tileColor;
        List<TileColor> colorPalette = Arrays.asList(TileColor.values());
        TileColor.getRandom();
        List<TileColor> listOfColors = checkField(finalTiles, row, column);

        List<TileColor> listOfColorsWithoutDuplicates = listOfColors.stream()
                .distinct()
                .collect(Collectors.toList());

        List<TileColor> missingColors = new ArrayList<>(colorPalette);
        missingColors.removeAll(listOfColorsWithoutDuplicates);

        if (missingColors.size() == 0 ){
            missingColors.add(WHITE);
        }

        tileColor = TileColor.getRandomFromList(missingColors);


        listOfColors.clear();
        listOfColorsWithoutDuplicates.clear();
        missingColors.clear();
        finalTiles[row][column] = new FilledTile(tileColor);
        gameTiles[row][column] = new FilledTile(tileColor);
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

    public void setDifficulty(int difficulty){

        int minimumWhiteTiles;
        int maximumWhiteTiles;

        if (difficulty == EASY) {
            minimumWhiteTiles = 3;
            maximumWhiteTiles = 5;
            setNumberOfHints(6);
            hideColors(minimumWhiteTiles,maximumWhiteTiles);
        }
        if (difficulty == MEDIUM){
            minimumWhiteTiles = 4;
            maximumWhiteTiles = 6;
            setNumberOfHints(5);
            hideColors(minimumWhiteTiles,maximumWhiteTiles);
        }
        if (difficulty == HARD){
            minimumWhiteTiles = 5;
            maximumWhiteTiles = 7;
            setNumberOfHints(4);
            hideColors(minimumWhiteTiles,maximumWhiteTiles);
        }
        if (difficulty == TEST){ //difficulty for testing
            gameTiles[0][0]= new EmptyTile();
            setNumberOfHints(10);
        }
        startMillis = System.currentTimeMillis();
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

    public void noteTile(int row, int column, TileColor color){
        if (gameState == GameState.PLAYING){
            final Tile tile = gameTiles[row][column];
            if (tile.getTileState() == TileState.EMPTY ) {
                gameTiles[row][column] = new NotedTile(color);
            }
            if (tile.getTileState() == TileState.NOTED){
                NotedTile tile2 = (NotedTile) gameTiles[row][column];
                for (int i=0; i<tile2.getNotedColors().size(); i++){
                    if (color == tile2.getNotedColors().get(i)){
                        tile2.removeColorFromNoted(color);
                    }
                }
                tile2.addColorToNoted(color);
            }
        }
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
        if (numberOfHints == 3){
            return 4000 - getPlayingTime();
        }
        else if (numberOfHints == 4){
            return 3000 - getPlayingTime();
        }
        else if (numberOfHints == 5){
            return 2000 - getPlayingTime();
        }
        else return 1000 - getPlayingTime();


    }



}
