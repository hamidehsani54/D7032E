package cards;

import java.util.ArrayList;
import java.util.List;

//max from each cards

public class CardFactory {
    public final int AttackCardMax = 4;
    public final int DefuseCardMax =5;
    public final int ExplodingKittenCardMax =3;
    public final int FavorCardMax =4;
    public final int NopeCardMax =4;
    public final int ShufffleCardMax =3;
    public final int ScryCardMax =3;
    public final int NormalCardMax =4;
    public final int SkipCardMax =3;

    //controller for every cards
    public int Attack_card_count;
    public int Defuse_card_count;
    public int ExplodingKittens_card_count;
    public int Favor_card_count;
    public int Nope_card_count;
    public int Shuffle_card_count;
    public int Scry_card_count;
    public int Normal_card_count;
    public int Skip_card_count;

    public static final int Attack_card =3;
    public static final int Defuse_card =2;
    public static final int ExplodingKittens_card =5;
    public static final int Favor_card =8;
    public static final int Nope_card =1;
    public static final int Skip_card =4;
    public static final int Shuffle_card =6;
    public static final int Scry_card =7;
    public static final String EXPLODING_KITTEN_CARD = null;

 

    public Card createCard(String cardName){
        Card card;
        switch(cardName){   
            case "Attack_card":
                Attack_card_count =(Attack_card_count)%AttackCardMax +1;
                card= new AttackCard("Attack_card");
                break;

            case "Defuse_card":
                Defuse_card =(Defuse_card)%DefuseCardMax +1;
                card= new DefuseCard("Defuse_card");
                break;

            case "ExplodingKittens_card":
                ExplodingKittens_card_count =(ExplodingKittens_card_count)%ExplodingKittenCardMax +1;
                card= new ExplodingKittenCard(cardName:"ExplodingKittens_card");
                break;

            case "Favor_card":
            Favor_card_count =(Favor_card_count)%FavorCardMax +1;
                card= new FavorCard(cardName: "Favor_card");
                break;

            case "Nope_card":
            Nope_card_count =(Nope_card_count)%NopeCardMax +1;
                card= new NopeCard("Nope_card");
                break;

            case "Skip_card":
            Skip_card_count =(Skip_card_count)%SkipCardMax +1;
                card= new SkipCard("Skip_card");
                break;
            case "Shuffle_card":
            Shuffle_card_count =(Shuffle_card_count)%ShufffleCardMax +1;
                card= new ShuffleCard("Shuffle_card");
                break;

            case "Scry_card":
                Scry_card_count =(Scry_card_count)%ScryCardMax +1;
                card= new ScryCard(cardName:"Scry_card");
                break;
            
            case "Normal_card":
            Normal_card_count =(Normal_card_count)%Normal_card_count +1;
                card= new NormalCard("NormalCard");
                break;
                

            default:
            throw new CardNotFoundException("incorrect cardID");



        }
        return new cardLogger(card);

    }
    public List<Card> createCards(int cardID, int numCards) throws IncorrectNumberOfCardsException {
        try{
            
		List<Card> cards = new ArrayList<Card>();

		for (int i = 0; i < numCards; i++) {
			cards.add(createCard(i));
		}
        return cards;
        }
        catch(Exception) {
		
			throw new Exception();
		}
	}


}