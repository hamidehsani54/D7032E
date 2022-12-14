package cards.Deck;

import java.util.ArrayList;

import java.util.List;

import cards.Card;

public class Deck {
    private List<Card> cards;

	public Deck() {
		this.cards = new ArrayList<Card>();
	}

	public Deck(List<Card> cards) {
		this.cards = cards;
	}

	public List<Card> getCards() {
		return new ArrayList<Card>(cards);
	}

	public void setCards(List<Card> cards) {
		this.cards = cards;
	}

	public boolean addCard(Card card, int i) {
		try {
			this.cards.add(i, card);
			return true;
		} catch (IndexOutOfBoundsException e) {
			return false;
		}

	}

	public boolean addCardFirst(Card cardToAdd) {
		return this.addCard(cardToAdd, 0);
	}

	public boolean addCardLast(Card cardToAdd) {
		int lastIndex = cards.size();
		return this.addCard(cardToAdd, lastIndex);
	}

	public boolean addAll(List<Card> cardsToAdd) {
		return this.cards.addAll(cardsToAdd);
	}

	public boolean removeCard(Card card) {
		return this.cards.remove(card);
	}
    
}
