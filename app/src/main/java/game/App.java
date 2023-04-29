package game;

import java.util.Scanner;
import java.util.Random;
import java.util.Arrays;

public class App {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String[][] board = getNewBoard(9, 9);
        while (true) {
            clearConsole();
            System.out.println(displayBoard(board));

            System.out.print("\nEnter move: ");
            scan.nextLine();
        }
    }

    public static String[][] getNewBoard(int height, int width) {
        String board[][] = new String[height][width];
        String[] letters = {"Q", "W", "X", "Y", "Z"};
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = letters[new Random().nextInt(letters.length)];
            }
        }
        return board;
    }

    public static String displayBoard(String[][] board) {
        String output = Arrays.deepToString(board);
        output = output.substring(1, output.length() - 1);
        output = output.replace("], ", "]\n").replace(", ", " | ");
        String buffer = new String("+---").repeat(board[0].length) + "+";
        output = buffer + "\n" + output.replace("[", "| ").replace("]", " |\n" + buffer);
        return output;
    }

    public static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
