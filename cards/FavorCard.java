package cards;

import player.Player;

public class FavorCard extends Card implements Cloneable{

	public FavorCard(String cardName){
		this.cardName= cardName;
		
	}

	@Override
	public void cardAction(Player p1, Player p2) {
		Hand hand1 = p1.getHandManager();
		Hand hand2 = p2.getHandManager();
		List<Card> cards = hand2.getSelectedCards();
		hand1.addCards(cards);
		hand1.clearSelectedCards();
		hand2.clearSelectedCards();
		
	}

	@Override
	public Card clone() {
		return new FavorCard("Favor_card");
	}

}