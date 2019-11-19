/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EBOOP;

import CHECKINAP.ReponseProtocol;
import CHECKINAP.RequeteProtocol;
import static CHECKINAP.RequeteProtocol.LOGIN_WEB;
import db.facilities.GestionBD;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.sql.ResultSet;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import log.LogServeur;
import rti_interface.ConsoleServeur;
import rti_interface.Requete;


/**
 *
 * @author Pierre
 */
public class RequeteEBOOP implements Requete, Serializable {     
    private int type;
    private Socket socketCli;
    private String charge; 
    private static Properties hashtable = new Properties();    
    
    public static final int REGISTER = 1;

    
    private String chargeUtile;
    
    ObjectInputStream in;
    
    public Properties getHashtable(){return hashtable;}

    public void setChargeUtile(String chargeUtile) {
        this.chargeUtile = chargeUtile;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public String getChargeUtile() {
        return chargeUtile;
    }

    public RequeteEBOOP(int t, String ch) {
        type = t;
        charge = ch;
    }
    public RequeteEBOOP(int t, Socket s, String ch) {
        type = t;
        socketCli = s;
        charge = ch;
    }

    @Override
    public Runnable createRunnable(final Socket s, final ObjectInputStream ois, final LogServeur ls) {        
        if (type==REGISTER)
            return new Runnable()
            {
                public void run()
                {
                    RequeteRegister(s, ls); 
                }
            }; 
        else return null; 
        
        /*return new Runnable() {
            @Override
            public void run() {
                
                else
                {
                    do
                    {
                       RequeteEBOOP req = null;
                       try
                       {
                           in = new ObjectInputStream(socketCli.getInputStream()); 
                           req = (RequeteEBOOP)in.readObject(); // Bloquant, on attend la prochaine requete
                           System.out.println("> Requete lue par le pas serveur, instance de " + req.getClass().getName());
                           setChargeUtile(req.getChargeUtile());
                       } 
                       catch (ClassNotFoundException e)
                       {
                           System.err.println("> Erreur de def de classe [" + e.getMessage() + "]");
                       }
                       catch (IOException e)
                       {
                           System.err.println("> IOEXCEPTION Requete EBOOP Erreur ? [" + e.getMessage() + "]");
                       }


                       switch(req.type)
                       {
                           case REGISTER  :   RequeteRegister(s, cs, oos); 
                                                   break;
                           default :   System.out.println("Code inconnu : " + type);
                                       break;

                       }

                    }while(type != ReponseEBOOP.CLOSE); 
                }
            }
        };*/
    }
    
    public void RequeteRegister(Socket s, ConsoleServeur cs)
    {
        GestionBD gdb = new GestionBD(); 
        ObjectOutputStream oos;
        ReponseEBOOP rep; 
        
        StringTokenizer strtok = new StringTokenizer(this.charge, "#");
        //String numcli = strtok.nextToken();
        String name = strtok.nextToken();
        String fstName = strtok.nextToken();
        String adr = strtok.nextToken();
        String mail = strtok.nextToken();
        String country = strtok.nextToken();
        
        String[] titu = new String[7]; 
        //titu[0] = numcli;
        titu[1] = name;
        titu[2] = fstName;
        titu[5] = adr;
        titu[4] = mail; 
        titu[6] = country;
        
        try {
            gdb.connection("HappyFerryDB","user","user"); 
            String id = gdb.AjoutClients(titu, null); 
            
            rep = new ReponseEBOOP(ReponseEBOOP.AJOUT_OK, id); 

            try {
                oos = new ObjectOutputStream(s.getOutputStream());
                oos.writeObject(rep); 
                oos.flush();
            } 
            catch (IOException e) {
                System.err.println("<RequeteEBOOP> Erreur réseau "+  e.getMessage());
            }
        }
        catch(Exception e) {
            System.err.println("<Register> " + e.getMessage());
        } 
    }
}
