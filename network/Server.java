import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import player.Players;

import java.io.*;

public class Server {
    
    private ServerSocket aSocket;
 

    public void server(int numberPlayers, int numberOfBots) throws Exception {
        Players.add(new players(0, false, null, null, null)); //add this instance as a player
        //Open for connections if there are online players
        for(int i=0; i<numberOfBots; i++) {
            players.add(new Player(i+1, true, null, null, null)); //add a bot    
        }
        if(numberPlayers>1)
            aSocket = new ServerSocket(2048);
        for(int i=numberOfBots+1; i<numberPlayers+numberOfBots; i++) {
            Socket connectionSocket = aSocket.accept();
            ObjectInputStream inFromClient = new ObjectInputStream(connectionSocket.getInputStream());
            ObjectOutputStream outToClient = new ObjectOutputStream(connectionSocket.getOutputStream());
            players.add(new Players(i, false, connectionSocket, inFromClient, outToClient)); //add an online client
            System.out.println("Connected to player " + i);
            outToClient.writeObject("You connected to the server as player " + i + "\n");
        }    
    }
    
}