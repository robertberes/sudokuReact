package sk.tuke.kpi.kp.colorsudoku;

import sk.tuke.kpi.kp.colorsudoku.consoleui.ConsoleUI;
import sk.tuke.kpi.kp.colorsudoku.core.Field;
import java.util.Scanner;

public class ColorSudoku {
    public static void main(String[] args) {
        boolean playGame = true;
        while (playGame){
            Scanner input = new Scanner(System.in);
            System.out.println("Welcome to Color Sudoku please choose difficulty: ");
            System.out.print("1 - Easy, 2 - Medium, 3 - Hard: ");
            int i = input.nextInt();
            while ((i < 1 || i > 3) && i != 99){
                System.out.println("Chosen wrong difficulty. Choose again: ");
                i = input.nextInt();
            }
            System.out.println("Format for filling the sudoku XYC where X=1-9, Y=A-H and \n C= B,C,D,G,M,O,P,R,Y   type 'X' for exit");
            Field field = new Field(i);
            ConsoleUI ui = new ConsoleUI(field);
            ui.play();

            System.out.println("Would you like to play another game? [Y/N]");
            Scanner scannerYesNo;
            char yesNo;

            do {
                scannerYesNo = new Scanner(System.in);
                yesNo = scannerYesNo.next(".").charAt(0);
                yesNo = Character.toUpperCase(yesNo);
            } while (yesNo != 'Y' && yesNo != 'N');

            playGame = yesNo == 'Y';
        }


    }

}
