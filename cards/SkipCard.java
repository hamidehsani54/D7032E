package cards;
import cards.Card;
import player.Player;

public class SkipCard {
    protected String cardName;
	
	public SkipCard(String cardName) {

		this.cardName="skipcard";
		
	}
	@Override
	public void cardAction(Player p1, Player p2) {
		TurnManager.getInstance().endTurnWithoutDraw();
	}

	@Override
	public SkipCard clone() {
		return new SkipCard("Skip_card");
	}
    public String getName(){
        return cardName;
        
    }
}
