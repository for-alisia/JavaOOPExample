package elenaromanova.chess;

public class ChessBoard {
    private Figure[][] board = new Figure[8][8];
    public void add(Figure figure, String coords) {
        Coordinates coordinates = new Coordinates(coords);
        board[coordinates.getX()][coordinates.getY()] = figure;
    }

    public Figure getFigureByCoords(String coords) {
        Coordinates coordinates = new Coordinates(coords);
        return board[coordinates.getX()][coordinates.getY()];
    }
}
