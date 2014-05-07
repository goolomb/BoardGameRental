/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bestguiever;

import java.awt.Component;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Patrik
 */
public class SetCellRenderer extends DefaultTableCellRenderer {
 
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
 
        super.getTableCellRendererComponent(table, value, isSelected,
                hasFocus, row, column);
        Set<String> cats = (Set)value;
        StringBuilder sb = new StringBuilder();
        int count = 0;
        for (String cat : cats) {
            count++;
            sb.append(cat);
            if (count < cats.size()) sb.append(", ");
        }
        setText(sb.toString());
        return this;
    }
 
}
