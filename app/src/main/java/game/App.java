package game;

import java.util.Scanner;

public class App {
    private static final Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        Board board = new Board();
        int score = 0;
        while (true) {
            updateDisplay(board, score);

            String move;
            do {
                System.out.print("Enter move: ");
                move = App.scan.nextLine();
                if (!board.isValidMove(move)) {
                    System.out.println("Invalid move");
                }
            } while (!board.isValidMove(move));

            board.movePieces(move);
            updateDisplay(board, score);
            sleep(0.5);

            for (Board.Position pos : board.findMatches()) {
                score += 100;
                board.removePiece(pos.row, pos.col);
            }
            updateDisplay(board, score);
            sleep(0.5);

            while (board.containsEmptySpaces()) {
                board.dropPieces();
                updateDisplay(board, score);
                sleep(0.5);
            }
        }
    }

    public static void updateDisplay(Board board, int score) {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.printf("Score: %d\n", score);
        System.out.println(board.toString());
    }

    public static void sleep(double seconds) {
        try {
            Thread.sleep((long)(seconds * 1000));
        } catch (InterruptedException e) {
            
        }
    }
}
