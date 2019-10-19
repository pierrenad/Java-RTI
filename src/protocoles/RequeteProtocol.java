/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package protocoles;

import db.facilities.GestionBD;
import java.io.FileInputStream;
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
import rti_interface.ConsoleServeur;
import rti_interface.Requete;

/**
 *
 * @author Pierre
 */
public class RequeteProtocol implements Requete, Serializable {
    
    public static final int LOGIN = 1;
    public static final int VERIF_BOOKING = 2; 
    public static final int BUY_TICKET = 3;
    public static final int CLOSE = 4;
    
    private int type;
    private Socket socketCli;
    private String charge;
    
    private static Properties hashtable = new Properties();
    public Properties getHashtable(){return hashtable;}
    
    public RequeteProtocol(int t, String ch) {
        type = t;
        charge = ch;
    }
    public RequeteProtocol(int t, Socket s, String ch) {
        type = t;
        socketCli = s;
        charge = ch;
    }

    public Runnable createRunnable(final Socket s, final ObjectInputStream ois, final ConsoleServeur cs) {
        if (type==LOGIN)
            return new Runnable()
            {
                public void run()
                {
                    requeteLogin(s, cs); 
                }
            }; 
        else if (type==VERIF_BOOKING)
            return new Runnable()
            {
                public void run()
                {
                    requeteVerifBooking(s, cs); 
                    System.out.println("req1");
                }
            };
        else if (type==BUY_TICKET)
            return new Runnable()
            {
                public void run()
                {
                    requeteBuyTicket(s, cs);
                }
            };
        else if (type==CLOSE)
            return new Runnable()
            {
                public void run()
                {
                    requeteClose(s, cs);
                }
            }; 
        else return null;
        /*return new Runnable() {
            @Override
            public void run() {
                if(!requeteLogin(s, cs)) {
                    try {
                        s.close();
                    } catch (IOException ex) {
                        Logger.getLogger(RequeteProtocol.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                else {
                    do {
                       Requete req = null;
                        try {
                            req = (Requete)ois.readObject();
                            System.out.println("> Requete lue par le serveur, instance de " + req.getClass().getName());
                        }
                        catch (Exception e) {
                            System.err.println("<RequeteProtocole> Erreur " + e.getMessage());
                        } 

                        switch(type)
                        {
                            case VERIF_BOOKING  :   requeteVerifBooking(s, cs);
                                                    break;
                            case BUY_TICKET :   requeteBuyTicket(s, cs);
                                                break;
                            case CLOSE  :   requeteClose(s, cs);
                                            break;
                            default :   System.out.println("Code inconnu : " + type);
                                        break;
                        }
                    }while(true);
                }
            }
        };*/
    }
    
    private boolean requeteLogin(Socket sock, ConsoleServeur cs) { 
        StringTokenizer st = new StringTokenizer(this.charge, "#");
        ObjectOutputStream oos;
        
        try {
            FileInputStream in = new FileInputStream("config.properties");
            getHashtable().load(in);
        } catch (Exception ex) {
            System.err.println("<RequeteProtocole> " + ex.getMessage()); 
        } 
        String login = st.nextToken();
        String pwd = st.nextToken();
        
        try {
            String log = (String)getHashtable().get(pwd);
            if(log!=null) {
                if(log.equals(login)) {
                    System.out.println("Logged");
                    ReponseProtocol rep = new ReponseProtocol(ReponseProtocol.LOGIN_OK, "");
                    try {
                        oos = new ObjectOutputStream(sock.getOutputStream());
                        oos.writeObject(rep); 
                        oos.flush();
                        return true; 
                    } 
                    catch (IOException e) {
                        System.err.println("<RequeteProtocole> Erreur réseau "+  e.getMessage());
                    }
                }
                else {
                    System.err.println("Erreur de login"); 
                }
            }
            else {
                System.err.println("Erreur de password"); 
            }
        }
        catch(Exception e) {
            System.err.println("<RequeteProtocole> Mauvaises entrées login/password"); 
            ReponseProtocol rep = new ReponseProtocol(ReponseProtocol.LOGIN_PASOK , "");
 
            try {
                oos = new ObjectOutputStream(sock.getOutputStream());
                oos.writeObject(rep);
                oos.flush();
                return false; 

            } catch (IOException ex) { 
                Logger.getLogger(RequeteProtocol.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }
        return false; 
    }
    private void requeteVerifBooking(Socket s, ConsoleServeur cs) {
        StringTokenizer strtok = new StringTokenizer(this.charge, "#");
        String reser = strtok.nextToken();
        int nbpass = Integer.parseInt(strtok.nextToken());
        
        GestionBD gdb = new GestionBD(); 
        try {
            gdb.connection("HappyFerryDB","user","user"); 
            ResultSet resSet = gdb.CheckReservation(reser,nbpass);
            ReponseProtocol rep; 
            if(!resSet.next()) { 
                rep = new ReponseProtocol(ReponseProtocol.RESERV_PASOK,"");
            }
            else { 
                String str = (resSet.getString(1)+"\t\t"+resSet.getString(2)+"\t"+resSet.getString(3)+"\t"+resSet.getString(4)+"\t"+resSet.getString(5)+"\n");
                rep = new ReponseProtocol(ReponseProtocol.RESERV_OK,str);
            } 
            ObjectOutputStream oos;
            oos = new ObjectOutputStream(s.getOutputStream());
            oos.writeObject(rep);
            oos.flush();
        }
        catch(Exception e) {
            System.err.println("<VerifBooking> " + e.getMessage());
        } 
    }
    
    private void requeteBuyTicket(Socket s, ConsoleServeur cs) {
        StringTokenizer strtok = new StringTokenizer(this.charge, "#");
        String[] titu = new String[7];
        for(int i=0;i<7;i++)
            titu[i]=strtok.nextToken();
        
        int nbpassager = Integer.parseInt(strtok.nextToken());
        
        String[][] passager = new String[nbpassager][5]; 
        
        for(int i=0;i<nbpassager;i++)
        {
            for(int j=0;j<5;j++)
                passager[i][j]=strtok.nextToken();
        }
        GestionBD gdb = new GestionBD();
        ReponseProtocol rep;
        String[] tmp = new String[5]; 
        String id; 
        tmp = passager[0]; 
        try{
            gdb.connection("HappyFerryDB","user","user");   
            id = gdb.AjoutClients(titu, null);
            gdb.AjoutClients(tmp, id);  
            rep = new ReponseProtocol(ReponseProtocol.ACHAT_OK,"");
        }
        catch(Exception e) {
            rep = new ReponseProtocol(ReponseProtocol.ACHAT_PASOK,"");
            System.err.println("<BuyTicket> " + e.getMessage());
        } 
        try {
            ObjectOutputStream oos;
            oos = new ObjectOutputStream(s.getOutputStream());
            oos.writeObject(rep);
            oos.flush();
        }
        catch(Exception e) { 
            System.err.println("<BuyTicket> " + e.getMessage());
        } 
    }
    
    private void requeteClose(Socket sock, ConsoleServeur cs) {
        System.exit(1); 
    }
}
