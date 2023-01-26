package elenaromanova.chess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChessBoardTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void shouldAddPawn() {
        ChessBoard chessBoard = new ChessBoard();
        IFigure pawn = new Pawn();
        chessBoard.add(pawn, "a1");
        IFigure figure = chessBoard.getFigureByCoords("a1");

        assertEquals(pawn, figure);
    }
}