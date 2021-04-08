package sk.tuke.colorsudoku.game.core;


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

    public void setTileColor(TileColor tileColor) {
        this.tileColor = tileColor;
    }
}
