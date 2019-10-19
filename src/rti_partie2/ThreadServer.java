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
        
        try {
           SSocket = new ServerSocket(port);
        }
        catch (IOException e) {
           System.err.println("<ThreadServer> Erreur de port : " + e.getMessage()); 
           System.exit(1);
        }
        // Démarrage du pool de threads
        for (int i=0; i<maxClient; i++) { 
           ThreadClient th = new ThreadClient (tachesAExecuter, "Thread du pool n°" + String.valueOf(i));
           th.start();
        } 

        // Mise en attente du serveur
        Socket CSocket = null;
        while (!isInterrupted()) {
            try {
                System.out.println("************ Serveur en attente");
                CSocket = SSocket.accept();
                logServ.addLog(CSocket.getRemoteSocketAddress().toString()+"#accept#thread serveur", servWin);
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
            }
            catch (Exception e) {
                System.err.println("<ThreadServer> Erreur " + e.getMessage()); 
            } 

            System.out.println("serv1");
            Runnable travail = req.createRunnable(CSocket, ois, logServ);
            if (travail != null) {
                tachesAExecuter.recordTache(travail);
                System.out.println("<ThreadServer> Travail mis dans la file");
            }
            else 
                System.out.println("<ThreadServer> Pas de mise en file");
        }
    }
} 

