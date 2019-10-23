/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rti_Windows;

import CHECKCARP.ReponseProtocolCard;
import CHECKCARP.RequeteProtocolCard;
import java.io.ObjectInputStream;
import java.net.Socket;
import javax.swing.JOptionPane;
import network.Network;
import CHECKINAP.ReponseProtocol;
import CHECKINAP.RequeteProtocol;
import java.util.StringTokenizer;

/**
 *
 * @author tiboo
 */
public class CustomerWindow extends javax.swing.JFrame {

    private Socket cSock;
    private ObjectInputStream ois; 
    
    /**
     * Creates new form CustomerWindow
     */
    public CustomerWindow() {
        initComponents();
    }
    public CustomerWindow(Socket s) {
        initComponents(); 
        cSock = s; 
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        closeButton = new javax.swing.JButton();
        buyTicketButton = new javax.swing.JButton();
        verfiBookingButton = new javax.swing.JButton();
        nbrPassagerTxt = new javax.swing.JTextField();
        reservationTxt = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Client");

        jLabel1.setText("Nombre de passagers :");

        jLabel2.setText("Réservation :");

        closeButton.setText("Close");
        closeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeButtonActionPerformed(evt);
            }
        });

        buyTicketButton.setText("Buy Ticket");
        buyTicketButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buyTicketButtonActionPerformed(evt);
            }
        });

        verfiBookingButton.setText("Verif. Booking");
        verfiBookingButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                verfiBookingButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(reservationTxt)
                            .addComponent(nbrPassagerTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(verfiBookingButton)
                        .addGap(12, 12, 12)
                        .addComponent(buyTicketButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(closeButton)))
                .addContainerGap(37, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(reservationTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(nbrPassagerTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(verfiBookingButton)
                    .addComponent(buyTicketButton)
                    .addComponent(closeButton))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void verfiBookingButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_verfiBookingButtonActionPerformed
        // TODO add your handling code here:
        String mess = reservationTxt.getText() +"#"+ nbrPassagerTxt.getText();
        RequeteProtocol requete = new RequeteProtocol(RequeteProtocol.VERIF_BOOKING,mess);
        Network n = new Network();
        cSock = n.Init(); 
        n.SendRequest(cSock,requete);
        ReponseProtocol reponse = null;
        try {
            ois = new ObjectInputStream(cSock.getInputStream());
            reponse = (ReponseProtocol)ois.readObject(); 
            if(reponse.getCode()==ReponseProtocol.RESERV_OK) {
                JOptionPane.showMessageDialog(null, "Votre réservation est juste", "Resultat réservation", JOptionPane.INFORMATION_MESSAGE);
            }
            else {
                JOptionPane.showMessageDialog(null, "Votre réservation est incorecte", "Resultat réservation", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        catch(Exception e) {
            System.err.println("<CustomerWindow> " + e.getMessage()); 
        } 
    }//GEN-LAST:event_verfiBookingButtonActionPerformed

    private void buyTicketButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buyTicketButtonActionPerformed
        RequeteProtocol requete = new RequeteProtocol(RequeteProtocol.LISTE_CLIENTS,"");
        Network n = new Network();
        cSock = n.Init(); 
        n.SendRequest(cSock,requete);
        ReponseProtocol reponse = null;
        try {
            ois = new ObjectInputStream(cSock.getInputStream());           
            reponse = (ReponseProtocol)ois.readObject(); 
        }
         catch(Exception e) {
            System.err.println("<CustomerWindow> " + e.getMessage()); 
         }
            
        Buy_ticket_window btw=new Buy_ticket_window(this,reponse.getCharge());
        btw.setVisible(true);
    }//GEN-LAST:event_buyTicketButtonActionPerformed

    public void AchatOk(Buy_ticket_window btw)
    {
        CardWindow cw = new CardWindow(this,btw);
        cw.setVisible(true); 
    }
    
    public void CarteOk(CardWindow cw,Buy_ticket_window btw)
    {
        // verif carte et code 
        String mess = (cw.numCarte.getText()+"#"+cw.codeCarte.getText()+"#"); 
        RequeteProtocolCard req = new RequeteProtocolCard(RequeteProtocolCard.CHECK_CARD,mess); 
        Network n = new Network();
        cSock = n.InitOnDemand(); 
        n.SendRequest(cSock,req); 
        
        ReponseProtocolCard rep = null;
        try {
            ois = new ObjectInputStream(cSock.getInputStream());
            rep = (ReponseProtocolCard)ois.readObject();
            
            if(rep.getCode()==ReponseProtocolCard.CODEPASOK) {
                JOptionPane.showMessageDialog(null, "Carte ou code incorrect", "Resultat achat", JOptionPane.INFORMATION_MESSAGE);
                AchatOk(btw); // si mauvais on refait 
            }
            else if(rep.getCode()==ReponseProtocolCard.CARTEPASOK) { 
                JOptionPane.showMessageDialog(null, "Crédit insuffisant", "Resultat achat", JOptionPane.INFORMATION_MESSAGE);
                AchatOk(btw); 
            } 
            else if(rep.getCode()==ReponseProtocolCard.CARTEOK) {
                JOptionPane.showMessageDialog(null, "Achat effectué", "Resultat achat", JOptionPane.INFORMATION_MESSAGE);
                CarteOkSuite(cw,btw);
            }
        }
        catch(Exception e) {
            System.err.println("<CartOK> " + e.getMessage());
        } 
    }
    public void CarteOkSuite(CardWindow cw,Buy_ticket_window btw)
    {
        StringTokenizer strtok = new StringTokenizer(""+btw.CBClient.getSelectedItem()," ");
        String mess;
        mess=(btw.CBTraversee.getSelectedItem()+"#");
        
        String temp=strtok.nextToken();
        
        if(temp.equals("NEW"))
        {
            mess+=("true"+"#"+strtok.nextToken()+"#"+strtok.nextToken()+"#"+btw.MatriculeTitu.getText()+"#");  
            mess+=(strtok.nextToken()+"#"+strtok.nextToken()+"#"+strtok.nextToken()+"#");
        }
        else
        {
            mess +=("false"+"#"+btw.CBClient.getSelectedItem()+"#"+btw.MatriculeTitu.getText()+"#");
        }
        mess+=((int)btw.NbPassagerSpinner.getValue()+"#");
        /*for(int i=0;i<(int)btw.NbPassagerSpinner.getValue();i++) {         
            if(btw.chknpassager.get(i).isSelected()) {
                 mess+=("true"+"#"+btw.tabJTFnom.get(i).getText()+"#"+btw.tabJTFprenom.get(i).getText()+"#"+btw.tabJTFadresse.get(i).getText()+"#"+btw.tabJTFpays.get(i).getText()+"#");
            }
            else {
               // mess +=("false"+"#"+btw.CBClient.getSelectedItem()+"#");
            }
        }*/
        RequeteProtocol requete = new RequeteProtocol(RequeteProtocol.BUY_TICKET,mess);
        Network n = new Network(); 
        cSock = n.Init(); 
        n.SendRequest(cSock,requete);
        ReponseProtocol reponse = null;
        try {
            ois = new ObjectInputStream(cSock.getInputStream());
            reponse = (ReponseProtocol)ois.readObject();
            if(reponse.getCode()==ReponseProtocol.ACHAT_OK) {
                JOptionPane.showMessageDialog(null, "Votre achat est bon\nVous pouvez vous mettre dans la file"+reponse.getCharge(), "Resultat achat", JOptionPane.INFORMATION_MESSAGE);
            }
            else {
                JOptionPane.showMessageDialog(null, "Votre achat est incorrect", "Resultat achat", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        catch(Exception e) {
            System.err.println("<AchatOk> " + e.getMessage());
        } 
    }
    
    private void closeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeButtonActionPerformed
        // TODO add your handling code here:
        RequeteProtocol requete = new RequeteProtocol(RequeteProtocol.CLOSE,"");
        Network n = new Network();
        cSock = n.Init(); 
        n.SendRequest(cSock,requete);
        
        ReponseProtocol reponse = null;
        try {
            ois = new ObjectInputStream(cSock.getInputStream());
            reponse = (ReponseProtocol)ois.readObject();
            
            /*if(reponse.getCode()==ReponseProtocol.ACHAT_OK) {
                JOptionPane.showMessageDialog(null, "Votre achat est bon", "Resultat achat", JOptionPane.INFORMATION_MESSAGE);
            }
            else {
                JOptionPane.showMessageDialog(null, "Votre achat est incorrect", "Resultat achat", JOptionPane.INFORMATION_MESSAGE);
            }*/
        }
        catch(Exception e) {
            System.err.println("<Close> " + e.getMessage()); 
        } 
    }//GEN-LAST:event_closeButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CustomerWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CustomerWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CustomerWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CustomerWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CustomerWindow().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buyTicketButton;
    private javax.swing.JButton closeButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField nbrPassagerTxt;
    private javax.swing.JTextField reservationTxt;
    private javax.swing.JButton verfiBookingButton;
    // End of variables declaration//GEN-END:variables
}
