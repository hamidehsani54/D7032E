package cards.Deck;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cards.Card;
import cards.CardFactory;
import cards.NormalCard;

public class MainDeck {
    private static MainDeck mainDeck;

	Deck deck;
	public CardFactory factory;

	public static MainDeck getInstance() {
		if (mainDeck == null) {
			mainDeck = new MainDeck();
		}
		return mainDeck;
	}

	public static void tearDown() {
		mainDeck = null;
	}

	private MainDeck() {
		deck = new Deck(new ArrayList<Card>());
		factory = new CardFactory();
	}

	public int getCardCount() {
		return mainDeck.getCards().size();
	}

	public List<Card> getCards() {
		return mainDeck.deck.getCards();
	}

	public void setCards(List<Card> cards) {
		mainDeck.deck = new Deck(cards);
	}

	public boolean insertCard(Card card, int position) {
		return mainDeck.deck.addCard(card, position);
	}

	public Card draw() {
		Card drawCard = getTopCard();
		mainDeck.deck.removeCard(drawCard);
		return drawCard;
	}

	private Card getTopCard() {
		return mainDeck.getCards().get(0);
	}

	public void shuffleDeck() {
		List<Card> toShuffle = mainDeck.deck.getCards();
		Collections.shuffle(toShuffle);
		mainDeck.deck.setCards(toShuffle);
	}

	public void initStartingDeck() {
		if (mainDeck.deck.getCards().size() == 0) {
			initFavorCards();
			initShuffleCards();
			initSkipCards();
			initAttackCards();
			initNormalCards();
			initNopeCards();
			initScryCards();
		}
		shuffleDeck();
	}

	private void initFavorCards() {
		for (int i = 0; i < 4; i++) {
			mainDeck.insertCard(factory.createCard("Favor_card"), 0);
		}
	}

	private void initShuffleCards() {
		for (int i = 0; i < 4; i++) {
			mainDeck.insertCard(factory.createCard("Shuffle_card"), 0);
		}
	}

	private void initSkipCards() {
		for (int i = 0; i < 4; i++) {
			mainDeck.insertCard(factory.createCard("Skip_card"), 0);
		}
	}

	private void initAttackCards() {
		for (int i = 0; i < 4; i++) {
			mainDeck.insertCard(factory.createCard("Attack_card"), 0);
		}
	}

	private void initNormalCards() {
		for (int i = 0; i < 20; i++) {
			NormalCard card = (NormalCard) factory.createCard("Normal_card");
			mainDeck.insertCard(card, 0);
		}
	}

	private void initNopeCards() {
		for (int i = 0; i < 5; i++) {
			mainDeck.insertCard(factory.createCard("Nope_card"), 0);
		}
	}

	private void initScryCards() {
		for (int i = 0; i < 5; i++) {
			mainDeck.insertCard(factory.createCard("Scry_card"), 0);
		}
	}

	public void populateDeck(int numPlayers) {
		initExplodingKittenCards(numPlayers);
		initDefuseCards(numPlayers);
		shuffleDeck();
	}

	private void initExplodingKittenCards(int numPlayers) {
		for (int i = 0; i < numPlayers - 1; i++) {
			mainDeck.insertCard(factory.createCard(CardFactory.EXPLODING_KITTEN_CARD), 0);
		}
	}

	private void initDefuseCards(int numPlayers) {
		for (int i = 0; i < 6 - numPlayers; i++) {
			mainDeck.insertCard(factory.createCard("Defuse_card"), 0);
		}
	}

}
