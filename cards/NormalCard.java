package cards;

import player.Player;

public class NormalCard extends Card implements Cloneable{
    protected String cardName;
	
	public NormalCard(String cardName) {

		this.cardName="nopecard";
		
	}

    public String getName(){
        return cardName;
        
    }
	@Override
	public void cardAction(Player p1, Player p2) {
		CardStack.getInstance().counterTopCard();
	}

	@Override
	public NormalCard clone() {
		return new NormalCard("Normal_card");
	}
    
}
