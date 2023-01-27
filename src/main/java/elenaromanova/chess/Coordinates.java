package elenaromanova.chess;

public class Coordinates {
    private int xCoord;
    private int yCoord;
    public Coordinates(String coords) {
        this.yCoord = translateYCoord(coords.charAt(1));
        this.xCoord = translateXCoord(coords.charAt(0));
    }

    private int translateXCoord(char x) {
        return x - 97;
    }

    private int translateYCoord(char y) {
        int num = Integer.parseInt(String.valueOf(y));
        return 8 - num;
    }

    public int getX() {
        return xCoord;
    }

    public int getY() {
        return yCoord;
    }
}
