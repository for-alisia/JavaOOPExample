package elenaromanova.chess;

public abstract class Figure {
    Color color;
    Coordinates coordinates;
    public Figure(Color color) {
        this.color = color;
    }

    public void setCoordinates(Coordinates coords) {
        coordinates = coords;
    }
}
