package Entite;

public class Carte {
	
	private int valeur;
	private boolean jou�;
	
	public Carte(int v){
		valeur = v;
		jou� = false;
	}

	public int getValeur() {
		return valeur;
	}

	public void setValeur(int valeur) {
		this.valeur = valeur;
	}

	public boolean isJou�() {
		return jou�;
	}

	public void setJou�(boolean jou�) {
		this.jou� = jou�;
	}
	

}
