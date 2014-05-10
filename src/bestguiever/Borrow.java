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
    BoardGameTableModelUnmodifiable boardGameTableModel;

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
            boardGameTableModel = (BoardGameTableModelUnmodifiable) jTableBordGames.getModel();
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
                if (lendingManager.isAvailable(i)) {
                    boardGameTableModel.addBoardGame(i);
                }
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
        jLabelLending = new javax.swing.JLabel();
        jScrollPaneLendings = new javax.swing.JScrollPane();
        jTableBorrow = new javax.swing.JTable();
        jProgressBarLendings = new javax.swing.JProgressBar();
        jButtonBack = new javax.swing.JButton();
        jLabelCustomers = new javax.swing.JLabel();
        jLabelBoardGames = new javax.swing.JLabel();
        jScrollPaneBoardGames = new javax.swing.JScrollPane();
        jTableBordGames = new javax.swing.JTable();
        jScrollPaneCustomers = new javax.swing.JScrollPane();
        jTableCustomers = new javax.swing.JTable();
        jLabelFrom = new javax.swing.JLabel();
        jLabelTo = new javax.swing.JLabel();
        jButtonAdd = new javax.swing.JButton();
        int current = Calendar.getInstance().get(Calendar.YEAR);
        jSpinnerStartYear = new javax.swing.JSpinner(new SpinnerNumberModel(current,current,current+1,1));
        int current2 = Calendar.getInstance().get(Calendar.YEAR);
        jSpinnerEndYear = new javax.swing.JSpinner(new SpinnerNumberModel(current2,current2,current2+1,1));
        jSpinnerStartMonth = new javax.swing.JSpinner(new SpinnerNumberModel(1,1,12,1));
        jSpinnerEndMonth = new javax.swing.JSpinner(new SpinnerNumberModel(1,1,12,1));
        jSpinnerStartDay = new javax.swing.JSpinner(new SpinnerNumberModel(1,1,31,1));
        jSpinnerEndDay = new javax.swing.JSpinner(new SpinnerNumberModel(1,1,31,1));
        jLabelYear = new javax.swing.JLabel();
        jLabelMonth = new javax.swing.JLabel();
        jLabelDay = new javax.swing.JLabel();
        jProgressBarCustomers = new javax.swing.JProgressBar();
        jProgressBarBoardGames = new javax.swing.JProgressBar();
        jButtonDelete = new javax.swing.JButton();
        jButtonAvailable = new javax.swing.JButton();

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

        jLabelLending.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelLending.setText("Lendings");

        jTableBorrow.setModel(new BorrowTableModel());
        jTableBorrow.setSelectionMode (0);
        jTableBorrow.getColumnModel().getColumn(0).setMaxWidth(20);
        jScrollPaneLendings.setViewportView(jTableBorrow);

        jButtonBack.setText("Back");
        jButtonBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBackActionPerformed(evt);
            }
        });

        jLabelCustomers.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelCustomers.setText("Customers");

        jLabelBoardGames.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelBoardGames.setText("Board Games");

        jTableBordGames.setModel(new BoardGameTableModelUnmodifiable());
        jTableBordGames.setSelectionMode (0);
        jTableBordGames.getColumnModel().getColumn(0).setMaxWidth(20);
        jScrollPaneBoardGames.setViewportView(jTableBordGames);

        jTableCustomers.setModel(new CustomerTableModelUnmodifiable());
        jTableCustomers.setSelectionMode (0);
        jTableCustomers.getColumnModel().getColumn(0).setMaxWidth(20);
        jScrollPaneCustomers.setViewportView(jTableCustomers);

        jLabelFrom.setText("From:");

        jLabelTo.setText("To:");

        jButtonAdd.setText("Add");
        jButtonAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddActionPerformed(evt);
            }
        });

        jLabelYear.setText("Year");

        jLabelMonth.setText("Month");

        jLabelDay.setText("Day");

        jButtonDelete.setText("Delete");
        jButtonDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDeleteActionPerformed(evt);
            }
        });

        jButtonAvailable.setText("Get available");
        jButtonAvailable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAvailableActionPerformed(evt);
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
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jProgressBarLendings, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButtonBack)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabelLending)
                                .addGap(69, 69, 69)
                                .addComponent(jButtonDelete)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonAdd))
                            .addComponent(jScrollPaneLendings, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabelTo)
                            .addComponent(jLabelFrom))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSpinnerEndYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSpinnerStartYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelYear))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSpinnerEndMonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSpinnerStartMonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelMonth))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                .addComponent(jSpinnerEndDay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jSpinnerStartDay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabelDay)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPaneCustomers, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jProgressBarCustomers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(101, 101, 101)
                                .addComponent(jLabelCustomers)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPaneBoardGames, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jProgressBarBoardGames, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(94, 94, 94)
                                .addComponent(jLabelBoardGames)
                                .addGap(18, 18, 18)
                                .addComponent(jButtonAvailable)))))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabelDay, jSpinnerEndDay, jSpinnerStartDay});

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabelMonth, jSpinnerEndMonth, jSpinnerStartMonth});

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabelYear, jSpinnerEndYear, jSpinnerStartYear});

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabelFrom, jLabelTo});

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jProgressBarCustomers, jScrollPaneCustomers});

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jProgressBarBoardGames, jScrollPaneBoardGames});

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jProgressBarLendings, jScrollPaneLendings});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addComponent(jButtonBack))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabelLending)))
                        .addGap(11, 11, 11))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButtonAdd)
                            .addComponent(jButtonDelete))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPaneLendings, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelMonth)
                            .addComponent(jLabelDay))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabelYear)
                                .addGap(11, 11, 11)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jSpinnerStartYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabelFrom))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jSpinnerEndYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabelTo)))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jSpinnerStartDay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jSpinnerEndDay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jSpinnerStartMonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jSpinnerEndMonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jProgressBarLendings, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonAvailable)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabelCustomers)
                        .addComponent(jLabelBoardGames)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPaneBoardGames, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPaneCustomers, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jProgressBarCustomers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jProgressBarBoardGames, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBackActionPerformed
        new openingTable().setVisible(true);
        dispose();
    }//GEN-LAST:event_jButtonBackActionPerformed

    private void jButtonAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddActionPerformed
        Lending lend = new Lending();

        Integer customer_id = null;
        try {
            customer_id = (Integer) customerTableModel.getValueAt(jTableCustomers.getSelectedRow(), 0);
        } catch (ArrayIndexOutOfBoundsException e) {
            String msg = "Customer not selected";
            LOGGER.log(Level.INFO, msg);
            JOptionPane.showMessageDialog(rootPane, msg, "Error", 2);
            return;
        }

        Integer boardGame_id = null;
        try {
            boardGame_id = (Integer) boardGameTableModel.getValueAt(jTableBordGames.getSelectedRow(), 0);
        } catch (ArrayIndexOutOfBoundsException e) {
            String msg = "Board Game not selected";
            LOGGER.log(Level.INFO, msg);
            JOptionPane.showMessageDialog(rootPane, msg, "Error", 2);
            return;
        }

        lend.setCustomer(customerManager.getCustomerById(customer_id));
        lend.setBoardGame(boardGameManager.getBoardGameById(boardGame_id));

        /* From, To */
        try {
            Calendar c = new GregorianCalendar();
            c.set(
                    (Integer) jSpinnerStartYear.getValue(),
                    (Integer) jSpinnerStartMonth.getValue() - 1,
                    (Integer) jSpinnerStartDay.getValue());
            lend.setStartTime(new Date(c.getTimeInMillis()));
            c.set(
                    (Integer) jSpinnerEndYear.getValue(),
                    (Integer) jSpinnerEndMonth.getValue() - 1,
                    (Integer) jSpinnerEndDay.getValue());
            lend.setExpectedEndTime(new Date(c.getTimeInMillis()));
        } catch (IllegalArgumentException ex) {
            String msg = "Lending from or to wrong format";
            LOGGER.log(Level.INFO, msg);
            JOptionPane.showMessageDialog(rootPane, msg, "Error", 2);
        }

        try {
            LOGGER.log(Level.INFO, "Adding lending");
            lendingManager.createLending(lend);
            borrowTableModel.addLending(lend);
        } catch (Exception ex) {
            //String msg = "User request failed";
            LOGGER.log(Level.INFO, ex.getMessage());
            JOptionPane.showMessageDialog(rootPane, ex.getMessage(), "Error", 2);
        }
    }//GEN-LAST:event_jButtonAddActionPerformed

    private void jButtonDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDeleteActionPerformed
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
    }//GEN-LAST:event_jButtonDeleteActionPerformed

    private void jButtonAvailableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAvailableActionPerformed
        if (boardGamesSwingWorker != null) {
            throw new IllegalStateException("Operation is already in progress");
        }
        jProgressBarBoardGames.setValue(0);
        boardGameTableModel.clear();
        boardGamesSwingWorker = new BoardGamesSwingWorker();
        boardGamesSwingWorker.addPropertyChangeListener(boardGameProgressListener);
        boardGamesSwingWorker.execute();
    }//GEN-LAST:event_jButtonAvailableActionPerformed

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
    private javax.swing.JButton jButtonAdd;
    private javax.swing.JButton jButtonAvailable;
    private javax.swing.JButton jButtonBack;
    private javax.swing.JButton jButtonDelete;
    private javax.swing.JLabel jLabelBoardGames;
    private javax.swing.JLabel jLabelCustomers;
    private javax.swing.JLabel jLabelDay;
    private javax.swing.JLabel jLabelFrom;
    private javax.swing.JLabel jLabelLending;
    private javax.swing.JLabel jLabelMonth;
    private javax.swing.JLabel jLabelTo;
    private javax.swing.JLabel jLabelYear;
    private javax.swing.JProgressBar jProgressBarBoardGames;
    private javax.swing.JProgressBar jProgressBarCustomers;
    private javax.swing.JProgressBar jProgressBarLendings;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPaneBoardGames;
    private javax.swing.JScrollPane jScrollPaneCustomers;
    private javax.swing.JScrollPane jScrollPaneLendings;
    private javax.swing.JSpinner jSpinnerEndDay;
    private javax.swing.JSpinner jSpinnerEndMonth;
    private javax.swing.JSpinner jSpinnerEndYear;
    private javax.swing.JSpinner jSpinnerStartDay;
    private javax.swing.JSpinner jSpinnerStartMonth;
    private javax.swing.JSpinner jSpinnerStartYear;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTableBordGames;
    private javax.swing.JTable jTableBorrow;
    private javax.swing.JTable jTableCustomers;
    // End of variables declaration//GEN-END:variables
}
