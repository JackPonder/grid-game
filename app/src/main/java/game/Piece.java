package game;

import java.util.Random;

public enum Piece {
    RED, YELLOW, GREEN, BLUE, CYAN, PURPLE, EMPTY;

    @Override
    public String toString() {
        switch (this) {
            case RED:
                return "\033[91mQ\033[0m";
            case YELLOW:
                return "\033[93mR\033[0m";
            case GREEN:
                return "\033[92mS\033[0m";
            case BLUE:
                return "\033[96mX\033[0m";
            case CYAN:
                return "\033[94mY\033[0m";
            case PURPLE:
                return "\033[95mZ\033[0m";
            default:
                return " ";
        }
    }

    public static Piece getRandom() {
        return values()[new Random().nextInt(values().length - 1)];
    }
}
