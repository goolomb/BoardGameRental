/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package boardgamerental;

import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Goolomb
 */
public interface LendingManager {
    void createLending(Lending lending);
    
    Lending getLendingById(Integer id);
    
    List<Lending> findAllLendings();
    
    List<Lending> findLendingsForCustomer(Customer customer);
    
    void updateLending(Lending lending);
    
    void deleteLending(Lending lending);
    
    BigDecimal calculateTotalPrice(Lending lending);
    
    boolean isAvailable(BoardGame boardGame);
    
}
