/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bestguiever;

/**
 *
 * @author Goolomb
 */
public class CustomerTableModelUnmodifiable extends CustomerTableModel {
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
	switch (COLUMNS.values()[columnIndex]) {
	    case ID:
	    case NAME:
	    case ADDRESS:
	    case PHONENUMBER:
		return false;
	    default:
		throw new IllegalArgumentException("columnIndex");
	}
    }
}
