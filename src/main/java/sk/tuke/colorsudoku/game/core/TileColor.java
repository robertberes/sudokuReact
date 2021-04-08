package sk.tuke.colorsudoku.game.core;

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
    BLUE(Color.blue),
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

    public static TileColor getColorFromChar(String colorChar) {
        int intValueOfChar = colorChar.charAt(0);
        if (intValueOfChar == 66){
            return BLUE;
        }
        else if (intValueOfChar == 67){
            return CYAN;
        }
        else if (intValueOfChar == 68){
            return DARKGRAY;
        }
        else if (intValueOfChar == 71){
            return GREEN;
        }
        else if (intValueOfChar == 77){
            return MAGENTA;
        }
        else if (intValueOfChar == 79){
            return ORANGE;
        }
        else if (intValueOfChar == 80){
            return PINK;
        }
        else if (intValueOfChar == 82){
            return RED;
        }
        else if (intValueOfChar == 89){
            return YELLOW;
        }
        else {
            return WHITE;
        }
    }
}
