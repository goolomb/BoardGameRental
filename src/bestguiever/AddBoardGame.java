/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bestguiever;

import boardgamerental.BoardGame;
import boardgamerental.BoardGameManagerImpl;
import common.ValidationException;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingWorker;
import javax.swing.table.TableColumnModel;
import org.apache.commons.dbcp.BasicDataSource;

/**
 *
 * @author Goolomb
 */
public class AddBoardGame extends javax.swing.JFrame {

    private static final Logger LOGGER = Logger.getLogger(AddBoardGame.class.getName());
    BasicDataSource basicDataSource = new BasicDataSource();
    BoardGameManagerImpl bgManager;
    static String [] categories = new String[] {"Roll and Move", "Simulation", "Strategy", "Wargames", "Party/Family",
                                            "Resource Management", "Cooperative", "Abstract", "Miniatures", 
                                            "Collectible", "Auction", "Tile Laying/Modular board"};
    private Set<String> category = new HashSet<>();
    /**
     * Creates new form AddBoardGame
     */
    public AddBoardGame() {
        
        try {
            setUp();
        } catch (IOException ex) {
	    String msg = "Application setup failed.";
            LOGGER.log(Level.SEVERE, msg, ex);
        }
        
        initComponents();
        
        bgManager = new BoardGameManagerImpl();
        bgManager.setDataSource(basicDataSource);
        
        bGamesSwingWorker = new BoardGamesSwingWorker();
        bGamesSwingWorker.addPropertyChangeListener(boardGamesProgressListener);
        bGamesSwingWorker.execute();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButtonBack = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextName = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jComboBoxCategory = new javax.swing.JComboBox();
        jSpinnerMinPlayers = new javax.swing.JSpinner(new SpinnerNumberModel(1,1,99,1));
        jSpinnerMaxPlayers = new javax.swing.JSpinner(new SpinnerNumberModel(1,1,99,1));
        jLabel6 = new javax.swing.JLabel();
        jButtonAdd = new javax.swing.JButton();
        try {
            jButtonChoose =(javax.swing.JButton)java.beans.Beans.instantiate(getClass().getClassLoader(), "bestguiever.AddBoardGame_jButtonChoose");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        jScrollPane2 = new javax.swing.JScrollPane();
        model = new DefaultListModel();
        jListCategories = new javax.swing.JList(model);
        jButtonClear = new javax.swing.JButton();
        jSpinnerPrizePerDay = new javax.swing.JSpinner(new SpinnerNumberModel(100,50,1000,100));
        jButtonChange = new javax.swing.JButton();
        jButtonDelete = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableBoardGames = new javax.swing.JTable();
        jProgressBarBG = new javax.swing.JProgressBar();
        jButtonRemove = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButtonBack.setText("Back");
        jButtonBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBackActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setText("Add Board game");

        jLabel2.setText("Name");

        jLabel3.setText("Minimum of players");

        jLabel4.setText("Maximum of players");

        jLabel5.setText("Categories");

        jComboBoxCategory.setModel(new javax.swing.DefaultComboBoxModel(categories));

        jLabel6.setText("Prize per day");

        jButtonAdd.setText("Add");
        jButtonAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddActionPerformed(evt);
            }
        });

        jButtonChoose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonChooseActionPerformed(evt);
            }
        });

        jScrollPane2.setViewportView(jListCategories);

        jButtonClear.setText("Clear");
        jButtonClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonClearActionPerformed(evt);
            }
        });

        jButtonChange.setText("Change Categories");
        jButtonChange.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonChangeActionPerformed(evt);
            }
        });

        jButtonDelete.setText("Delete");
        jButtonDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDeleteActionPerformed(evt);
            }
        });

        int maxWidth = 25;
        int preferredWidth = 20;
        jTableBoardGames.setModel(new BoardGameTableModel());
        jTableBoardGames.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        //jTableBoardGames.setDefaultRenderer(Set.class, new SetCellRenderer());
        jTableBoardGames.setSelectionMode (0);
        TableColumnModel columnModel = jTableBoardGames.getColumnModel();
        columnModel.getColumn(0).setMaxWidth(maxWidth);
        columnModel.getColumn(0).setPreferredWidth(preferredWidth);
        columnModel.getColumn(2).setMaxWidth(maxWidth * 2);
        columnModel.getColumn(2).setPreferredWidth(preferredWidth * 2);
        columnModel.getColumn(3).setMaxWidth(maxWidth * 2);
        columnModel.getColumn(3).setPreferredWidth(preferredWidth * 2);
        columnModel.getColumn(5).setMaxWidth(45);
        columnModel.getColumn(5).setPreferredWidth(40);
        jScrollPane1.setViewportView(jTableBoardGames);

        jButtonRemove.setText("Remove");
        jButtonRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRemoveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jButtonBack)
                        .addGap(28, 28, 28)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextName)
                            .addComponent(jComboBoxCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jSpinnerMinPlayers, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
                                .addComponent(jSpinnerMaxPlayers, javax.swing.GroupLayout.Alignment.LEADING))
                            .addComponent(jSpinnerPrizePerDay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonAdd))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonClear)
                            .addComponent(jButtonChoose)
                            .addComponent(jButtonRemove))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jProgressBarBG, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButtonChange)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButtonDelete)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 384, Short.MAX_VALUE))))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jButtonAdd, jSpinnerPrizePerDay, jTextName});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonAdd))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jButtonChange)
                                    .addComponent(jButtonDelete))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jButtonBack))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2)
                                    .addComponent(jTextName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel3)
                                    .addComponent(jSpinnerMinPlayers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel4)
                                    .addComponent(jSpinnerMaxPlayers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButtonRemove))
                                .addGap(6, 6, 6)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jComboBoxCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButtonChoose)
                                    .addComponent(jLabel5))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jSpinnerPrizePerDay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel6)))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonClear)
                    .addComponent(jProgressBarBG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(51, 51, 51))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBackActionPerformed
        new openingTable().setVisible(true);
        dispose();
    }//GEN-LAST:event_jButtonBackActionPerformed

    private void jButtonAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddActionPerformed
        try {
            BoardGame bGame = new BoardGame(jTextName.getText(), 
                (Integer)jSpinnerMaxPlayers.getValue(), 
                (Integer)jSpinnerMinPlayers.getValue(), 
                category,
                new BigDecimal((Integer)jSpinnerPrizePerDay.getValue()));
            bgManager.createBoardGame(bGame);
            boardGameTableModel.addBoardGame(bGame);
            jTextName.setText("");
            jSpinnerMinPlayers.setValue(1);
            jSpinnerMaxPlayers.setValue(1);
            jSpinnerPrizePerDay.setValue(100);
            jButtonClearActionPerformed(evt);
        }
        catch (ValidationException ex){
            String msg = "User request failed";
            LOGGER.log(Level.INFO, msg);
            JOptionPane.showMessageDialog(rootPane, ex.getMessage(), "Error", 2);
        }
    }//GEN-LAST:event_jButtonAddActionPerformed

    private void jButtonChooseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonChooseActionPerformed
        String cat = (String)jComboBoxCategory.getSelectedItem();
        if (category.add(cat)) model.addElement(cat);
    }//GEN-LAST:event_jButtonChooseActionPerformed

    private void jButtonClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonClearActionPerformed
        category.clear();
        model.removeAllElements();
    }//GEN-LAST:event_jButtonClearActionPerformed

    private void jButtonDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDeleteActionPerformed
        Integer bGame_id = null;
        try {
            bGame_id = (Integer) boardGameTableModel.getValueAt(jTableBoardGames.getSelectedRow(), 0);
        } catch (ArrayIndexOutOfBoundsException e) {
            String msg = "No row selected";
            LOGGER.log(Level.INFO, msg);
            JOptionPane.showMessageDialog(rootPane, msg, "Error", 2);
        }
        
        BoardGame bGame = bgManager.getBoardGameById(bGame_id);
        try {
            bgManager.deleteBoardGame(bGame);
            boardGameTableModel.removeBoardGame(bGame);
        } catch (Exception ex) {
            String msg = "Deleting failed";
            LOGGER.log(Level.INFO, msg);
            JOptionPane.showMessageDialog(rootPane, msg, "Error", 2);
        }
    }//GEN-LAST:event_jButtonDeleteActionPerformed

    private void jButtonRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRemoveActionPerformed
        String cat = (String)jComboBoxCategory.getSelectedItem();
        if (category.remove(cat)) model.removeElement(cat);
    }//GEN-LAST:event_jButtonRemoveActionPerformed

    private void jButtonChangeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonChangeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonChangeActionPerformed

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
            java.util.logging.Logger.getLogger(AddBoardGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddBoardGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddBoardGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddBoardGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AddBoardGame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAdd;
    private javax.swing.JButton jButtonBack;
    private javax.swing.JButton jButtonChange;
    private javax.swing.JButton jButtonChoose;
    private javax.swing.JButton jButtonClear;
    private javax.swing.JButton jButtonDelete;
    private javax.swing.JButton jButtonRemove;
    private javax.swing.JComboBox jComboBoxCategory;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JList jListCategories;
    protected DefaultListModel model;
    private javax.swing.JProgressBar jProgressBarBG;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSpinner jSpinnerMaxPlayers;
    private javax.swing.JSpinner jSpinnerMinPlayers;
    private javax.swing.JSpinner jSpinnerPrizePerDay;
    private javax.swing.JTable jTableBoardGames;
    private javax.swing.JTextField jTextName;
    // End of variables declaration//GEN-END:variables

    private void setUp() throws IOException {
        Properties configFile = new Properties();
        configFile.load(new FileInputStream("src/DBprop.properties"));
	BasicDataSource bds = new BasicDataSource();
	bds.setUrl( configFile.getProperty( "url" ) );
	bds.setPassword( configFile.getProperty( "password" ) );
	bds.setUsername( configFile.getProperty( "username" ) );
	basicDataSource = bds;
    }
    
    BoardGameTableModel boardGameTableModel;
    private BoardGamesSwingWorker bGamesSwingWorker;
    private class BoardGamesSwingWorker extends SwingWorker<Void, BoardGame> {

	@Override
	protected Void doInBackground() throws Exception {
	    boardGameTableModel = (BoardGameTableModel) jTableBoardGames.getModel();
            boardGameTableModel.setBGManager(bgManager);
            int counter = 0;
	    for (BoardGame bGame : bgManager.findAllBoardGames()) {
                counter++;
		publish(bGame);
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
            //customers_load.setEnabled(true);
            jProgressBarBG.setValue(100);
            bGamesSwingWorker = null;
        }
    }
   
    private PropertyChangeListener boardGamesProgressListener = new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if (evt.getPropertyName().equals("progress")) {
                jProgressBarBG.setValue((Integer) evt.getNewValue());
            }
        }
    };
}
