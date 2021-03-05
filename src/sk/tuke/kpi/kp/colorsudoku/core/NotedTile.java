package sk.tuke.kpi.kp.colorsudoku.core;

import java.util.ArrayList;
import java.util.List;

public class NotedTile extends Tile{

    private TileColor tileColor;
    private List<TileColor> notedColors = new ArrayList<>();
    private TileState tileState;

    public NotedTile(TileColor tileColor) {
        super();
        this.setTileColor(tileColor);
        addColorToNoted(tileColor);
        tileState = TileState.NOTED;
    }

    private List<TileColor> addColorToNoted(TileColor tileColor){
        notedColors.add(tileColor);
        return notedColors;
    }

    public TileColor getTileColor() {
        return tileColor;
    }

    public void setTileColor(TileColor tileColor) {
        this.tileColor = tileColor;
    }

    public List<TileColor> getNotedColors() {
        return notedColors;
    }

    public void setNotedColors(List<TileColor> notedColors) {
        this.notedColors = notedColors;
    }

    @Override
    public TileState getTileState() {
        return tileState;
    }

    @Override
    public void setTileState(TileState tileState) {
        this.tileState = tileState;
    }
}
