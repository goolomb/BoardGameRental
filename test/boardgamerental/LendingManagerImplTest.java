/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package boardgamerental;

import common.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import javax.sql.DataSource;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.dbcp.BasicDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


/**
 *
 * @author Goolomb
 */
public class LendingManagerImplTest {
    
    private LendingManagerImpl manager;
    private DataSource ds;
    private BoardGameManagerImpl boardGameManager;
    private CustomerManagerImpl customerManager;
    
    private static DataSource prepareDataSource() throws SQLException {
        BasicDataSource ds = new BasicDataSource();
        //we will use in memory database
        ds.setUrl("jdbc:derby:memory:LendingManager-test;create=true");
        return ds;
    }
    
    private Customer c1, c2, customerWithNullId, customerNotInDB;
    private BoardGame b1, b2, b3, boardGameWithNullId, boardGameNotInDB;
    
    private void prepareTestData() {
        c1 = new Customer("Franta", "Praha", "900000000");
        c2 = new Customer("Pepa", "Brno", "800000000");
        customerManager.createCustomer(c1);
        customerManager.createCustomer(c2);
 
        Set<String> category = new HashSet<>();
        category.add("cool");
        category.add("nice");
        Set<String> category2 = new HashSet<>();
        category2.add("pretty");
        category2.add("badd ass");
        b1 = new BoardGame("Nice BoardGame", 6, 2, category, new BigDecimal(150));
        b2 = new BoardGame("Even Better BoardGame", 4, 3, category2, new BigDecimal(35));
        b3 = new BoardGame("The best BoardGame", 8, 1, category2, new BigDecimal(30));
        boardGameManager.createBoardGame(b1);
        boardGameManager.createBoardGame(b2);
        boardGameManager.createBoardGame(b3);
        
        customerWithNullId = new Customer("Dont", "Have", "Id");
        customerNotInDB = new Customer("Not","In","Database");
        customerNotInDB.setId(c2.getId() + 100);
        boardGameWithNullId = new BoardGame("Without Id", 1, 1, category, new BigDecimal(1));
        boardGameNotInDB = new BoardGame("Not in DB", 1, 1, category, new BigDecimal(1));
        boardGameNotInDB.setId(b2.getId() + 100);
    }

    @Before
    public void setUp() throws SQLException {
        ds = prepareDataSource();
        DBUtils.executeSqlScript(ds, LendingManager.class.getResource("createTables.sql"));
        manager = new LendingManagerImpl();
        manager.setDataSource(ds);
        boardGameManager = new BoardGameManagerImpl();
        boardGameManager.setDataSource(ds);
        customerManager = new CustomerManagerImpl();
        customerManager.setDataSource(ds);
        prepareTestData();
    }

    @After
    public void tearDown() throws SQLException {
        DBUtils.executeSqlScript(ds,LendingManager.class.getResource("dropTables.sql"));
    }

    private Date date(String date) {
        return Date.valueOf(date);
    }
    
    /**
     * Test of createLending method, of class LendingManagerImpl.
     */
    @Test
    public void createLending() {
        Lending l1 = new Lending(c1, b1, date("2014-01-01"), date("2014-02-01"));
        manager.createLending(l1);
        
        Integer lendingId = l1.getId();
        assertNotNull(lendingId);
        Lending result = manager.getLendingById(lendingId);
        assertEquals(l1, result);
        assertNotSame(l1, result);
        assertDeepEquals(l1, result);
    }
    
    @Test
    public void createLendingWithWrongAttributes() {
        try {
            manager.createLending(null);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }
        
        Lending l1 = new Lending(c1, b1, date("2014-01-01"), date("2014-02-01"));
        try {
            l1.setId(1);
            manager.createLending(l1);
            fail();
        } catch (IllegalEntityException ex) {
            //OK
        }

        l1 = new Lending(c1, b1, date("2014-01-01"), date("2014-02-01"));
        try {
            Lending l2 = new Lending(c2, b1, date("2014-01-01"), date("2014-02-01"));
            manager.createLending(l1);
            manager.createLending(l2);
            fail();
        }
        catch(ValidationException ex) {
            //OK
        }
        
        l1 = new Lending(c1, b1, date("2014-03-01"), date("2014-02-01"));
        try {
            manager.createLending(l1);
            fail();
        } catch (ValidationException ex) {
            //OK
        }

        l1 = new Lending(c1, b1, null, date("2014-02-01"));
        try {
            manager.createLending(l1);
            fail();
        } catch (ValidationException ex) {
            //OK
        }

        l1 = new Lending(c1, b1, date("2014-03-01"), null);
        try {
            manager.createLending(l1);
            fail();
        } catch (ValidationException ex) {
            //OK
        }

        l1 = new Lending(c1, b1, date("2014-03-01"), date("2014-02-01"));
        try {
            l1.setRealEndTime(date("2014-03-01"));
            manager.createLending(l1);
            fail();
        } catch (ValidationException ex) {
            //OK
        }
        
        l1 = new Lending(c1, null, date("2014-03-01"), date("2014-02-01"));
        try {
            manager.createLending(l1);
            fail();
        } catch (ValidationException ex) {
            //OK
        }

        l1 = new Lending(c1, boardGameWithNullId, date("2014-03-01"), date("2014-02-01"));
        try {
            manager.createLending(l1);
            fail();
        } catch (ValidationException ex) {
            //OK
        }

        l1 = new Lending(c1, boardGameNotInDB, date("2014-03-01"), date("2014-02-01"));
        try {
            manager.createLending(l1);
            fail();
        } catch (ValidationException ex) {
            //OK
        }

        l1 = new Lending(null, b1, date("2014-03-01"), date("2014-02-01"));
        try {
            manager.createLending(l1);
            fail();
        } catch (ValidationException ex) {
            //OK
        }

        l1 = new Lending(customerWithNullId, b1, date("2014-03-01"), date("2014-02-01"));
        try {
            manager.createLending(l1);
            fail();
        } catch (ValidationException ex) {
            //OK
        }

        l1 = new Lending(customerNotInDB, b1, date("2014-03-01"), date("2014-02-01"));
        try {
            manager.createLending(l1);
            fail();
        } catch (ValidationException ex) {
            //OK
        }        
    }
    
    @Test
    public void getLendingById() {
        assertNull(manager.getLendingById(1));
        
        Lending l1 = new Lending(c1, b1, date("2014-01-01"), date("2014-02-01"));
        manager.createLending(l1);
        Integer lendingId = l1.getId();

        Lending result = manager.getLendingById(lendingId);
        assertEquals(l1, result);
        assertDeepEquals(l1, result);
    }
    
    @Test
    public void findLendingsForCustomer() {
        assertTrue(manager.findLendingsForCustomer(c1).isEmpty());

        Lending l1 = new Lending(c1, b1, date("2014-01-01"), date("2014-02-01"));
        Lending l2 = new Lending(c2, b2, date("2013-07-06"), date("2013-07-21"));
        Lending l3 = new Lending(c1, b3, date("2013-05-01"), date("2014-04-01"));
        manager.createLending(l1);
        manager.createLending(l2);
        manager.createLending(l3);
        
        List<Lending> expected = Arrays.asList(l1,l3);
        List<Lending> actual = manager.findLendingsForCustomer(c1);
        
        assertCollectionDeepEquals(expected, actual);
    }
    
    @Test
    public void findAllLendings() {
        
        assertTrue(manager.findAllLendings().isEmpty());

        Lending l1 = new Lending(c1, b1, date("2014-01-01"), date("2014-02-01"));
        Lending l2 = new Lending(c2, b2, date("2013-07-06"), date("2013-07-21"));
        Lending l3 = new Lending(c1, b3, date("2013-05-01"), date("2014-04-01"));
        
        manager.createLending(l1);
        manager.createLending(l2);
        //manager.createLending(l3);

        List<Lending> expected = Arrays.asList(l1,l2);
        List<Lending> actual = manager.findAllLendings();

        Collections.sort(actual,idComparator);
        Collections.sort(expected,idComparator);

        assertEquals(expected, actual);
        assertCollectionDeepEquals(expected, actual);
    }
    
    @Test
    public void updateLending() {
        Lending l1 = new Lending(c1, b1, date("2014-01-01"), date("2014-02-01"));
        Lending l2 = new Lending(c2, b2, date("2013-07-06"), date("2013-07-21"));

        manager.createLending(l1);
        manager.createLending(l2);
        
        Integer lendingId = l1.getId();
        Lending result;
        
        l1 = manager.getLendingById(lendingId);
        l1.setStartTime(date("2011-04-21"));
        manager.updateLending(l1);
        result = manager.getLendingById(lendingId);
        assertDeepEquals(l1, result);

        l1 = manager.getLendingById(lendingId);
        l1.setExpectedEndTime(date("2015-06-17"));
        manager.updateLending(l1);
        result = manager.getLendingById(lendingId);
        assertDeepEquals(l1, result);

        l1 = manager.getLendingById(lendingId);
        l1.setRealEndTime(date("2016-07-18"));
        manager.updateLending(l1);
        result = manager.getLendingById(lendingId);
        assertDeepEquals(l1, result);
        
        l1 = manager.getLendingById(lendingId);
        l1.setCustomer(c2);
        manager.updateLending(l1);
        result = manager.getLendingById(lendingId);
        assertDeepEquals(l1, result);
        
        l1 = manager.getLendingById(lendingId);
        l1.setBoardGame(b2);
        manager.updateLending(l1);
        result = manager.getLendingById(lendingId);
        assertDeepEquals(l1, result);

        // Check if updates didn't affected other records
        assertDeepEquals(l2, manager.getLendingById(l2.getId()));
    }
    
    @Test
    public void updateLendingWithWrongAttributes() {
        Lending l1 = new Lending(c1, b1, date("2014-01-01"), date("2014-01-02"));

        manager.createLending(l1);
        
        Integer lendingId = l1.getId();
        
        try {
            manager.updateLending(null);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }
        
        try {
            l1.setId(null);
            manager.updateLending(l1);
            fail();
        } catch (IllegalEntityException ex) {
            //OK
        }

        try {
            l1.setId(lendingId - 1);
            manager.updateLending(l1);        
            fail();
        } catch (IllegalEntityException ex) {
            //OK
        }

        try {
            l1.setBoardGame(null);
            manager.updateLending(l1);        
            fail();
        } catch (ValidationException ex) {
            //OK
        }

        try {
            l1.setCustomer(null);
            manager.updateLending(l1);
            fail();
        } catch (ValidationException ex) {
            //OK
        }

        try {
            l1.setStartTime(null);
            manager.updateLending(l1);        
            fail();
        } catch (ValidationException ex) {
            //OK
        }

        try {
            l1.setExpectedEndTime(null);
            manager.updateLending(l1);        
            fail();
        } catch (ValidationException ex) {
            //OK
        }
        
        try {
            l1.setRealEndTime(null);
            manager.updateLending(l1);        
            fail();
        } catch (ValidationException ex) {
            //OK
        }
        
        try {
            l1.setRealEndTime(date("2013-12-31"));
            manager.updateLending(l1);
            fail();
        } catch (ValidationException ex) {
            //OK
        }
        
        try {
            l1.setExpectedEndTime(date("2013-12-31"));
            manager.updateLending(l1);
            fail();
        } catch (ValidationException ex) {
            //OK
        }

        try {
            l1.setBoardGame(boardGameWithNullId);
            manager.updateLending(l1);
            fail();
        } catch (ValidationException ex) {
            //OK
        }

        try {
            l1.setBoardGame(boardGameNotInDB);
            manager.updateLending(l1);
            fail();
        } catch (ValidationException ex) {
            //OK
        }
        
        try {
            l1.setCustomer(customerWithNullId);
            manager.updateLending(l1);
            fail();
        } catch (ValidationException ex) {
            //OK
        }
        
        try {
            l1.setCustomer(customerNotInDB);
            manager.updateLending(l1);
            fail();
        } catch (ValidationException ex) {
            //OK
        }
    }

    @Test
    public void deleteLending() {

        assertTrue(manager.findAllLendings().isEmpty());

        Lending l1 = new Lending(c1, b1, date("2014-01-01"), date("2014-02-01"));
        Lending l2 = new Lending(c2, b2, date("2013-07-06"), date("2013-07-21"));
        
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

        Lending l1 = new Lending(c1, b1, date("2014-01-01"), date("2014-02-01"));
        
        try {
            manager.deleteLending(null);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }

        try {
            l1.setId(null);
            manager.deleteLending(l1);
            fail();
        } catch (IllegalEntityException ex) {
            //OK
        }
        
        try {
            l1.setId(null);
            manager.deleteLending(l1);
            fail();
        } catch (IllegalEntityException ex) {
            //OK
        }

        try {
            manager.createLending(l1);
            l1.setId(l1.getId() + 1);
            manager.deleteLending(l1);
            fail();
        } catch (IllegalEntityException ex) {
            //OK
        }
    }
    
    @Test
    public void calculateTotalPrice() {
        Lending l1 = new Lending(c1, b1, date("2014-01-01"), date("2014-02-13"));
        
        manager.createLending(l1);
        
        BigDecimal result;
        
        BigDecimal expPrice = b1.getPricePerDay().multiply(new BigDecimal(44)); //31 for january, 13 for february
        result = manager.calculateTotalPrice(l1);
        assertEquals(expPrice, result);
        
        l1.setRealEndTime(date("2014-01-13"));
        expPrice = b1.getPricePerDay().multiply(new BigDecimal(13));
        result = manager.calculateTotalPrice(l1);
        assertEquals(expPrice, result);
        
        l1.setRealEndTime(date("2014-02-19"));
        expPrice = b1.getPricePerDay().multiply(new BigDecimal(50)).add(new BigDecimal(300)); //6 days extra
        result = manager.calculateTotalPrice(l1);
        assertEquals(expPrice, result);
    }
    
    @Test
    public void calculateTotalPriceWithWrongAttributes() {
        Lending l1 = new Lending(c1, b1, date("2014-01-01"), date("2014-01-02"));

        manager.createLending(l1);
        
        Integer lendingId = l1.getId();

        try {
            manager.calculateTotalPrice(null);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }
        
        try {
            l1 = manager.getLendingById(lendingId);
            l1.setBoardGame(null);
            manager.calculateTotalPrice(l1);
            fail();
        } catch (ValidationException ex) {
            //OK
        }

        try {
            l1 = manager.getLendingById(lendingId);
            l1.setBoardGame(boardGameWithNullId);
            manager.calculateTotalPrice(l1);
            fail();
        } catch (ValidationException ex) {
            //OK
        }

        try {
            l1 = manager.getLendingById(lendingId);
            l1.setBoardGame(boardGameNotInDB);
            manager.calculateTotalPrice(l1);
            fail();
        } catch (ValidationException ex) {
            //OK
        }

        try {
            l1 = manager.getLendingById(lendingId);
            l1.setStartTime(null);
            manager.calculateTotalPrice(l1);
            fail();
        } catch (ValidationException ex) {
            //OK
        }

        try {
            l1 = manager.getLendingById(lendingId);
            l1.setExpectedEndTime(null);
            manager.calculateTotalPrice(l1);
            fail();
        } catch (ValidationException ex) {
            //OK
        }

        try {
            l1 = manager.getLendingById(lendingId);
            l1.setExpectedEndTime(date("2013-12-31"));
            manager.calculateTotalPrice(l1);
            fail();
        } catch (ValidationException ex) {
            //OK
        }

        try {
            l1 = manager.getLendingById(lendingId);
            l1.setRealEndTime(date("2013-12-31"));
            manager.calculateTotalPrice(l1);
            fail();
        } catch (ValidationException ex) {
            //OK
        }

        try {
            b1.setPricePerDay(null);
            l1 = manager.getLendingById(lendingId);
            l1.setBoardGame(b1);
            manager.calculateTotalPrice(l1);
            fail();
        } catch (ValidationException ex) {
            //OK
        }
        
        try {
            b1.setPricePerDay(BigDecimal.ZERO);
            l1 = manager.getLendingById(lendingId);
            l1.setBoardGame(b1);
            manager.calculateTotalPrice(l1);
            fail();
        } catch (ValidationException ex) {
            //OK
        }
        
        try {
            b1.setPricePerDay(new BigDecimal(-1));
            l1 = manager.getLendingById(lendingId);
            l1.setBoardGame(b1);
            manager.calculateTotalPrice(l1);
            fail();
        } catch (ValidationException ex) {
            //OK
        }
    }

    @Test
    public void isAvailable() {
        assertTrue(manager.findAllLendings().isEmpty());

        Lending l1 = new Lending(c1, b1, date("2014-01-01"), date("2014-02-01"));
        Lending l2 = new Lending(c2, b2, date("2013-07-06"), date("2013-07-21"));
        
        manager.createLending(l1);
        
        assertTrue(manager.isAvailable(b2));
        assertFalse(manager.isAvailable(b1));
    }

    
    private void assertCollectionDeepEquals(List<Lending> expectedList, List<Lending> actualList) {
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

    private static final Comparator<Lending> idComparator = new Comparator<Lending>() {
        
        public int compare(Lending o1, Lending o2) {
            Integer l1 = o1.getId();
            Integer l2 = o2.getId();
            if (l1 == null && l2 == null) {
                return 0;
            } else if (l1 == null && l2 != null) {
                return -1;
            } else if (l1 != null && l2 == null) {
                return 1;
            } else {
                return l1.compareTo(l2);
            }
        }
    };
}
