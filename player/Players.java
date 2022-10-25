package player;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import cards.Card;
import network.*;

public class Players {
    public int playerID;
    public boolean online;
    public boolean isBot;
    public Socket connection;
    public boolean exploded = false;
    public ObjectInputStream inFromClient;
    public ObjectOutputStream outToClient;
    public ArrayList<Card> hand = new ArrayList<Card>();
    public static int numberOfTurnsToTake = 1;
    Scanner in = new Scanner(System.in);

    public Players(int playerID, boolean isBot, Socket connection, ObjectInputStream inFromClient, ObjectOutputStream outToClient) {
        this.playerID = playerID; this.connection = connection; this.inFromClient = inFromClient; this.outToClient = outToClient; this.isBot = isBot;
        if(connection == null)
            this.online = false;
        else
            this.online = true;
    }
    public void sendMessage(Object message) {
        if(online) {
            try {outToClient.writeObject(message);} catch (Exception e) {}
        } else if(!isBot){
            System.out.println(message);                
        }
    }
    public String readMessage(boolean interruptable) {
        String word = " "; 
        if(online)
            try{
                word = (String) inFromClient.readObject();
            } catch (Exception e){
                System.out.println("Reading from client failed: " + e.getMessage());
            }
        else
            try {
                if(interruptable) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                    int millisecondsWaited = 0;
                    int secondsToInterruptWithNope=5;
                    while(!br.ready() && millisecondsWaited<(secondsToInterruptWithNope*1000)) {
                        Thread.sleep(200);
                        millisecondsWaited += 200;
                    }
                    if(br.ready())
                        return br.readLine();               		
                } else {
                    in = new Scanner(System.in); 
                    word=in.nextLine();
                }
            } catch(Exception e){System.out.println(e.getMessage());}
        return word;
    }		
}

public void ExplodingKittens(String[] params) throws Exception {
    if(params.length == 2) {
        this.initGame(Integer.valueOf(params[0]).intValue(), Integer.valueOf(params[1]).intValue());
    } else if(params.length == 1) {
        client(params[0]);
    } else {
        System.out.println("Server syntax: java ExplodingKittens numPlayers numBots");
        System.out.println("Client syntax: IP");
    }
}

public static void main(String argv[]) {
    try {
        new App(argv);
    } catch(Exception e) {

    }
}
public static int size() {
    return 0;
}
public static Players get(int startPlayer) {
    return null;
}