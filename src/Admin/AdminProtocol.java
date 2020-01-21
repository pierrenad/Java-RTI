/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Admin;

import network.Network;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Pierre
 */
public class AdminProtocol extends Thread {
    
    int port;
    
    List<Socket> listCli = null;
    List<Thread> listThread = null;
    
    ServerSocket SSocket = null;
    
    DataInputStream dis = null;
    DataOutputStream dos = null;
    
    boolean pause = false;
    
    public AdminProtocol(int p, List<Socket> listc, List<Thread> listT) {
        port = p;
        listCli = listc;
        listThread =listT;
    }
    
    public void run() {
        try {
            try {
                SSocket = new ServerSocket(port);
            }
            catch (IOException e) {
                Logger.getLogger(AdminProtocol.class.getName()).log(Level.SEVERE, null, e);
                System.exit(1);
            } 
            
            Socket CSocket = null;
            
            try {
                CSocket = SSocket.accept(); 
                System.out.println("ADMIN ACCEPTED");
            }
            catch (IOException e) {
                Logger.getLogger(AdminProtocol.class.getName()).log(Level.SEVERE, null, e);
                System.exit(1);
            }
            
            dis = new DataInputStream(CSocket.getInputStream());
            dos = new DataOutputStream(CSocket.getOutputStream());
            
            while (!isInterrupted()) {
                try {
                    byte[] mb = new byte[100];
                    
                    dis.read(mb);
                    System.out.println("Message lu");
                    
                    String message = new String(mb);
                    
                    StringTokenizer st = new StringTokenizer(message, "#");
                    
                    String type = st.nextToken();
                    String chargeUtile = st.nextToken();
                    
                    switch(type) {
                        case "LOGIN" :      requeteLOGIN(chargeUtile);
                        break;
                        
                        case "SUSPEND"  :   requeteSUSPEND(chargeUtile);
                        break;
                        
                        case "SHUTDOWN" :   requeteSHUTDOWN(chargeUtile);
                        break;
                        
                        case "LIST"     :   requeteLIST(chargeUtile);
                        break;
                    }
                }
                catch (IOException ex) {
                    Logger.getLogger(AdminProtocol.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        catch (IOException ex) {
            Logger.getLogger(AdminProtocol.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void requeteLOGIN(String cu) {
        System.out.println("DANS LOGIN");
        StringTokenizer st = new StringTokenizer(cu, "@");
        
        String login = st.nextToken();
        String pwd = st.nextToken();
        String password = null;
        
        try {
            FileInputStream in = new FileInputStream("config.properties");
            Properties data = new Properties();
            data.load(in);
            password = (String)data.get(login);
        } catch (Exception ex) {
            Logger.getLogger(AdminProtocol.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("user : " + login);
        System.out.println("pwd : " + pwd);
        System.out.println("password : " + password);
        
        try {
            if(password.equals(pwd)) {
                System.out.println("LOGIN OK");
                dos.write(new String("Y"+"#").getBytes());
            }
            else {
                System.out.println("LOGIN PAS OK");
                dos.write(new String("N"+"#").getBytes());
            }
        } catch (Exception e) {
            Logger.getLogger(AdminProtocol.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    public void requeteLIST(String cu) {
        System.out.println("REQUETE LIST RECUE");
        
        String chargeUt = "";
        for(int i = 0;i<listCli.size();i++) {
            chargeUt = chargeUt.concat(listCli.get(i).getLocalSocketAddress()+"\n");
        }
        
        try {
            dos.write(new String(chargeUt+"#").getBytes());
        } catch (IOException ex) {
            Logger.getLogger(AdminProtocol.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void requeteSUSPEND(String cu) {
        
        System.out.println("REQUETE SUSPEND RECUE");
        
        listCli.forEach((f) ->{
            try {
                Network n = new Network();
                Socket s = n.InitLect();

                DataOutputStream dos2 = new DataOutputStream(s.getOutputStream());
                DataInputStream dis2 = new DataInputStream(s.getInputStream());

                dos2.write(new String("SUSPEND#").getBytes());
            } catch (IOException ex) {
                Logger.getLogger(AdminProtocol.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
                
        if(pause == true) {
            pause = false;
            listThread.get(0).suspend(); // thread du serveur 
        }
        else {
            pause = true;
            listThread.get(0).resume();
        }
    }
    
    public void requeteSHUTDOWN(String cu) {
        DataOutputStream dos2 = null;
        try {
            
            System.out.println("REQUETE SHUTDOWN RECUE");
            
            listThread.forEach((e)->{
                System.out.println(e.getName());
                if(!e.getName().equals("admin")) {
                    e.stop();
                }
                else
                    System.out.println("admin");
            });
            
            Network n = new Network();
            Socket s = n.InitLect();

            dos2 = new DataOutputStream(s.getOutputStream());
            DataInputStream dis2 = new DataInputStream(s.getInputStream());

            dos2.write(new String("SHUTDOWN#").getBytes());

            dos.write(new String("SHUTDOWNBABY"+"#").getBytes());
            
        } catch (IOException ex) {
            Logger.getLogger(AdminProtocol.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
