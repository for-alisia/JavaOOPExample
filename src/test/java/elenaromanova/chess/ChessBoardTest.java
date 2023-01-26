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
        Figure pawn = new Pawn("black");
        chessBoard.add(pawn, "a1");
        Figure figure = chessBoard.getFigureByCoords("a1");

        assertEquals(pawn, figure);
    }
}