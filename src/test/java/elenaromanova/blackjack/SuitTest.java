package elenaromanova.blackjack;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SuitTest {
    @Test
    void heartToString() {
        assertEquals("♥", Suit.HEARTS.toString());
    }

    @Test
    void diamondToString() {
        assertEquals("♦", Suit.DIAMONDS.toString());
    }

    @Test
    void spadeToString() {
        assertEquals("♠", Suit.SPADES.toString());
    }

    @Test
    void clubToString() {
        assertEquals("♣", Suit.CLUBS.toString());
    }
}