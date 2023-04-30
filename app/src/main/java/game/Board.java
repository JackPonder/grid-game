package game;

import java.util.Random;
import java.util.Arrays;

public class Board {
    private Character[][] boardData;
    private static final char[] pieces = {'Q', 'W', 'X', 'Y', 'Z'};

    public Board() {
        this.boardData = new Character[8][8];
        this.fillEmptySpaces();
    }

    @Override
    public String toString() {
        String buffer = String.format("\n%s+\n", new String("+---").repeat(boardData[0].length));
        String output = Arrays.deepToString(boardData);
        output = output.substring(1, output.length() - 1).replace("], ", "]").replace(", ", " | ");
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

        int row = Character.getNumericValue(move.charAt(0)) - 10;
        int col = Character.getNumericValue(move.charAt(1)) - 1;
        Character temp = null;
        switch (move.charAt(2)) {
            case 'u':
                temp = boardData[row - 1][col];
                boardData[row - 1][col] = boardData[row][col];
                break;
            case 'd':
                temp = boardData[row + 1][col];
                boardData[row + 1][col] = boardData[row][col];
                break;
            case 'l':
                temp = boardData[row][col - 1];
                boardData[row][col - 1] = boardData[row][col];
                break;
            case 'r':
                temp = boardData[row][col + 1];
                boardData[row][col + 1] = boardData[row][col];
                break;
        }
        boardData[row][col] = temp;
    }

    public void findMatches() {
        
    }

    public void removePiece(int row, int col) {
        boardData[row][col] = null;
    }

    public void dropPieces() {

    }

    public void fillEmptySpaces() {
        Random rand = new Random();
        for (int i = 0; i < boardData.length; i++) {
            for (int j = 0; j < boardData[i].length; j++) {
                if (boardData[i][j] == null) {
                    boardData[i][j] = pieces[rand.nextInt(pieces.length)];
                }
            }
        }
    }
}
