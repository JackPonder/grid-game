package game;

import java.util.Random;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.LinkedList;

public class Board {
    private char[][] board;
    private char[] pieces;
    private char empty;

    public Board(int height, int width) {
        this.board = new char[height][width];
        this.pieces = new char[] {'Q', 'R', 'S', 'X', 'Y', 'Z'};
        this.empty = ' ';

        Random random = new Random();
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                board[row][col] = pieces[random.nextInt(pieces.length)];
            }
        }
        Position[] matches;
        while ((matches = findMatches()).length != 0) {
            for (Position pos : matches) {
                board[pos.row][pos.col] = pieces[random.nextInt(pieces.length)];
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
        String buffer = String.format("\n%s+\n", new String("+---").repeat(board[0].length));
        StringBuilder output = new StringBuilder(buffer);
        for (int row = 0; row < board.length; row++) {
            char rowLabel = (char) ('1' + row);
            for (int col = 0; col < board[row].length; col++) {
                output.append(String.format("| %s ", board[row][col]));
            }
            output.append(String.format("| %s%s", rowLabel, buffer));
        }
        for (int col = 0; col < board[0].length; col++) {
            char colLabel = (char) ('A' + col);
            output.append(String.format("  %s ", colLabel));
        }
        output.append("\n");
        return output.toString();
    }

    public boolean isValidMove(String move) {
        if (move.length() != 3) {
            return false;
        }

        move = move.toLowerCase();
        LinkedList<Character> validRows = new LinkedList<>(), validCols = new LinkedList<>();
        for (int row = 0; row < board.length; row++) {
            validRows.add((char)('1' + row));
        }
        for (int col = 0; col < board[0].length; col++) {
            validCols.add((char)('a' + col));
        }

        if (!validCols.contains(move.charAt(0))) {
            return false;
        } if (!validRows.contains(move.charAt(1))) {
            return false;
        } if (!Arrays.asList('u', 'd', 'l', 'r').contains(move.charAt(2))) {
            return false;
        }

        if (validCols.getFirst().equals(move.charAt(0)) && move.charAt(2) == 'l') {
            return false;
        } if (validCols.getLast().equals(move.charAt(0)) && move.charAt(2) == 'r') {
            return false;
        } if (validRows.getFirst().equals(move.charAt(1)) && move.charAt(2) == 'u') {
            return false;
        } if (validRows.getLast().equals(move.charAt(1)) && move.charAt(2) == 'd') {
            return false;
        }

        return true;
    }

    public void movePieces(String move) {
        int col = Character.getNumericValue(move.charAt(0)) - 10;
        int row = Character.getNumericValue(move.charAt(1)) - 1;
        char temp = empty;
        switch (move.charAt(2)) {
            case 'u':
                temp = board[row - 1][col];
                board[row - 1][col] = board[row][col];
                break;
            case 'd':
                temp = board[row + 1][col];
                board[row + 1][col] = board[row][col];
                break;
            case 'l':
                temp = board[row][col - 1];
                board[row][col - 1] = board[row][col];
                break;
            case 'r':
                temp = board[row][col + 1];
                board[row][col + 1] = board[row][col];
                break;
        }
        board[row][col] = temp;
    }

    public Position[] findMatches() {
        Position[][] possibleMatches = {
            {new Position(0, 0), new Position(1, 0), new Position(2, 0)},
            {new Position(0, 0), new Position(0, 1), new Position(0, 2)},
        };

        ArrayList<Position> matches = new ArrayList<>();
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                final int r = row, c = col;
                for (Position[] positions : possibleMatches) {
                    try {
                        if (Arrays.stream(positions).allMatch(p -> board[r][c] == board[r + p.row][c + p.col])) {
                            for (Position p : positions) matches.add(new Position(r + p.row, c + p.col));
                        }
                    } catch (IndexOutOfBoundsException e) {}
                }
            }
        }
        return matches.toArray(new Position[0]);
    }

    public void removePiece(int row, int col) {
        board[row][col] = empty;
    }

    public void dropPieces() {
        for (int row = board.length - 1; row > 0; row--) {
            for (int col = 0; col < board[row].length; col++) {
                if (board[row][col] != empty) continue;
                board[row][col] = board[row - 1][col];
                board[row - 1][col] = empty;    
            }
        }
        Random random = new Random();
        for (int col = 0; col < board[0].length; col++) {
            if (board[0][col] == empty) {
                board[0][col] = pieces[random.nextInt(pieces.length)];
            }
        }
    }

    public boolean containsEmptySpaces() {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                if (board[row][col] == empty) return true;
            }
        }
        return false;
    }
}
