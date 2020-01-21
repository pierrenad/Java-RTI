/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rti_partie2;

import Admin.AdminProtocol;
import java.io.FileInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import log.LogServeur;
import rti_Windows.LoginWindow;

/**
 *
 * @author Pierre
 */
public class MainProgram {
    
    public static List<Socket> listClient;
    public static List<Thread> listThread;
    
    public static void main(String[] args) {
        int port = 59000;
        int portC = 59001; 
        // ajout pour admin
        int portAdmin = 59002; 
        
        try {
            FileInputStream in = new FileInputStream("donnees.properties"); 
            Properties data = new Properties();
            data.load(in);
            
            port = Integer.parseInt((String) data.getProperty("port"));
            portC = Integer.parseInt((String) data.getProperty("portC"));
            // pour admin 
            portAdmin = Integer.parseInt((String) data.getProperty("portAdmin"));
        } catch (Exception e) {
            System.err.println("<Main> " + e.getMessage());
        }
        
        ListeTaches lt = new ListeTaches(); 
        LogServeur ls = new LogServeur(); 

        // pour admin 
        listClient = new ArrayList<Socket>();
        listThread = new ArrayList<Thread>();
        
        ThreadServer serv = new ThreadServer(port, lt, ls, listClient, listThread); 
        Thread th = new Thread(serv); 
        th.start(); 
        
        // admin 
        AdminProtocol admin = new AdminProtocol(portAdmin, listClient, listThread); 
        Thread t = new Thread(admin); 
        t.start();
        // admin 
        listThread.add(th);
        
        ServerALaDemande servD = new ServerALaDemande(portC, lt, ls); 
        Thread thd = new Thread(servD); 
        thd.start(); 
        
        /*LoginWindow logWin = new LoginWindow(); // a lancer manuellement (oupas) 
        logWin.setVisible(true); */
    }
}
