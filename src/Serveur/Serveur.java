package Serveur;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import Entite.Game;

public class Serveur {
 public static ServerSocket ss = null;
 public static ArrayList<Game> games;
 public static Salon salon;
 public static Thread t;
 private static Date date;

 
	public static void main(String[] args) {
		
		try {
			 date = Calendar.getInstance().getTime();
				String[] infos = date.toString().split(" ");
				int mois = date.getMonth()+1;
				String heure = infos[3].replaceAll(":", "-");
				heure = heure.substring(0, 5);
				String total = "log_"+infos[2]+"-"+mois+"-"+infos[5]+"_"+heure+".txt";
				File actuel = new File(System.getProperty("user.dir"));
				File nouv = new File(actuel.getPath()+"/"+"log");
				nouv.mkdirs();
			PrintStream log = new PrintStream(new FileOutputStream(nouv+"/"+total));
			System.setOut(log);

			ss = new ServerSocket(2009);
			System.out.println("Le serveur est à l'écoute du port "+ss.getLocalPort());
			games = new ArrayList<Game>();
			salon = new Salon(games);
			t = new Thread(new Accepter_connexion(ss, games, salon));
			t.start();

			
		} catch (IOException e) {
			System.err.println("Le port "+ss.getLocalPort()+" est déjà utilisé !");
		}
	
	}

	
	}

