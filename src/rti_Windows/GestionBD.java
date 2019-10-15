/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rti_Windows;

import java.sql.*;

/**
 *
 * @author Pierre
 */
public class GestionBD {
    protected Connection con;
    protected PreparedStatement pstate; 
    protected ResultSet resSet; 
    private AccessBD abd;

    public GestionBD(){
    }
    
    public GestionBD(AccessBD accessBD) {
        abd = accessBD; 
    }
    
    public void connection(String bd, String log, String pwd) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver"); 
        con = DriverManager.getConnection(bd, log, pwd); 
    }
    
    public void requete(String req) throws Exception {
        pstate = con.prepareStatement("SELECT * FROM Ports"); // ca lance exception à"!(àç'"!(àç'"!
        //System.out.println("coucou56646"); 
        resSet = pstate.executeQuery(); 
        afficheTable(); 
        /*while(resSet.next()) { 
            System.out.println("%s\n", resSet.getString("nom_port"));//,resSet.getString(2),resSet.getInt(3),resSet.getInt(4));
        }*/
    }
    
    public void afficheTable() throws Exception {
        while(resSet.next()) { 
            abd.ResRequete.setText(resSet.getString(1)+"\n"+resSet.getString(2)+"\n"+resSet.getInt(3)+"\n"+resSet.getInt(4)); 
        } 
    }
}
