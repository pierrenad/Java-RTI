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

import rti_interface.*;

public class ThreadClient extends Thread {
    private SourceTache tachesAExecuter;
    private String nom;
    private Runnable tacheEnCours;

    public ThreadClient(SourceTache st, String n) {
       tachesAExecuter = st;
       nom = n;
    }

    @Override 
    public void run() {
        while (!isInterrupted()) {
            try {
                System.out.println("Thread client avant get");
                tacheEnCours = tachesAExecuter.getTache();
            }
            catch (InterruptedException e) { 
                System.err.println("<ThreadClient> Interruption : " + e.getMessage());
            }
            System.out.println("<ThreadClient> run de tachesEnCours"); 
            tacheEnCours.run();
        }
    } 
}
