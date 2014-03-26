

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package boardgamerental;

import cz.muni.fi.pv168.common.DBUtils;
import cz.muni.fi.pv168.common.IllegalEntityException;
import cz.muni.fi.pv168.common.ValidationException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.sql.DataSource;
import org.apache.commons.dbcp.BasicDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Patrik
 */
public class BoardGameManagerImplTest {

    private BoardGameManagerImpl manager;
    private DataSource ds;
    
    private static DataSource prepareDataSource() throws SQLException {
        BasicDataSource ds = new BasicDataSource();
        //we will use in memory database
        ds.setUrl("jdbc:derby:memory:BoardGameManager-test;create=true");
        return ds;
    }

    @Before
    public void setUp() throws SQLException {
        ds = prepareDataSource();
        DBUtils.executeSqlScript(ds,BoardGameManager.class.getResource("createTables.sql"));
        manager = new BoardGameManagerImpl();
        manager.setDataSource(ds);
    }
    
    @After
    public void tearDown() throws SQLException {
        DBUtils.executeSqlScript(ds,BoardGameManager.class.getResource("dropTables.sql"));
    }

    /**
     * Test of createBoardGame method, of class BoardGameManagerImpl.
     */
    @Test
    public void testCreateBoardGame() {
        Set<String> category = new HashSet<>();
        category.add("cool");
        category.add("nice");
        BoardGame boardGame = new BoardGame("Nice BoardGame", 6, 2, category, new BigDecimal(150));
        manager.createBoardGame(boardGame);

        Integer boardGameId = boardGame.getId();
        assertNotNull(boardGameId);
        BoardGame result = manager.getBoardGameById(boardGameId);
        assertEquals(boardGame, result);
        assertNotSame(boardGame, result);
        assertBGDeepEquals(boardGame, result);


    }

    /**
     * Test of getBoardGameById method, of class BoardGameManagerImpl.
     */
    @Test
    public void testGetBoardGameById() {
        assertNull(manager.getBoardGameById(1));
        
        Set<String> category = new HashSet<>();
        category.add("cool");
        category.add("nice");
        BoardGame boardGame = new BoardGame("Nice BoardGame", 6, 2, category, new BigDecimal(150));
        manager.createBoardGame(boardGame);
        Integer boardGameId = boardGame.getId();

        BoardGame result = manager.getBoardGameById(boardGameId);
        assertEquals(boardGame, result);
        assertBGDeepEquals(boardGame, result);
    }

    /**
     * Test of findAllBoardGames method, of class BoardGameManagerImpl.
     */
    @Test
    public void testFindAllBoardGames() {
        assertTrue(manager.findAllBoardGames().isEmpty());

        Set<String> category = new HashSet<>();
        category.add("cool");
        category.add("nice");
        BoardGame bg1 = new BoardGame("Nice BoardGame", 6, 2, category, new BigDecimal(150));
        Set<String> cat = new HashSet<>();
        cat.add("another");
        cat.add("alternative");
        BoardGame bg2 = new BoardGame("Another BoardGame", 8, 1, cat, new BigDecimal(300));
        manager.createBoardGame(bg1);
        manager.createBoardGame(bg2);

        List<BoardGame> expected = Arrays.asList(bg1,bg2);
        List<BoardGame> actual = manager.findAllBoardGames();
        
        assertBGCollectionDeepEquals(expected, actual);
    }

    /**
     * Test of findBoardGameByPlayers method, of class BoardGameManagerImpl.
     */
    @Test
    public void testFindBoardGameByPlayers() {
        assertTrue(manager.findBoardGameByPlayers(6).isEmpty());

        Set<String> category = new HashSet<>();
        category.add("cool");
        category.add("nice");
        BoardGame bg1 = new BoardGame("Nice BoardGame", 6, 2, category, new BigDecimal(150));
        Set<String> cat = new HashSet<>();
        cat.add("cool");
        cat.add("alternative");
        BoardGame bg2 = new BoardGame("Another BoardGame", 8, 1, cat, new BigDecimal(300));
        manager.createBoardGame(bg1);
        manager.createBoardGame(bg2);

        List<BoardGame> expected = Arrays.asList(bg1,bg2);
        List<BoardGame> actual = manager.findBoardGameByPlayers(6);
        
        assertBGCollectionDeepEquals(expected, actual);
    }

    /**
     * Test of findBoardGameByCategory method, of class BoardGameManagerImpl.
     */
    @Test
    public void testFindBoardGameByCategory() {
        assertTrue(manager.findBoardGameByCategory("cool").isEmpty());

        Set<String> category = new HashSet<>();
        category.add("cool");
        category.add("nice");
        BoardGame bg1 = new BoardGame("Nice BoardGame", 6, 2, category, new BigDecimal(150));
        Set<String> cat = new HashSet<>();
        cat.add("cool");
        cat.add("alternative");
        BoardGame bg2 = new BoardGame("Another BoardGame", 8, 1, cat, new BigDecimal(300));
        manager.createBoardGame(bg1);
        manager.createBoardGame(bg2);

        List<BoardGame> expected = Arrays.asList(bg1,bg2);
        List<BoardGame> actual = manager.findBoardGameByCategory("cool");
        
        assertBGCollectionDeepEquals(expected, actual);
    }

    /**
     * Test of findBoardGameByPricePerDay method, of class BoardGameManagerImpl.
     */
    @Test
    public void testFindBoardGameByPricePerDay() {
        assertTrue(manager.findBoardGameByPricePerDay(new BigDecimal(500)).isEmpty());

        Set<String> category = new HashSet<>();
        category.add("cool");
        category.add("nice");
        BoardGame bg1 = new BoardGame("Nice BoardGame", 6, 2, category, new BigDecimal(150));
        Set<String> cat = new HashSet<>();
        cat.add("cool");
        cat.add("alternative");
        BoardGame bg2 = new BoardGame("Another BoardGame", 8, 1, cat, new BigDecimal(300));
        manager.createBoardGame(bg1);
        manager.createBoardGame(bg2);

        List<BoardGame> expected = Arrays.asList(bg1,bg2);
        List<BoardGame> actual = manager.findBoardGameByPricePerDay(new BigDecimal(500));
        
        assertBGCollectionDeepEquals(expected, actual);
    }

    @Test
    public void addBoardGameWithWrongAttributes() {

        try {
            manager.createBoardGame(null);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }

        Set<String> category = new HashSet<>();
        category.add("cool");
        category.add("nice");
        BoardGame boardGame = new BoardGame("Nice BoardGame", 6, 2, category, new BigDecimal(150));
        boardGame.setId(1);
        try {
            manager.createBoardGame(boardGame);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }

        boardGame = new BoardGame(null, 6, 2, category, new BigDecimal(150));
        try {
            manager.createBoardGame(boardGame);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }

        boardGame = new BoardGame("", 6, 2, category, new BigDecimal(150));
        try {
            manager.createBoardGame(boardGame);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }

        boardGame = new BoardGame("Nice BoardGame", 6, 0, category, new BigDecimal(150));
        try {
            manager.createBoardGame(boardGame);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }

        boardGame = new BoardGame("Nice BoardGame", 0, 2, category, new BigDecimal(150));
        try {
            manager.createBoardGame(boardGame);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }

        boardGame = new BoardGame("Nice BoardGame", 2, 6, category, new BigDecimal(150));
        try {
            manager.createBoardGame(boardGame);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }

        boardGame = new BoardGame("Nice BoardGame", 6, 2, null, new BigDecimal(150));
        try {
            manager.createBoardGame(boardGame);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }

        category.clear();
        boardGame = new BoardGame("Nice BoardGame", 2, 4, category, new BigDecimal(150));
        try {
            manager.createBoardGame(boardGame);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }

        category.add("cool");
        category.add("nice");
        boardGame = new BoardGame("Nice BoardGame", 2, 4, category, new BigDecimal(0));
        try {
            manager.createBoardGame(boardGame);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }
        
        boardGame = new BoardGame("Nice BoardGame", 2, 4, category, null);
        try {
            manager.createBoardGame(boardGame);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }

        // these variants should be ok
        boardGame = new BoardGame("Nice BoardGame", 2, 2, category, new BigDecimal(150));
        manager.createBoardGame(boardGame);
        BoardGame result = manager.getBoardGameById(boardGame.getId());
        assertNotNull(result);
    }

    /**
     * Test of updateBoardGame method, of class BoardGameManagerImpl.
     */
    @Test
    public void testUpdateBoardGame() {
        Set<String> category = new HashSet<>();
        category.add("cool");
        category.add("nice");
        BoardGame boardGame = new BoardGame("Nice BoardGame", 6, 2, category, new BigDecimal(150));
        Set<String> cat = new HashSet<>();
        cat.add("another");
        cat.add("alternative");
        BoardGame bg2 = new BoardGame("Another BoardGame", 8, 1, cat, new BigDecimal(300));
        manager.createBoardGame(boardGame);
        manager.createBoardGame(bg2);
        Integer boardGameId = boardGame.getId();
        BoardGame result;
        
        boardGame = manager.getBoardGameById(boardGameId);
        boardGame.setName("Cool BG");
        manager.updateBoardGame(boardGame);        
        result = manager.getBoardGameById(boardGameId);
        assertBGDeepEquals(boardGame, result);

        boardGame = manager.getBoardGameById(boardGameId);
        boardGame.setMaxPlayers(10);
        manager.updateBoardGame(boardGame);        
        result = manager.getBoardGameById(boardGameId);
        assertBGDeepEquals(boardGame, result);

        boardGame = manager.getBoardGameById(boardGameId);
        boardGame.setMinPlayers(4);
        manager.updateBoardGame(boardGame);        
        result = manager.getBoardGameById(boardGameId);
        assertBGDeepEquals(boardGame, result);

        category = new HashSet<>();
        category.add("awesome");
        category.add("pretty");
        boardGame = manager.getBoardGameById(boardGameId);
        boardGame.setCategory(category);
        manager.updateBoardGame(boardGame);        
        result = manager.getBoardGameById(boardGameId);
        assertBGDeepEquals(boardGame, result);

        boardGame = manager.getBoardGameById(boardGameId);
        boardGame.setPricePerDay(new BigDecimal(500));
        manager.updateBoardGame(boardGame);        
        result = manager.getBoardGameById(boardGameId);
        assertBGDeepEquals(boardGame, result);

        // Check if updates didn't affected other records
        assertBGDeepEquals(bg2, manager.getBoardGameById(bg2.getId()));
    }
    
    public void updateBoardGameWithWrongAttributes(){
        
        Set<String> category = new HashSet<>();
        category.add("cool");
        category.add("nice");
        BoardGame boardGame = new BoardGame("Nice BoardGame", 6, 2, category, new BigDecimal(150));
        manager.createBoardGame(boardGame);
        Integer boardGameId = boardGame.getId();
        
        try {
            manager.updateBoardGame(null);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }
        
        try {
            boardGame = manager.getBoardGameById(boardGameId);
            boardGame.setId(null);
            manager.updateBoardGame(boardGame);        
            fail();
        } catch (IllegalEntityException ex) {
            //OK
        }

        try {
            boardGame = manager.getBoardGameById(boardGameId);
            boardGame.setId(boardGameId - 1);
            manager.updateBoardGame(boardGame);        
            fail();
        } catch (IllegalEntityException ex) {
            //OK
        }

        try {
            boardGame = manager.getBoardGameById(boardGameId);
            boardGame.setName(null);
            manager.updateBoardGame(boardGame);        
            fail();
        } catch (IllegalEntityException ex) {
            //OK
        }

        try {
            boardGame = manager.getBoardGameById(boardGameId);
            boardGame.setName("");
            manager.updateBoardGame(boardGame);        
            fail();
        } catch (IllegalEntityException ex) {
            //OK
        }

        try {
            boardGame = manager.getBoardGameById(boardGameId);
            boardGame.setMaxPlayers(0);
            manager.updateBoardGame(boardGame);        
            fail();
        } catch (IllegalEntityException ex) {
            //OK
        }

        try {
            boardGame = manager.getBoardGameById(boardGameId);
            boardGame.setMinPlayers(0);
            manager.updateBoardGame(boardGame);        
            fail();
        } catch (IllegalEntityException ex) {
            //OK
        }
        
        try {
            boardGame = manager.getBoardGameById(boardGameId);
            boardGame.setCategory(null);
            manager.updateBoardGame(boardGame);        
            fail();
        } catch (IllegalEntityException ex) {
            //OK
        }
        
        category.clear();
        try {
            boardGame = manager.getBoardGameById(boardGameId);
            boardGame.setCategory(category);
            manager.updateBoardGame(boardGame);        
            fail();
        } catch (IllegalEntityException ex) {
            //OK
        }
        
        try {
            boardGame = manager.getBoardGameById(boardGameId);
            boardGame.setPricePerDay(null);
            manager.updateBoardGame(boardGame);        
            fail();
        } catch (IllegalEntityException ex) {
            //OK
        }
        
        try {
            boardGame = manager.getBoardGameById(boardGameId);
            boardGame.setPricePerDay(new BigDecimal(0));
            manager.updateBoardGame(boardGame);        
            fail();
        } catch (IllegalEntityException ex) {
            //OK
        }
    }

    /**
     * Test of deleteBoardGame method, of class BoardGameManagerImpl.
     */
    @Test
    public void testDeleteBoardGame() {
        
        Set<String> category = new HashSet<>();
        category.add("cool");
        category.add("nice");
        BoardGame bg1 = new BoardGame("Nice BoardGame", 6, 2, category, new BigDecimal(150));
        Set<String> cat = new HashSet<>();
        cat.add("another");
        cat.add("alternative");
        BoardGame bg2 = new BoardGame("Another BoardGame", 8, 1, cat, new BigDecimal(300));
        manager.createBoardGame(bg1);
        manager.createBoardGame(bg2);
        
        assertNotNull(manager.getBoardGameById(bg1.getId()));
        assertNotNull(manager.getBoardGameById(bg2.getId()));

        manager.deleteGrave(g1);
        
        assertNull(manager.getGrave(g1.getId()));
        assertNotNull(manager.getGrave(g2.getId()));
    }

    private static void assertBGDeepEquals(BoardGame expected, BoardGame actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getMinPlayers(), actual.getMinPlayers());
        assertEquals(expected.getMaxPlayers(), actual.getMaxPlayers());
        assertEquals(expected.getCategory(), actual.getCategory());
        assertEquals(expected.getPricePerDay(), actual.getPricePerDay());
    }
    
    private static final Comparator<BoardGame> idComparator = new Comparator<BoardGame>() {

        @Override
        public int compare(BoardGame o1, BoardGame o2) {
            Integer id1 = o1.getId();
            Integer id2 = o2.getId();
            if (id1 == null && id2 == null) {
                return 0;
            } else if (id1 == null && id2 != null) {
                return -1;
            } else if (id1 != null && id2 == null) {
                return 1;
            } else {
                return id1.compareTo(id2);
            }
        }
    };
    
    private static void assertBGCollectionDeepEquals(List<BoardGame> expected, List<BoardGame> actual) {
        
        assertEquals(expected.size(), actual.size());
        List<BoardGame> expectedSortedList = new ArrayList<>(expected);
        List<BoardGame> actualSortedList = new ArrayList<>(actual);
        Collections.sort(expectedSortedList,idComparator);
        Collections.sort(actualSortedList,idComparator);
        for (int i = 0; i < expectedSortedList.size(); i++) {
            assertBGDeepEquals(expectedSortedList.get(i), actualSortedList.get(i));
        }
    }
}

