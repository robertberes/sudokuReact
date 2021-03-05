package sk.tuke.kpi.kp.colorsudoku.core;


public abstract class Tile {
    private TileState tileState;
    private TileColor tileColor;

    public TileState getTileState() {
        return tileState;
    }

    public void setTileState(TileState tileState) {
        this.tileState = tileState;
    }

    public TileColor getTileColor() {
        return tileColor;
    }
}
