/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bestguiever;

import boardgamerental.Customer;
import boardgamerental.CustomerManagerImpl;
import boardgamerental.Lending;
import boardgamerental.LendingManagerImpl;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import org.apache.commons.dbcp.BasicDataSource;

/**
 *
 * @author Goolomb
 */
public class ShowLendingsForCustomer extends javax.swing.JFrame {

    private static final Logger LOGGER = Logger.getLogger(FindBoardGame.class.getName());
    BasicDataSource basicDataSource = new BasicDataSource();
    CustomerManagerImpl customerManager;
    LendingManagerImpl lendingManager;
    
    /**
     * Creates new form ShowMyLendings
     */
    public ShowLendingsForCustomer() {
        try {
            setUp();
        } catch (IOException ex) {
	    String msg = "Application setup failed.";
            LOGGER.log(Level.SEVERE, msg, ex);
        }
        
        initComponents();
        
        customerManager = new CustomerManagerImpl();
        customerManager.setDataSource(basicDataSource);
        lendingManager = new LendingManagerImpl();
        lendingManager.setDataSource(basicDataSource);
    }
    
    private void setUp() throws IOException {
        Properties configFile = new Properties();
        configFile.load(new FileInputStream("src/DBprop.properties"));
	BasicDataSource bds = new BasicDataSource();
	bds.setUrl( configFile.getProperty( "url" ) );
	bds.setPassword( configFile.getProperty( "password" ) );
	bds.setUsername( configFile.getProperty( "username" ) );
	basicDataSource = bds;
    }
    
    DefaultListModel<String> nameModel;
    private CustomersNameSwingWorker customerNamesSwingWorker;
    List<Customer> byName = new ArrayList<>();
    
    private class CustomersNameSwingWorker extends SwingWorker<Void, Customer> {

        @Override
        protected Void doInBackground() throws Exception {

            String name = jTextFieldName.getText();
            
            if (!name.equals("")) {
                byName = customerManager.findCustomerByName(name);
            }
            
            nameModel = (DefaultListModel)jList1.getModel();
            nameModel.clear();
            
            int counter = 0;
            for (Customer customer : byName) {
                counter++;
                publish(customer);
                setProgress(counter);
            }
            return null;
        }
	
	@Override
	protected void process(List<Customer> items) {
	    for (Customer i : items) {
                nameModel.addElement(i.getName());
	    }
	}

        @Override
        protected void done() {
            //customers_load.setEnabled(true);
            jProgressBar.setValue(100);
            customerNamesSwingWorker = null;
        }
    }
    
    LendingSwingWorker lendingSwingWorker;
    BorrowTableModel borrowModel;
    
    private class LendingSwingWorker extends SwingWorker<Void, Lending> {

        @Override
        protected Void doInBackground() throws Exception {
            List<Lending> byCustomer = new ArrayList<>();
            int index = 0;
            
            try {
                index = jList1.getSelectedIndex();
            } catch (ArrayIndexOutOfBoundsException e) {
                String msg = "No name selected";
                LOGGER.log(Level.INFO, msg);
                JOptionPane.showMessageDialog(rootPane, msg, "Error", 2);
            }
        
            byCustomer = lendingManager.findLendingsForCustomer(byName.get(index));

            borrowModel = (BorrowTableModel)jTable2.getModel();
            borrowModel.setLendingManager(lendingManager);
            
            int counter = 0;
            for (Lending lending : byCustomer) {
                counter++;
                publish(lending);
                setProgress(counter);
            }
            return null;
        }
	
	@Override
	protected void process(List<Lending> items) {
	    for (Lending i : items) {
                borrowModel.addLending(i);
	    }
	}

        @Override
        protected void done() {
            //customers_load.setEnabled(true);
            jProgressBar.setValue(100);
            customerNamesSwingWorker = null;
        }
    }
   
    private PropertyChangeListener progressListener = new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if (evt.getPropertyName().equals("progress")) {
                jProgressBar.setValue((Integer) evt.getNewValue());
            }
        }
    };

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jButtonBack = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldName = new javax.swing.JTextField();
        jButtonFind = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jButtonChoose = new javax.swing.JButton();
        jProgressBar = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setText("Lendings:");

        jTable2.setModel(new BorrowTableModel());
        jScrollPane1.setViewportView(jTable2);

        jButtonBack.setText("Back");
        jButtonBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBackActionPerformed(evt);
            }
        });

        jLabel2.setText("For Customer:");

        jButtonFind.setText("Find");
        jButtonFind.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonFindActionPerformed(evt);
            }
        });

        jList1.setModel(new javax.swing.DefaultListModel<String>()
        );
        jScrollPane3.setViewportView(jList1);

        jButtonChoose.setText("Choose");
        jButtonChoose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonChooseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jTextFieldName, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButtonFind))
                                    .addComponent(jLabel2)
                                    .addComponent(jButtonBack))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonChoose))
                            .addComponent(jProgressBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(146, 146, 146)
                        .addComponent(jLabel1)))
                .addContainerGap(50, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButtonBack)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonFind)))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonChoose))
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jProgressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(37, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBackActionPerformed
        new openingTable().setVisible(true);
        dispose();
    }//GEN-LAST:event_jButtonBackActionPerformed

    private void jButtonFindActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonFindActionPerformed
        customerNamesSwingWorker = new ShowLendingsForCustomer.CustomersNameSwingWorker();
        customerNamesSwingWorker.addPropertyChangeListener(progressListener);
        customerNamesSwingWorker.execute();
    }//GEN-LAST:event_jButtonFindActionPerformed

    private void jButtonChooseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonChooseActionPerformed
        lendingSwingWorker = new ShowLendingsForCustomer.LendingSwingWorker();
        lendingSwingWorker.addPropertyChangeListener(progressListener);
        lendingSwingWorker.execute();
    }//GEN-LAST:event_jButtonChooseActionPerformed

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
            java.util.logging.Logger.getLogger(ShowLendingsForCustomer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ShowLendingsForCustomer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ShowLendingsForCustomer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ShowLendingsForCustomer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ShowLendingsForCustomer().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonBack;
    private javax.swing.JButton jButtonChoose;
    private javax.swing.JButton jButtonFind;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JList jList1;
    private javax.swing.JProgressBar jProgressBar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextFieldName;
    // End of variables declaration//GEN-END:variables
}
