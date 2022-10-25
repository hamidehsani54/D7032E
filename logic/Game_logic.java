package logic;
import java.util.ArrayList;
import java.util.Collections;
import player.*;

import deck.Players;

public class Game_logic {

    public ArrayList<Players> players = new ArrayList<Players>();

	public void game(int startPlayer) throws Exception {
		Players currentPlayer = Players.get(startPlayer);
		int playersLeft = Players.size();
		do { //while playersLeft>1
			for(Players p : players) {
				if(p == currentPlayer)
					p.sendMessage("It is your turn");
				else
					p.sendMessage("It is now the turn of player " + currentPlayer.playerID);
			}
			Collections.sort(currentPlayer.hand);
			for(int i=0; i<numberOfTurnsToTake; i++) {
				String otherPlayerIDs = "PlayerID: ";
				for(Players p : players) {
					if(p.playerID != currentPlayer.playerID)
						otherPlayerIDs += p.playerID + " ";
				}

				String response = "";
				while(!response.equalsIgnoreCase("pass")) {
					int turnsLeft = numberOfTurnsToTake-i;
					currentPlayer.sendMessage("\nYou have " + turnsLeft + ((turnsLeft>1)?" turns":" turn") + " to take");
					currentPlayer.sendMessage("Your hand: " + currentPlayer.hand);
					String yourOptions = "You have the following options:\n";
					Set<Card> handSet = new HashSet<Card>(currentPlayer.hand);
					for(Card card : handSet) {
						int count = Collections.frequency(currentPlayer.hand, card);
						if(count>=2)
							yourOptions += "\tTwo " + card + " [target] (available targets: " + otherPlayerIDs + ") (Steal random card)\n";
						if(count>=3)
							yourOptions += "\tThree " + card + " [target] [Card Type] (available targets: " + otherPlayerIDs + ") (Name and pick a card)\n";
						if(card == Card.Attack)
							yourOptions += "\tAttack\n";
						if(card == Card.Favor)
							yourOptions += "\tFavor [target] (available targets: " + otherPlayerIDs + ")\n";
						if(card == Card.Shuffle)
							yourOptions += "\tShuffle\n";
						if(card == Card.Skip)
							yourOptions += "\tSkip\n";
						if(card == Card.SeeTheFuture)
							yourOptions += "\tSeeTheFuture\n";
					}  
					yourOptions += "\tPass\n";
					//We don't need to offer Nope as an option - it's only viable 5 seconds after another card is played and handled elsewhere
					currentPlayer.sendMessage(yourOptions);
					response = currentPlayer.readMessage(false);
					if(yourOptions.contains(response.replaceAll("\\d",""))) { //remove targetID to match vs yourOptions
						if(response.equals("Pass")) { //Draw a card and end turn
							Card drawCard = deck.remove(0);
							if(drawCard == Card.ExplodingKitten) {
								if(currentPlayer.hand.contains(Card.Defuse)) {
									currentPlayer.hand.remove(Card.Defuse);
									currentPlayer.sendMessage("You defused the kitten. Where in the deck do you wish to place the ExplodingKitten? [0.." + (deck.size()-1) + "]");
									deck.add((Integer.valueOf(currentPlayer.readMessage(false))).intValue(), drawCard);
									for(Player p : players) {
										p.sendMessage("Player " + currentPlayer.playerID + " successfully defused a kitten");
									}
								} else {
									discard.add(drawCard); //we discard them to the bottom of the pile, that way we don't end up with problems of Attack ending up as the last card
									discard.addAll(currentPlayer.hand);
									currentPlayer.hand.clear();
									for(Player p : players) {
										p.sendMessage("Player " + currentPlayer.playerID + " exploded");
									}
									currentPlayer.exploded = true;
									playersLeft--;
								}
							} else {
								currentPlayer.hand.add(drawCard);
								currentPlayer.sendMessage("You drew: " + drawCard);
							}
						} else if(response.contains("Two")) { //played 2 of a kind - steal random card from target player
							String[] args = response.split(" ");
							currentPlayer.hand.remove(Card.valueOf(args[1])); 
							currentPlayer.hand.remove(Card.valueOf(args[1]));
							discard.add(0, Card.valueOf(args[1]));
							discard.add(0, Card.valueOf(args[1]));
							addToDiscardPile(currentPlayer, "Two of a kind against player " + args[2]);
							if(checkNrNope() % 2 == 0) {
								Player target = players.get((Integer.valueOf(args[2])).intValue());
						        Random rnd = new Random();
						        Card aCard = target.hand.remove(rnd.nextInt(target.hand.size()-1));
						        currentPlayer.hand.add(aCard);
						        target.sendMessage("You gave " + aCard + " to player " + currentPlayer.playerID);
						        currentPlayer.sendMessage("You received " + aCard + " from player " + target.playerID);								
							}
						} else if(response.contains("Three")) { //played 3 of a kind - name a card and force target player to hand one over if they have it
							String[] args = response.split(" ");
							currentPlayer.hand.remove(Card.valueOf(args[1])); 
							currentPlayer.hand.remove(Card.valueOf(args[1]));
							currentPlayer.hand.remove(Card.valueOf(args[1]));
							discard.add(0, Card.valueOf(args[1]));
							discard.add(0, Card.valueOf(args[1]));
							discard.add(0, Card.valueOf(args[1]));
							addToDiscardPile(currentPlayer, "Three of a kind against player " + args[2]);
							if(checkNrNope() % 2 == 0) {
								Player target = players.get((Integer.valueOf(args[2])).intValue());
								Card aCard = Card.valueOf(args[3]);
								if(target.hand.contains(aCard)) {
									target.hand.remove(aCard);
									currentPlayer.hand.add(aCard);
						        	target.sendMessage("Player " + currentPlayer.playerID + " stole " + aCard);
						        	currentPlayer.sendMessage("You received " + aCard + " from player " + target.playerID);										
								} else {
									currentPlayer.sendMessage("The player did not have any " + aCard);
								}								
							}
						} else if(response.equals("Attack")) {
							int turnsToTake = 0;
							if(discard.size()>0 && discard.get(0).equals(Card.Attack)) {
								turnsToTake = numberOfTurnsToTake + 2;	
							} else {
								turnsToTake = 2;
							}
							currentPlayer.hand.remove(Card.Attack);
							discard.add(0, Card.Attack);
							addToDiscardPile(currentPlayer, "Attack");
							if(checkNrNope() % 2 == 0) {
								numberOfTurnsToTake = turnsToTake; //do not modify if Nope
								i = numberOfTurnsToTake; //ugly hack - make sure we also exit the for loop
								response="Pass"; //part of the ugly hack
								break; //exit the while-loop and move to the next player - do not draw.								
							}
						} else if(response.contains("Favor")) {
							currentPlayer.hand.remove(Card.Favor);
							discard.add(0, Card.Favor);
							String[] args = response.split(" ");
							Player target = players.get((Integer.valueOf(args[1])).intValue());
							addToDiscardPile(currentPlayer, "Favor player " + target.playerID);
							if(checkNrNope() % 2 == 0) {
								boolean viableOption = false;
								if(target.hand.size()==0)
									viableOption=true; //special case - target has no cards to give
								while(!viableOption) {
									target.sendMessage("Your hand: " + target.hand);
									target.sendMessage("Give a card to Player " + currentPlayer.playerID);
									String tres = target.readMessage(false);
									if(target.hand.contains(Card.valueOf(tres))) {
										viableOption = true;
										currentPlayer.hand.add(Card.valueOf(tres));
										target.hand.remove(Card.valueOf(tres));
									} else {
										target.sendMessage("Not a viable option, try again");
									}
								}								
							}
						} else if(response.equals("Shuffle")) {
							discard.add(0, Card.Shuffle);
							currentPlayer.hand.remove(Card.Shuffle);
							addToDiscardPile(currentPlayer, "Shuffle");
							if(checkNrNope() % 2 == 0) {
								Collections.shuffle(deck);
							}
						} else if(response.equals("Skip")) {
							currentPlayer.hand.remove(Card.Skip);
							discard.add(0, Card.Skip);
							addToDiscardPile(currentPlayer, "Skip");
							if(checkNrNope() % 2 == 0) {
								break; //Exit the while loop
							}
						} else if(response.equals("SeeTheFuture")) {
							currentPlayer.hand.remove(Card.SeeTheFuture);
							discard.add(0, Card.SeeTheFuture);
							addToDiscardPile(currentPlayer, "SeeTheFuture");
							if(checkNrNope() % 2 == 0) {
								currentPlayer.sendMessage("The top 3 cards are: " + deck.get(0) + " " + deck.get(1) + " " + deck.get(2));
							}							
						} 
					} else {
						currentPlayer.sendMessage("Not a viable option, try again");
					}
					if(i==(numberOfTurnsToTake-1))
						numberOfTurnsToTake=1; //We have served all of our turns, reset it for the next player
				}
			}
			do { //next player that is still in the game
				int nextID=((currentPlayer.playerID+1)<players.size()?(currentPlayer.playerID)+1:0);
				currentPlayer = players.get(nextID);
			} while(currentPlayer.exploded && playersLeft>1);
		} while(playersLeft>1);
		Player winner = currentPlayer;
		for(Player notify: players)
			winner = (!notify.exploded?notify:winner);
		for(Player notify: players)
			notify.sendMessage("Player " + winner.playerID + " has won the game");
		System.exit(0);
	}

    
}
