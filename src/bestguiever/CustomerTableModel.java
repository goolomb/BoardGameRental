/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bestguiever;

import boardgamerental.Customer;
import boardgamerental.CustomerManager;
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
public class CustomerTableModel extends AbstractTableModel {
    
    private static final Logger LOGGER = Logger.getLogger(CustomerTableModel.class.getName());
    private CustomerManager customerManager;
    private List<Customer> customers = new ArrayList<Customer>();
    
    static enum COLUMNS {
        ID, NAME, ADDRESS, PHONENUMBER
    }

    public void setCustomerManager(CustomerManager customerManager) {
        this.customerManager = customerManager;
    }
    
    @Override
    public int getRowCount() {
        return customers.size();
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
	    case NAME:
	    case ADDRESS:
	    case PHONENUMBER:
		return String.class;
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
	    case NAME:
		return rb.getString("BoardGameRental.Name");
	    case ADDRESS:
		return rb.getString("BoardGameRental.Address");
	    case PHONENUMBER:
		return rb.getString("BoardGameRental.PhoneNumber");
	    default:
		throw new IllegalArgumentException("columnIndex");
	}
    }
 
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Customer customer = customers.get(rowIndex);
        switch (COLUMNS.values()[columnIndex]) {
            case ID:
                return customer.getId();
            case NAME:
                return customer.getName();
            case ADDRESS:
                return customer.getAddress();
	    case PHONENUMBER:
                return customer.getPhoneNumber();
            default:
                throw new IllegalArgumentException("columnIndex");
        }
    }
    
    public void addCustomer(Customer customer) {
	customers.add(customer);
	fireTableDataChanged();
    }
        
    public void removeCustomer(Customer customer) {
	customers.remove(customer);
	fireTableDataChanged();
    }
    
    public void clear() {
	customers.clear();
        fireTableDataChanged();
    }
    
     public List<Customer> getAllCustomers() {
	return customers;
    }
    
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
	Customer customer = customers.get(rowIndex);
	switch (COLUMNS.values()[columnIndex]) {
	    case NAME:
		customer.setName((String) aValue);
		break;
	    case ADDRESS:
		customer.setAddress((String) aValue);
		break;
	    case PHONENUMBER:
		customer.setPhoneNumber((String) aValue);
		break;
	    default:
		throw new IllegalArgumentException("columnIndex");
	}
        try {
            customerManager.updateCustomer(customer);
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
		return false;
	    case NAME:
	    case ADDRESS:
	    case PHONENUMBER:
		return true;
	    default:
		throw new IllegalArgumentException("columnIndex");
	}
    }

}