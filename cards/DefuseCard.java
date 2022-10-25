package cards;
import cards.Card;
import cards.Deck.MainDeck;
import discrad.DiscardDeck;
import player.Player;

public class DefuseCard {
    protected String cardName;
	CardStack stack;
	TurnManager tm;
	MainDeck mDeck;
	CardFactory factory;
	DiscardDeck dDeck;
	
	public DefuseCard(String cardName) {
		this.cardName="Defuse_card";
	}

	@Override
	public void cardAction(Player p1, Player p2) {
		stack = CardStack.getInstance();
		tm = TurnManager.getInstance();
		mDeck = MainDeck.getInstance();
		factory = new CardFactory();
		dDeck = DiscardDeck.getInstance();

		if (stack.getStack().isEmpty()) {
			tm.getCurrentPlayer().addDefuseCardToHand();
			return;
		}

		if (stack.getStack().elementAt(0).getID() == 5) {
			stack.setStack(new Stack<Card>());
			mDeck.insertCard(factory.createCard(CardFactory.EXPLODING_KITTEN_CARD), mDeck.getCardCount() - 1);
			dDeck.addCard(factory.createCard(CardFactory.Defuse_card));
		} else {
			tm.getCurrentPlayer().addDefuseCardToHand();
		}
	}

	@Override
	public DefuseCard clone() {
		return new DefuseCard("Defuse_card");
	}  
    }

}
