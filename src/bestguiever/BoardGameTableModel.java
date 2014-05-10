package bestguiever;

import boardgamerental.BoardGame;
import boardgamerental.BoardGameManager;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

public class BoardGameTableModel extends AbstractTableModel {
 
    private static final Logger LOGGER = Logger.getLogger(BoardGameTableModel.class.getName());
    BoardGameManager bGameManager;
    List<BoardGame> bGames = new ArrayList<>();
    static enum COLUMNS {
        ID, NAME, MINPLAYERS, MAXPLAYERS, CATEGORIES, PRIZEPERDAY
    }

    public void setBGManager(BoardGameManager bGameManager) {
        this.bGameManager = bGameManager;
    }
    
 
    @Override
    public int getRowCount() {
        return bGames.size();
    }
 
    @Override
    public int getColumnCount() {
        return COLUMNS.values().length;
    }
    
    @Override
    public Class<?> getColumnClass(int columnIndex) {
	switch (COLUMNS.values()[columnIndex]) {
	    case ID:
            case MINPLAYERS:
            case MAXPLAYERS:
		return Integer.class;
	    case NAME:
		return String.class;
	    case CATEGORIES:
		return Set.class;
	    case PRIZEPERDAY:
		return BigDecimal.class;
	    default:
		throw new IllegalArgumentException("columnIndex");
	}
    }
    
    @Override
    public String getColumnName(int columnIndex) {
	switch (COLUMNS.values()[columnIndex]) {
	    case ID:
                return "Id";
	    case NAME:
		return "Name";
	    case MINPLAYERS:
		return "Min players";
	    case MAXPLAYERS:
		return "Max players";
	    case CATEGORIES:
		return "Categories";
	    case PRIZEPERDAY:
		return "Prize";
	    default:
		throw new IllegalArgumentException("columnIndex");
	}
    }
 
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        BoardGame bGame = bGames.get(rowIndex);
        switch (COLUMNS.values()[columnIndex]) {
            case ID:
                return bGame.getId();
            case MINPLAYERS:
                return bGame.getMinPlayers();
            case MAXPLAYERS:
                return bGame.getMaxPlayers();
	    case NAME:
                return bGame.getName();
            case CATEGORIES:
                Set<String> cats = (Set) bGame.getCategory();
                StringBuilder sb = new StringBuilder();
                int count = 0;
                for (String cat : cats) {
                    count++;
                    sb.append(cat);
                    if (count < cats.size()) {
                        sb.append(", ");
                    }
                }
                return sb.toString();
	    case PRIZEPERDAY:
                return bGame.getPricePerDay();
            default:
                throw new IllegalArgumentException("columnIndex");
        }
    }
    
    public void addBoardGame(BoardGame bGame) {
	bGames.add(bGame);
	fireTableDataChanged();
    }
    
    public void removeBoardGame(BoardGame bGame) {
	bGames.remove(bGame);
	fireTableDataChanged();
    }
    
    public void clear() {
	bGames.clear();
        fireTableDataChanged();
    }
    
     public List<BoardGame> getAllBGames() {
	return bGames;
    }
    
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
	BoardGame bGame = bGames.get(rowIndex);
	switch (COLUMNS.values()[columnIndex]) {
	    case NAME:
		bGame.setName((String) aValue);
		break;
	    case MINPLAYERS:
		bGame.setMinPlayers((Integer) aValue);
		break;
	    case MAXPLAYERS:
		bGame.setMaxPlayers((Integer) aValue);
		break;
            case CATEGORIES:
                Set<String> cat = new HashSet<>(bGame.getCategory());
                cat.add((String) aValue);
                bGame.setCategory(cat);
            case PRIZEPERDAY:
                bGame.setPricePerDay((BigDecimal) aValue);
                break;
	    default:
		throw new IllegalArgumentException("columnIndex");
	}
        try {
            bGameManager.updateBoardGame(bGame);
            fireTableDataChanged();
        }
        catch (Exception ex) {
            String msg = "User request failed";
            LOGGER.log(Level.INFO, msg);
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", 2);
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
	switch (COLUMNS.values()[columnIndex]) {
	    case ID:

		return false;
	    case NAME:
            case CATEGORIES:
            case MINPLAYERS:
            case MAXPLAYERS:
            case PRIZEPERDAY:
                return true;
	    default:
		throw new IllegalArgumentException("columnIndex");
	}
    }
}
