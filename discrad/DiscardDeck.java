package discrad;
import java.util.List;

import cards.Card;
import cards.Deck.Deck;

public class DiscardDeck {

	private static DiscardDeck discardDeck;

	Deck deck;

	public static DiscardDeck getInstance() {
		if (discardDeck == null) {
			discardDeck = new DiscardDeck();
		}
		return discardDeck;
	}

	private DiscardDeck() {
		deck = new Deck();
	}

	public static void tearDown() {
		discardDeck = null;
	}

	public List<Card> getCards() {
		return discardDeck.deck.getCards();
	}

	public int getCardCount() {
		return discardDeck.getCards().size();
	}

	public Card removeCard(Class<?> cardType) throws Exception {
		Card retCard = null;
		if (discardDeck.getCardCount() == 0) {
			throw new Exception("Discard Deck is empty");
		}
		for (Card card : discardDeck.getCards()) {
			if (card.getClass().equals(cardType)) {
				retCard = card;
				discardDeck.deck.removeCard(retCard);
				return retCard;
			}
		}

		throw new Exception(cardType.getName() + " is not in discard pile");
	}

	public void addCard(Card card) {
		discardDeck.deck.addCard(card, 0);
	}

	public void addAll(List<Card> cardsToAdd) {
		deck.addAll(cardsToAdd);
	}

	public void removeAllCards() {
		discardDeck.deck = new Deck();
	}

}
