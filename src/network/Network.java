/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Properties;
import rti_interface.*;

/**
 *
 * @author Pierre
 */
public class Network {
    
    public Network() {
    }
    
    public Socket Init() {
        Socket cSock;
        String adresse = "192.168.1.55";
        int port = 59000; 

        /*try {
            FileInputStream in = new FileInputStream("donnees.properties");
            Properties data = new Properties();
            data.load(in);
            adresse = (String)data.get("ip");
            port = Integer.parseInt((String) data.getProperty("port"));
        } catch (Exception e) { 
            System.err.println("<Network-Init> " + e.getMessage()); 
        }*/

        cSock = null;
        try {
            cSock = new Socket(adresse, port); 
        }
        catch (Exception e) {
            System.err.println("<Network-Init> Erreur ! Aucune correspondance serveur trouvée : " + e.getMessage()); 
        }

        return cSock;
    }
    
    public Socket InitOnDemand() {
        Socket cSock;
        String adresse = "192.168.1.55";
        int port = 59001; 
                
        /*try {
            FileInputStream in = new FileInputStream("donnees.properties");
            Properties data = new Properties();
            data.load(in);
            adresse = (String)data.get("ip");
            port = Integer.parseInt((String) data.getProperty("portC"));
        } catch (Exception e) { 
            System.err.println(e.getMessage()); 
        }*/
        
        cSock = null;
        try {
            cSock = new Socket(adresse, port); 
        }
        catch (Exception e) {
            System.err.println("<Network-Init> Erreur ! Aucune correspondance serveur trouvée : "+ e.getMessage()); 
        }
        
        return cSock;
    }
    
    public void SendRequest(Socket cSock, Requete req) {
        ObjectOutputStream oos;

        try {
            oos = new ObjectOutputStream(cSock.getOutputStream()); 
            oos.writeObject(req);
            oos.flush();
        } 
        catch (Exception e) {
            System.err.println("<Network-SendRequest> " + e.getMessage()); 
        }
    }
}
