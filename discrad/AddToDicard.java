package discrad;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import cards.*;
import player.*;
import deck.InItGame;
import logic.Game_logic;

public class AddToDicard {
	public static ArrayList<Card> discard = new ArrayList<Card>();
    public void addToDiscardPile(Players currentPlayer, String card) throws Exception {
		//After an interruptable card is played everyone has 5 seconds to play Nope
		int nopePlayed = checkNrNope();
		Object players;
        ExecutorService threadpool = Executors.newFixedThreadPool(((ArrayList<Card>) players).size());
		for(Players p : players) {
			p.sendMessage("Action: Player " + currentPlayer.playerID + " played " + card);
			if(p.hand.contains(Card.NopeCard)) { //only give the option to interrupt to those who have a Nope card
				p.sendMessage("Press <Enter> to play Nope");
				Runnable task = new Runnable() {
		        	@Override
		        	public void run() {
	        			try {
			        		String nextMessage = p.readMessage(true); //Read that is interrupted after secondsToInterruptWithNope
			        		if(!nextMessage.equals(" ") && p.hand.contains(Card.NopeCard)) {
		    	    			p.hand.remove(Card.NopeCard);
		    	    			discard.add(0, Card.NopeCard);
		    	    			for(Players notify: players)
		    	    				notify.sendMessage("Player " + p.playerID + " played Nope");
			        		}
	        			} catch(Exception e) {
	        				System.out.println("addToDiscardPile: " +e.getMessage());
	        			}
	        		}
	        	};
            	threadpool.execute(task);
			}
		}
		int secondsToInterruptWithNope=5;
		threadpool.awaitTermination((secondsToInterruptWithNope*1000)+500, TimeUnit.MILLISECONDS); //add an additional delay to avoid concurrancy problems with the ObjectInputStream
		for(Players notify: players)
			notify.sendMessage("The timewindow to play Nope passed");
		if(checkNrNope()>nopePlayed) {
			for(Players notify: players)
				notify.sendMessage("Play another Nope? (alternate between Nope and Yup)");
			addToDiscardPile(currentPlayer, card);
		}
	}
    
	public int checkNrNope() {
		int i=0;
		while(i<discard.size() && discard.get(i)==Card.NopeCard) {
			i++;	
		}
		return i;
	}
    
}
