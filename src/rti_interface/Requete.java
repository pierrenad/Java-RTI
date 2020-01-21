/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rti_interface;

/**
 *
 * @author tiboo, still the bg
 */

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*; 
import log.LogServeur;

public interface Requete
{
 // Ce qui va être exécuté doit connaître la socket du client distant
 // ainsi que le GUI qui affiche les traces
 // avant admin
 //public Runnable createRunnable (Socket s, ObjectInputStream ois, LogServeur cs); 
 // avec admin 
 public Runnable createRunnable (Socket s, ObjectInputStream ois, ObjectOutputStream oos); 
 public Runnable createRunnable (Socket s, ConsoleServeur cs, ObjectInputStream ois, ObjectOutputStream oos); 
} 
