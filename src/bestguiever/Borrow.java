/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bestguiever;

import boardgamerental.BoardGame;
import boardgamerental.BoardGameManagerImpl;
import boardgamerental.Customer;
import boardgamerental.CustomerManagerImpl;
import boardgamerental.Lending;
import boardgamerental.LendingManagerImpl;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingWorker;
import org.apache.commons.dbcp.BasicDataSource;

/**
 *
 * @author Goolomb
 */
public class Borrow extends javax.swing.JFrame {

    BasicDataSource basicDataSource = new BasicDataSource();
    private static final Logger LOGGER = Logger.getLogger(Borrow.class.getName());
    //private String action;

    LendingManagerImpl lendingManager;
    CustomerManagerImpl customerManager;
    BoardGameManagerImpl boardGameManager;

    BorrowTableModel borrowTableModel;
    CustomerTableModel customerTableModel;
    BoardGameTableModel boardGameTableModel;

    /**
     * Creates new form Borrow
     */
    public Borrow() {

        try {
            setUp();
        } catch (IOException ex) {
            String msg = "Application setup failed.";
            LOGGER.log(Level.SEVERE, msg, ex);
        }

        initComponents();

        lendingManager = new LendingManagerImpl();
        lendingManager.setDataSource(basicDataSource);
        customerManager = new CustomerManagerImpl();
        customerManager.setDataSource(basicDataSource);
        boardGameManager = new BoardGameManagerImpl();
        boardGameManager.setDataSource(basicDataSource);

        lendingsSwingWorker = new LendingsSwingWorker();
        lendingsSwingWorker.addPropertyChangeListener(lendingProgressListener);
        lendingsSwingWorker.execute();
        customersSwingWorker = new CustomersSwingWorker();
        customersSwingWorker.addPropertyChangeListener(customerProgressListener);
        customersSwingWorker.execute();
        boardGamesSwingWorker = new BoardGamesSwingWorker();
        boardGamesSwingWorker.addPropertyChangeListener(boardGameProgressListener);
        boardGamesSwingWorker.execute();
        
    }

    private LendingsSwingWorker lendingsSwingWorker;
    private class LendingsSwingWorker extends SwingWorker<Void, Lending> {

        @Override
        protected Void doInBackground() throws Exception {
            borrowTableModel = (BorrowTableModel) jTableBorrow.getModel();
            borrowTableModel.setLendingManager(lendingManager);
            int counter = 0;
            for (Lending lend : lendingManager.findAllLendings()) {
                counter++;
                publish(lend);
                setProgress(counter);
            }
            return null;
        }

        @Override
        protected void process(List<Lending> items) {
            for (Lending i : items) {
                borrowTableModel.addLending(i);
            }
        }

        @Override
        protected void done() {
            jProgressBarLendings.setValue(100);
            lendingsSwingWorker = null;
        }
    }

    private PropertyChangeListener lendingProgressListener = new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if (evt.getPropertyName().equals("progress")) {
                jProgressBarLendings.setValue((Integer) evt.getNewValue());
            }
        }
    };
    
    private CustomersSwingWorker customersSwingWorker;
    private class CustomersSwingWorker extends SwingWorker<Void, Customer> {

        @Override
        protected Void doInBackground() throws Exception {
            customerTableModel = (CustomerTableModel) jTable3.getModel();
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
            jProgressBarCustomers.setValue(100);
            customersSwingWorker = null;
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
    
    private BoardGamesSwingWorker boardGamesSwingWorker;
    private class BoardGamesSwingWorker extends SwingWorker<Void, BoardGame> {

        @Override
        protected Void doInBackground() throws Exception {
            boardGameTableModel = (BoardGameTableModel) jTable2.getModel();
            boardGameTableModel.setBGManager(boardGameManager);
            int counter = 0;
            for (BoardGame bg : boardGameManager.findAllBoardGames()) {
                counter++;
                publish(bg);
                setProgress(counter);
            }
            return null;
        }

        @Override
        protected void process(List<BoardGame> items) {
            for (BoardGame i : items) {
                boardGameTableModel.addBoardGame(i);
            }
        }

        @Override
        protected void done() {
            jProgressBarBoardGames.setValue(100);
            boardGamesSwingWorker = null;
        }
    }

    private PropertyChangeListener boardGameProgressListener = new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if (evt.getPropertyName().equals("progress")) {
                jProgressBarBoardGames.setValue((Integer) evt.getNewValue());
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

        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableBorrow = new javax.swing.JTable();
        jProgressBarLendings = new javax.swing.JProgressBar();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        int current = Calendar.getInstance().get(Calendar.YEAR);
        jSpinnerStartYear = new javax.swing.JSpinner(new SpinnerNumberModel(current,current,current+1,1));
        int current2 = Calendar.getInstance().get(Calendar.YEAR);
        jSpinnerEndYear = new javax.swing.JSpinner(new SpinnerNumberModel(current2,current2,current2+1,1));
        jSpinnerStartMonth = new javax.swing.JSpinner(new SpinnerNumberModel(1,1,12,1));
        jSpinnerEndMonth = new javax.swing.JSpinner(new SpinnerNumberModel(1,1,12,1));
        jSpinnerStartDay = new javax.swing.JSpinner(new SpinnerNumberModel(1,1,31,1));
        jSpinnerEndDay = new javax.swing.JSpinner(new SpinnerNumberModel(1,1,31,1));
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jProgressBarCustomers = new javax.swing.JProgressBar();
        jProgressBarBoardGames = new javax.swing.JProgressBar();
        jButton3 = new javax.swing.JButton();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setText("Lendings");

        jTableBorrow.setModel(new BorrowTableModel());
        jTableBorrow.setSelectionMode (0);
        jTableBorrow.getColumnModel().getColumn(0).setMaxWidth(20);
        jScrollPane1.setViewportView(jTableBorrow);

        jButton1.setText("Back");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Customers");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Board Games");

        jTable2.setModel(new BoardGameTableModelUnmodifiable());
        jTable2.setSelectionMode (0);
        jTable2.getColumnModel().getColumn(0).setMaxWidth(20);
        jScrollPane3.setViewportView(jTable2);

        jTable3.setModel(new CustomerTableModelUnmodifiable());
        jTable3.setSelectionMode (0);
        jTable3.getColumnModel().getColumn(0).setMaxWidth(20);
        jScrollPane4.setViewportView(jTable3);

        jLabel4.setText("From:");

        jLabel5.setText("To:");

        jButton2.setText("Add");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel6.setText("Year");

        jLabel7.setText("Month");

        jLabel8.setText("Day");

        jButton3.setText("Delete");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(85, 85, 85)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3)
                        .addGap(134, 134, 134))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton1)
                                .addGap(74, 74, 74)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
                                .addComponent(jButton3))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(jProgressBarLendings, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(29, 29, 29)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton2)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jSpinnerEndYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jSpinnerStartYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jSpinnerEndMonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jSpinnerStartMonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                    .addComponent(jSpinnerEndDay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jSpinnerStartDay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel8))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(layout.createSequentialGroup()
                .addGap(81, 81, 81)
                .addComponent(jProgressBarCustomers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jProgressBarBoardGames, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel8, jSpinnerEndDay, jSpinnerStartDay});

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel7, jSpinnerEndMonth, jSpinnerStartMonth});

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel6, jSpinnerEndYear, jSpinnerStartYear});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jLabel1)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(jButton3))
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jSpinnerStartYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSpinnerStartMonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSpinnerStartDay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jSpinnerEndYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSpinnerEndMonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSpinnerEndDay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jProgressBarLendings, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jProgressBarCustomers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jProgressBarBoardGames, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        new openingTable().setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        Lending lend = new Lending();
        
        Integer customer_id = null;
        try {
            customer_id = (Integer) customerTableModel.getValueAt(jTable3.getSelectedRow(), 0);
        } catch (ArrayIndexOutOfBoundsException e) {
            String msg = "Board Game not selected";
            LOGGER.log(Level.INFO, msg);
            JOptionPane.showMessageDialog(rootPane, msg, "Error", 2);
        }
        
        Integer boardGame_id = null;
        try {
            boardGame_id = (Integer) boardGameTableModel.getValueAt(jTable2.getSelectedRow(), 0);
        } catch (ArrayIndexOutOfBoundsException e) {
            String msg = "Board Game not selected";
            LOGGER.log(Level.INFO, msg);
            JOptionPane.showMessageDialog(rootPane, msg, "Error", 2);
        }
        
        lend.setCustomer(customerManager.getCustomerById(customer_id));
        lend.setBoardGame(boardGameManager.getBoardGameById(boardGame_id));

        /* From, To */
        try {
            Calendar c = new GregorianCalendar();
            c.set(
                    (Integer)jSpinnerStartYear.getValue(),
                    (Integer)jSpinnerStartMonth.getValue()-1,
                    (Integer)jSpinnerStartDay.getValue());
            lend.setStartTime(new Date(c.getTimeInMillis()));
            c.set(
                    (Integer)jSpinnerEndYear.getValue(),
                    (Integer)jSpinnerEndMonth.getValue()-1,
                    (Integer)jSpinnerEndDay.getValue());
            lend.setExpectedEndTime(new Date(c.getTimeInMillis()));
        } catch (IllegalArgumentException ex) {
            String msg = "Rent from or to wrong format";
            LOGGER.log(Level.INFO, msg);
            JOptionPane.showMessageDialog(rootPane, msg, "Error", 2);
        }
        /* Cost */
        /*try {
            rent.setCost(BigDecimal.valueOf(Double.parseDouble(dialog_rents_costInput.getText())).setScale(2));
        } catch (NumberFormatException ex) { // chyba prevodu String -> Double
            String msg = "Rent cost wrong format";
            LOGGER.log(Level.SEVERE, msg);
        }*/
        
        try {
                LOGGER.log(Level.INFO, "Adding lending");
                lendingManager.createLending(lend);
                borrowTableModel.addLending(lend);
            }
         catch (Exception ex) {
            String msg = "User request failed";
            LOGGER.log(Level.INFO, msg);
            JOptionPane.showMessageDialog(rootPane, msg, "Error", 2);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        Integer lending_id = null;
        try {
            lending_id = (Integer) borrowTableModel.getValueAt(jTableBorrow.getSelectedRow(), 0);
        } catch (ArrayIndexOutOfBoundsException e) {
            String msg = "No row selected";
            LOGGER.log(Level.INFO, msg);
            JOptionPane.showMessageDialog(rootPane, msg, "Error", 2);
        }
        
        Lending lend = lendingManager.getLendingById(lending_id);
        try {
            lendingManager.deleteLending(lend);
            borrowTableModel.removeLending(lend);
        } catch (Exception ex) {
            String msg = "Deleting failed";
            LOGGER.log(Level.INFO, msg);
            JOptionPane.showMessageDialog(rootPane, msg, "Error", 2);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

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
            java.util.logging.Logger.getLogger(Borrow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Borrow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Borrow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Borrow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Borrow().setVisible(true);
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
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JProgressBar jProgressBarBoardGames;
    private javax.swing.JProgressBar jProgressBarCustomers;
    private javax.swing.JProgressBar jProgressBarLendings;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSpinner jSpinnerEndDay;
    private javax.swing.JSpinner jSpinnerEndMonth;
    private javax.swing.JSpinner jSpinnerEndYear;
    private javax.swing.JSpinner jSpinnerStartDay;
    private javax.swing.JSpinner jSpinnerStartMonth;
    private javax.swing.JSpinner jSpinnerStartYear;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTableBorrow;
    // End of variables declaration//GEN-END:variables
}
