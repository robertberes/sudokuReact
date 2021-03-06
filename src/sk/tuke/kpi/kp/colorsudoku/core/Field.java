package sk.tuke.kpi.kp.colorsudoku.core;

import java.util.*;
import java.util.stream.Collectors;

import static sk.tuke.kpi.kp.colorsudoku.core.TileColor.*;

public class Field {


    private GameState gameState = GameState.PLAYING;
    private final Tile[][] finalTiles;
    private Tile[][] gameTiles;
    private int difficulty;
    private final int FIELD_DIMENSION = 9;
    int numberOfFilledTiles;


    public Field(int difficulty) {
        this.difficulty = difficulty;
        finalTiles = new Tile[FIELD_DIMENSION][FIELD_DIMENSION];
        gameTiles = new Tile[FIELD_DIMENSION][FIELD_DIMENSION];
        numberOfFilledTiles = 81;
        generate();
    }



    public void generate(){

        generateFinalField();
        setDifficulty(difficulty);
        for (int i = 0; i < FIELD_DIMENSION; i++) {
            for (int j = 0; j < FIELD_DIMENSION; j++) {
                char s = getColorChar(gameTiles[i][j].getTileColor());
                System.out.print(s + " ");
            }
            System.out.println();
        }


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
    }

    private List<TileColor> checkField(Tile[][] tiles, int row, int column){
        List<TileColor> colors;
        colors = getColorsFromRow(tiles, row, column);
        colors.addAll(getColorsFromColumn(tiles, row, column, colors));
        colors.addAll(getColorsFromBox(tiles, row, column, colors));
        return colors;
    }

    private List<TileColor> getColorsFromRow(Tile[][] tiles, int row, int column){
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
        gameTiles = finalTiles;
        int minimumWhiteTiles;
        int maximumWhiteTiles;

        if (difficulty == 1) {
            minimumWhiteTiles = 3;
            maximumWhiteTiles = 5;
            hideColors(minimumWhiteTiles,maximumWhiteTiles);

        }
        if (difficulty == 2){
            minimumWhiteTiles = 4;
            maximumWhiteTiles = 6;
            hideColors(minimumWhiteTiles,maximumWhiteTiles);
        }
        if (difficulty == 3){
            minimumWhiteTiles = 5;
            maximumWhiteTiles = 7;
            hideColors(minimumWhiteTiles,maximumWhiteTiles);
        }
    }

    public void hideColors(int minimumWhiteTiles, int maximumWhiteTiles){
        ArrayList<Integer> listOfColumnNumbers = new ArrayList<>();
        for (int i = 0; i < FIELD_DIMENSION; i++){
            listOfColumnNumbers.add(i);
        }
        for (int i = 0; i < FIELD_DIMENSION; i++) {
            int randomNumFromRange = minimumWhiteTiles + (int) (Math.random() * ((maximumWhiteTiles - minimumWhiteTiles) + 1));
            Collections.shuffle(listOfColumnNumbers);
            numberOfFilledTiles -= randomNumFromRange;
            for (int j = 0; j < randomNumFromRange; j++) {
                gameTiles[i][listOfColumnNumbers.get(j)] = new EmptyTile();
            }
        }
    }

    public Tile[][] getFinalTiles() {
        return finalTiles;
    }

    public GameState getGameState() {
        return gameState;
    }

    public boolean isSolved(){
        return numberOfFilledTiles == 81;
    }

    public void fillTile(int row, int column, TileColor color){
        if (gameState == GameState.PLAYING){
            final Tile tile = gameTiles[row][column];
            if (tile.getTileState() == TileState.EMPTY){
                gameTiles[row][column] = new FilledTile(color);
                numberOfFilledTiles++;
                if (gameTiles[row][column].getTileColor() == finalTiles[row][column].getTileColor()){
                    gameState = GameState.FAILED;
                    return;
                }
                if (isSolved())
                    gameState = GameState.SOLVED;
            }
        }
    }
}
