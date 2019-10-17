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
    protected Connection con = null;
    protected Statement state = null; 
    protected ResultSet resSet = null; 
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
        state = con.createStatement(); 
        resSet = state.executeQuery(req); 
        afficheTable(); 
    }
    
    public void afficheTable() throws Exception {
        while(resSet.next()) { 
            abd.ResRequete.setText(resSet.getString(1)+"\t"+resSet.getString(2)+"\t"+resSet.getInt(3)+"\t"+resSet.getInt(4)+"\n"); 
        } 
    }
}
