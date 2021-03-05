package sk.tuke.kpi.kp.colorsudoku.core;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static sk.tuke.kpi.kp.colorsudoku.core.TileColor.*;

public class Field {


    private GameState state = GameState.PLAYING;
    private Tile[][] tiles;
    private final int FIELD_DIMENSION = 9;


    public Field() {
        tiles = new Tile[FIELD_DIMENSION][FIELD_DIMENSION];
        generate();
    }



    public void generate(){

        generateEmptyField();
        //addColorsToField();


    }

    private void generateEmptyField(){
        for (int i = 0; i < FIELD_DIMENSION; i++){
            for (int j = 0; j < FIELD_DIMENSION; j++){
                tiles[i][j] = new EmptyTile();
                addColorsToField( i, j);
                //char s = getColorChar(tiles[i][j].getTileColor());
                if (tiles[i][j].getTileColor()==WHITE){
                    j=0;
                }
//                else {
//                    System.out.print(s + " ");
//                }

            }
//            System.out.println();
        }

        for (int i = 0; i < FIELD_DIMENSION; i++) {
            for (int j = 0; j < FIELD_DIMENSION; j++) {
                char s = getColorChar(tiles[i][j].getTileColor());
                System.out.print(s + " ");
            }
            System.out.println();
        }

    }

    private void addColorsToField(int i, int j){
        TileColor tileColor;
        List<TileColor> colorPalette= Arrays.asList(TileColor.values());
        TileColor.getRandom();
        List<TileColor> listOfColors= checkField(tiles, i, j);
        List<TileColor> listOfColorsWithoutDuplicates = listOfColors.stream()
                .distinct()
                .collect(Collectors.toList());
        List<TileColor> missingColors = new ArrayList<>(colorPalette);
        missingColors.removeAll(listOfColorsWithoutDuplicates);
        if (missingColors.size() == 0 ){
            missingColors.add(WHITE);
        }
        tileColor = TileColor.getRandomFromList(missingColors);
        TileColor firstColor = TileColor.getRandomFromList(missingColors);;
        if (j == 9){
            firstColor = tileColor;
            //System.out.println("x");
        }
        if (j == 0 && i > 0){
            tileColor = firstColor;
        }


        listOfColors.removeAll(listOfColors);
        listOfColorsWithoutDuplicates.removeAll(listOfColorsWithoutDuplicates);
        missingColors.removeAll(missingColors);
        tiles[i][j] = new FilledTile(tileColor);

    }


    private List<TileColor> checkField(Tile tiles[][], int row, int column){
        List<TileColor> colors;
        colors = getColorsFromRow(tiles, row, column);
        getColorsFromColumn(tiles, row, column, colors);
        getColorsFromBox(tiles, row, column, colors);
        return colors;
    }

    private List<TileColor> getColorsFromRow(Tile tiles[][], int row, int column){
        List<TileColor> colors = new ArrayList<>();
        for (int i = 0; i <= column; i++){
            colors.add(tiles[row][i].getTileColor());
        }
        return colors;
    }

    private List<TileColor> getColorsFromColumn(Tile tiles[][], int row, int column, List<TileColor> colors){
        for (int i = 0; i <= row; i++){
            colors.add(tiles[i][column].getTileColor());
        }
        return colors;
    }

    private List<TileColor> getColorsFromBox(Tile tiles[][], int row, int column, List<TileColor> colors){
        int x = (row / 3) * 3;
        int y = (column / 3) * 3;
        for (int i = x; i <= x + 2; i++){
            for (int j = y; j <= y + 2; j++){
                if (tiles[i][j] != null){
                    colors.add(tiles[i][j].getTileColor());
                }
                else {
                    continue;
                }
            }
        }
        return colors;
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public GameState getState() {
        return state;
    }
}
