/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package boardgamerental;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.junit.After;
import org.junit.Before;
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
        conn = DriverManager.getConnection("jdbc:derby:memory:CustomerManagerTest;create=true");
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
        assertTrue(manager.findAllCustomers().isEmpty());

        Customer c1 = new Customer("I.C. Wiener", "New York City", "+420 666 666 666");
        Customer c2 = new Customer("Hugh Gerection", "California", "+420 123 456 789");

        manager.createCustomer(c1);
        manager.createCustomer(c2);

        List<Customer> expected = Arrays.asList(c1,c2);
        List<Customer> actual = manager.findAllCustomers();

        Collections.sort(actual,idComparator);
        Collections.sort(expected,idComparator);

        assertEquals(expected, actual);
        assertDeepEquals(expected, actual);
    }

    @Test
    public void addCustomerWithWrongAttributes() {

        try {
            manager.createCustomer(null);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }

        Customer customer = new Customer("I.C. Wiener", "New York City", "+420 666 666 666");
        customer.setId(1);
        try {
            manager.createCustomer(customer);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }

        customer = new Customer(null, "New York City", "+420 666 666 666");
        try {
            manager.createCustomer(customer);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }

        customer = new Customer("I.C. Wiener", null, "+420 666 666 666"); 
        try {
            manager.createCustomer(customer);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }

        customer = new Customer("I.C.Wiener", "New York City", null); 
        try {
            manager.createCustomer(customer);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }
    }
    /**
     * Test of findCustomerByName method, of class CustomerManagerImpl.
     */
    @Test
    public void testFindCustomerByName() {
        assertTrue(manager.findCustomerByName("wie").isEmpty());

        Customer c1 = new Customer("I.C. Wiener", "New York City", "+420 666 666 666");
        Customer c2 = new Customer("I.C. Wiener", "California", "+420 123 456 789");

        manager.createCustomer(c1);
        manager.createCustomer(c2);

        List<Customer> expected = Arrays.asList(c1,c2);
        List<Customer> actual = manager.findCustomerByName("I.C. Wiener");

        Collections.sort(actual,idComparator);
        Collections.sort(expected,idComparator);

        assertEquals(expected, actual);
        assertDeepEquals(expected, actual);
    }
    
    /**
     * Test of updateCustomer method, of class CustomerManagerImpl.
     */
    @Test
    public void testUpdateCustomer() {
        Customer customer = new Customer("I.C. Wiener", "New York City", "+420 666 666 666");
        Customer c2 = new Customer("Hugh Gerection", "California", "+420 123 456 789");
        manager.createCustomer(customer);
        manager.createCustomer(c2);
        Integer customerId = customer.getId();

        customer = manager.getCustomerById(customerId);
        customer.setName("Hulla Hop");
        manager.updateCustomer(customer);        
        assertEquals("Hulla Hop", customer.getName());
        assertEquals("New York City", customer.getAddress());
        assertEquals("+420 666 666 666", customer.getPhoneNumber());

        customer = manager.getCustomerById(customerId);
        customer.setAddress("Oakland");
        manager.updateCustomer(customer);        
        assertEquals("Hulla Hop", customer.getName());
        assertEquals("Oakland", customer.getAddress());
        assertEquals("+420 666 666 666", customer.getPhoneNumber());

        customer = manager.getCustomerById(customerId);
        customer.setPhoneNumber("+420 987 654 321");
        manager.updateCustomer(customer);        
        assertEquals("Hulla Hop", customer.getName());
        assertEquals("Oakland", customer.getAddress());
        assertEquals("+420 987 654 321", customer.getPhoneNumber());

        // Check if updates didn't affected other records
        assertDeepEquals(c2, manager.getCustomerById(c2.getId()));
    }

    @Test
    public void updateCustomerWithWrongAttributes() {

        Customer customer = new Customer("I.C. Wiener", "New York City", "+420 666 666 666");
        manager.createCustomer(customer);
        Integer customerId = customer.getId();
        
        try {
            manager.updateCustomer(null);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }
        
        try {
            customer = manager.getCustomerById(customerId);
            customer.setId(null);
            manager.updateCustomer(customer);        
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }

        try {
            customer = manager.getCustomerById(customerId);
            customer.setId(customerId - 1);
            manager.updateCustomer(customer);        
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }

        try {
            customer = manager.getCustomerById(customerId);
            customer.setName(null);
            manager.updateCustomer(customer);        
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }

        try {
            customer = manager.getCustomerById(customerId);
            customer.setAddress(null);
            manager.updateCustomer(customer);        
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }

        try {
            customer = manager.getCustomerById(customerId);
            customer.setPhoneNumber(null);
            manager.updateCustomer(customer);        
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }
        }
    /**
     * Test of deleteCustomer method, of class CustomerManagerImpl.
     */
    @Test
    public void testDeleteCustomer() {
        Customer c1 = new Customer("I.C. Wiener", "New York City", "+420 666 666 666");
        Customer c2 = new Customer("Hugh Gerection", "California", "+420 123 456 789");
        manager.createCustomer(c1);
        manager.createCustomer(c2);
        
        assertNotNull(manager.getCustomerById(c1.getId()));
        assertNotNull(manager.getCustomerById(c2.getId()));

        manager.deleteCustomer(c1);
        
        assertNull(manager.getCustomerById(c1.getId()));
        assertNotNull(manager.getCustomerById(c2.getId()));
    }
    
    @Test
    public void deleteCustomerWithWrongAttributes() {

        Customer customer = new Customer("I.C. Wiener", "New York City", "+420 666 666 666");
        
        try {
            manager.deleteCustomer(null);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }

        try {
            customer.setId(null);
            manager.deleteCustomer(customer);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }

        try {
            customer.setId(1);
            manager.deleteCustomer(customer);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }        

    }

    private void assertDeepEquals(Customer expected, Customer actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getAddress(), actual.getAddress());
        assertEquals(expected.getPhoneNumber(), actual.getPhoneNumber());
    }
    
    private static Comparator<Customer> idComparator = new Comparator<Customer>() {

        @Override
        public int compare(Customer o1, Customer o2) {
            return Integer.valueOf(o1.getId()).compareTo(Integer.valueOf(o2.getId()));
        }
    };

    private void assertDeepEquals(List<Customer> expectedList, List<Customer> actualList) {
        for (int i = 0; i < expectedList.size(); i++) {
            Customer expected = expectedList.get(i);
            Customer actual = actualList.get(i);
            assertDeepEquals(expected, actual);
        }
    }
}
