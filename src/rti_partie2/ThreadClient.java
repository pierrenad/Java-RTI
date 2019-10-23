/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rti_partie2;

/**
 *
 * @author tiboo
 */

import log.LogServeur;
import rti_interface.*;

public class ThreadClient extends Thread {
    private SourceTache tachesAExecuter;
    private String nom;
    private Runnable tacheEnCours;
    LogServeur logserv; 

    public ThreadClient(SourceTache st, String n, LogServeur ls) {
       tachesAExecuter = st;
       nom = n;
       logserv = ls; 
    }

    @Override 
    public void run() {
        while (!isInterrupted()) {
            try {
                System.out.println("<ThreadClient> avant getTache");
                logserv.TraceEvenements("Avant getTache # thread client");
                tacheEnCours = tachesAExecuter.getTache();
            }
            catch (InterruptedException e) { 
                System.err.println("<ThreadClient> Interruption : " + e.getMessage());
            }
            System.out.println("<ThreadClient> run de tachesEnCours"); 
            logserv.TraceEvenements("Run de tachesEnCours # thread client");
            tacheEnCours.run();
        }
    } 
}
