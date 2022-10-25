package cards;

import java.util.Stack;

import cards.Deck.MainDeck;
import discrad.DiscardDeck;
import player.Player;

import java.util.List;


public class CardStack {
    private static CardStack cardstack;
    private Stack<Card> stack = new Stack<Card>();

    private CardStack(){
        this.stack = new Stack<Card>();

    }
    MainDeck maindDeck = MainDeck.getInstance();
    DiscardDeck discardDeck = DiscardDeck.getInstance();

	public static CardStack getInstance() {
		if (cardstack == null) {
			cardstack = new CardStack();
		}
		return cardstack;
	}

	public void addCard(Card card) {
		this.stack.add(card);
	}

	public void resolveTopCard() {
		this.stack.pop().cardAction(null, null); 
	}

	public void resolveTopCard(Player player1, Player player2) {
		this.stack.pop().cardAction(player1, player2);
	}

	public void moveCardsToDiscardDeck() {
		discardDeck.addAll(stack);
		stack.clear();
	}

	public static void tearDown() {
		cardstack = null;
	}

	@SuppressWarnings("unchecked")
	public Stack<Card> getStack() {
		return (Stack<Card>) stack.clone();
	}

	public Card peek() {
		return stack.peek().clone();
	}

	public void setStack(Stack<Card> stack) {
		this.stack = stack;
	}

	public void moveCardsToStack(List<Card> cardsToMove) {
		stack.addAll(cardsToMove);
	}

	public void counterTopCard() throws Exception {
		if (stack.isEmpty() || stack.peek().getID() == "ExplodingKittens_card" || stack.peek().getID() =="Defuse_card") {

			throw new Exception();
		}
		stack.pop();
	}



}
