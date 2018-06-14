package Serveur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;


public class Reception extends Thread {

	private BufferedReader in;
	private PrintWriter out = null;
	private String message = null, login = null;
	private Game game;
	private boolean running = true;
	
	public Reception(BufferedReader in, String login, Game g){
		
		this.in = in;
		this.login = login;
		this.game = g;
	}
	
	public void arret(){
		running = false;
	}

	
	public void run() {
		
		while(running){
	        try {
			message = in.readLine();
			if(message == null)
				arret();
				
			if(login.equals(game.getJ1())){
				System.out.println(game.getJ1()+" : "+message+"   "+game.getJ2());
				out = new PrintWriter(game.getJoueur2().getOutputStream());
				out.println(message);
			    out.flush();
			}
			else {
				System.out.println(game.getJ2()+" : "+message+"   "+game.getJ1());
				out = new PrintWriter(game.getJoueur1().getOutputStream());
				out.println(message);
			    out.flush();
			}
		    } catch (IOException e) {
				arret();
		    	System.err.println(login +"s'est déconnecté ");
			}
		}
	}

}
