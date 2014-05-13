/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bestguiever;

import boardgamerental.Customer;
import boardgamerental.CustomerManagerImpl;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import static java.util.ResourceBundle.getBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import org.apache.commons.dbcp.BasicDataSource;

/**
 *
 * @author Goolomb
 */
public class AddCustomer extends javax.swing.JFrame {

    private static final Logger LOGGER = Logger.getLogger(AddCustomer.class.getName());
    private BasicDataSource basicDataSource = new BasicDataSource();
    private CustomerManagerImpl customerManager;
    private CustomerTableModel customerTableModel;

    /**
     * Creates new form AddCustomer
     */
    public AddCustomer() {
        
        try {
            setUp();
        } catch (IOException ex) {
            String msg = "Application setup failed.";
            LOGGER.log(Level.SEVERE, msg, ex);
        }

        initComponents();

        customerManager = new CustomerManagerImpl();
        customerManager.setDataSource(basicDataSource);
        
        customerSwingWorker = new CustomerSwingWorker();
        customerSwingWorker.addPropertyChangeListener(customerProgressListener);
        customerSwingWorker.execute();
    }
    
    private CustomerSwingWorker customerSwingWorker;
    private class CustomerSwingWorker extends SwingWorker<Void, Customer> {

        @Override
        protected Void doInBackground() throws Exception {
            customerTableModel = (CustomerTableModel) jTableCustomers.getModel();
            customerTableModel.setCustomerManager(customerManager);
            int counter = 0;
            for (Customer customer : customerManager.findAllCustomers()) {
                counter++;
                publish(customer);
                setProgress(counter);
            }
            return null;
        }

        @Override
        protected void process(List<Customer> items) {
            for (Customer i : items) {
                customerTableModel.addCustomer(i);
            }
        }

        @Override
        protected void done() {
            //customers_load.setEnabled(true);
            jProgressBarCustomers.setValue(100);
            customerSwingWorker = null;
        }
    }

    private PropertyChangeListener customerProgressListener = new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if (evt.getPropertyName().equals("progress")) {
                jProgressBarCustomers.setValue((Integer) evt.getNewValue());
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

        jLabelCustomer = new javax.swing.JLabel();
        jLabelName = new javax.swing.JLabel();
        jLabelAddress = new javax.swing.JLabel();
        jLabelPhone = new javax.swing.JLabel();
        jTextName = new javax.swing.JTextField();
        jTextAddress = new javax.swing.JTextField();
        jTextPhone = new javax.swing.JTextField();
        jButtonBack = new javax.swing.JButton();
        jButtonAdd = new javax.swing.JButton();
        jScrollPaneCustomers = new javax.swing.JScrollPane();
        jTableCustomers = new javax.swing.JTable();
        jProgressBarCustomers = new javax.swing.JProgressBar();
        jButtonDelete = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabelCustomer.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("bestguiever/Bundle"); // NOI18N
        jLabelCustomer.setText(bundle.getString("BoardGameRental.jLabelCustomer")); // NOI18N

        jLabelName.setText(bundle.getString("BoardGameRental.jLabelName")); // NOI18N

        jLabelAddress.setText(bundle.getString("BoardGameRental.jLabelAddress")); // NOI18N

        jLabelPhone.setText(bundle.getString("BoardGameRental.jLabelPhone")); // NOI18N

        jTextName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextNameActionPerformed(evt);
            }
        });

        jTextAddress.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextAddressActionPerformed(evt);
            }
        });

        jButtonBack.setText(bundle.getString("BoardGameRental.jButtonBack")); // NOI18N
        jButtonBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBackActionPerformed(evt);
            }
        });

        jButtonAdd.setText(bundle.getString("BoardGameRental.jButtonAdd")); // NOI18N
        jButtonAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddActionPerformed(evt);
            }
        });

        jTableCustomers.setModel(new CustomerTableModel());
        jTableCustomers.setSelectionMode (0);
        jTableCustomers.getColumnModel().getColumn(0).setMaxWidth(20);
        jScrollPaneCustomers.setViewportView(jTableCustomers);

        jButtonDelete.setText(bundle.getString("BoardGameRental.jButtonDelete")); // NOI18N
        jButtonDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDeleteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelName)
                            .addComponent(jLabelAddress)
                            .addComponent(jLabelPhone))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextName, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButtonBack)
                        .addGap(40, 40, 40)
                        .addComponent(jLabelCustomer)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPaneCustomers, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jProgressBarCustomers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonDelete)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonAdd)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jTextAddress, jTextName, jTextPhone});

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabelAddress, jLabelName, jLabelPhone});

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jProgressBarCustomers, jScrollPaneCustomers});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonBack)
                    .addComponent(jLabelCustomer)
                    .addComponent(jButtonAdd)
                    .addComponent(jButtonDelete))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelName)
                            .addComponent(jTextName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelAddress)
                            .addComponent(jTextAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelPhone)
                            .addComponent(jTextPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPaneCustomers, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jProgressBarCustomers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextNameActionPerformed

    private void jButtonAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddActionPerformed
        try {
            Customer customer = new Customer(
                    jTextName.getText(),
                    jTextAddress.getText(),
                    jTextPhone.getText());

            customerManager.createCustomer(customer);
            customerTableModel.addCustomer(customer);
            jTextName.setText("");
            jTextAddress.setText("");
            jTextPhone.setText("");

        } catch (Exception ex) {
            String msg = getBundle("bestguiever/Bundle").getString("BoardGameRental.RequestFailed");
            LOGGER.log(Level.INFO, msg);
            JOptionPane.showMessageDialog(rootPane, ex.getMessage(), "Error", 2);
        }
    }//GEN-LAST:event_jButtonAddActionPerformed

    private void jButtonBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBackActionPerformed
        new openingTable().setVisible(true);
        dispose();
    }//GEN-LAST:event_jButtonBackActionPerformed

    private void jButtonDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDeleteActionPerformed
        Integer customer_id = null;
        try {
            customer_id = (Integer) customerTableModel.getValueAt(jTableCustomers.getSelectedRow(), 0);
        } catch (ArrayIndexOutOfBoundsException e) {
            String msg = getBundle("bestguiever/Bundle").getString("BoardGameRental.CustomerNotSelected");
            LOGGER.log(Level.INFO, msg);
            JOptionPane.showMessageDialog(rootPane, msg, "Error", 2);
        }
        
        Customer customer = customerManager.getCustomerById(customer_id);
        try {
            customerManager.deleteCustomer(customer);
            customerTableModel.removeCustomer(customer);
        } catch (Exception ex) {
            String msg = getBundle("bestguiever/Bundle").getString("BoardGameRental.DeletingFailed");
            LOGGER.log(Level.INFO, msg);
            JOptionPane.showMessageDialog(rootPane, msg, "Error", 2);
        }
    }//GEN-LAST:event_jButtonDeleteActionPerformed

    private void jTextAddressActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextAddressActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextAddressActionPerformed

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
            java.util.logging.Logger.getLogger(AddCustomer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddCustomer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddCustomer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddCustomer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AddCustomer().setVisible(true);
            }
        });
    }

    private void setUp() throws IOException {
        Properties configFile = new Properties();
        configFile.load(new FileInputStream("src/DBprop.properties"));
        BasicDataSource bds = new BasicDataSource();
        bds.setUrl(configFile.getProperty("url"));
        bds.setPassword(configFile.getProperty("password"));
        bds.setUsername(configFile.getProperty("username"));
        basicDataSource = bds;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAdd;
    private javax.swing.JButton jButtonBack;
    private javax.swing.JButton jButtonDelete;
    private javax.swing.JLabel jLabelAddress;
    private javax.swing.JLabel jLabelCustomer;
    private javax.swing.JLabel jLabelName;
    private javax.swing.JLabel jLabelPhone;
    private javax.swing.JProgressBar jProgressBarCustomers;
    private javax.swing.JScrollPane jScrollPaneCustomers;
    private javax.swing.JTable jTableCustomers;
    private javax.swing.JTextField jTextAddress;
    private javax.swing.JTextField jTextName;
    private javax.swing.JTextField jTextPhone;
    // End of variables declaration//GEN-END:variables
}
