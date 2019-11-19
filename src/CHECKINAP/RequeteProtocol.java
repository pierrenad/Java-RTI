/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CHECKINAP;

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
import log.LogServeur;
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
    public static final int LISTE_CLIENTS = 5;
    public static final int LOGIN_WEB = 6; 
    
    public static final long serialVersionUID = 683388106844655395L; // Pas touche 
    
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

    @Override
    public Runnable createRunnable(final Socket s, final ObjectInputStream ois, final LogServeur ls) {
        if (type==LOGIN)
            return new Runnable()
            {
                public void run()
                {
                    requeteLogin(s, ls); 
                }
            }; 
        else if (type==VERIF_BOOKING)
            return new Runnable()
            {
                public void run()
                {
                    requeteVerifBooking(s, ls); 
                }
            };
        else if (type==BUY_TICKET)
            return new Runnable()
            {
                public void run()
                {
                    requeteBuyTicket(s, ls);
                }
            };
        else if (type==CLOSE)
            return new Runnable()
            {
                public void run()
                {
                    requeteClose(s, ls);
                }
            }; 
        else if (type==LISTE_CLIENTS)
            return new Runnable()
            {
                public void run()
                {
                    requeteListeClients(s, ls);
                }
            }; 
        else if (type==LOGIN_WEB)
            return new Runnable()
            {
                public void run()
                {
                    LoginWeb(s, ls); 
                }
            }; 
        else return null; 
    }
    
    private void LoginWeb(Socket sock, LogServeur ls) { 
        GestionBD gdb = new GestionBD();
        boolean ClientFound = false; 
        
        //StringTokenizer st = new StringTokenizer(this.charge, "#");
        ObjectOutputStream oos;
        ReponseProtocol rep; 
        
        try { 
            gdb.connection("HappyFerryDB","user","user"); 
            System.out.println("<p>Connection to HappyFerryDB established<p>"); 
            
            String numCli = this.charge; 
            ResultSet resSet = gdb.requete("select * from voyageurs;");
            while(resSet.next()) {
                //System.out.println(resSet.getString(2) + " ; " + resSet.getString(3) + " ; " + resSet.getString(4) + " ; " + resSet.getString(6)); 
                if((numCli.equals(resSet.getString(1)))) { // si nom/prenom correspondent 
                    ClientFound = true; 
                    break; 
                } 
            }
            if(ClientFound) {
                rep = new ReponseProtocol(ReponseProtocol.CLIENT_FOUND, resSet.getString(1)+"#"+resSet.getString(2)+"#"+resSet.getString(3)+"#"+resSet.getString(4)+"#"+resSet.getString(6));
                try {
                    oos = new ObjectOutputStream(sock.getOutputStream());
                    oos.writeObject(rep); 
                    oos.flush();
                } 
                catch (IOException e) {
                    System.err.println("<RequeteProtocole> Erreur réseau "+  e.getMessage());
                    rep = new ReponseProtocol(ReponseProtocol.LOGIN_PASOK , "");

                    try {
                        oos = new ObjectOutputStream(sock.getOutputStream());
                        oos.writeObject(rep);
                        oos.flush();
                    } catch (IOException ex) { 
                        Logger.getLogger(RequeteProtocol.class.getName()).log(Level.SEVERE, null, ex);
                    } 
                }
            }
            else {
                rep = new ReponseProtocol(ReponseProtocol.LOGIN_PASOK, "");
                try {
                    oos = new ObjectOutputStream(sock.getOutputStream());
                    oos.writeObject(rep); 
                    oos.flush();
                } 
                catch (IOException e) {
                    System.err.println("<RequeteProtocole> Erreur réseau "+  e.getMessage());
                    rep = new ReponseProtocol(ReponseProtocol.LOGIN_PASOK , "");

                    try {
                        oos = new ObjectOutputStream(sock.getOutputStream());
                        oos.writeObject(rep);
                        oos.flush();
                    } catch (IOException ex) { 
                        Logger.getLogger(RequeteProtocol.class.getName()).log(Level.SEVERE, null, ex);
                    } 
                }
            }
        }
        catch(Exception e) {
            rep = new ReponseProtocol(ReponseProtocol.LOGIN_PASOK, "");
            try {
                oos = new ObjectOutputStream(sock.getOutputStream());
                oos.writeObject(rep); 
                oos.flush();
            } 
            catch (IOException ex) {
                System.err.println("<RequeteProtocole> Erreur réseau "+  e.getMessage());
                rep = new ReponseProtocol(ReponseProtocol.LOGIN_PASOK , "");

                try {
                    oos = new ObjectOutputStream(sock.getOutputStream());
                    oos.writeObject(rep);
                    oos.flush();
                } catch (IOException iex) { 
                    Logger.getLogger(RequeteProtocol.class.getName()).log(Level.SEVERE, null, ex);
                } 
            }
            System.out.println("<doGet> " + e.getMessage());
            System.out.println("<p>Connection to HappyFerryDB not established<p>"); 
        } 
    }
    
    private boolean requeteLogin(Socket sock, LogServeur ls) { 
        StringTokenizer st = new StringTokenizer(this.charge, "#");
        ObjectOutputStream oos;
        ReponseProtocol rep; 
        
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
                    System.out.println("<RequeteProtocole> Logged");
                    ls.TraceEvenements("Logged # RequeteProtocole"); 
                    rep = new ReponseProtocol(ReponseProtocol.LOGIN_OK, "");
                    try {
                        oos = new ObjectOutputStream(sock.getOutputStream());
                        oos.writeObject(rep); 
                        oos.flush();
                        return true; 
                    } 
                    catch (IOException e) {
                        System.err.println("<RequeteProtocole> Erreur réseau "+  e.getMessage());
                        rep = new ReponseProtocol(ReponseProtocol.LOGIN_PASOK , "");
 
                        try {
                            oos = new ObjectOutputStream(sock.getOutputStream());
                            oos.writeObject(rep);
                            oos.flush();
                            return false; 
                        } catch (IOException ex) { 
                            Logger.getLogger(RequeteProtocol.class.getName()).log(Level.SEVERE, null, ex);
                        } 
                    }
                }
                else {
                    System.err.println("<RequeteProtocole> Erreur de login"); 
                    rep = new ReponseProtocol(ReponseProtocol.LOGIN_PASOK , "");
 
                    try {
                        oos = new ObjectOutputStream(sock.getOutputStream());
                        oos.writeObject(rep);
                        oos.flush();
                        return false; 
                    } catch (IOException ex) { 
                        Logger.getLogger(RequeteProtocol.class.getName()).log(Level.SEVERE, null, ex);
                    } 
                }
            }
            else {
                System.err.println("<RequeteProtocole> Erreur de password"); 
                rep = new ReponseProtocol(ReponseProtocol.LOGIN_PASOK , "");
 
                try {
                    oos = new ObjectOutputStream(sock.getOutputStream());
                    oos.writeObject(rep);
                    oos.flush();
                    return false; 
                } catch (IOException ex) { 
                    Logger.getLogger(RequeteProtocol.class.getName()).log(Level.SEVERE, null, ex);
                } 
            }
        }
        catch(Exception e) {
            System.err.println("<RequeteProtocole> Mauvaises entrées login/password"); 
            rep = new ReponseProtocol(ReponseProtocol.LOGIN_PASOK , "");
 
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
    private void requeteVerifBooking(Socket s, LogServeur ls) {
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
    
     private void requeteListeClients(Socket s, LogServeur ls)
    {
        GestionBD gdb = new GestionBD();
        ReponseProtocol rep;
        String mess = new String();
        try{
            gdb.connection("HappyFerryDB","user","user"); 
            ResultSet rs=gdb.ListeTraversees();
            while(rs.next()) 
            {
                mess+=(rs.getString(1)+'#'+rs.getString(2)+'#'+rs.getString(3)+'#'+rs.getDate(4)+'#');
            }
            mess+=("end"+"#");
            rs=gdb.ListeClients();
            while(rs.next()) 
            {
                mess+=(rs.getString(1)+'#'+rs.getString(2)+'#'+rs.getString(3)+'#');
            }
            mess+=("end"+"#");
            rs=gdb.ListeClientsAcc();
            while(rs.next())
            {
                mess+=(rs.getString(1)+'#'+rs.getString(2)+'#');
            }
            mess+=("end"+"#");
            rep = new ReponseProtocol(ReponseProtocol.LISTE_OK,mess);
            ObjectOutputStream oos;
            oos = new ObjectOutputStream(s.getOutputStream());
            oos.writeObject(rep);
            oos.flush();
        }
        catch(Exception e) { 
            System.err.println("ListeClients " + e.getMessage());
        } 
    }
    
    private void requeteBuyTicket(Socket s, LogServeur ls) {
        System.out.println(this.charge);
        String matricule="1BDO123";
        StringTokenizer strtokk = new StringTokenizer(this.charge, " ");
        String tempoo = strtokk.nextToken();
        tempoo = strtokk.nextToken();
        tempoo = strtokk.nextToken();
        
        StringTokenizer strtok = new StringTokenizer(this.charge, "#");
        String tempo;
        tempo = strtok.nextToken();
        String[] titu = new String[7];
        titu[0] = strtok.nextToken();
        boolean newtitu=false;
        String emailclient=null;
        if(titu[0].equals("true"))
        {         
            for(int i=0;i<6;i++)
                titu[i+1]=strtok.nextToken();
            
            newtitu=true;
            matricule=titu[3];
            System.out.println("haha");
        }
        else
        {
            System.out.println("haha????");
             tempo = strtok.nextToken();
             matricule = strtok.nextToken();
             StringTokenizer strtokkk = new StringTokenizer(tempo, " ");
             emailclient = strtokkk.nextToken();
             emailclient = strtokkk.nextToken();
             emailclient = strtokkk.nextToken();
             
        }
   
        int nbpassager = Integer.parseInt(strtok.nextToken());
        
        /*String[][] passager = new String[nbpassager][5]; 
        
        for(int i=0;i<nbpassager;i++)
        {
            for(int j=0;j<5;j++)
                passager[i][j]=strtok.nextToken();
        }*/
        GestionBD gdb = new GestionBD();
        ReponseProtocol rep;
        //String[] tmp = new String[5]; 
        String id; 
        //tmp = passager[0]; 
        try{
            gdb.connection("HappyFerryDB","user","user"); 
            
            if(newtitu==true)
            {
                id = gdb.AjoutClients(titu, null);
            }
            else
            {
                id = gdb.getIdClient(emailclient);
            }
            //gdb.AjoutClients(tmp, id); 
            gdb.setReservation(tempoo,id,matricule);
            String file = gdb.getFile(tempoo);
            gdb.setInfoFile(id,Integer.parseInt(file));
            rep = new ReponseProtocol(ReponseProtocol.ACHAT_OK," "+file);
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
    
    private void requeteListeTraversee(Socket s, LogServeur ls)
    {
        GestionBD gdb = new GestionBD();
        ReponseProtocol rep;
        String mess = new String();
        try{
            gdb.connection("HappyFerryDB","user","user"); 
            ResultSet rs=gdb.ListeTraversees();
            while(rs.next()) 
            {
                mess+=(rs.getString(1)+'#'+rs.getString(2)+'#'+rs.getString(3)+'#'+rs.getDate(4)+'#'+rs.getFloat(5)+'#'+rs.getInt(6)+'#');
            }
            mess+=("end"+"#");

            rep = new ReponseProtocol(ReponseProtocol.LISTE_OK,mess);
            ObjectOutputStream oos;
            oos = new ObjectOutputStream(s.getOutputStream());
            oos.writeObject(rep);
            oos.flush();
        }
        catch(Exception e) { 
            System.err.println("ListeClients " + e.getMessage());
        } 
    }
    
    private void requeteClose(Socket sock, LogServeur ls) {
        System.exit(0); 
    }
}
