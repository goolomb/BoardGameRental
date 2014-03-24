/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package boardgamerental;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Patrik
 */
public class CustomerManagerImplTest {
    
    private CustomerManagerImpl manager;
    private Connection conn;
    
    @Before
    public void setUp() throws SQLException{
        conn = DriverManager.getConnection("jdbc:derby:memory:GraveManagerTest;create=true");
        conn.prepareStatement("CREATE TABLE CUSTOMER ("
                + "id int primary key generated always as identity,"
                + "name varchar(30),"
                + "address varchar(255),"
                + "phoneNumber varchar(20))").executeUpdate();
        manager = new CustomerManagerImpl(conn);
    }
    
    @After
    public void tearDown() throws SQLException {
        conn.prepareStatement("DROP TABLE CUSTOMER").executeUpdate();        
        conn.close();
    }

    /**
     * Test of createCustomer method, of class CustomerManagerImpl.
     */
    @Test
    public void testCreateCustomer() {
        Customer customer = new Customer("I.C. Wiener", "New York City", "+420 666 666 666");
        manager.createCustomer(customer);

        Integer customerId = customer.getId();
        assertNotNull(customerId);
        Customer result = manager.getCustomerById(customerId);
        assertEquals(customer, result);
        assertNotSame(customer, result);
        assertDeepEquals(customer, result);
    }

    /**
     * Test of getCustomerById method, of class CustomerManagerImpl.
     */
    @Test
    public void testGetCustomerById() {
        assertNull(manager.getCustomerById(1));
        
        Customer customer = new Customer("I.C. Wiener", "New York City", "+420 666 666 666");
        manager.createCustomer(customer);
        Integer customerId = customer.getId();

        Customer result = manager.getCustomerById(customerId);
        assertEquals(customer, result);
        assertDeepEquals(customer, result);
    }

    /**
     * Test of findAllCustomers method, of class CustomerManagerImpl.
     */
    @Test
    public void testFindAllCustomers() {
        System.out.println("findAllCustomers");
        CustomerManagerImpl instance = null;
        List<Customer> expResult = null;
        List<Customer> result = instance.findAllCustomers();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findCustomerByName method, of class CustomerManagerImpl.
     */
    @Test
    public void testFindCustomerByName() {
        
    }

    /**
     * Test of updateCustomer method, of class CustomerManagerImpl.
     */
    @Test
    public void testUpdateCustomer() {
        System.out.println("updateCustomer");
        Customer customer = null;
        CustomerManagerImpl instance = null;
        instance.updateCustomer(customer);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteCustomer method, of class CustomerManagerImpl.
     */
    @Test
    public void testDeleteCustomer() {
        System.out.println("deleteCustomer");
        Customer customer = null;
        CustomerManagerImpl instance = null;
        instance.deleteCustomer(customer);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    private void assertDeepEquals(Customer expected, Customer actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getAddress(), actual.getAddress());
        assertEquals(expected.getPhoneNumber(), actual.getPhoneNumber());
    }
}
