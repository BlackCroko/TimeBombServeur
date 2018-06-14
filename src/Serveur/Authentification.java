package Serveur;

import java.net.*;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.io.*;

public class Authentification implements Runnable {

	private Socket socket;
	private PrintWriter out = null;
	private BufferedReader in = null;
	private String type;
	private String nomGame;
	private String login = "";
	private String pass;

	private ArrayList<Game> games;
	private Salon salon;
	private Game game;

	public boolean authentifier = false;
	public boolean enAttente = false;
	public boolean enSalon = false;
	public Thread t2;

	public Authentification(Socket s, ArrayList<Game> games, Salon salon) {
		socket = s;
		this.games = games;
		this.salon = salon;
	}

	public void run() {

		try {

			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream());
			out.println("anim");
			out.flush();
			boolean hf = false;
			while (!authentifier && !socket.isClosed()) {

				String texte = in.readLine();
				if (texte != null) {
					String infos[] = texte.split(",");

					type = infos[0];

					if (type.equals("Join")) {
						login = infos[1];
						out.println("menu," + login + ",connecte");
						out.flush();
						salon.addJoueur(socket);
						authentifier = true;
					}
					if (type.equals("Quit")) {
						socket.close();
						authentifier = true;
					} else {
						out.println("erreur");
						out.flush();
					}
				}
				if (socket.isClosed())
					System.out.println("Un joueur s'est deconnecte");
			}
			while (!enAttente && !socket.isClosed()) {

				String texte = in.readLine();
				System.out.println(texte);
				if (texte != null) {
					String infos[] = texte.split(",");
					type = infos[0];

					if (type.equals("Join") && infos.length > 2) {
						salon.DelJoueur(socket);
						out.println("createGame," + login + ","+infos[1]);
						out.flush();
						for(Game g : games){
							if(g.getName().equals(infos[1]))
								g.addJoueur(new Joueur(socket, login));
						}
						enAttente = true;
					} else if (type.equals("Create")) {
						salon.DelJoueur(socket);
						out.println("salon," + login + ",connecte");
						out.flush();
						enAttente = true;

						while (!enSalon && !socket.isClosed()) {
							
							texte = in.readLine();
							System.out.println(texte);
							if (texte != null) {
								infos = texte.split(",");
								type = infos[0];

								if (type.equals("CreateGame")) {
									Game g = new Game(login, infos[1]);
									out.println("createGame," + login + ","+infos[1]);
									out.flush();
									games.add(g);
									g.addJoueur(new Joueur(socket, login));
									salon.addGame(g);
									enSalon = true;
								} else if (type.equals("Quit")) {
									socket.close();
									enSalon = true;
								} else {
									out.println("erreur");
									out.flush();
								}
							}
							if (socket.isClosed() || !socket.isConnected())
								System.out.println("Un joueur s'est deconnecte");
						}

					} else if (type.equals("Quit")) {
						salon.DelJoueur(socket);
						socket.close();
						enAttente = true;
					} else {
						out.println("erreur");
						out.flush();
					}
				}
				if (socket.isClosed() || !socket.isConnected())
					System.out.println("Un joueur s'est deconnecte");
			}
			if (!socket.isClosed()) {
				// t2 = new Thread(new Chat_ClientServeur(socket,login, game));
				// t2.start();
				System.out.println("affichage salon d'une game");
			}
		} catch (IOException e) {

			System.err.println(login + " ne répond pas !");
		}
	}
}
