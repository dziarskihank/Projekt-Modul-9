package Blackjack;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Rank {
    private String name;
    private String symbol;

    final static Rank ACE = new Rank("Ace", "a");
    private final static Rank TWO = new Rank("Two", "2");
    private final static Rank THREE = new Rank("Three", "3");
    private final static Rank FOUR = new Rank("Four", "4");
    private final static Rank FIVE = new Rank("Five", "5");
    private final static Rank SIX = new Rank("Six", "6");
    private final static Rank SEVEN = new Rank("Seven", "7");
    private final static Rank EIGHT = new Rank("Eight", "8");
    private final static Rank NINE = new Rank("Nine", "9");
    private final static Rank TEN = new Rank("Ten", "t");
    private final static Rank JACK = new Rank("Jack", "j");
    private final static Rank QUEEN = new Rank("Queen", "q");
    private final static Rank KING = new Rank("King", "k");

    final static List<Rank> VALUES =
            Collections.unmodifiableList(Arrays.asList(ACE, TWO, THREE, FOUR, FIVE, SIX, SEVEN,
                    EIGHT, NINE, TEN, JACK, QUEEN, KING));

    private Rank(String nameValue, String symbolValue) {
        name = nameValue;
        symbol = symbolValue;
    }

    @Override
    public String toString() {
        return name;
    }

    String getSymbol() {
        return symbol;
    }
}