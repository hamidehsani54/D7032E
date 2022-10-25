package cards;

import java.util.ArrayList;

import cards.Deck.MainDeck;
import player.Player;

public class ScryCard {
    protected String cardName;
	
	public ScryCard(String cardName) {
		this.cardName="nopecard";
		
	}

    public String getName(){
        return cardName;
        
    }
	@Override
	public void cardAction(Player p1, Player p2) {
		int numberOfCardsToScry = 3;
		cardsToReveal = new ArrayList<>();// top card of deck is at
		                                  // index 0.
		MainDeck mainDeck = MainDeck.getInstance();
		if (mainDeck.getCardCount() < numberOfCardsToScry) {
			numberOfCardsToScry = mainDeck.getCardCount();
		}
		for (int i = 0; i < numberOfCardsToScry; i++) {
			cardsToReveal.add(mainDeck.getCards().get(i));
		}
	}

	@Override
	public ScryCard clone() {
		return new ScryCard("Scry_card");
	}
}
