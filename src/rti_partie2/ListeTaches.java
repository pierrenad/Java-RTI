/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rti_partie2;

/**
 *
 * @author tiboo heeeeeell yeah
 */
import java.util.*; 
import rti_interface.*;
import rti_Windows.*;

public class ListeTaches implements SourceTache{
    private LinkedList listeTaches;
    public ListeTaches()
    {
        listeTaches = new LinkedList();
    }   
    public synchronized Runnable getTache() throws InterruptedException
    {
        System.out.println("getTache avant wait");
        while (!existTaches()) wait();
            return (Runnable)listeTaches.remove();
    }  
    
    public synchronized boolean existTaches()
    {
        return !listeTaches.isEmpty();
    }
    public synchronized void recordTache (Runnable r)
    {
        listeTaches.addLast(r);
        System.out.println("ListeTaches : tache dans la file");
        notify();
    } 

    @Override
    public boolean existTache() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
