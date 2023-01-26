package elenaromanova.chess;

public class Coordinates {
    private int xCoord;
    private int yCoord;
    public Coordinates(String coords) {
        char xRank = coords.charAt(0);
        this.yCoord = Integer.parseInt(String.valueOf(coords.charAt(1)));
        this.xCoord = translateXCoord(coords.charAt(0));
    }

    public int translateXCoord(char x) {
        return x - 97;
    }

    public void setxCoord(int xCoord) {
        this.xCoord = xCoord;
    }

    public int getyCoord() {
        return yCoord;
    }

    public void setyCoord(int yCoord) {
        this.yCoord = yCoord;
    }
}
