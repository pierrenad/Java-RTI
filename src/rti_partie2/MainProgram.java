/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rti_partie2;

import java.io.FileInputStream;
import java.util.Properties;
import log.LogServeur;
import rti_Windows.LoginWindow;

/**
 *
 * @author Pierre
 */
public class MainProgram {
    
    public static void main(String[] args) {
        int port = 50000;
        try {
            FileInputStream in = new FileInputStream("donnees.properties"); 
            Properties data = new Properties();
            data.load(in);
            
            port = Integer.parseInt((String) data.getProperty("port"));
        } catch (Exception e) {
            System.out.println("<Main> " + e.getMessage());
        }
        
        ListeTaches lt = new ListeTaches(); 
        LogServeur ls = new LogServeur(); 

        ThreadServer serv = new ThreadServer(port, lt, ls); 
        Thread th = new Thread(serv); 
        th.start(); 
        
        LoginWindow logWin = new LoginWindow(); 
        logWin.setVisible(true); 
    }
}
