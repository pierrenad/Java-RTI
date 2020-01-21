/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CHECKCARP;

import db.facilities.GestionBD;
import rti_interface.Requete;
import rti_interface.ConsoleServeur;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.sql.ResultSet;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import log.LogServeur;


/**
 *
 * @author Pierre
 */
public class RequeteProtocolCard implements Requete, Serializable{
    
    public static int CHECK_CARD = 1;
    public static int CLOSE = 2;
    public static int CHECK_CARD2 = 3; 
    
    private int type;
    private Socket socketClient;
    
    private String charge;
    
    public RequeteProtocolCard(int t, String ch) {
        type = t;
        charge = ch;
    }
    public RequeteProtocolCard(int t, Socket s, String ch) {
        type = t;
        socketClient = s;
        charge = ch;
    }

    @Override
    public Runnable createRunnable(Socket s, ObjectInputStream ois, ObjectOutputStream oos) {
        if(type == CHECK_CARD) {
            return new Runnable() {
                public void run() {
                    RequeteCheckCard(s);
                }
            }; 
        }
        else if(type == CHECK_CARD2) {
            return new Runnable() {
                public void run() {
                    RequeteCheckCard2(s);
                }
            }; 
        }
        else if(type == CLOSE) {
            return new Runnable() {
                public void run() {
                    RequeteClose(s);
                }
            }; 
        }
        else return null;
    }
    
    @Override
    public Runnable createRunnable(Socket s, ConsoleServeur cs, ObjectInputStream ois, ObjectOutputStream oos) { return null; }
    
    private void RequeteCheckCard2(Socket s) {
        StringTokenizer strtok = new StringTokenizer(this.charge, "#");
        String numcarte = strtok.nextToken();
        String code = strtok.nextToken();
        
        GestionBD gdb = new GestionBD(); 
        try {
            gdb.connection("DB_CARD","user","user"); 
            ResultSet resSet = gdb.CheckPayement(numcarte,code);
            ReponseProtocolCard rep; 
            if(!resSet.next()) { 
                rep = new ReponseProtocolCard(ReponseProtocolCard.CODEPASOK,"The card have bad informations");
            }
            else { 
                
                int argent = resSet.getInt(1);
                System.out.println(""+argent);
                strtok.nextToken(); // date on s'en fou 
                float prix = Float.parseFloat(strtok.nextToken()); // prix 
                if(argent<prix) { 
                    rep = new ReponseProtocolCard(ReponseProtocolCard.CARTEPASOK,"You don't have enough money on this card");
                }
                else {
                    rep = new ReponseProtocolCard(ReponseProtocolCard.CARTEOK,"The payement is done");
                } 
            } 
            ObjectOutputStream oos;
            oos = new ObjectOutputStream(s.getOutputStream());
            oos.writeObject(rep);
            oos.flush();
        }
        catch(Exception e) {
            System.err.println("<CheckCard> " + e.getMessage());
        } 
    }
    
    private void RequeteCheckCard(Socket s) {
        StringTokenizer strtok = new StringTokenizer(this.charge, "#");
        String numcarte = strtok.nextToken();
        String code = strtok.nextToken();
        
        GestionBD gdb = new GestionBD(); 
        try {
            gdb.connection("DB_CARD","user","user"); 
            ResultSet resSet = gdb.CheckPayement(numcarte,code);
            ReponseProtocolCard rep; 
            if(!resSet.next()) { 
                rep = new ReponseProtocolCard(ReponseProtocolCard.CODEPASOK,"");
            }
            else { 
                
                int argent = resSet.getInt(1);
                System.out.println(""+argent);
                if(argent<100) {
                    rep = new ReponseProtocolCard(ReponseProtocolCard.CARTEPASOK,"");
                }
                else {
                    rep = new ReponseProtocolCard(ReponseProtocolCard.CARTEOK,"");
                } 
            } 
            ObjectOutputStream oos;
            oos = new ObjectOutputStream(s.getOutputStream());
            oos.writeObject(rep);
            oos.flush();
        }
        catch(Exception e) {
            System.err.println("<CheckCard> " + e.getMessage());
        } 
    }
    
    private void RequeteClose(Socket s) {
        try {
            s.close();
        } catch (IOException ex) {
            Logger.getLogger(RequeteProtocolCard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
