package cards;
import cards.Card;
import cards.Deck.MainDeck;
import player.Player;

public class ShuffleCard {
    protected String cardName;
	
	public ShuffleCard(String cardName) {

		this.cardName="Shuffle_card";
		
	}
	public void Action(Player p1, Player p2){
		MainDeck mainDeck = MainDeck.getInstance();
		mainDeck.shuffleDeck();
	}
    public String getName(){
        return cardName;  
    }
	@Override
	public ShuffleCard clone() {
		return new ShuffleCard("Shuffle_card");
	}

}
