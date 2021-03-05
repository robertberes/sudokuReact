package sk.tuke.kpi.kp.colorsudoku.core;

import java.awt.*;
import java.util.List;
import java.util.Random;

public enum TileColor {
    RED(Color.red),
    ORANGE(Color.orange),
    YELLOW(Color.yellow),
    GREEN(Color.green),
    CYAN(Color.cyan),
    MAGENTA(Color.magenta),
    BLACK(Color.black),
    PINK(Color.pink),
    DARKGRAY(Color.darkGray),
    WHITE(Color.white);

    private final Color color;

    TileColor(Color color){
        this.color=color;
    }

    public Color getColor() {
        return color;
    }

    public static TileColor getRandom(){
        int randomValue;
        do {
            randomValue = (int) (Math.random() * (values().length));
        } while (randomValue > 8);
        return values()[randomValue];
    }

    public static TileColor getRandomFromList(List<TileColor> palette){
        Random random = new Random();
        return palette.get(random.nextInt(palette.size()));
    }

    public static char getColorChar(TileColor tileColor) {
        if (tileColor == TileColor.BLACK){
            return 'B';
        }
        else if (tileColor == TileColor.CYAN){
            return 'C';
        }
        else if (tileColor == TileColor.DARKGRAY){
            return 'D';
        }
        else if (tileColor == TileColor.GREEN){
            return 'G';
        }
        else if (tileColor == TileColor.MAGENTA){
            return 'M';
        }
        else if (tileColor == TileColor.ORANGE){
            return 'O';
        }
        else if (tileColor == TileColor.PINK){
            return 'P';
        }
        else if (tileColor == TileColor.RED){
            return 'R';
        }
        else if (tileColor == TileColor.YELLOW){
            return 'Y';
        }
        else {
            return 'W';
        }
    }
}
