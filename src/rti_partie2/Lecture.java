/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rti_partie2;

import network.Network;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import rti_Windows.CustomerWindow;

/**
 *
 * @author Pierre
 */
public class Lecture extends Thread {
    Network n = null;
    
    ServerSocket SSocket = null;
    
    DataInputStream dis = null;
    DataOutputStream dos = null;
    CustomerWindow mw =null;
    boolean enabled = true;
            
    
    public Lecture(CustomerWindow m) {
        n = new Network();
        mw = m;
    }
            
    public void run() {
        System.out.println("Démarrage du thread lecture");
        
        try {
            SSocket = new ServerSocket(59003);
        }
        catch (IOException e) {
            System.out.println("IO Exception : " + e.getMessage());
            System.exit(1);
        }
        
        Socket CSocket = null;
        while (!isInterrupted()) {
            System.out.println("Dans le while");
            try {
                try {
                    CSocket = SSocket.accept();
                    System.out.println("Client accepté dans le thread lecture");
                }
                catch (IOException e) {
                    System.out.println("IO Exception :  Erreur sur l'accept : " + e.getMessage());
                    System.exit(1);
                }
                
                dis = new DataInputStream(CSocket.getInputStream());
                dos = new DataOutputStream(CSocket.getOutputStream());
                
                byte[] mb = new byte[100];
                
                System.out.println("avant le read du thread lecture");
                dis.read(mb);
                System.out.println("Message lu");
                
                String message = new String(mb);
                
                StringTokenizer st = new StringTokenizer(message, "#");
                
                String type = st.nextToken();
                //String chargeUtile = st.nextToken();
                
                switch(type) {
                    case "SUSPEND"  :   requeteSUSPEND();
                    break;
                    
                    case "SHUTDOWN" :   requeteSHUTDOWN();
                    break;
                    default         :   System.out.println("CODE INCONNU");
                    
                }
            }
            catch (IOException ex) {
                Logger.getLogger(Lecture.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void requeteSUSPEND() {
        System.out.println("Dans le suspend du thread lecture");
        
        if(enabled) {
            JOptionPane.showMessageDialog(mw, "Serveur en pause", "PAUSE", JOptionPane.PLAIN_MESSAGE);
            mw.setEnabled(false);
            enabled = false;
        }
        else {
            JOptionPane.showMessageDialog(mw, "Reprise du serveur", "DEMARRAGE", JOptionPane.PLAIN_MESSAGE);
            mw.setEnabled(true);
            enabled = true;
        }
    }
    
    public void requeteSHUTDOWN() {
        for(int i = 10; i > 0; i--) {
            JOptionPane jop = new JOptionPane();
            jop.setMessageType(JOptionPane.PLAIN_MESSAGE);
            jop.setMessage("Le serveur de coupe dans " + i + " secondes");
            JDialog dialog = jop.createDialog(null, "Fermeture");

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                    }
                    dialog.dispose();
                }
            }).start();

            dialog.setVisible(true);
        }
        
        System.exit(1);
        
        /*JOptionPane jop = new JOptionPane();
        jop.setMessageType(JOptionPane.PLAIN_MESSAGE);
        jop.setMessage("Fermeture du serveur dans 10 secondes !");
        JDialog dialog = jop.createDialog(null, "Fermeture");
        dialog.setVisible(true);
        
        for(int i = 10; i > 0; i--) {
            System.out.println("Temps restant : " + i + " s");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                    }
                    //dialog.dispose();
                }
            }).start();
            
            //dialog.setVisible(true);
        }
        
        dialog.dispose();
        System.exit(1);*/
        
        /*new Thread(new Runnable() {
                @Override
                public void run() {
                    JOptionPane jop = new JOptionPane();
                    jop.setMessageType(JOptionPane.PLAIN_MESSAGE);
                    jop.setMessage("Fermeture du serveur dans 10 secondes !");
                    JDialog dialog = jop.createDialog(null, "Fermeture");
                    dialog.setVisible(true);
                    try {
                        Thread.sleep(10000);
                    } catch (Exception e) {
                    }
                    dialog.dispose();
                }
            }).start();
        
        System.exit(1); */
    }
}
