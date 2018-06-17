package Entite;

import java.net.Socket;
import java.util.ArrayList;

public class Joueur {
	
	private Socket socket;
	private String login;
	
	private ArrayList<Carte> cartes;
	private int personnage;
	
	public Joueur(Socket socket , String login){
		this.socket = socket;
		this.login = login;
		cartes = new ArrayList<Carte>();
	}
	
	public void afficheperso(){
		System.out.println(personnage);
	}
	
	public void affichepioche(){
		for(int i = 0; i < cartes.size(); i++){
			System.out.print(cartes.get(i).getValeur());
		}
		System.out.println();
	}
	
	
	public void addcarte(Carte j){
		cartes.add(j);
	}
	
	public int jouercarte(int j){
		cartes.get(j).setJoué(true);
		return cartes.get(j).getValeur();
	}
	
	public void supprcarte(int j){
		cartes.remove(j);
	}
	
	public void reconstruire(){
		for(int i = 0; i < cartes.size(); i++){
			if(cartes.get(i).isJoué()){
				cartes.remove(i);
				i--;
			}
		}
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

	public ArrayList<Carte> getCartes() {
		return cartes;
	}

	public void setCartes(ArrayList<Carte> cartes) {
		this.cartes = cartes;
	}

	public int getPersonnage() {
		return personnage;
	}

	public void setPersonnage(int personnage) {
		this.personnage = personnage;
	}
	
	

}
