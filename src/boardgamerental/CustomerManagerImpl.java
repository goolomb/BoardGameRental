/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package boardgamerental;

import cz.muni.fi.pv168.common.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;

/**
 *
 * @author Goolomb
 */
public class CustomerManagerImpl implements CustomerManager {
    
    private static final Logger logger = Logger.getLogger(
            BoardGameManagerImpl.class.getName());    
    
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private void checkDataSource() {
        if (dataSource == null) {
            throw new IllegalStateException("DataSource is not set");
        }
    }
    
    @Override
    public void createCustomer(Customer customer)  throws ServiceFailureException {
        checkDataSource();
        validate(customer);
        if (customer.getId() != null) {
            throw new IllegalArgumentException("customer id is already set");            
        }
        
        try (Connection conn = dataSource.getConnection()){
            try (PreparedStatement st = conn.prepareStatement(
                    "INSERT INTO CUSTOMER (name,address,phonenumber) VALUES (?,?,?)",
                    Statement.RETURN_GENERATED_KEYS)){
                st.setString(1, customer.getName());
                st.setString(2, customer.getAddress());
                st.setString(3, customer.getPhoneNumber());
                int addedRows = st.executeUpdate();
                if (addedRows != 1) {
                    throw new ServiceFailureException("Internal Error: More rows "
                            + "inserted when trying to insert customer " + customer);
                }            
            
                Integer id = DBUtils.getId(st.getGeneratedKeys());
                customer.setId(id);
            }
            
        } catch (SQLException ex) {
            String msg = "Error when inserting customer into db";
            logger.log(Level.SEVERE, msg, ex);
            throw new ServiceFailureException(msg, ex);
        }
    }

    @Override
    public Customer getCustomerById(Integer id) throws ServiceFailureException {
        checkDataSource();
        try (Connection conn = dataSource.getConnection()){
            try(PreparedStatement st = conn.prepareStatement(
                    "SELECT id,name,address,phonenumber FROM customer WHERE id = ?")){
                st.setInt(1, id);
                try (ResultSet rs = st.executeQuery()){
            
                    if (rs.next()) {
                        Customer customer = resultSetToCustomer(rs);
                        
                        if (rs.next()) {
                            throw new ServiceFailureException(
                                    "Internal error: More entities with the same id found "
                                 + "(source id: " + id + ", found " + customer + " and " + resultSetToCustomer(rs));
                        }
                        return customer;
                    } else return null;
                }
            }
        } catch (SQLException ex) {
            String msg = "Error when getting customer with id = " + id + " from DB";
            logger.log(Level.SEVERE, msg, ex);
            throw new ServiceFailureException(msg, ex);
        }
    }

    @Override
    public List<Customer> findAllCustomers() throws ServiceFailureException {
        checkDataSource();
        try (Connection conn = dataSource.getConnection()) {
            try(PreparedStatement st = conn.prepareStatement(
                    "SELECT id,name,address,phonenumber FROM Customer")){
                try(ResultSet rs = st.executeQuery()){
            
                    List<Customer> result = new ArrayList<>();
                    while (rs.next()) {
                        result.add(resultSetToCustomer(rs));
                    }
                    return result;
                }
            }
            
        } catch (SQLException ex) {
            String msg = "Error when getting all customers from DB";
            logger.log(Level.SEVERE, msg, ex);
            throw new ServiceFailureException(msg, ex);
        }
    }

    
    @Override
    public List<Customer> findCustomerByName(String name) {
        checkDataSource();
        try (Connection conn = dataSource.getConnection()){
            try(PreparedStatement st = conn.prepareStatement(
                    "SELECT id,name,address,phonenumber FROM customer WHERE name = ? ")){
                st.setString(1, name);
                try(ResultSet rs = st.executeQuery()){
            
                    List<Customer> result = new ArrayList<>();
                    while (rs.next()) {
                        result.add(resultSetToCustomer(rs));
                    }
                    return result;
                }
            }
            
        } catch (SQLException ex) {
            String msg = "Error when getting customer with name = " + name + " from DB";
            logger.log(Level.SEVERE, msg, ex);
            throw new ServiceFailureException(msg, ex);
        }
    }

    @Override
    public void updateCustomer(Customer customer) {
        checkDataSource();
        validate(customer);
        if (customer.getId() == null) {
            throw new IllegalArgumentException("customer id is null");            
        }
        
        try (Connection conn = dataSource.getConnection()) {
            try(PreparedStatement st = conn.prepareStatement(
                   "UPDATE Customer SET name = ?, address = ?, phonenumber = ? WHERE id = ?")){
                st.setString(1, customer.getName());
                st.setString(2, customer.getAddress());
                st.setString(3, customer.getPhoneNumber());
                st.setInt(4, customer.getId());
                int updateCount = st.executeUpdate();
                if (updateCount == 0) {
                    throw new IllegalArgumentException("Customer " + customer + " does not exist in the db");
                }
                if (updateCount != 1) {
                    throw new ServiceFailureException("Internal Error: Internal integrity error:"
                            + "Unexpected rows count in database affected: " + updateCount);
                }
            }
        } catch (SQLException ex) {
            String msg = "Error when updating customer in the db";
            logger.log(Level.SEVERE, msg, ex);
            throw new ServiceFailureException(msg, ex);
        }
     }

    @Override
    public void deleteCustomer(Customer customer) {
        checkDataSource();
        validate(customer);
        if (customer.getId() == null) {
            throw new IllegalArgumentException("customer id null");            
        }
        
        try (Connection conn = dataSource.getConnection()){
            try (PreparedStatement st = conn.prepareStatement(
                    "DELETE FROM Customer WHERE id = ?")){
                st.setInt(1, customer.getId());
                int updateCount = st.executeUpdate();
                if (updateCount == 0) {
                    throw new IllegalArgumentException("Customer " + customer + " does not exist in the db");
                }
                if (updateCount != 1) {
                    throw new ServiceFailureException("Internal Error: Internal integrity error:"
                            + "Unexpected rows count in database affected: " + updateCount);
                }
            }
        } catch (SQLException ex) {
            String msg = "Error when deleting customer from the db";
            logger.log(Level.SEVERE, msg, ex);
            throw new ServiceFailureException(msg, ex);
        }
    }
    
    private Customer resultSetToCustomer(ResultSet rs) throws SQLException {
        Customer customer = new Customer(rs.getString("name"), rs.getString("address"), rs.getString("phonenumber"));
        customer.setId(rs.getInt("id"));
        return customer;
    }
    
    private void validate(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("customer is null");            
        }
        if (customer.getAddress() == null) {
            throw new IllegalArgumentException("customer address is null");            
        }
        if (customer.getName() == null) {
            throw new IllegalArgumentException("customer name is null");            
        }
        if (customer.getPhoneNumber() == null) {
            throw new IllegalArgumentException("customer phone number is null");          
        }
    }
        
}
