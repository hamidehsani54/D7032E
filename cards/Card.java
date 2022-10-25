package cards;

import player.Player;

public abstract class Card{
	protected String cardName;


	public String getID() {
		return this.cardName;
	}

	public String toString() {
		return this.getClass().getName();
	}

	abstract public void cardAction(Player p1, Player p2);

	abstract public Card clone();


}
