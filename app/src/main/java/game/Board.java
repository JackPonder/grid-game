package game;

import java.util.Random;
import java.util.Arrays;

public class Board {
    private String[][] board;

    public Board() {
        this.board = new String[8][8];
        Random rand = new Random();
        String[] letters = {"Q", "W", "X", "Y", "Z"};
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = letters[rand.nextInt(letters.length)];
            }
        }
    }

    @Override
    public String toString() {
        String buffer = String.format("\n%s+\n", new String("+---").repeat(board[0].length));
        String output = Arrays.deepToString(board);
        output = output.substring(1, output.length() - 1);
        output = output.replace("], ", "]").replace(", ", " | ");
        return output.replace("[", buffer + "| ").replace("]", " |") + buffer;
    }

    public boolean isValidMove(String move) {
        if (move.length() != 3) {
            return false;
        }
        move = move.toLowerCase();
        if (!Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h').contains(move.charAt(0))) {
            return false;
        } if (!Arrays.asList('1', '2', '3', '4', '5', '6', '7', '8').contains(move.charAt(1))) {
            return false;
        } if (!Arrays.asList('u', 'd', 'l', 'r').contains(move.charAt(2))) {
            return false;
        }
        return true;
    }

    public void movePieces(String move) {
        if (!isValidMove(move)) return;

        int x = Character.getNumericValue(move.charAt(0)) - 10;
        int y = Character.getNumericValue(move.charAt(1)) - 1;
        String temp = "";
        switch (move.charAt(2)) {
            case 'u':
                temp = board[x - 1][y];
                board[x - 1][y] = board[x][y];
                break;
            case 'd':
                temp = board[x + 1][y];
                board[x + 1][y] = board[x][y];
                break;
            case 'l':
                temp = board[x][y - 1];
                board[x][y - 1] = board[x][y];
                break;
            case 'r':
                temp = board[x][y + 1];
                board[x][y + 1] = board[x][y];
                break;
        }
        board[x][y] = temp;
    }

    public void removePiece() {
        
    }

    public void dropPieces() {

    }
}
