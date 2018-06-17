package Entite;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class Game {

	private String name;
	private String proprio;

	private boolean finGame = false;
	private int nbtour = 0;
	private int teamwin;

	private ArrayList<Joueur> joueurs;

	private ArrayList<Carte> piochecartes;
	private ArrayList<Integer> piocheperso;

	private String joueurActuel = "";

	private int cabletrouve = 0;
	private int cableJoue = 0;

	private PrintWriter out = null;

	public Game(String proprio, String name) {
		this.name = name;
		this.proprio = proprio;
		joueurs = new ArrayList<Joueur>();
		piochecartes = new ArrayList<Carte>();
		piocheperso = new ArrayList<Integer>();
	}

	public void initGame() {
		switch (joueurs.size()) {
		case 2:
			for (int i = 0; i < 7; i++) {
				if (i < 1)
					piocheperso.add(1);
				if (i < 1)
					piocheperso.add(0);
				if (i < 2)
					piochecartes.add(new Carte(1));
				piochecartes.add(new Carte(0));
			}
			piochecartes.add(new Carte(2));
			break;
		case 4:
			for (int i = 0; i < 15; i++) {
				if (i < 2)
					piocheperso.add(1);
				if (i < 3)
					piocheperso.add(0);
				if (i < 4)
					piochecartes.add(new Carte(1));
				piochecartes.add(new Carte(0));
			}
			piochecartes.add(new Carte(2));
			break;
		case 5:
			for (int i = 0; i < 19; i++) {
				if (i < 2)
					piocheperso.add(1);
				if (i < 3)
					piocheperso.add(0);
				if (i < 5)
					piochecartes.add(new Carte(1));
				piochecartes.add(new Carte(0));
			}
			piochecartes.add(new Carte(2));
			break;
		case 6:
			for (int i = 0; i < 23; i++) {
				if (i < 2)
					piocheperso.add(1);
				if (i < 4)
					piocheperso.add(0);
				if (i < 6)
					piochecartes.add(new Carte(1));
				piochecartes.add(new Carte(0));
			}
			piochecartes.add(new Carte(2));
			break;
		case 7:
			for (int i = 0; i < 27; i++) {
				if (i < 3)
					piocheperso.add(1);
				if (i < 5)
					piocheperso.add(0);
				if (i < 7)
					piochecartes.add(new Carte(1));
				piochecartes.add(new Carte(0));
			}
			piochecartes.add(new Carte(2));
			break;
		case 8:
			for (int i = 0; i < 31; i++) {
				if (i < 3)
					piocheperso.add(1);
				if (i < 5)
					piocheperso.add(0);
				if (i < 8)
					piochecartes.add(new Carte(1));
				piochecartes.add(new Carte(0));
			}
			piochecartes.add(new Carte(2));
			break;
		default:
			/* Action */;
		}
		joueurActuel = joueurs.get((int) (Math.random() * joueurs.size())).getLogin();
		distributionperso();
		distribution();
		initGameJoueur();
	}

	public void jouerCarte(String name, String joueur, int i) {
		if (joueur.equals(joueurActuel) && cableJoue < joueurs.size()) {
			for (Joueur j : joueurs) {
				if (j.getLogin().equals(name)) {
					int p = j.jouercarte(i);
					if (p != 2) {
						cabletrouve += p;
					} else {
						finGame = true;
						teamwin = 1;
					}
					if (cabletrouve >= joueurs.size()) {
						finGame = true;
						teamwin = 0;
					}
					SendCarte(name, i, p);

					cableJoue++;
				}
			}
			joueurActuel = name;
		}
	}

	public void retournerFin() {
		if (!finGame) {
			if (cableJoue >= joueurs.size()) {
				if(nbtour < 3){
				reconstruction();
				distribution();
				SendDeck();
				nbtour++;
			} else{
				System.out.println(nbtour);
				teamwin = 1;
				SendFinGame();
			}
			}
		} else {
			SendFinGame();
		}
	}

	public void SendFinGame() {
		for (Joueur g : joueurs) {
			String message = "finGame," + teamwin + ",";
			try {
				out = new PrintWriter(g.getSocket().getOutputStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for (Joueur j : joueurs) {
				message += j.getLogin() + "@" + j.getPersonnage() + ";";
			}
			out.println(message);
			out.flush();
		}
	}

	public void SendCarte(String name, int numcarte, int idcarte) {
		for (Joueur j : joueurs) {
			String message = "retourner," + name + "," + numcarte + "," + idcarte + "," + cabletrouve;
			try {
				out = new PrintWriter(j.getSocket().getOutputStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			out.println(message);
			out.flush();
		}
	}

	public void affichepiocheperso() {
		for (int i : piocheperso) {
			System.out.print(i);
		}
		System.out.println();
	}

	public void affichepiochecartes() {
		for (Carte c : piochecartes) {
			System.out.print(c.getValeur());
		}
		System.out.println();
	}

	public void distributionperso() {
		for (int i = 0; i < joueurs.size(); i++) {
			int rand = (int) (Math.random() * piocheperso.size());
			joueurs.get(i).setPersonnage(piocheperso.get(rand));
			piocheperso.remove(rand);
		}
	}

	public void distribution() {
		int max = (int) (piochecartes.size() / joueurs.size());
		for (int i = 0; i < joueurs.size(); i++) {
			joueurs.get(i).getCartes().clear();
			for (int j = 0; j < max; j++) {
				int rand = (int) (Math.random() * piochecartes.size());
				joueurs.get(i).addcarte(piochecartes.get(rand));
				piochecartes.remove(rand);
			}
		}
	}

	public void reconstruction() {
		cableJoue = 0;
		piochecartes.clear();
		for (int i = 0; i < joueurs.size(); i++) {
			joueurs.get(i).reconstruire();
			piochecartes.addAll(joueurs.get(i).getCartes());
		}
	}

	public void SendDeck() {
		for (Joueur g : joueurs) {
			String message = "distribution,";
			try {
				out = new PrintWriter(g.getSocket().getOutputStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for (Carte c : g.getCartes()) {
				message += c.getValeur() + ";";
			}
			out.println(message);
			out.flush();
		}
	}

	public void initGameJoueur() {

		for (Joueur g : joueurs) {
			String message = "initGame,";
			try {
				out = new PrintWriter(g.getSocket().getOutputStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			message += g.getPersonnage() + ",";
			for (Carte c : g.getCartes()) {
				message += c.getValeur() + ";";
			}
			message += "," + joueurActuel;
			out.println(message);
			out.flush();
		}
	}

	public void addJoueur(Joueur j) {
		if (joueurs.size() < 8) {
			joueurs.add(j);
			for (Joueur g : joueurs)
				SendInfos(g);
		} else {
			String message = "menu,";
			try {
				out = new PrintWriter(j.getSocket().getOutputStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			message += j.getLogin();
			out.println(message);
			out.flush();
		}
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

	public void suppr() {
		String message = "menu,";
		if (joueurs.size() != 0)
			for (Joueur g : joueurs) {
				try {
					out = new PrintWriter(g.getSocket().getOutputStream());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				message += g.getLogin();
				out.println(message);
				out.flush();

			}
	}

	public int getCabletrouve() {
		return cabletrouve;
	}

	public void setCabletrouve(int cabletrouve) {
		this.cabletrouve = cabletrouve;
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

	public ArrayList<Joueur> getJoueurs() {
		return joueurs;
	}

	public void setJoueurs(ArrayList<Joueur> joueurs) {
		this.joueurs = joueurs;
	}

}
