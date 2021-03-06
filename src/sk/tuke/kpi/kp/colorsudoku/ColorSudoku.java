package sk.tuke.kpi.kp.colorsudoku;

import sk.tuke.kpi.kp.colorsudoku.consoleui.ConsoleUI;
import sk.tuke.kpi.kp.colorsudoku.core.Field;

public class ColorSudoku {
    public static void main(String[] args) {
        Field field = new Field(3);
        ConsoleUI ui = new ConsoleUI(field);
    }

}
