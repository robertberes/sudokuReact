package test;

import org.junit.jupiter.api.Test;
import sk.tuke.kpi.kp.colorsudoku.core.Field;
import sk.tuke.kpi.kp.colorsudoku.core.GameState;
import sk.tuke.kpi.kp.colorsudoku.core.Tile;
import sk.tuke.kpi.kp.colorsudoku.core.TileColor;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class FieldTest {
    private final Random randomGenerator = new Random();
    private final Field field;
    private final int DIMENSIONS = 9;

    public FieldTest(){
        int difficulty = randomGenerator.nextInt(2) + 1;
        field = new Field(difficulty);
    }

    @Test
    public void checkGameDifficulty(){
        Field fieldWithIncorrectDifficulty = null;
        int incorrectDifficulty = 4;
        try {
            fieldWithIncorrectDifficulty = new Field(incorrectDifficulty);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertTrue((fieldWithIncorrectDifficulty == null) || (fieldWithIncorrectDifficulty.getDifficulty() <= 3));
    }

    @Test
    public void checkColorCount(){
        int colorCounter = 0;
        for (int i = 0; i < DIMENSIONS; i++){
            for (int j = 0; j < DIMENSIONS; j++){
                if (field.getGameTile(i,j).getTileColor() != TileColor.WHITE){
                    colorCounter++;
                }
            }
        }

        assertEquals(field.getNumberOfFilledTiles(), colorCounter, "Field was initialized incorrectly - " +
                "actual number of filled tiles was different than number returned by method getNumberOfFilledTiles()");
    }

    @Test
    public void sameColorInRowColumnBox(){
        int randomRow;
        int randomColumn;
        do {
            randomRow = randomGenerator.nextInt(8);
            randomColumn = randomGenerator.nextInt(8);
        } while (field.getGameTile(randomRow,randomColumn).getTileColor() != TileColor.WHITE);
        try {
            field.fillTile(randomRow,randomColumn,TileColor.RED);
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertSame(field.getGameState(), GameState.FAILED);

    }

    @Test
    public void checkSolved(){
        Tile[][] finalField = field.getFinalField();
        while (field.getGameState() != GameState.SOLVED){
            for (int i = 0; i < DIMENSIONS; i++) {
                for (int j = 0; j < DIMENSIONS; j++) {
                    if (field.getGameTile(i, j).getTileColor() == TileColor.WHITE) {
                        field.fillTile(i, j, finalField[i][j].getTileColor());
                    }
                }
            }
        }
        assertSame(field.getGameState(), GameState.SOLVED);
    }


}
