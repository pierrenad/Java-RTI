package rti_partie2;

import log.LogServeur;
import java.net.*;
import java.io.*; 
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import rti_interface.*;
import rti_Windows.*;

public class ThreadServer extends Thread {
    private int port;
    private SourceTache tachesAExecuter; 
    private ConsoleServeur logServ; 
    private ServerSocket SSocket = null;
    private int maxClient = 3; 
    List<Socket> listCli;
    List<Thread> listThread = null;

    // avant admin 
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

    /*
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
    }*/
    
    // avec admin 
    public ThreadServer(int p, SourceTache st, ConsoleServeur fs, List<Socket> listc, List<Thread> listT) {
       port = p; tachesAExecuter = st; logServ = fs;
       listCli = listc;
       listThread = listT;
       this.setName("Serveur (" + getName() +")");
    }
    
    public void run() {
        try {
           SSocket = new ServerSocket(port);
        }
        catch (IOException e) {
           logServ.TraceEvenements("ServeurCompagnie : IOException Erreur de port d'écoute " + e.getMessage());
           System.exit(1);
        }
        // Démarrage du pool de threads
        logServ.TraceEvenements("ServeurCompagnie :Creation des threads du pool de thread");
        for (int i=0; i<3; i++) { // 3 devrait être constante ou une propriété du fichier de config
            ThreadClient thr;
            thr = new ThreadClient (tachesAExecuter, "Thread du pool n" + String.valueOf(i));
            thr.start();
            
            listThread.add(thr);
        }

        // Mise en attente du serveur
        Socket CSocket = null;
        while (!isInterrupted()) {
            try {
                logServ.TraceEvenements("ServeurCompagnie : attente sur accept");
                CSocket = SSocket.accept();
                if(port != 59002) { // On accepte des clients et pas des admins
                    listCli.add(CSocket);
                }
                logServ.TraceEvenements(CSocket.getRemoteSocketAddress().toString()+"#accept#thread serveurCompagnie");
            }
            catch (IOException e) {
                logServ.TraceEvenements("ServeurCompagnie : IOException Erreur d'accept " + e.getMessage());
                System.exit(1);
            }

            ObjectInputStream ois=null;
            Requete req = null;
            try {
                ois = new ObjectInputStream(CSocket.getInputStream());
                req = (Requete)ois.readObject();
                logServ.TraceEvenements("ServeurCompagnie : Requete lue par le serveur");
                
            }
            catch (ClassNotFoundException e) {
                logServ.TraceEvenements("ServeurCompagnie : ClassNotFoundException Erreur de def de classe " + e.getMessage());
            }
            catch (IOException e) {
                logServ.TraceEvenements("ServeurCompagnie : IOException " + e.getMessage());
            }

            Runnable travail;
            try {
               
                travail = req.createRunnable(CSocket, logServ, ois, new ObjectOutputStream(CSocket.getOutputStream()));
               
                if (travail != null) {
                    tachesAExecuter.recordTache(travail);
                    logServ.TraceEvenements("ServeurCompagnie : Travail mis dans la file d'attente");
                }
            else logServ.TraceEvenements("ServeurCompagnie : Pas de mise en file");
            } catch (IOException ex) {
               Logger.getLogger(ThreadServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
} 

