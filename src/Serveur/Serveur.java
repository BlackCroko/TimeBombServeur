package Serveur;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Serveur {
 public static ServerSocket ss = null;
 public static ArrayList<Game> games = new ArrayList<Game>();
 public static Salon salon = new Salon();
 public static Thread t;

 
	public static void main(String[] args) {
		
		try {
			ss = new ServerSocket(2009);
			System.out.println("Le serveur est à l'écoute du port "+ss.getLocalPort());
			
			t = new Thread(new Accepter_connexion(ss, games, salon));
			t.start();

			
		} catch (IOException e) {
			System.err.println("Le port "+ss.getLocalPort()+" est déjà utilisé !");
		}
	
	}

	
	}

