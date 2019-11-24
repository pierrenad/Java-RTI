/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EBOOP;

import java.io.Serializable;
import rti_interface.Reponse;

/**
 *
 * @author Pierre
 */
public class ReponseEBOOP implements Reponse, Serializable {
    public static int LOGIN_OK = 100;
    public static int LOGIN_PASOK = 101;
    
    public static int RESERV_OK = 102;
    public static int RESERV_PASOK = 103;
    
    public static int ACHAT_OK = 104;
    public static int ACHAT_PASOK = 105; 
        
    public static int LISTE_OK = 106;
    public static int CLOSE = 107;
    
    public static int CLIENT_FOUND = 108; 
    public static int CLIENT_NOTFOUND = 109; 
    
    public static int AJOUT_OK = 110; 
    public static int AJOUT_PAS_OK = 111;  
    
    public static int ADD_CART_OK = 112; 
    public static int ADD_CART_PAS_OK = 113;
    
    public static int VIEW_CART_OK = 114; 
    public static int VIEW_CART_PAS_OK = 115;
    
    public static int BUY_CART_BAD_INFO = 116;
    public static int BUY_CART_OK= 117;
    public static int BUY_CART_BAD_MONEY = 118;
    
    private int codeRet;
    private String charge; 
    
    public int getCode() { return codeRet; } 
    public void setCharge(String s) { charge = s; } 
    public String getCharge() { return charge; } 
    
    public ReponseEBOOP(int c, String str) {
        codeRet = c;
        setCharge(str); 
    }
}
