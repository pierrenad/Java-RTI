package rti_partie2;

import java.net.*;
import java.io.*; 
import rti_interface.*;
import rti_Windows.*;

public class Server extends Thread{
 private int port;
 private SourceTache tachesAExecuter;
 private ConsoleServeur guiApplication;
 private ServerSocket SSocket = null;
 
 public Server(int p, SourceTache st, ConsoleServeur fs)
 {
    port = p; tachesAExecuter = st; guiApplication = fs;
 }

 public void run()
 {
    try
    {
       SSocket = new ServerSocket(port);
    }
    catch (IOException e)
    {
       System.err.println("Erreur de port d'écoute ! ? [" + e + "]"); System.exit(1);
    }
    // Démarrage du pool de threads
    for (int i=0; i<3; i++) // 3 devrait être constante ou une propriété du fichier de config
    {
       Client thr = new Client (tachesAExecuter, "Thread du pool n°" + String.valueOf(i));
       thr.start();
    } 
    
    // Mise en attente du serveur
    Socket CSocket = null;
    while (!isInterrupted())
    {
        try
        {
            System.out.println("************ Serveur en attente");
            CSocket = SSocket.accept();
            guiApplication.TraceEvenements(CSocket.getRemoteSocketAddress().toString()+"#accept#thread serveur");
        }
        catch (IOException e)
        {
            System.err.println("Erreur d'accept ! ? [" + e.getMessage() + "]"); System.exit(1);
        }

        ObjectInputStream ois=null;
        Requete req = null;
        try
        {
            ois = new ObjectInputStream(CSocket.getInputStream());
            req = (Requete)ois.readObject();
            System.out.println("Requete lue par le serveur, instance de " + req.getClass().getName());
        }
        catch (ClassNotFoundException e)
        {
            System.err.println("Erreur de def de classe [" + e.getMessage() + "]");
        }
        catch (IOException e)
        {
            System.err.println("Erreur ? [" + e.getMessage() + "]");
        }

        Runnable travail = req.createRunnable(CSocket, guiApplication);
        if (travail != null)
        {
            tachesAExecuter.recordTache(travail);
            System.out.println("Travail mis dans la file");
        }
        else 
            System.out.println("Pas de mise en file");
    }
  }
} 

