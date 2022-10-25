package cards;
import cards.Card;
import player.Player;
import player.PlayerManager;

public class ExplodingKittenCard extends Card implements Cloneable{
    protected String cardName;
	
	PlayerManager playerManager;
	TurnManager turnManager;

	public ExplodingKittenCard() {
		this.cardName = "ExplodingKittens_card";
		turnManager = TurnManager.getInstance();
		playerManager = turnManager.getPlayerManager();
	}

	public ExplodingKittenCard(String cardName) {
		this.cardName=cardName;
		
	}

	@Override
	public void cardAction(Player p1, Player p2) {
		turnManager.makeCurrentPlayerLose();
	}

	@Override
	public Card clone() {
		return new ExplodingKittenCard("ExplodingKittens_card");
	}



}
