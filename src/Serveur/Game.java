package Serveur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class Game {

	private String name;
	private String proprio;

	private ArrayList<Joueur> joueurs;

	private BufferedReader in = null;
	private PrintWriter out = null;

	public Game(String proprio, String name) {
		this.name = name;
		this.proprio = proprio;
		joueurs = new ArrayList<Joueur>();
	}

	public void addJoueur(Joueur j) {
		joueurs.add(j);
		for (Joueur g : joueurs)
			SendInfos(g);
	}

	public void DelJoueur(Socket s) {
		for (int i = 0; i < joueurs.size(); i++) {
			if (joueurs.get(i).getSocket() == s) {
				joueurs.remove(i);
				for (Joueur g : joueurs)
					SendInfos(g);
			}
		}

	}

	public void SendInfos(Joueur j) {
		try {
			out = new PrintWriter(j.getSocket().getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String message = "gameAjout,";
		for (Joueur g : joueurs) {
			message += g.getLogin() + "@";

		}
		if (!message.equals("gameAjout,")) {
			out.println(message);
			out.flush();
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProprio() {
		return proprio;
	}

	public void setProprio(String proprio) {
		this.proprio = proprio;
	}

}
