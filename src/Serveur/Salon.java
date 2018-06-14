package Serveur;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class Salon {

	private ArrayList<Socket> joueurs;
	private ArrayList<Game> games;
	private PrintWriter out = null;

	public Salon() {
		joueurs = new ArrayList<Socket>();
		games = new ArrayList<Game>();
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
			message += g.getProprio() + ";" + g.getName() + "@";

		}
		if (!message.equals("salonAjout,")) {
			out.println(message);
			out.flush();
		}
	}
}
