package Blackjack;

import java.util.*;

import javafx.scene.image.Image;

public class Deck {
    private List<Card> deck;
    private int index;

    public Deck(int numDecks) {
        deck = new ArrayList<>();
        index = 0;

        for (int i = 0; i < numDecks; i++) {
            for (Suit suit : Suit.VALUES) {
                for (Rank rank : Rank.VALUES) {
                    Card card = new Card(suit, rank, new Image(Card.getFilename(suit, rank)));
                    addCard(card);
                }
            }
        }
        shuffle();
    }

    public void addCard(Card card) {
        deck.add(card);
    }

    public int getSizeOfDeck() {
        return deck.size();
    }

    public int getNumberOfCardsRemaining() {
        return deck.size() - index;
    }

    public Card dealCard() {
        if (index >= deck.size())
            return null;
        else
            return deck.get(index++);
    }

    public void shuffle() {
        Collections.shuffle(deck);
    }

    public void restoreDeck() {
        index = 0;
    }
}