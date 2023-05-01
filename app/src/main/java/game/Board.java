package game;

import java.util.Random;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

public class Board {
    private char[][] boardData;
    private char[] pieces;
    private char empty;

    public Board() {
        this.boardData = new char[8][8];
        this.pieces = new char[] {'Q', 'R', 'S', 'X', 'Y', 'Z'};
        this.empty = ' ';

        Random random = new Random();
        for (int row = 0; row < boardData.length; row++) {
            for (int col = 0; col < boardData[row].length; col++) {
                boardData[row][col] = pieces[random.nextInt(pieces.length)];
            }
        }

        Position[] matches;
        while ((matches = findMatches()).length != 0) {
            for (Position pos : matches) {
                boardData[pos.row][pos.col] = pieces[random.nextInt(pieces.length)];
            }           
        }
    }

    public class Position {
        public final int row, col;

        public Position(int row, int col) {
            this.row = row;
            this.col = col;
        }
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

        if (move.charAt(0) == 'a' && move.charAt(2) == 'u') {
            return false;
        } if (move.charAt(0) == 'h' && move.charAt(2) == 'd') {
            return false;
        } if (move.charAt(1) == '1' && move.charAt(2) == 'l') {
            return false;
        } if (move.charAt(1) == '8' && move.charAt(2) == 'r') {
            return false;
        }

        return true;
    }

    public void movePieces(String move) {
        if (!isValidMove(move)) return;

        int row = Character.getNumericValue(move.charAt(0)) - 10;
        int col = Character.getNumericValue(move.charAt(1)) - 1;
        char temp = empty;
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

    public Position[] findMatches() {
        Position[][] possibleMatches = {
            {new Position(0, 0), new Position(1, 0), new Position(2, 0)},
            {new Position(0, 0), new Position(0, 1), new Position(0, 2)},
        };

        List<Position> matches = new ArrayList<>();
        for (int row = 0; row < boardData.length; row++) {
            for (int col = 0; col < boardData[row].length; col++) {
                for (Position[] positions : possibleMatches) {
                    boolean foundMatch = true;
                    for (Position pos : positions) {
                        try {
                            if (boardData[row][col] != boardData[row + pos.row][col + pos.col]) {
                                foundMatch = false;
                                break;
                            }
                        } catch (IndexOutOfBoundsException e) {
                            foundMatch = false;
                        }
                    }
                    if (foundMatch) {
                        for (Position pos : positions) {
                            matches.add(new Position(row + pos.row, col + pos.col));
                        }
                    }

                }
            }
        }
        return matches.toArray(new Position[0]);
    }

    public void removePiece(int row, int col) {
        boardData[row][col] = empty;
    }

    public void dropPieces() {
        for (int row = boardData.length - 1; row > 0; row--) {
            for (int col = 0; col < boardData[row].length; col++) {
                if (boardData[row][col] == empty) {
                    boardData[row][col] = boardData[row - 1][col];
                    boardData[row - 1][col] = empty;
                }
            }
        }
        Random random = new Random();
        for (int col = 0; col < boardData[0].length; col++) {
            if (boardData[0][col] == empty) {
                boardData[0][col] = pieces[random.nextInt(pieces.length)];
            }
        }
    }

    public boolean containsEmptySpaces() {
        for (int row = 0; row < boardData.length; row++) {
            for (int col = 0; col < boardData[row].length; col++) {
                if (boardData[row][col] == empty) return true;
            }
        }
        return false;
    }
}
