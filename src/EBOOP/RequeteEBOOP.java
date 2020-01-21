/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EBOOP;

import db.facilities.GestionBD;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.sql.ResultSet;
import java.util.Properties;
import java.util.StringTokenizer;
import log.LogServeur;
import rti_interface.ConsoleServeur;
import rti_interface.Requete;


/**
 *
 * @author Pierre
 */
public class RequeteEBOOP implements Requete, Serializable {     
    public static final int REGISTER = 1;
    public static final int CROSSING = 2;
    public static final int ADD_CART = 3; 
    public static final int VIEW_CART = 4; 
    public static final int BUY_CART = 5; 
    public static final int RESERV = 6; 
    public static final int DELETE_CART = 7;
    public static final int REMOVE_FROM_CART = 8; 
    
    public static final long serialVersionUID = 683388106844655395L; // Pas touche 
    
    private int type;
    private Socket socketCli;
    private String charge; 
    private static Properties hashtable = new Properties();
    
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
    public Runnable createRunnable(final Socket s, final ConsoleServeur ls, final ObjectInputStream ois, final ObjectOutputStream oos) {  
        if (type==REGISTER)
            return new Runnable()
            {
                public void run()
                {
                    RequeteRegister(s, ls); 
                }
            }; 
        else if (type==CROSSING)
            return new Runnable()
            {
                public void run()
                {
                    ListCrossing(s, ls); 
                }
            }; 
        else if (type==ADD_CART)
            return new Runnable() 
            {
                public void run() 
                {
                    AddToCart(s, ls); 
                }
            }; 
        else if (type==VIEW_CART)
            return new Runnable() 
            {
                public void run() 
                {
                    ViewCart(s, ls); 
                }
            };
        else if (type==BUY_CART)
            return new Runnable() 
            {
                public void run() 
                {
                    BuyCart(s, ls); 
                }
            }; 
        else if (type==RESERV)
            return new Runnable() 
            {
                public void run() 
                {
                    Reservation(s, ls); 
                }
            }; 
        else if (type==DELETE_CART)
            return new Runnable() 
            {
                public void run() 
                {
                    DeleteCart(s, ls); 
                }
            }; 
        else if (type==REMOVE_FROM_CART) 
            return new Runnable() 
            {
                public void run() 
                {
                    RemoveFromCart(s, ls); 
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
    
    public void RemoveFromCart(Socket s, ConsoleServeur cs) { 
        GestionBD gdb = new GestionBD(); 
        ObjectOutputStream oos;
        ReponseEBOOP rep; 
        try {
            gdb.connection("HappyFerryDB","user","user");

            gdb.setDimPlace(this.charge); 

            rep = new ReponseEBOOP(ReponseEBOOP.REMOVE_OK, null); // on envoie l'id

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
            System.err.println("<DeleteCart> " + e.getMessage());
        } 
    }
    
    public void DeleteCart(Socket s, ConsoleServeur cs) { 
        GestionBD gdb = new GestionBD(); 
        ObjectOutputStream oos;
        ReponseEBOOP rep; 
        String res = "";
        String crossing = ""; 
        try {
            gdb.connection("HappyFerryDB","user","user");
            StringTokenizer st = new StringTokenizer(this.charge, "#\n");
            while(st.hasMoreTokens())
            {
                st.nextToken(); 
                st.nextToken(); 
                st.nextToken(); 
                st.nextToken(); 
                gdb.setDimPlace(st.nextToken()); 
            }

            /*rep = new ReponseEBOOP(ReponseEBOOP.RESERV_OK, res); // on envoie l'id
            
            try {
                oos = new ObjectOutputStream(s.getOutputStream());
                oos.writeObject(rep); 
                oos.flush();
            } 
            catch (IOException e) {
                System.err.println("<RequeteEBOOP> Erreur réseau "+  e.getMessage());
            }*/
        }
        catch(Exception e) {
            System.err.println("<DeleteCart> " + e.getMessage());
        } 
    }
    
    public void Reservation(Socket s, ConsoleServeur cs) { 
        GestionBD gdb = new GestionBD(); 
        ObjectOutputStream oos;
        ReponseEBOOP rep; 
        String res = "";
        String crossing = ""; 
        try {
            gdb.connection("HappyFerryDB","user","user");
            StringTokenizer st = new StringTokenizer(this.charge, ";");
            String Cross = st.nextToken(); 
            String Client = st.nextToken(); 
            String Matricule = st.nextToken(); 
            StringTokenizer str = new StringTokenizer(Cross, "\n"); 
            StringTokenizer stro;
            while(str.hasMoreElements()) {
                stro = new StringTokenizer(str.nextToken(), "#"); 
                stro.nextToken();stro.nextToken();stro.nextToken();stro.nextToken(); // elements avant l'id on s'en fou ici 
                crossing = stro.nextToken(); 
                res = gdb.setReservation(crossing, Client, Matricule); // idtrav idclient matricule 
            }

            rep = new ReponseEBOOP(ReponseEBOOP.RESERV_OK, res); // on envoie l'id
            
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
            System.err.println("<AddToCart> " + e.getMessage());
        } 
    }
    
    public void BuyCart(Socket s, ConsoleServeur cs) { 
        GestionBD gdb = new GestionBD(); 
        ObjectOutputStream oos;
        ReponseEBOOP rep; 
        String mess = "";

        try {
            gdb.connection("db_card","user","user");
            StringTokenizer st = new StringTokenizer(this.charge, "#");
            String num = st.nextToken(); 
            String code = st.nextToken(); 
            String date = st.nextToken(); 
            float prix = Float.parseFloat(st.nextToken()); 

            ResultSet rs = gdb.CheckPayement(num,code); //carte et code
            rs.next(); 
            //st.nextToken(); // expiration
            if(prix <= rs.getInt(1)) // comparer prix total et argent sur carte
            {
                rep = new ReponseEBOOP(ReponseEBOOP.BUY_CART_OK, "The payement is done"); 
            }
            else
            {
                rep = new ReponseEBOOP(ReponseEBOOP.BUY_CART_BAD_MONEY, "You don't have enough money on this card"); 
            } 

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
            System.err.println("<AddToCart> " + e.getMessage());
            rep = new ReponseEBOOP(ReponseEBOOP.BUY_CART_BAD_INFO, "The card have bad informations"); 
            try {
                oos = new ObjectOutputStream(s.getOutputStream());
                oos.writeObject(rep); 
                oos.flush();
            } 
            catch (IOException ex) {
                System.err.println("<RequeteEBOOP> Erreur réseau "+  ex.getMessage());
            }
        } 
    }
    
    public void ViewCart(Socket s, ConsoleServeur cs) { 
        GestionBD gdb = new GestionBD(); 
        ObjectOutputStream oos;
        ReponseEBOOP rep; 
        String mess = "";
        
        try {
            gdb.connection("HappyFerryDB","user","user");
            StringTokenizer st = new StringTokenizer(this.charge, "#");
            while(st.hasMoreElements()) {
                ResultSet rs = gdb.getCartInfo(st.nextToken()); 
                
                mess+=(rs.getString(1)+'#'+rs.getString(2)+"#"+rs.getTimestamp(3)+'#'+rs.getFloat(4)+"#"+rs.getString(5)+'#'+"\n"); 
            }
            
            rep = new ReponseEBOOP(ReponseEBOOP.VIEW_CART_OK, mess); 
            
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
            System.err.println("<AddToCart> " + e.getMessage());
        } 
    }
    
    public void AddToCart(Socket s, ConsoleServeur cs) {
        GestionBD gdb = new GestionBD(); 
        ObjectOutputStream oos;
        ReponseEBOOP rep; 
        int place; 
        
        try {
            String idTrav = this.charge; 
            gdb.connection("HappyFerryDB","user","user");
            
            place = gdb.checkPlacesDispo(idTrav); 
            if(place > 0) {
                gdb.setAugPlace(idTrav); 
                //System.out.println("<addtocart> id : " + idTrav); 
                rep = new ReponseEBOOP(ReponseEBOOP.ADD_CART_OK, ""); 
            }
            else {
                rep = new ReponseEBOOP(ReponseEBOOP.ADD_CART_PAS_OK, ""); 
            }
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
            System.err.println("<AddToCart> " + e.getMessage());
        } 
    }
    
    public void ListCrossing(Socket s, ConsoleServeur cs) {
        GestionBD gdb = new GestionBD(); 
        ObjectOutputStream oos;
        ReponseEBOOP rep; 
        String mess = ""; 
        try { 
            gdb.connection("HappyFerryDB","user","user"); 
            ResultSet rs = gdb.requete("select * from traversees t INNER JOIN navires n " +
                                        "Where n.capacite_navire_leger-t.nbtickets_traversee >0 " +
                                        "And t.navire_traversee = n.matricule_navire " +
                                        "order by depart_traversee;"); 
            
            while(rs.next()) {
                mess+=(rs.getString(1)+'#'+rs.getTimestamp(2)+'#'+rs.getString(3)+'#'+rs.getString(4)+'#'+rs.getString(5)+'#'+rs.getInt(6)+'#'+rs.getInt(7)+'#'+rs.getInt(8)+'#'+rs.getFloat(9)+'#'+"\n"); 
            }
            //mess+="end"; 
            //System.out.println("mess : " + mess);
            rep = new ReponseEBOOP(ReponseEBOOP.LISTE_OK, mess); 

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
            System.err.println("<ListCrossing> " + e.getMessage());
        } 
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

    @Override
    public Runnable createRunnable(Socket s, ObjectInputStream ois, ObjectOutputStream oos) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
