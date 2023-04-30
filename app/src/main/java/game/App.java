package game;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        Board board = new Board();
        while (true) {
            clearConsole();
            System.out.println(board.toString());

            String move;
            do {
                System.out.print("Enter move: ");
                move = scan.nextLine();                
            } while (!board.isValidMove(move));
            board.movePieces(move);
        }
    }

    public static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
