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
        Figure pawn = new Pawn(Color.WHITE);
        chessBoard.add(pawn, "a1");
        Figure figure = chessBoard.getFigureByCoords("a1");

        assertEquals(pawn, figure);
    }

    @Test
    void shouldAddKnight() {
        ChessBoard chessBoard = new ChessBoard();
        Figure knight = new Knight(Color.BLACK);
        chessBoard.add(knight, "c3");
        Knight figure = (Knight) chessBoard.getFigureByCoords("c3");

        assertEquals(knight, figure);
    }

}