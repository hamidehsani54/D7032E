package cards;

import player.Player;

public class AttackCard extends Card implements Cloneable{
	public AttackCard(){
		this.cardName ="AttackCard";
	}

	public AttackCard(String cardName) {

		this.cardName="Attack_card";
		System.out.println(cardName);		
	}

	@Override
	public Card clone() {
		return new AttackCard();
	}
	@Override
	public void cardAction(Player p1, Player p2) {
		TurnManager.getInstance().endTurnWithoutDrawForAttacks();
		TurnManager.getInstance().addTurnForCurrentPlayer();
		
	}


}
