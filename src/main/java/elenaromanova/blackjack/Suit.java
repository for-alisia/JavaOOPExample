package elenaromanova.blackjack;

public enum Suit {
    CLUBS('♣'),
    DIAMONDS('♦'),
    HEARTS('♥'),
    SPADES('♠');

    private char symbol;

    Suit(char symbol) {
        this.symbol = symbol;
    }


    @Override
    public String toString() {
        return Character.toString(symbol);
    }
}
