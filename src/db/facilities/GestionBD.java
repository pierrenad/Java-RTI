/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.facilities;

import java.sql.*;

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
    
    public void connection(String bd, String log, String pwd) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver"); 
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+bd+"?serverTimezone=UTC", log, pwd); 
    }
    
    public void requete(String req) throws Exception {
        state = con.createStatement(); 
        resSet = state.executeQuery(req); 
    }
    
    public ResultSet CheckReservation(String reservation, int nbpass) {
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
               return null;
        } 
    }
    
    public String AjoutClients(String[]client,String titu) {
        try{
            if(titu==null) {
                PreparedStatement num = con.prepareStatement("SELECT count(num_cli_voyageur) " +
                "FROM Voyageurs");
                ResultSet rs = num.executeQuery();
                rs.next(); 
                int id = rs.getInt(1); 
                id++;
                PreparedStatement requete = con.prepareStatement("INSERT INTO Voyageurs VALUES(?,?,?,?,?,?)");
                requete.setString(2, client[0]);
                requete.setString(3, client[1]);
                requete.setString(5, client[4]);
                requete.setString(4, client[5]);
                requete.setString(6, client[6]);   
                requete.setString(1,String.valueOf(id));
                requete.executeUpdate();
                return ""+id; 
            }
            else {
                PreparedStatement requete = con.prepareStatement("INSERT INTO Voyageurs_Acc VALUES(?,?,?,?,?)");
                requete.setString(1, client[0]);
                requete.setString(2, client[1]);
                requete.setString(3, client[3]);
                requete.setString(4, titu);
                requete.setString(5, client[4]);
                requete.executeUpdate();
                return null; 
            }        
        }
        catch (SQLException ex) {
            System.err.println("<AjoutClient> " + ex.getMessage());
        }
        return null; 
    }
}
