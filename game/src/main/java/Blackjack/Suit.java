package Blackjack;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class Suit {
    private String name;
    private String symbol;

    private final static Suit CLUBS = new Suit("Clubs", "c");
    private final static Suit DIAMONDS = new Suit("Diamonds", "d");
    private final static Suit HEARTS = new Suit("Hearts", "h");
    private final static Suit SPADES = new Suit("Spades", "s");

    final static List<Suit> VALUES = Collections.unmodifiableList(Arrays.asList(CLUBS, DIAMONDS, HEARTS, SPADES));

    private Suit(String nameValue, String symbolValue) {
        name = nameValue;
        symbol = symbolValue;
    }

    public String getSymbol() {
        return symbol;
    }

    @Override
    public String toString() {
        return name;
    }
}