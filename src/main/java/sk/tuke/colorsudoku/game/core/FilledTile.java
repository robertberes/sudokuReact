package sk.tuke.colorsudoku.game.core;


public class FilledTile extends Tile{
    public FilledTile(TileColor tileColor) {
        super();
        this.setTileColor(tileColor);
        setTileState(TileState.FILLED);
    }



}
