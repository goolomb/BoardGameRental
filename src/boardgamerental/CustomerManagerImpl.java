/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package boardgamerental;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Goolomb
 */
public class CustomerManagerImpl implements CustomerManager {
    public static final Logger logger = Logger.getLogger(CustomerManagerImpl.class.getName());

    public CustomerManagerImpl(Connection conn) {
        this.conn = conn;
    }    
    private Connection conn;

    public void createCustomer(Customer customer)  throws ServiceFailureException {
        validate(customer);
        if (customer.getId() != null) {
            throw new IllegalArgumentException("customer id is already set");            
        }

        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "INSERT INTO CUSTOMER (name,address,phonenumber) VALUES (?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            st.setString(1, customer.getName());
            st.setString(2, customer.getAddress());
            st.setString(3, customer.getPhoneNumber());
            int addedRows = st.executeUpdate();
            if (addedRows != 1) {
                throw new ServiceFailureException("Internal Error: More rows "
                        + "inserted when trying to insert customer " + customer);
            }            
            
            ResultSet keyRS = st.getGeneratedKeys();
            customer.setId(getKey(keyRS,customer));
            
        } catch (SQLException ex) {
            throw new ServiceFailureException("Error when inserting customer " + customer, ex);
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException ex) {
                    logger.log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    private Integer getKey(ResultSet keyRS, Customer customer) throws ServiceFailureException, SQLException {
        if (keyRS.next()) {
            if (keyRS.getMetaData().getColumnCount() != 1) {
                throw new ServiceFailureException("Internal Error: Generated key"
                        + "retriving failed when trying to insert customer " + customer
                        + " - wrong key fields count: " + keyRS.getMetaData().getColumnCount());
            }
            Integer result = keyRS.getInt(1);
            if (keyRS.next()) {
                throw new ServiceFailureException("Internal Error: Generated key"
                        + "retriving failed when trying to insert customer " + customer
                        + " - more keys found");
            }
            return result;
        } else {
            throw new ServiceFailureException("Internal Error: Generated key"
                    + "retriving failed when trying to insert customer " + customer
                    + " - no key found");
        }
    }

    public Customer getCustomerById(Integer id) throws ServiceFailureException {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "SELECT id,name,address,phonenumber FROM customer WHERE id = ?");
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            
            if (rs.next()) {
                Customer customer = resultSetToCustomer(rs);

                if (rs.next()) {
                    throw new ServiceFailureException(
                            "Internal error: More entities with the same id found "
                            + "(source id: " + id + ", found " + customer + " and " + resultSetToCustomer(rs));                    
                }            
                
                return customer;
            } else {
                return null;
            }
            
        } catch (SQLException ex) {
            throw new ServiceFailureException(
                    "Error when retrieving customer with id " + id, ex);
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException ex) {
                    logger.log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    private Customer resultSetToCustomer(ResultSet rs) throws SQLException {
        Customer customer = new Customer();
        customer.setId(rs.getInt("id"));
        customer.setName(rs.getString("name"));
        customer.setAddress(rs.getString("address"));
        customer.setPhoneNumber(rs.getString("phonenumber"));
        return customer;
    }

    public List<Customer> findAllCustomers() throws ServiceFailureException {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "SELECT id,name,address,phonenumber FROM Customer");
            ResultSet rs = st.executeQuery();
            
            List<Customer> result = new ArrayList<Customer>();
            while (rs.next()) {
                result.add(resultSetToCustomer(rs));
            }
            return result;
            
        } catch (SQLException ex) {
            throw new ServiceFailureException(
                    "Error when retrieving all customers", ex);
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException ex) {
                    logger.log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    
    public List<Customer> findCustomerByName(String name) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "SELECT id,name,address,phonenumber FROM customer WHERE name = ? ");
            st.setString(1, name);
            ResultSet rs = st.executeQuery();
            
            List<Customer> result = new ArrayList<Customer>();
            while (rs.next()) {
                result.add(resultSetToCustomer(rs));
            }
            return result;
            
        } catch (SQLException ex) {
            throw new ServiceFailureException(
                    "Error when retrieving all customers", ex);
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException ex) {
                    logger.log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void updateCustomer(Customer customer) {
        validate(customer);
        if (customer.getId() == null) {
            throw new IllegalArgumentException("customer id is null");            
        }
        
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                   "UPDATE Customer SET name = ?, address = ?, phonenumber = ? WHERE id = ?");
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

        } catch (SQLException ex) {
            throw new ServiceFailureException("Error when inserting customer " + customer, ex);
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException ex) {
                    logger.log(Level.SEVERE, null, ex);
                }
            }
        }
     }

    public void deleteCustomer(Customer customer) {
        validate(customer);
        if (customer.getId() == null) {
            throw new IllegalArgumentException("customer id null");            
        }
        
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "DELETE FROM Customer WHERE id = ?");
            st.setInt(1, customer.getId());
            int updateCount = st.executeUpdate();
            if (updateCount == 0) {
                throw new IllegalArgumentException("Customer " + customer + " does not exist in the db");
            }
            if (updateCount != 1) {
                throw new ServiceFailureException("Internal Error: Internal integrity error:"
                        + "Unexpected rows count in database affected: " + updateCount);
            }

        } catch (SQLException ex) {
            throw new ServiceFailureException("Error when inserting customer " + customer, ex);
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException ex) {
                    logger.log(Level.SEVERE, null, ex);
                }
            }
        }
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
