package sk.tuke.kpi.kp.colorsudoku.core;

import java.awt.*;

public class Field {
    private Tile[][] tiles;
    private final int rowCount = 9;
    private final int columnCount = 9;
    private int difficulty;

    public Field(int difficulty) {
        this.difficulty = difficulty;
        generate(difficulty);
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public void generate(int difficulty){
        for (int i=0; i < rowCount; i++){
            for (int j=0; j< columnCount; j++){
                Color tileColor = TileColor.getColor();
                tiles[i][j]= new Tile(tileColor);
            }
        }
    }

    public Tile[][] getTiles() {
        return tiles;
    }
}
