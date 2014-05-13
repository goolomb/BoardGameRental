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
import javax.swing.ListModel;
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
    final static java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("bestguiever/Bundle");
    static String [] categories = new String[] {bundle.getString("BoardGameRental.RollandMove"), 
                                                bundle.getString("BoardGameRental.Simulation"), 
                                                bundle.getString("BoardGameRental.Strategy"), 
                                                bundle.getString("BoardGameRental.Wargames"), 
                                                bundle.getString("BoardGameRental.Party/Family"),
                                                bundle.getString("BoardGameRental.ResourceManagement"),
                                                bundle.getString("BoardGameRental.Cooperative"),
                                                bundle.getString("BoardGameRental.Abstract"),
                                                bundle.getString("BoardGameRental.Miniatures"), 
                                                bundle.getString("BoardGameRental.Collectible"),
                                                bundle.getString("BoardGameRental.Auction"),
                                                bundle.getString("BoardGameRental.TileLaying")};
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
        jLabelAddBoardGame = new javax.swing.JLabel();
        jLabelName = new javax.swing.JLabel();
        jTextName = new javax.swing.JTextField();
        jLabelMinimumOfPlayers = new javax.swing.JLabel();
        jLabelMaximumOfPlayers = new javax.swing.JLabel();
        jLabelCategories = new javax.swing.JLabel();
        jComboBoxCategory = new javax.swing.JComboBox();
        jSpinnerMinPlayers = new javax.swing.JSpinner(new SpinnerNumberModel(1,1,99,1));
        jSpinnerMaxPlayers = new javax.swing.JSpinner(new SpinnerNumberModel(1,1,99,1));
        jLabelPrizePerDay = new javax.swing.JLabel();
        jButtonAdd = new javax.swing.JButton();
        jButtonChoose = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        model = new DefaultListModel<String>();
        jListCategories = new javax.swing.JList(model);
        jButtonClear = new javax.swing.JButton();
        jSpinnerPrizePerDay = new javax.swing.JSpinner(new SpinnerNumberModel(100,50,1000,100));
        jButtonDelete = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableBoardGames = new javax.swing.JTable();
        jProgressBarBG = new javax.swing.JProgressBar();
        jButtonRemove = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("bestguiever/Bundle"); // NOI18N
        jButtonBack.setText(bundle.getString("BoardGameRental.jButtonBack")); // NOI18N
        jButtonBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBackActionPerformed(evt);
            }
        });

        jLabelAddBoardGame.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabelAddBoardGame.setText(bundle.getString("BoardGameRental.jLabelAddBoardGame")); // NOI18N

        jLabelName.setText(bundle.getString("BoardGameRental.jLabelName")); // NOI18N

        jLabelMinimumOfPlayers.setText(bundle.getString("BoardGameRental.jLabelMinimumOfPlayers")); // NOI18N

        jLabelMaximumOfPlayers.setText(bundle.getString("BoardGameRental.jLabelMaxmimumOfPlayers")); // NOI18N

        jLabelCategories.setText(bundle.getString("BoardGameRental.jLabelCategories")); // NOI18N

        jComboBoxCategory.setModel(new javax.swing.DefaultComboBoxModel(categories));

        jLabelPrizePerDay.setText(bundle.getString("BoardGameRental.jLabelPrizePerDay")); // NOI18N

        jButtonAdd.setText(bundle.getString("BoardGameRental.jButtonAdd")); // NOI18N
        jButtonAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddActionPerformed(evt);
            }
        });

        jButtonChoose.setText(bundle.getString("BoardGameRental.jButtonChoose")); // NOI18N
        jButtonChoose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonChooseActionPerformed(evt);
            }
        });

        jScrollPane2.setViewportView(jListCategories);

        jButtonClear.setText(bundle.getString("BoardGameRental.jButtonClear")); // NOI18N
        jButtonClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonClearActionPerformed(evt);
            }
        });

        jButtonDelete.setText(bundle.getString("BoardGameRental.jButtonDelete")); // NOI18N
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

        jButtonRemove.setText(bundle.getString("BoardGameRental.jButtonRemove")); // NOI18N
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
                        .addComponent(jLabelAddBoardGame))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabelMinimumOfPlayers)
                            .addComponent(jLabelName)
                            .addComponent(jLabelMaximumOfPlayers)
                            .addComponent(jLabelCategories)
                            .addComponent(jLabelPrizePerDay))
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
                            .addComponent(jButtonRemove)
                            .addComponent(jButtonChoose, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jProgressBarBG, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButtonDelete)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE))))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jButtonAdd, jSpinnerPrizePerDay, jTextName});

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jButtonChoose, jButtonRemove});

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
                                .addComponent(jButtonDelete)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabelAddBoardGame)
                                    .addComponent(jButtonBack))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabelName)
                                    .addComponent(jTextName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabelMinimumOfPlayers)
                                    .addComponent(jSpinnerMinPlayers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabelMaximumOfPlayers)
                                    .addComponent(jSpinnerMaxPlayers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButtonRemove))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jComboBoxCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabelCategories))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(jSpinnerPrizePerDay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jLabelPrizePerDay))))
                                    .addComponent(jButtonChoose, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonClear)
                    .addComponent(jProgressBarBG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(51, 51, 51))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButtonChoose, jButtonRemove});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBackActionPerformed
        new openingTable().setVisible(true);
        dispose();
    }//GEN-LAST:event_jButtonBackActionPerformed

    private void jButtonAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddActionPerformed
        try {
            LOGGER.log(Level.INFO, "Adding Board game");
            BoardGame bGame = new BoardGame(jTextName.getText(), 
                (Integer)jSpinnerMaxPlayers.getValue(), 
                (Integer)jSpinnerMinPlayers.getValue(), 
                category,
                new BigDecimal((String)jSpinnerPrizePerDay.getValue()));
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
            JOptionPane.showMessageDialog(rootPane, bundle.getString("BoardGameRental.RequestFailed"), "Error", 2);
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
            JOptionPane.showMessageDialog(rootPane, bundle.getString("BoardGameRental.BoardGameNotSelected"), "Error", 2);
        }
        
        BoardGame bGame = bgManager.getBoardGameById(bGame_id);
        try {
            LOGGER.log(Level.INFO, "Deleting Board game");
            bgManager.deleteBoardGame(bGame);
            boardGameTableModel.removeBoardGame(bGame);
        } catch (Exception ex) {
            String msg = "Deleting failed";
            LOGGER.log(Level.INFO, msg);
            JOptionPane.showMessageDialog(rootPane, bundle.getString("BoardGameRental.DeletingFailed"), "Error", 2);
        }
    }//GEN-LAST:event_jButtonDeleteActionPerformed

    private void jButtonRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRemoveActionPerformed
        String cat = (String)jListCategories.getSelectedValue();
        if (category.remove(cat)) model.removeElement(cat);
    }//GEN-LAST:event_jButtonRemoveActionPerformed

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
    private javax.swing.JButton jButtonChoose;
    private javax.swing.JButton jButtonClear;
    private javax.swing.JButton jButtonDelete;
    private javax.swing.JButton jButtonRemove;
    private javax.swing.JComboBox jComboBoxCategory;
    private javax.swing.JLabel jLabelAddBoardGame;
    private javax.swing.JLabel jLabelCategories;
    private javax.swing.JLabel jLabelMaximumOfPlayers;
    private javax.swing.JLabel jLabelMinimumOfPlayers;
    private javax.swing.JLabel jLabelName;
    private javax.swing.JLabel jLabelPrizePerDay;
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
    /*
    BoardGameAddSwingWorker bgAddSwingWorker;
    
    private class BoardGameAddSwingWorker extends SwingWorker<Void, BoardGame> {

	@Override
	protected Void doInBackground() throws Exception {
	    boardGameTableModel = (BoardGameTableModel) jTableBoardGames.getModel();
            boardGameTableModel.setBGManager(bgManager);
            
	    return null;
	}

        @Override
        protected void done() {
            //customers_load.setEnabled(true);
            jProgressBarBG.setValue(100);
            bgAddSwingWorker = null;
        }
    }
   */
    private PropertyChangeListener boardGamesProgressListener = new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if (evt.getPropertyName().equals("progress")) {
                jProgressBarBG.setValue((Integer) evt.getNewValue());
            }
        }
    };
}
