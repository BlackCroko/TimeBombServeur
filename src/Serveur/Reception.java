package Serveur;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import Entite.Game;
import Entite.Joueur;

public class Reception extends Thread {

	private BufferedReader in;
	private PrintWriter out = null;
	private Socket socket;
	private String message = null;
	private ArrayList<Game> games;
	private Salon salon;
	private Game game;
	private String login = "";
	private boolean running = true;

	public Reception(Socket s, ArrayList<Game> games, Salon salon) {
		this.socket = s;
		this.games = games;
		this.salon = salon;

		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		out.println("anim");
		out.flush();
	}

	public void arret() {
		running = false;
	}

	public void run() {

		while (running) {
			try {

				message = in.readLine();
				
				if (login != "")
					System.out.println(login + " dit : " + message);
				else
					System.out.println("Le joueur dit : " + message);
				String infos[] = message.split(",");
				switch (infos[0]) {
				case "Join":
					login = infos[1];
					out.println("menu," + login + ",connecte");
					out.flush();

					break;
				case "Salon":
					salon.addJoueur(socket);
					game = null;
					break;
				case "JoinGame":
					salon.DelJoueur(socket);
					out.println("createGame," + login + "," + infos[2]);
					out.flush();
					for (Game g : games) {
						if (g.getName().equals(infos[1])) {
							game = g;
							g.addJoueur(new Joueur(socket, login));
						}
					}
					break;
				case "Create":
					salon.DelJoueur(socket);
					out.println("salon," + login + ",connecte");
					out.flush();

					break;
				case "CreateGame":
					game = new Game(login, infos[1]);
					out.println("createGame," + infos[1] + "," + login);
					out.flush();
					game.addJoueur(new Joueur(socket, login));
					salon.addGame(game);

					break;
				case "startGame":
					game.initGame();
					break;
				case "retourner":
					game.jouerCarte(infos[1], infos[2], Integer.parseInt(infos[3]));
					break;
				case "retournerfin":
					game.retournerFin(login);
					break;
				case "Quit":
					salon.DelJoueur(socket);
					if (game != null) {
						game.DelJoueur(socket);
						if (game.getProprio().equals(login)) {
							game.suppr();
							salon.DelGame(game);
						}
					}
					System.out.println("Un joueur s'est deconnecte");
					socket.close();
					running = false;
					break;
				default:
					/* Action */;
				}
			} catch (IOException e) {
				arret();
				System.out.println("Le client est mort");
			}
		}
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
