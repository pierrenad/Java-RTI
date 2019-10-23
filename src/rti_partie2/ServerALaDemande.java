/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rti_partie2;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;
import log.LogServeur;
import rti_interface.Requete;

/**
 *
 * @author Pierre
 */
public class ServerALaDemande extends Thread {
    private int port;
    private ListeTaches tachesAExecuter; 
    private LogServeur logServ; 
    private ServerSocket SSocket = null;
    private int maxClient = 3; 

    public ServerALaDemande(int p, ListeTaches lt, LogServeur ls) { 
       port = p; tachesAExecuter = lt; logServ = ls;

       try { 
            FileInputStream in = new FileInputStream("donnees.properties");
            Properties data = new Properties();
            data.load(in);
            maxClient = Integer.parseInt((String) data.get("maxClient"));
        } catch (Exception e) {
            System.err.println("<Server A Demande> " + e.getMessage()); 
        }
    }

    @Override
    public void run()
    {
        try {
           SSocket = new ServerSocket(port);
        }
        catch (IOException e) {
           System.err.println("<Server A Demande> Erreur de port : " + e.getMessage()); 
           System.exit(1);
        }

        // Mise en attente du serveur
        Socket CSocket = null;
        int i=0; 
        while (!isInterrupted()) {
            try {
                System.out.println("<Server A Demande> ************ Serveur en attente");
                logServ.TraceEvenements("************ Serveur en attente # server a demande");
                CSocket = SSocket.accept();
                logServ.TraceEvenements(CSocket.getRemoteSocketAddress().toString()+" # accept # server a demande");
                logServ.TraceEvenements("Démarrage d'un thread # server a demande"); 
                ThreadClient th = new ThreadClient (tachesAExecuter, "Thread du pool n°" + String.valueOf(i), logServ); 
                th.start();
                i++; 
            }
            catch (IOException e) {
                System.err.println("<Server A Demande> Erreur d'accept : " + e.getMessage()); 
                System.exit(1);
            }

            ObjectInputStream ois= null; 
            Requete req = null;
            try {
                ois = new ObjectInputStream(CSocket.getInputStream());
                req = (Requete)ois.readObject();
                System.out.println("<Server A Demande> Requete lue par le serveur");
                logServ.TraceEvenements("Requete lue par le serveur # server a demande");
            }
            catch (Exception e) {
                System.err.println("<Server A Demande> Erreur " + e.getMessage()); 
            } 

            Runnable travail = req.createRunnable(CSocket, ois, logServ);
            if (travail != null) {
                tachesAExecuter.recordTache(travail);
                System.out.println("<Server A Demande> Travail mis dans la file");
                logServ.TraceEvenements("Ajout d'un travail dans la file # server a demande");
            }
            else {
                System.out.println("<Server A Demande> Pas de mise en file");
                logServ.TraceEvenements("Pas de mise en file # server a demande");
            }
        }
    }
}
