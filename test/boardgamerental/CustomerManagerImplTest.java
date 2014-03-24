/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package boardgamerental;

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
    
    public CustomerManagerImplTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of createCustomer method, of class CustomerManagerImpl.
     */
    @Test
    public void testCreateCustomer() {
        System.out.println("createCustomer");
        Customer customer = null;
        CustomerManagerImpl instance = null;
        instance.createCustomer(customer);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCustomerById method, of class CustomerManagerImpl.
     */
    @Test
    public void testGetCustomerById() {
        System.out.println("getCustomerById");
        Integer id = null;
        CustomerManagerImpl instance = null;
        Customer expResult = null;
        Customer result = instance.getCustomerById(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
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
        System.out.println("findCustomerByName");
        CustomerManagerImpl instance = null;
        List<Customer> expResult = null;
        List<Customer> result = instance.findCustomerByName();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
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
    
}
