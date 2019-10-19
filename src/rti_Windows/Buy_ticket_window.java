/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rti_Windows;
import java.awt.Dimension;
import java.awt.Point;
import java.util.Vector;

/**
 *
 * @author tiboo
 */
public class Buy_ticket_window extends javax.swing.JFrame {
    
    private CustomerWindow Mywindow;

    private int nbpassager =0;
    private boolean newpassager=false;
    private boolean newtitu = false;
    
    public Vector<javax.swing.JTextField> tabJTFnom;
    public Vector<javax.swing.JTextField> tabJTFprenom;
    public Vector<javax.swing.JTextField> tabJTFadresse;
    public Vector<javax.swing.JTextField> tabJTFpays;
    public javax.swing.JTextField JTFemailtitu;
    public javax.swing.JTextField JTFadressetitu;
    public javax.swing.JTextField JTFpaystitu;
    
    public javax.swing.JLabel labemail,labadresse,labpays;
    public Vector<javax.swing.JLabel> labnpassager;
    
    public Vector<javax.swing.JCheckBox> chknpassager;
    
    private javax.swing.GroupLayout layout;
    /**
     * Creates new form Buy_ticket_window
     */
    public Buy_ticket_window(CustomerWindow mywindow) {
        initComponents();
        Mywindow = mywindow;
        tabJTFnom = new Vector<javax.swing.JTextField>();
        tabJTFnom.setSize(10);
        tabJTFprenom = new Vector<javax.swing.JTextField>();
        tabJTFprenom.setSize(10);
        tabJTFadresse = new Vector<javax.swing.JTextField>();
        tabJTFadresse.setSize(10);
        tabJTFpays = new Vector<javax.swing.JTextField>();
        tabJTFpays.setSize(10);
        JTFemailtitu = new javax.swing.JTextField();
        JTFadressetitu = new javax.swing.JTextField();
        JTFpaystitu = new javax.swing.JTextField();
        labemail = new javax.swing.JLabel();
        labadresse = new javax.swing.JLabel();
        labpays = new javax.swing.JLabel();
        labnpassager = new Vector<javax.swing.JLabel>();
        labnpassager.setSize(10);
        chknpassager = new Vector<javax.swing.JCheckBox>();
        chknpassager.setSize(10);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        labmatricule = new javax.swing.JLabel();
        NomTitu = new javax.swing.JTextField();
        PrenomTitu = new javax.swing.JTextField();
        MatriculeTitu = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        NbPassagerSpinner = new javax.swing.JSpinner();
        NewCli = new javax.swing.JCheckBox();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        OKButton = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jLabel7.setText("Formulaire Voyageur Titulaire");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("ACHAT TICKET");
        jLabel1.setToolTipText("");

        jLabel2.setText("Formulaire Voyageur Titulaire");

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Nom");

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Prénom");

        labmatricule.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labmatricule.setText("Matricule Voiture");

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Nombre passager");

        NbPassagerSpinner.setModel(new javax.swing.SpinnerNumberModel(0, 0, 10, 1));
        NbPassagerSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                NbPassagerSpinnerStateChanged(evt);
            }
        });

        NewCli.setText("Nouveau Client?");
        NewCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NewCliActionPerformed(evt);
            }
        });

        jLabel8.setText("Formulaire Voyageurs Accompagnants");

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Nom");

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Prénom");

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Nouveau Client?");

        OKButton.setText("OK");
        OKButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OKButtonActionPerformed(evt);
            }
        });

        jButton1.setText("Annuler");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                                    .addComponent(NomTitu))
                                .addGap(32, 32, 32)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(PrenomTitu, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(37, 37, 37)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(MatriculeTitu, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                                    .addComponent(labmatricule, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(NbPassagerSpinner, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(NewCli)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(38, 38, 38)
                        .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(26, 26, 26)
                        .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(11, 11, 11))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(OKButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(NewCli))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(labmatricule)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(NomTitu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(PrenomTitu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(MatriculeTitu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(NbPassagerSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(OKButton)
                    .addComponent(jButton1)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    private void CheckboxActionPerformed(java.awt.event.ActionEvent evt) { 
        javax.swing.JCheckBox chktemp = new javax.swing.JCheckBox();
        chktemp=(javax.swing.JCheckBox)evt.getSource();
        int index;
        index=chknpassager.indexOf(chktemp);
        if(chktemp.isSelected()==true) {
            if(newtitu == false && newpassager ==false) {
                System.out.println("Je suis dans agrandir fenetre car premier cochage");
                setSize(new Dimension(this.getWidth()+400,this.getHeight()));
                newpassager =true;
            }
            System.out.println("Je suis dans ajouter les trucs au correspondant");
            javax.swing.JTextField jtftemp = new javax.swing.JTextField();
            jtftemp = new javax.swing.JTextField();
            System.out.println("test1");
            jtftemp.setLayout(layout);
            jtftemp.setSize(MatriculeTitu.getSize());
            Point yo = MatriculeTitu.getLocation();
            jtftemp.setLocation(yo.x+135*2,yo.y+100+(75*index));
            jtftemp.setVisible(true);
            System.out.println("test2 index = "+index);
            tabJTFadresse.setElementAt(jtftemp,index);
            System.out.println("test3");
            add(tabJTFadresse.get(index));
            System.out.println("test4");

            jtftemp = new javax.swing.JTextField();
            jtftemp.setLayout(layout);
            jtftemp.setSize(MatriculeTitu.getSize());
            yo = MatriculeTitu.getLocation();
            jtftemp.setLocation(yo.x+135*3,yo.y+100+(75*index));
            jtftemp.setVisible(true);
            System.out.println("test5");
            tabJTFpays.setElementAt(jtftemp,index);
            System.out.println("test6");
            add(tabJTFpays.get(index));
            System.out.println("test7");
            
            setSize(new Dimension(this.getWidth()-400,this.getHeight()));
            setSize(new Dimension(this.getWidth()+400,this.getHeight()));
        }
        else {
            System.out.println("Je suis dans enlever les truc a cité du bon");
            remove(tabJTFadresse.get(index));
            tabJTFadresse.remove(index);
            remove(tabJTFpays.get(index));
            tabJTFpays.remove(index);
            
            setSize(new Dimension(this.getWidth()-400,this.getHeight()));
            setSize(new Dimension(this.getWidth()+400,this.getHeight()));
            
            boolean yay=false;
            for(int i=0;i<chknpassager.size();i++) {
                chktemp = new javax.swing.JCheckBox();
                if(chktemp.isSelected()==true) {
                    yay=true;
                }
            }
            if(yay==false && newtitu ==false) {
                newpassager=false;
                setSize(new Dimension(this.getWidth()-400,this.getHeight()));
                System.out.println("Je suis dans rétraicir fenetre car dernier decochage");
            }
        }
    }
     
    private void NewCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NewCliActionPerformed
        // TODO add your handling code here:
        if(NewCli.isSelected()) {
             if(newpassager==false)
                setSize(new Dimension(this.getWidth()+400,this.getHeight()));
             
             newtitu=true;
             JTFemailtitu.setLayout(layout);
             JTFemailtitu.setSize(MatriculeTitu.getSize());
             Point yo = MatriculeTitu.getLocation();
             JTFemailtitu.setLocation(yo.x+135,yo.y);
             JTFemailtitu.setVisible(true);
             add(JTFemailtitu);
             JTFadressetitu.setLayout(layout);
             JTFadressetitu.setSize(MatriculeTitu.getSize());
             yo = MatriculeTitu.getLocation();
             JTFadressetitu.setLocation(yo.x+135*2,yo.y);
             JTFadressetitu.setVisible(true);
             add(JTFadressetitu);
             JTFpaystitu.setLayout(layout);
             JTFpaystitu.setSize(MatriculeTitu.getSize());
             yo = MatriculeTitu.getLocation();
             JTFpaystitu.setLocation(yo.x+135*3,yo.y);
             JTFpaystitu.setVisible(true);
             add(JTFpaystitu);
             labemail.setLayout(layout);
             labemail.setSize(labmatricule.getSize());
             yo = labmatricule.getLocation();
             labemail.setLocation(yo.x+135,yo.y);
             labemail.setVisible(true);
             labemail.setText("Email");
             labemail.setHorizontalAlignment(0);
             add(labemail);
             labadresse = new javax.swing.JLabel();
             labadresse.setLayout(layout);
             labadresse.setSize(labmatricule.getSize());
             yo = labmatricule.getLocation();
             labadresse.setLocation(yo.x+135*2,yo.y);
             labadresse.setText("Adresse");
             labadresse.setHorizontalAlignment(0);
             add(labadresse);
             labpays = new javax.swing.JLabel();
             labpays.setLayout(layout);
             labpays.setSize(labmatricule.getSize());
             yo = labmatricule.getLocation();
             labpays.setLocation(yo.x+135*3,yo.y);
             labpays.setText("Pays");
             labpays.setHorizontalAlignment(0);
             add(labpays);
        }
        else {
            if(newpassager==false)
                setSize(new Dimension(this.getWidth()-400,this.getHeight())); 
            
            newtitu=false;
            remove(labpays);
            remove(labemail);
            remove(labadresse);
            remove(JTFpaystitu);
            remove(JTFemailtitu);
            remove(JTFadressetitu);
        }
    }//GEN-LAST:event_NewCliActionPerformed

    private void NbPassagerSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_NbPassagerSpinnerStateChanged
        int temp;
        temp =(int)NbPassagerSpinner.getValue()-nbpassager;
        nbpassager=(int)NbPassagerSpinner.getValue();
        
        setSize(new Dimension(this.getWidth(),this.getHeight()+(temp*75))); 
        
        if(temp>=0) {
            for(int i=0;i<temp;i++) {
                javax.swing.JTextField jtftemp = new javax.swing.JTextField();
                jtftemp.setLayout(layout);
                jtftemp.setSize(PrenomTitu.getSize());
                Point yo = PrenomTitu.getLocation();
                jtftemp.setLocation(yo.x,yo.y+100+(75*(nbpassager-(temp-i))));
                jtftemp.setVisible(true);
                tabJTFnom.setElementAt(jtftemp,nbpassager-(temp-i));                
                add(tabJTFnom.get(nbpassager-(temp-i)));
                
                jtftemp = new javax.swing.JTextField();
                jtftemp.setLayout(layout);
                jtftemp.setSize(MatriculeTitu.getSize());
                yo = MatriculeTitu.getLocation();
                jtftemp.setLocation(yo.x,yo.y+100+(75*(nbpassager-(temp-i))));
                jtftemp.setVisible(true);
                tabJTFprenom.setElementAt(jtftemp,nbpassager-(temp-i));                
                add(tabJTFprenom.get(nbpassager-(temp-i)));
                
                if(newpassager==true) {
                    jtftemp = new javax.swing.JTextField();
                    jtftemp.setLayout(layout);
                    jtftemp.setSize(MatriculeTitu.getSize());
                    yo = MatriculeTitu.getLocation();
                    jtftemp.setLocation(yo.x+135*2,yo.y+100+(75*(nbpassager-(temp-i))));
                    jtftemp.setVisible(true);
                    tabJTFadresse.setElementAt(jtftemp,nbpassager-(temp-i));                
                    add(tabJTFadresse.get(nbpassager-(temp-i)));
                    
                    jtftemp = new javax.swing.JTextField();
                    jtftemp.setLayout(layout);
                    jtftemp.setSize(MatriculeTitu.getSize());
                    yo = MatriculeTitu.getLocation();
                    jtftemp.setLocation(yo.x+135*3,yo.y+100+(75*(nbpassager-(temp-i))));
                    jtftemp.setVisible(true);
                    tabJTFpays.setElementAt(jtftemp,nbpassager-(temp-i));                
                    add(tabJTFpays.get(nbpassager-(temp-i)));
                }
                
                javax.swing.JLabel ltemp = new javax.swing.JLabel();
                ltemp.setLayout(layout);
                ltemp.setSize(NomTitu.getSize());
                yo = NomTitu.getLocation();
                ltemp.setLocation(yo.x,yo.y+100+(75*(nbpassager-(temp-i))));
                ltemp.setText("Passager"+((nbpassager-(temp-i))+1));
                ltemp.setHorizontalAlignment(0);
                labnpassager.setElementAt(ltemp, nbpassager-(temp-i));
                add(labnpassager.get(nbpassager-(temp-i)));
                
                javax.swing.JCheckBox chktemp = new javax.swing.JCheckBox();
                chktemp = new javax.swing.JCheckBox();
                chktemp.setLayout(layout);
                chktemp.setSize(NewCli.getSize());
                yo = NomTitu.getLocation();
                chktemp.setLocation(yo.x+135*3,yo.y+100+(75*(nbpassager-(temp-i))));
                chktemp.addActionListener(new java.awt.event.ActionListener() {
                    @Override
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        CheckboxActionPerformed(evt);
                        }
                    });
                chknpassager.setElementAt(chktemp, nbpassager-(temp-i));
                add(chknpassager.get(nbpassager-(temp-i)));
            }
        }
        else {
            for(int i=0;i>temp;i--) {
                remove(tabJTFnom.get(nbpassager-temp+i));
                tabJTFnom.remove(nbpassager-temp+i);
                remove(tabJTFprenom.get(nbpassager-temp+i));
                tabJTFprenom.remove(nbpassager-temp+i);
                remove(labnpassager.get(nbpassager-temp+i));
                labnpassager.remove(nbpassager-temp+i);
                remove(chknpassager.get(nbpassager-temp+i));
                chknpassager.remove(nbpassager-temp+i);               
                remove(tabJTFadresse.get(nbpassager-temp+i));
                tabJTFadresse.remove(nbpassager-temp+i);
                remove(tabJTFpays.get(nbpassager-temp+i));
                tabJTFpays.remove(nbpassager-temp+i);
            }
        }
    }//GEN-LAST:event_NbPassagerSpinnerStateChanged

    private void OKButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OKButtonActionPerformed
        Mywindow.AchatOk(this);
        this.setVisible(false);
    }//GEN-LAST:event_OKButtonActionPerformed

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
            java.util.logging.Logger.getLogger(Buy_ticket_window.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Buy_ticket_window.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Buy_ticket_window.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Buy_ticket_window.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Buy_ticket_window(null).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JTextField MatriculeTitu;
    public javax.swing.JSpinner NbPassagerSpinner;
    public javax.swing.JCheckBox NewCli;
    public javax.swing.JTextField NomTitu;
    private javax.swing.JButton OKButton;
    public javax.swing.JTextField PrenomTitu;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel labmatricule;
    // End of variables declaration//GEN-END:variables
    private javax.swing.JTextField NomPassager1;
}
