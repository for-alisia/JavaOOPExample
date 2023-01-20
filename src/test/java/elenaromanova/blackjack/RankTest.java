package elenaromanova.blackjack;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RankTest {
    @Test
    void getValueOfKing() {
        assertEquals(10, Rank.KING.getValue());
    }

    @Test
    void getValueOfQueen() {
        assertEquals(10, Rank.QUEEN.getValue());
    }

    @Test
    void getValueOfJack() {
        assertEquals(10, Rank.JACK.getValue());
    }

    @Test
    void getValueOfAce() {
        assertEquals(1, Rank.ACE.getValue());
    }

    @Test
    void getValueOfTwo() {
        assertEquals(2, Rank.TWO.getValue());
    }

    @Test
    void getValueOfSix() {
        assertEquals(6, Rank.SIX.getValue());
    }

    @Test
    void getValueOfTen() {
        assertEquals(10, Rank.TEN.getValue());
    }
}