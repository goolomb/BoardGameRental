/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package boardgamerental;

import java.util.List;

/**
 *
 * @author Goolomb
 */
public interface CustomerManager {
    void createCustomer(Customer customer);
    
    Customer getCustomerById(Integer id);
    
    List<Customer> findAllCustomers();
    
    List<Customer> findCustomerByName();
    
    void updateCustomer(Customer customer);
    
    void deleteCustomer(Customer customer);
    
}
