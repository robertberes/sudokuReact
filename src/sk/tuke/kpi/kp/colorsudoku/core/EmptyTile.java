package sk.tuke.kpi.kp.colorsudoku.core;

import java.awt.*;

public class EmptyTile extends Tile{
    private Color hiddenColor;
    public EmptyTile(Color tileColor) {
        super(Color.white);
        hiddenColor=tileColor;
    }
}
