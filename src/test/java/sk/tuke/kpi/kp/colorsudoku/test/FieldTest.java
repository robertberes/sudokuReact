package sk.tuke.kpi.kp.colorsudoku.test;

import org.junit.jupiter.api.Test;
import sk.tuke.colorsudoku.game.core.*;

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
        assertTrue(fieldWithIncorrectDifficulty.isUnsupportedGameDifficulty());
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
        int startNumberOfMistakes = field.getNumberOfHints();
        int numberOfMistakesAfterWrongFill;
        do {
            randomRow = randomGenerator.nextInt(8);
            randomColumn = randomGenerator.nextInt(8);
        } while (field.getGameTile(randomRow,randomColumn).getTileColor() != TileColor.WHITE);
        try {
            TileColor color = field.getFinalField()[randomRow][randomColumn].getTileColor();
            field.fillTile(randomRow,randomColumn,color);

        } catch (Exception e) {
            e.printStackTrace();
        }
        numberOfMistakesAfterWrongFill = field.getNumberOfHints();
        assertSame(startNumberOfMistakes, numberOfMistakesAfterWrongFill);

    }

/*    @Test
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
    }*/

    @Test
    public void checkNoted(){
        int i;
        int j = 0;
        outerLoop:
        for (i = 0; i < DIMENSIONS; i++) {
            for (j = 0; j < DIMENSIONS; j++) {
                if(field.getGameTile(i, j).getTileColor() == TileColor.WHITE){
                    field.noteTile(i,j,TileColor.GREEN);
                    break outerLoop;
                }
            }
        }
        assertTrue(field.getGameTile(i,j).getTileState()== TileState.NOTED);

    }


}
