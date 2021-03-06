package sk.tuke.kpi.kp.colorsudoku.core;



public class EmptyTile extends Tile{
    private TileState tileState;
    private TileColor tileColor;
    public EmptyTile() {
        tileState = TileState.EMPTY;
        tileColor = TileColor.WHITE;
    }

    @Override
    public TileState getTileState() {
        return tileState;
    }


    @Override
    public TileColor getTileColor() {
        return tileColor;
    }
}
