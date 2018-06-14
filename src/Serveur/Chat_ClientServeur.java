package Serveur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class Chat_ClientServeur implements Runnable {

	private Socket socket = null;
	private BufferedReader in = null;
	private PrintWriter out = null;
	private String login = "zero";
	
	private Game game;
	
	
	public Chat_ClientServeur(Socket s, String log, Game game){
		
		socket = s;
		login = log;
		this.game = game;
		
		if(game.getJ2() == null)
			System.out.println(login +" vient de se connecter ");
		else
			System.out.println(login +" vient de rejoindre la partie ");
	}
	public void run() {
		
		try {
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new PrintWriter(socket.getOutputStream());
		
		Reception t3 = new Reception(in,login, game);
		t3.start();

		if(socket.isClosed()){
			t3.arret();
			System.out.println("socket close");
		}
			
		
		} catch (IOException e) {
			System.err.println(login +"s'est déconnecté ");
		}
}
}
