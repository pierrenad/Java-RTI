/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package protocoles;

import java.io.Serializable;
import rti_interface.Reponse;

/**
 *
 * @author Pierre
 */
public class ReponseProtocol implements Reponse, Serializable {
    
    public static int LOGIN_OK = 100;
    public static int LOGIN_PASOK = 101;
    
    public static int RESERV_OK = 102;
    public static int RESERV_PASOK = 103;
    
    public static int ACHAT_OK = 104;
    public static int ACHAT_PASOK = 105; 
    
    private int codeRet;
    private String charge; 
    
    public int getCode() { return codeRet; } 
    public void setCharge(String s) { charge = s; } 
    public String getCharge() { return charge; } 
    
    public ReponseProtocol(int c, String str) {
        codeRet = c;
        setCharge(str); 
    }
}
