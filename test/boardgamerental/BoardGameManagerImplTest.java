

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package boardgamerental;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Patrik
 */
public class BoardGameManagerImplTest {

    private BoardGameManagerImpl manager;

    @Before
    public void setUp() throws SQLException {
        manager = new BoardGameManagerImpl();
    }

    /**
     * Test of createBoardGame method, of class BoardGameManagerImpl.
     */
    @Test
    public void testCreateBoardGame() {
        TreeSet<String> category = new TreeSet<>();
        category.add("cool");
        category.add("nice");
        BoardGame boardGame = newBoardGame(1, "Nice BoardGame", 2, 6, category, new BigDecimal(150));
        manager.createBoardGame(boardGame);

        Integer boardGameId = boardGame.getId();
        assertNotNull(boardGameId);
        BoardGame result = manager.getBoardGameById(boardGameId);
        assertEquals(boardGame, result);
        assertNotSame(boardGame, result);
        assertDeepEquals(boardGame, result);


    }

    /**
     * Test of getBoardGameById method, of class BoardGameManagerImpl.
     */
    @Test
    public void testGetBoardGameById() {
        System.out.println("getBoardGameById");
        Integer id = null;
        BoardGameManagerImpl instance = new BoardGameManagerImpl();
        BoardGame expResult = null;
        BoardGame result = instance.getBoardGameById(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findAllBoardGames method, of class BoardGameManagerImpl.
     */
    @Test
    public void testFindAllBoardGames() {
        System.out.println("findAllBoardGames");
        BoardGameManagerImpl instance = new BoardGameManagerImpl();
        List<BoardGame> expResult = null;
        List<BoardGame> result = instance.findAllBoardGames();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findBoardGameByPlayers method, of class BoardGameManagerImpl.
     */
    @Test
    public void testFindBoardGameByPlayers() {
        System.out.println("findBoardGameByPlayers");
        int players = 0;
        BoardGameManagerImpl instance = new BoardGameManagerImpl();
        List<BoardGame> expResult = null;
        List<BoardGame> result = instance.findBoardGameByPlayers(players);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findBoardGameByCategory method, of class BoardGameManagerImpl.
     */
    @Test
    public void testFindBoardGameByCategory() {
        System.out.println("findBoardGameByCategory");
        String category = "";
        BoardGameManagerImpl instance = new BoardGameManagerImpl();
        List<BoardGame> expResult = null;
        List<BoardGame> result = instance.findBoardGameByCategory(category);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findBoardGameByPricePerDay method, of class BoardGameManagerImpl.
     */
    @Test
    public void testFindBoardGameByPricePerDay() {
        System.out.println("findBoardGameByPricePerDay");
        BigDecimal pricePerDay = null;
        BoardGameManagerImpl instance = new BoardGameManagerImpl();
        List<BoardGame> expResult = null;
        List<BoardGame> result = instance.findBoardGameByPricePerDay(pricePerDay);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void addBoardGameWithWrongAttributes() {

        try {
            manager.createBoardGame(null);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }

        TreeSet<String> category = new TreeSet<>();
        category.add("cool");
        category.add("nice");
        BoardGame boardGame = newBoardGame(-1, "Nice BoardGame", 2, 6, category, new BigDecimal(150));
        manager.createBoardGame(boardGame);
        try {
            manager.createBoardGame(boardGame);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }

        boardGame = newBoardGame(1, null, 2, 6, category, new BigDecimal(150));
        try {
            manager.createBoardGame(boardGame);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }

        boardGame = newBoardGame(1, "", 2, 6, category, new BigDecimal(150));
        try {
            manager.createBoardGame(boardGame);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }

        boardGame = newBoardGame(1, "Nice BoardGame", 0, 6, category, new BigDecimal(150));
        try {
            manager.createBoardGame(boardGame);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }

        boardGame = newBoardGame(1, "Nice BoardGame", 0, 0, category, new BigDecimal(150));
        try {
            manager.createBoardGame(boardGame);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }

        boardGame = newBoardGame(1, "Nice BoardGame", 6, 3, category, new BigDecimal(150));
        try {
            manager.createBoardGame(boardGame);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }

        boardGame = newBoardGame(1, "Nice BoardGame", 3, 6, null, new BigDecimal(150));
        try {
            manager.createBoardGame(boardGame);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }

        category.clear();
        boardGame = newBoardGame(1, "Nice BoardGame", 2, 4, category, new BigDecimal(150));
        try {
            manager.createBoardGame(boardGame);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }

        category.add("cool");
        category.add("nice");
        boardGame = newBoardGame(1, "Nice BoardGame", 2, 4, category, new BigDecimal(0));
        try {
            manager.createBoardGame(boardGame);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }

        // these variants should be ok
        boardGame = newBoardGame(0, "Nice BoardGame", 2, 4, category, new BigDecimal(150));
        manager.createBoardGame(boardGame);
        BoardGame result = manager.getBoardGameById(boardGame.getId());
        assertNotNull(result);

        boardGame = newBoardGame(30, "Nice BoardGame", 2, 2, category, new BigDecimal(150));
        manager.createBoardGame(boardGame);
        result = manager.getBoardGameById(boardGame.getId());
        assertNotNull(result);

        boardGame = newBoardGame(30, "Nice BoardGame", 2, 2, category, new BigDecimal(150));
        manager.createBoardGame(boardGame);
        result = manager.getBoardGameById(boardGame.getId());
        assertNotNull(result);

    }

    /**
     * Test of updateBoardGame method, of class BoardGameManagerImpl.
     */
    @Test
    public void testUpdateBoardGame() {
        TreeSet<String> category = new TreeSet<>();
        category.add("cool");
        category.add("nice");
        BoardGame bg = newBoardGame(10, "Nice BoardGame", 2, 6, category, new BigDecimal(150));
        TreeSet<String> cat = new TreeSet<>();
        cat.add("another");
        cat.add("alternative");
        BoardGame bg2 = newBoardGame(20, "Another BoardGame", 1, 8, cat, new BigDecimal(300));
        manager.createBoardGame(bg);
        manager.createBoardGame(bg2);
        Integer bgId = bg.getId();

        TreeSet<String> expected = new TreeSet<>();
        expected.add("cool");
        expected.add("nice");

        bg = manager.getBoardGameById(bgId);
        bg.setName("Cool BoardGame");
        manager.updateBoardGame(bg);        
        assertEquals("Cool BoardGame", bg.getName());
        assertEquals(2, bg.getMinPlayers());
        assertEquals(6, bg.getMaxPlayers());
        assertArrayEquals(expected.toArray(), bg.getCategory().toArray());
        assertEquals(new BigDecimal(150), bg.getPricePerDay());

        bg = manager.getBoardGameById(bgId);
        bg.setMinPlayers(1);
        manager.updateBoardGame(bg);        
        assertEquals("Cool BoardGame", bg.getName());
        assertEquals(1, bg.getMinPlayers());
        assertEquals(6, bg.getMaxPlayers());
        assertArrayEquals(expected.toArray(), bg.getCategory().toArray());
        assertEquals(new BigDecimal(150), bg.getPricePerDay());

        bg = manager.getBoardGameById(bgId);
        bg.setMaxPlayers(3);
        manager.updateBoardGame(bg);        
        assertEquals("Cool BoardGame", bg.getName());
        assertEquals(1, bg.getMinPlayers());
        assertEquals(3, bg.getMaxPlayers());
        assertArrayEquals(expected.toArray(), bg.getCategory().toArray());
        assertEquals(new BigDecimal(150), bg.getPricePerDay());

        bg = manager.getBoardGameById(bgId);
        category.add("super");
        expected.add("super");
        bg.setCategory(category);
        manager.updateBoardGame(bg);        
        assertEquals("Cool BoardGame", bg.getName());
        assertEquals(1, bg.getMinPlayers());
        assertEquals(3, bg.getMaxPlayers());
        assertArrayEquals(expected.toArray(), bg.getCategory().toArray());
        assertEquals(new BigDecimal(150), bg.getPricePerDay());

        bg = manager.getBoardGameById(bgId);
        bg.setPricePerDay(new BigDecimal(450));
        manager.updateBoardGame(bg);        
        assertEquals("Cool BoardGame", bg.getName());
        assertEquals(1, bg.getMinPlayers());
        assertEquals(3, bg.getMaxPlayers());
        assertArrayEquals(expected.toArray(), bg.getCategory().toArray());
        assertEquals(new BigDecimal(450), bg.getPricePerDay());

        assertDeepEquals(bg2, manager.getBoardGameById(bg2.getId()));
    }

    /**
     * Test of deleteBoardGame method, of class BoardGameManagerImpl.
     */
    @Test
    public void testDeleteBoardGame() {
        System.out.println("deleteBoardGame");
        BoardGame boardGame = null;
        BoardGameManagerImpl instance = new BoardGameManagerImpl();
        instance.deleteBoardGame(boardGame);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    private static BoardGame newBoardGame(Integer id, String name, int minPlayers, int maxPlayers, Set<String> category, BigDecimal pricePerDay) {
        BoardGame boardGame = new BoardGame();
        boardGame.setId(id);
        boardGame.setName(name);
        boardGame.setMinPlayers(minPlayers);
        boardGame.setMaxPlayers(maxPlayers);
        boardGame.setCategory(category);
        boardGame.setPricePerDay(pricePerDay);
        return boardGame;
    }

    private void assertDeepEquals(BoardGame expected, BoardGame actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getMinPlayers(), actual.getMinPlayers());
        assertEquals(expected.getMaxPlayers(), actual.getMaxPlayers());
        assertEquals(expected.getCategory(), actual.getCategory());
        assertEquals(expected.getPricePerDay(), actual.getPricePerDay());
    }
}

