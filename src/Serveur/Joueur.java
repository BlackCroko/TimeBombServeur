package Serveur;

import java.net.Socket;

public class Joueur {
	
	private Socket socket;
	private String login;
	
	public Joueur(Socket socket , String login){
		this.socket = socket;
		this.login = login;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}
	
	

}
