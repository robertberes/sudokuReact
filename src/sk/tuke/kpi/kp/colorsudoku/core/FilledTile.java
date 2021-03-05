package sk.tuke.kpi.kp.colorsudoku.core;


public class FilledTile extends Tile{
    private TileColor tileColor;
    private TileState tileState;
    public FilledTile(TileColor tileColor) {
        super();
        this.setTileColor(tileColor);
    }

    @Override
    public TileState getTileState() {
        return tileState;
    }

    @Override
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
