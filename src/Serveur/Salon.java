package Serveur;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import Entite.Game;

public class Salon {

	private ArrayList<Socket> joueurs;
	private ArrayList<Game> games;
	private PrintWriter out = null;

	public Salon(ArrayList<Game> games) {
		joueurs = new ArrayList<Socket>();
		this.games = games;
	}

	public void addJoueur(Socket s) {
		joueurs.add(s);
		SendGames(s);
	}

	public void addGame(Game g) {
		games.add(g);
		for (Socket s : joueurs) {
			SendGames(s);
		}
	}

	public void DelJoueur(Socket s) {
		if(joueurs != null)
		for (int i = 0; i < joueurs.size(); i++) {
			if (joueurs.get(i) == s) {
				joueurs.remove(i);
			}
		}
	}

	public void DelGame(Game g) {
		for (int i = 0; i < games.size(); i++) {
			if (games.get(i) == g) {
				games.remove(i);
				for (Socket s : joueurs) {
					SendGames(s);
				}
			}
		}
	}

	public void SendGames(Socket socket) {
		try {
			out = new PrintWriter(socket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String message = "salonAjout,";
		for (Game g : games) {
			message += g.getProprio() + ";" + g.getName() +";"+g.getJoueurs().size()+"/8@";

		}
			out.println(message);
			out.flush();
		
	}
}
