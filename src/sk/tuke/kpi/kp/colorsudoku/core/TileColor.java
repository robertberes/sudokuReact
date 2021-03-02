package sk.tuke.kpi.kp.colorsudoku.core;

import java.awt.*;

public enum TileColor {
    RED(Color.red),
    ORANGE(Color.orange),
    YELLOW(Color.yellow),
    GREEN(Color.green),
    BLUE(Color.blue),
    MAGENTA(Color.magenta),
    BLACK(Color.black),
    WHITE(Color.white),
    PINK(Color.pink),
    GRAY(Color.gray);

    private final Color color;

    TileColor(Color color){
        this.color=color;
    }

    public static Color getColor() {
        return getRandom().color;
    }

    public static TileColor getRandom(){
        return values()[(int) (Math.random() * values().length)];
    }
}
