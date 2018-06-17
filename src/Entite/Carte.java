package Entite;

public class Carte {
	
	private int valeur;
	private boolean joué;
	
	public Carte(int v){
		valeur = v;
		joué = false;
	}

	public int getValeur() {
		return valeur;
	}

	public void setValeur(int valeur) {
		this.valeur = valeur;
	}

	public boolean isJoué() {
		return joué;
	}

	public void setJoué(boolean joué) {
		this.joué = joué;
	}
	

}
