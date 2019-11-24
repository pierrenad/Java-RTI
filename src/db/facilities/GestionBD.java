/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.facilities;

import java.sql.*;
import java.util.Random;

/**
 *
 * @author Pierre
 */
public class GestionBD {
    protected Connection con = null;
    protected Statement state = null; 
    protected ResultSet resSet = null; 
    
    public GestionBD(){
    }
    
    public synchronized void connection(String bd, String log, String pwd) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver"); 
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+bd+"?serverTimezone=UTC", log, pwd); 
    }
    
    public synchronized ResultSet requete(String req)
    {
        resSet = null;
        try {
            PreparedStatement requete = con.prepareStatement(req);
            
            resSet = requete.executeQuery();
            return resSet;
           
                            
        } catch (SQLException ex) {
            System.err.println("<requete> " + ex.getMessage());
        }
        return resSet;
    }
    
    public synchronized boolean CheckLoginWeb(String reservation, String nom) {
        try {
            PreparedStatement requete = con.prepareStatement("select v.nom_voyageur " +
            "from reservations r " +
            "INNER JOIN voyageurs v " +
            "WHERE r.id_reservation LIKE ?"); 
            requete.setString(1, reservation);

            ResultSet rs = requete.executeQuery();
            rs.next();
            if(nom.equals(rs.getString(1)))
                return true;

            else
                return false;

        } catch (SQLException ex) {
            System.err.println("<CheckReservation> " + ex.getMessage());
            return false;
        } 
    }
    
    public synchronized ResultSet CheckReservation(String reservation, int nbpass) {
        try {
            PreparedStatement requete = con.prepareStatement("select r.traversee_reservation,v.nom_voyageur,t.port_dep_traversee,t.port_dest_traversee,t.depart_traversee " +
            "from reservations r " +
            "INNER JOIN voyageurs_acc va " +
            "INNER JOIN voyageurs v " +
            "INNER JOIN traversees t " +
            "where r.voy_titu_reservation = va.voyageur_titu_acc AND id_reservation LIKE ? AND r.voy_titu_reservation = v.num_cli_voyageur AND r.traversee_reservation = t.id_traversee " +
            "group by  voyageur_titu_acc " +
            "having count(*) = ?-1"); 
            requete.setString(1, reservation);
            requete.setInt(2,nbpass);  
            ResultSet rs = requete.executeQuery();
            return rs;
        } catch (SQLException ex) {
            System.err.println("<CheckReservation> " + ex.getMessage());
            return null;
        } 
    }
    
    public synchronized String AjoutClients(String[]client,String titu) {
        System.out.println("client[0} : " + client[0]);
        try{
            if(titu==null) {
                PreparedStatement num = con.prepareStatement("SELECT count(num_cli_voyageur) " +
                "FROM Voyageurs");
                ResultSet rs = num.executeQuery();
                rs.next(); 
                int id = rs.getInt(1); 
                id++;
                PreparedStatement requete = con.prepareStatement("INSERT INTO Voyageurs VALUES(?,?,?,?,?,?)");
                requete.setString(2, client[1]);
                requete.setString(3, client[2]);
                requete.setString(5, client[4]);
                requete.setString(4, client[5]);
                requete.setString(6, client[6]);   
                requete.setString(1,String.valueOf(id));
                requete.executeUpdate();
                return ""+id; 
            }
            else {
                PreparedStatement requete = con.prepareStatement("INSERT INTO Voyageurs_Acc VALUES(?,?,?,?,?)");
                requete.setString(1, client[1]);
                requete.setString(2, client[2]);
                requete.setString(3, client[4]);
                requete.setString(4, titu);
                requete.setString(5, client[5]);
                requete.executeUpdate();
                return null; 
            }        
        }
        catch (SQLException ex) {
            System.err.println("<AjoutClient> " + ex.getMessage());
        }
        return null; 
    }
    
    public synchronized ResultSet CheckPayement(String numcarte, String code) {
        try {
            PreparedStatement requete = con.prepareStatement("SELECT argent_carte, date_expiration "+
            "FROM cartes "+
            "WHERE num_carte LIKE ? AND code_carte LIKE ? ;");
            requete.setString(1, numcarte);
            requete.setString(2, code);
            ResultSet rs = requete.executeQuery();
            return rs;
        } catch (SQLException ex) {
            System.err.println("<CheckPayement> " + ex.getMessage());
            return null;
        } 
    }
    
    public synchronized ResultSet ListeTraversees() {
        try {
            PreparedStatement requete = con.prepareStatement("SELECT id_traversee, port_dep_traversee, port_dest_traversee, depart_traversee, prix_traversee, nbtickets_traversee "+
            "FROM Traversees order by depart_traversee;"); 
            ResultSet rs = requete.executeQuery();
            return rs;
        }
        catch (SQLException ex) {
            System.err.println("<ListeClient> " + ex.getMessage());
            return null;
        }
    }
    
    public synchronized ResultSet ListeClients() {
        try {
            PreparedStatement requete = con.prepareStatement("SELECT nom_voyageur, prenom_voyageur, email_voyageur "+
            "FROM Voyageurs;"); 
            ResultSet rs = requete.executeQuery();
            return rs;
        }
        catch (SQLException ex) {
            System.err.println("<ListeClient> " + ex.getMessage());
            return null;
        }
    }
    
    public synchronized ResultSet ListeClientsAcc() {
        try {
            PreparedStatement requete = con.prepareStatement("SELECT nom_voyageur_acc, prenom_voyageur_acc "+
            "FROM Voyageurs_acc;");
            ResultSet rs = requete.executeQuery();
            return rs;
        } 
        catch (SQLException ex) {
            System.err.println("<ListeClientsAcc> " + ex.getMessage());
            return null;
        }
    }
    
    public synchronized String getFile(String id) {
        try {
            PreparedStatement num = con.prepareStatement("SELECT placeprise_traversee, placefile_traversee " +
            "FROM traversees " +
            "WHERE id_traversee = ? ;");
            num.setString(1, id);
            ResultSet rs = num.executeQuery();
            rs.next(); 
            int nbplaceprise = rs.getInt(1);
            int nbplaceparfile = rs.getInt(2);
            
            int valret = nbplaceprise % nbplaceparfile;
            PreparedStatement requete = con.prepareStatement("UPDATE Traversees "+
            "SET placeprise_traversee = ?+1 "+
            "WHERE id_traversee = ? ;");
            requete.setInt(1, nbplaceprise);
            requete.setString(2, id);
            requete.executeUpdate();
            return ""+valret;
        } 
        catch (SQLException ex) {
            System.err.println("<GetFile> " + ex.getMessage());
            return null;
        }
    }
    
    public synchronized String getIdClient(String email)
    {
        try {
            PreparedStatement requete = con.prepareStatement("Select num_cli_voyageur " +
            "from voyageurs " +
            "where email_voyageur LIKE ?;");
            requete.setString(1, email);
            ResultSet rs = requete.executeQuery();
            rs.next();
            return rs.getString(1);
        } 
        catch (SQLException ex) {
            System.err.println("<getidclient> " + ex.getMessage());
            return null;
        }
    }
    
    public synchronized void setInfoFile(String id, int file)
    {
        try{
        PreparedStatement requete = con.prepareStatement("INSERT INTO InfoFiles VALUES(?,?);");
        requete.setString(1, id);
        requete.setInt(2, file);
        requete.executeUpdate();
        }
        catch (SQLException ex) {
            System.err.println("<setinfofile> " + ex.getMessage());
        }
    }
    
    public synchronized String setReservation(String traversee, String idclient,String matricule)
    {
        try{
            Random rand=new Random();
            PreparedStatement requete = con.prepareStatement("INSERT INTO reservations VALUES(?,?,?,?,'O','O');");
            String temp = ("20191125-RES"+rand.nextInt(1000));
            requete.setString(1,temp);
            requete.setString(2, traversee);
            requete.setString(3, idclient);
            requete.setString(4, matricule);
            requete.executeUpdate();
            return temp; 
        }
        catch (SQLException ex) {
            System.err.println("<setreservation> " + ex.getMessage());
            return null;
        }
    }
    
    public synchronized int checkPlacesDispo(String id)
    {
        try{
            PreparedStatement requete = con.prepareStatement("select (n.capacite_navire_leger-t.nbtickets_traversee) "+
            "from traversees t "+
            "INNER JOIN navires n "+
            "where t.navire_traversee = n.matricule_navire AND t.id_traversee = ?;");
            requete.setString(1, id);
            ResultSet rs = requete.executeQuery();
            rs.next();
            int nbplacerest = rs.getInt(1);

            return nbplacerest;
        }
        catch (SQLException ex) {
            System.err.println("<chkplacedispo> " + ex.getMessage());
            return 0;
        }
    }
    
    public synchronized void setAugPlace(String id)
    {
        try{
            PreparedStatement requete = con.prepareStatement("Update traversees set nbtickets_traversee = nbtickets_traversee +1 " +
            "Where id_traversee = ?;");
            requete.setString(1, id);
            requete.executeUpdate();
        }
        catch (SQLException ex) {
            System.err.println("<setAugPlace> " + ex.getMessage());
        }
    }
    
    public synchronized void setDimPlace(String id)
    {
        try{
            PreparedStatement requete = con.prepareStatement("Update traversees set nbtickets_traversee = nbtickets_traversee -1 " +
            "Where id_traversee = ?;");
            requete.setString(1, id);
            requete.executeUpdate();
        }
        catch (SQLException ex) {
            System.err.println("<setDimPlace> " + ex.getMessage());
        }
    }
    
    public synchronized ResultSet getCartInfo(String id)
    {
        try{
            PreparedStatement requete = con.prepareStatement("select port_dep_traversee, port_dest_traversee, depart_traversee, prix_traversee, id_traversee "+
            "from traversees "+
            "where id_traversee = ?;");
            requete.setString(1, id);
            ResultSet rs = requete.executeQuery();
            rs.next();      
            return rs;
        }
        catch (SQLException ex) {
            System.err.println("<chkplacedispo> " + ex.getMessage());
            return null;
        }
    }
}
