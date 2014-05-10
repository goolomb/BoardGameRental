/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bestguiever;

import boardgamerental.BoardGame;
import boardgamerental.BoardGameManagerImpl;
import boardgamerental.LendingManagerImpl;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Goolomb
 */
public class BoardGameTableModelUnmodifiable extends BoardGameTableModel {
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
	switch (COLUMNS.values()[columnIndex]) {
	    case ID:
	    case NAME:
	    case MINPLAYERS:
	    case MAXPLAYERS:
            case CATEGORIES:
            case PRIZEPERDAY:
		return false;
	    default:
		throw new IllegalArgumentException("columnIndex");
	}
    }
    
}
