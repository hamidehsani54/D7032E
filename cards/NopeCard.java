package cards;

import player.Player;

public class NopeCard extends Card implements Cloneable{
    protected String cardName;
	
	public NopeCard(String cardName) {

		this.cardName="nopecard";
		
	}
	public void Action(){

		//action for NopeCard
	}
    public String getName(){
        return cardName;
        
    }
	@Override
	public void cardAction(Player p1, Player p2) {
		CardStack.getInstance().counterTopCard();
	}

	@Override
	public NopeCard clone() {
		return new NopeCard("Nope_card");
	}

}
