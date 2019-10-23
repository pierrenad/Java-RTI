/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package CHECKCARP;

import rti_interface.Reponse;
import java.io.Serializable;

/**
 *
 * @author Thibi le best
 */
public class ReponseProtocolCard implements Reponse, Serializable{
        
    public static int CARTEOK = 201;
    public static int CARTEPASOK = 202;
    public static int CODEPASOK = 203;
 
    private int code;
    private String charge;
    
    public ReponseProtocolCard(int c, String chu)
    {
        code = c;
        setChargeUtile(chu);
    }
    
    public int getCode() { return code; }
    public String getChargeUtile() { return charge; }
    public void setChargeUtile(String chargeUtile) { this.charge = chargeUtile; } 
}
