/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package boardgamerental;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Goolomb
 */
public class LendingManagerImplTest {
    
    private LendingManagerImpl manager;
    
    @Before
    public  void setUp() throws SQLException {
        manager = new LendingManagerImpl();
    }
    
    /**
     * Test of createLending method, of class LendingManagerImpl.
     */
    @Test
    public void createLending() {
        
        Lending lending = newLending(5, new Customer(15, "Pepa", "Brno", "800000000"), new BoardGame(25, "Nice game", 1, 5, new TreeSet<String>(), new BigDecimal(7.185)), new GregorianCalendar(), new GregorianCalendar(), new GregorianCalendar());
        manager.createLending(lending);

        Integer lendingId = lending.getId();
        assertNotNull(lendingId);
        Lending result = manager.getLendingById(lendingId);
        assertEquals(lending, result);
        assertNotSame(lending, result);
        assertDeepEquals(lending, result);
    }
    
    @Test
    public void findAllLendings() {

        assertTrue(manager.findAllLendings().isEmpty());

        Lending l1 = newLending(5, new Customer(15, "Pepa", "Brno", "800000000"), new BoardGame(25, "Nice game", 1, 5, new TreeSet<String>(), new BigDecimal(7.185)), new GregorianCalendar(), new GregorianCalendar(), new GregorianCalendar());
        Lending l2 = newLending(8, new Customer(15, "Pepa", "Brno", "800000000"), new BoardGame(25, "Nice game", 1, 5, new TreeSet<String>(), new BigDecimal(7.185)), new GregorianCalendar(), new GregorianCalendar(), new GregorianCalendar());

        manager.createLending(l1);
        manager.createLending(l2);

        List<Lending> expected = Arrays.asList(l1,l2);
        List<Lending> actual = manager.findAllLendings();

        Collections.sort(actual,idComparator);
        Collections.sort(expected,idComparator);

        assertEquals(expected, actual);
        assertDeepEquals(expected, actual);
    }

    @Test
    public void deleteLending() {

        Lending l1 = newLending(5, new Customer(15, "Pepa", "Brno", "800000000"), new BoardGame(25, "Nice game", 1, 5, new TreeSet<String>(), new BigDecimal(7.185)), new GregorianCalendar(), new GregorianCalendar(), new GregorianCalendar());
        Lending l2 = newLending(8, new Customer(15, "Pepa", "Brno", "800000000"), new BoardGame(25, "Nice game", 1, 5, new TreeSet<String>(), new BigDecimal(7.185)), new GregorianCalendar(), new GregorianCalendar(), new GregorianCalendar());
        manager.createLending(l1);
        manager.createLending(l2);
        
        assertNotNull(manager.getLendingById(l1.getId()));
        assertNotNull(manager.getLendingById(l2.getId()));

        manager.deleteLending(l1);
        
        assertNull(manager.getLendingById(l1.getId()));
        assertNotNull(manager.getLendingById(l2.getId()));
                
    }

    @Test
    public void deleteLendingWithWrongAttributes() {

        Lending lending = newLending(5, new Customer(15, "Pepa", "Brno", "800000000"), new BoardGame(25, "Nice game", 1, 5, new TreeSet<String>(), new BigDecimal(7.185)), new GregorianCalendar(), new GregorianCalendar(), new GregorianCalendar());
        
        try {
            manager.deleteLending(null);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }

        try {
            lending.setId(null);
            manager.deleteLending(lending);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }

        try {
            lending.setId(-1);
            manager.deleteLending(lending);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }        

    }
    
    private static Lending newLending(Integer id, Customer customer, BoardGame boardGame, Calendar startTime, Calendar expectedEndTime, Calendar realEndTime) {
        Lending lending = new Lending();
        lending.setId(id);
        lending.setCustomer(customer);
        lending.setBoardGame(boardGame);
        lending.setStartTime(startTime);
        lending.setExpectedEndTime(expectedEndTime);
        lending.setRealEndTime(realEndTime);
        return lending;
    }

    private void assertDeepEquals(List<Lending> expectedList, List<Lending> actualList) {
        for (int i = 0; i < expectedList.size(); i++) {
            Lending expected = expectedList.get(i);
            Lending actual = actualList.get(i);
            assertDeepEquals(expected, actual);
        }
    }

    private void assertDeepEquals(Lending expected, Lending actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getCustomer(), actual.getCustomer());
        assertEquals(expected.getBoardGame(), actual.getBoardGame());
        assertEquals(expected.getStartTime(), actual.getStartTime());
        assertEquals(expected.getExpectedEndTime(), actual.getExpectedEndTime());
        assertEquals(expected.getRealEndTime(), actual.getRealEndTime());
    }

    private static Comparator<Lending> idComparator = new Comparator<Lending>() {

        @Override
        public int compare(Lending o1, Lending o2) {
            return Integer.valueOf(o1.getId()).compareTo(Integer.valueOf(o2.getId()));
        }
    };
    
}
