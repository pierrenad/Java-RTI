package rti_partie2;

import log.LogServeur;
import java.net.*;
import java.io.*; 
import java.util.Properties;
import rti_interface.*;
import rti_Windows.*;

public class ThreadServer extends Thread {
    private int port;
    private ListeTaches tachesAExecuter; 
    private LogServeur logServ; 
    private ServerSocket SSocket = null;
    private int maxClient = 3; 

    public ThreadServer(int p, ListeTaches lt, LogServeur ls) { 
       port = p; tachesAExecuter = lt; logServ = ls;

       try { 
            FileInputStream in = new FileInputStream("donnees.properties");
            Properties data = new Properties();
            data.load(in);
            maxClient = Integer.parseInt((String) data.get("maxClient"));
        } catch (Exception e) {
            System.err.println("<ThreadServer> " + e.getMessage()); 
        }
    }

    @Override
    public void run()
    {
        ServerWindow servWin = new ServerWindow(); 
        servWin.setVisible(true); 
        logServ.servWin = servWin; 
        
        try {
           SSocket = new ServerSocket(port);
        }
        catch (IOException e) {
           System.err.println("<ThreadServer> Erreur de port : " + e.getMessage()); 
           System.exit(1);
        }
        // Démarrage du pool de threads
        logServ.TraceEvenements("Démarrage pool de thread # thread serveur"); 
        for (int i=0; i<maxClient; i++) { 
           ThreadClient th = new ThreadClient (tachesAExecuter, "Thread du pool n°" + String.valueOf(i), logServ); 
           th.start();
        } 

        // Mise en attente du serveur
        Socket CSocket = null;
        while (!isInterrupted()) {
            try {
                System.out.println("<ThreadServer> ************ Serveur en attente");
                logServ.TraceEvenements("************ Serveur en attente # thread serveur");
                CSocket = SSocket.accept();
                logServ.TraceEvenements(CSocket.getRemoteSocketAddress().toString()+" # accept # thread serveur");
            }
            catch (IOException e) {
                System.err.println("<ThreadServer> Erreur d'accept : " + e.getMessage()); 
                System.exit(1);
            }

            ObjectInputStream ois= null; 
            Requete req = null;
            try {
                ois = new ObjectInputStream(CSocket.getInputStream());
                req = (Requete)ois.readObject();
                System.out.println("<ThreadServer> Requete lue par le serveur");
                logServ.TraceEvenements("Requete lue par le serveur # thread serveur");
            }
            catch (Exception e) {
                System.err.println("<ThreadServer> Erreur " + e.getMessage()); 
            } 

            Runnable travail = req.createRunnable(CSocket, ois, logServ);
            if (travail != null) {
                tachesAExecuter.recordTache(travail);
                System.out.println("<ThreadServer> Travail mis dans la file");
                logServ.TraceEvenements("Ajout d'un travail dans la file # thread serveur");
            }
            else {
                System.out.println("<ThreadServer> Pas de mise en file");
                logServ.TraceEvenements("Pas de mise en file # thread serveur");
            }
        }
    }
} 

