package Serveur;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

import Entite.Game;


public class Accepter_connexion implements Runnable{

	private ServerSocket socketserver = null;
	private Socket socket = null;
	private ArrayList<Game> games;
	private Salon salon;
	private boolean running = true;

	public Thread t1;
	public Accepter_connexion(ServerSocket ss, ArrayList<Game> games, Salon salon){
	 socketserver = ss;
	 this.games = games;
	 this.salon = salon;
	}
	
	public void arret(){
		running = false;
	}
	
	public void run() {
		try {
			while(running){
			socket = socketserver.accept();
			
			System.out.println("Un joueur veut se connecter");
			/*t1 = new Thread(new Authentification(socket, games, salon));
			t1.start();*/
			Reception rec = new Reception(socket, games, salon);
			rec.start();

			}
		} catch (IOException e) {
			arret();
			System.err.println("Erreur serveur");
		}
		
	}
}
