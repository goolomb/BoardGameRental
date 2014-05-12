/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bestguiever;

import boardgamerental.Lending;
import boardgamerental.LendingManager;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Goolomb
 */
class BorrowTableModel  extends AbstractTableModel {
    private static final Logger LOGGER = Logger.getLogger(BorrowTableModel.class.getName());
    private LendingManager lendingManager;
    private List<Lending> lendings = new ArrayList<Lending>();
    
    private static enum COLUMNS {
        ID, CUSTOMER, BOARDGAME, STARTTIME, EXPECTEDENDTIME, REALENDTIME, PRICE
    }

    public void setLendingManager(LendingManager lendingManager) {
        this.lendingManager = lendingManager;
    }
    
    @Override
    public int getRowCount() {
        return lendings.size();
    }
 
    @Override
    public int getColumnCount() {
        return COLUMNS.values().length;
    }
    
    @Override
    public Class<?> getColumnClass(int columnIndex) {
	switch (COLUMNS.values()[columnIndex]) {
	    case ID:
		return Integer.class;
	    case CUSTOMER:
                return String.class;
	    case BOARDGAME:
                return String.class;
	    case STARTTIME:
            case EXPECTEDENDTIME:
            case REALENDTIME:
		return Date.class;
            case PRICE:
                return BigDecimal.class;
	    default:
		throw new IllegalArgumentException("columnIndex");
	}
    }
    
    @Override
    public String getColumnName(int columnIndex) {
        ResourceBundle rb = ResourceBundle.getBundle("bestguiever/Bundle");
	switch (COLUMNS.values()[columnIndex]) {
	    case ID:
		return rb.getString("BoardGameRental.Id");
	    case CUSTOMER:
		return rb.getString("BoardGameRental.Customer");
	    case BOARDGAME:
		return rb.getString("BoardGameRental.BoardGame");
	    case STARTTIME:
		return rb.getString("BoardGameRental.Borrowed");
            case EXPECTEDENDTIME:
		return rb.getString("BoardGameRental.ReturnExected");
            case REALENDTIME:
		return rb.getString("BoardGameRental.Returned");
            case PRICE:
                return rb.getString("BoardGameRental.Price");
	    default:
		throw new IllegalArgumentException("columnIndex");
	}
    }
 
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Lending lending = lendings.get(rowIndex);
        switch (COLUMNS.values()[columnIndex]) {
            case ID:
                return lending.getId();
            case CUSTOMER:
                return lending.getCustomer();
            case BOARDGAME:
                return lending.getBoardGame();
	    case STARTTIME:
		return lending.getStartTime();
            case EXPECTEDENDTIME:
		return lending.getExpectedEndTime();
            case REALENDTIME:
		return lending.getRealEndTime();
            case PRICE:
                return lendingManager.calculateTotalPrice(lending);
	    default:
                throw new IllegalArgumentException("columnIndex");
        }
    }
    
    public void addLending(Lending lending) {
	lendings.add(lending);
	fireTableDataChanged();
    }
        
    public void removeLending(Lending lending) {
	lendings.remove(lending);
	fireTableDataChanged();
    }
    
    public void clear() {
	lendings.clear();
        fireTableDataChanged();
    }
    
     public List<Lending> getAllLendings() {
	return lendings;
    }
    
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
	Lending lending = lendings.get(rowIndex);
	switch (COLUMNS.values()[columnIndex]) {
	    case STARTTIME:
		lending.setStartTime(Date.valueOf((String) aValue));
		break;
            case EXPECTEDENDTIME:
		lending.setExpectedEndTime(Date.valueOf((String) aValue));
		break;
            case REALENDTIME:
		lending.setRealEndTime(Date.valueOf((String) aValue));
		break;
            default:
		throw new IllegalArgumentException("columnIndex");
	}
        try {
            lendingManager.updateLending(lending);
            fireTableDataChanged();
        } catch (Exception ex) {
            String msg = "User request failed";
            LOGGER.log(Level.INFO, msg);
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
	switch (COLUMNS.values()[columnIndex]) {
	    case ID:
            case CUSTOMER:
            case BOARDGAME:
            case PRICE:
		return false;
	    case STARTTIME:
	    case EXPECTEDENDTIME:
	    case REALENDTIME:
		return true;
	    default:
		throw new IllegalArgumentException("columnIndex");
	}
    }
}
