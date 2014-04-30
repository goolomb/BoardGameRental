/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package boardgamerental;

import common.*;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;

/**
 *
 * @author Goolomb
 */
public class BoardGameManagerImpl implements BoardGameManager {
    
    private static final Logger logger = Logger.getLogger(
            BoardGameManagerImpl.class.getName());    
    
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private void checkDataSource() {
        if (dataSource == null) {
            throw new IllegalStateException("DataSource is not set");
        }
    }

    @Override
    public void createBoardGame(BoardGame boardGame) throws ServiceFailureException{
        checkDataSource();
        validate(boardGame);
        if (boardGame.getId() != null) {
            throw new IllegalEntityException("board game id is already set");
        }        
        try (Connection conn = dataSource.getConnection()) {

            try (PreparedStatement st = conn.prepareStatement(
                    "INSERT INTO BoardGame (name, maxPlayers, minPlayers, pricePerDay) VALUES (?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS)){
            st.setString(1, boardGame.getName());
            st.setInt(2, boardGame.getMaxPlayers());
            st.setInt(3, boardGame.getMinPlayers());
            st.setBigDecimal(4, boardGame.getPricePerDay());

            int count = st.executeUpdate();
            DBUtils.checkUpdatesCount(count, boardGame, true);

            Integer id = DBUtils.getId(st.getGeneratedKeys());
            boardGame.setId(id);
            }
            insertIntoCategory(boardGame, conn);
        } catch (SQLException ex) {
            String msg = "Error when inserting board game into db";
            logger.log(Level.SEVERE, msg, ex);
            throw new ServiceFailureException(msg, ex);
        }
    }

    @Override
    public BoardGame getBoardGameById(Integer id) throws ServiceFailureException{
        checkDataSource();
        
        if (id == null) {
            throw new IllegalArgumentException("id is null");
        }
        
        try (Connection conn = dataSource.getConnection()){
            try(PreparedStatement st = conn.prepareStatement(
                    "SELECT id, name, maxPlayers, minPlayers, pricePerDay FROM BoardGame WHERE id = ?")){
                st.setInt(1, id);
                
                BoardGame boardGame = executeQueryForSingleBoardGame(st);
                
                if (boardGame != null) {
                    retrieveFromCategory(boardGame, conn);
                }
                return boardGame;
            }
        } catch (SQLException ex) {
            String msg = "Error when getting board game with id = " + id + " from DB";
            logger.log(Level.SEVERE, msg, ex);
            throw new ServiceFailureException(msg, ex);
        }
    }

    @Override
    public List<BoardGame> findAllBoardGames() {
        checkDataSource();
        try (Connection conn = dataSource.getConnection()){
            try (PreparedStatement st = conn.prepareStatement(
                    "SELECT id, name, maxPlayers, minPlayers, pricePerDay FROM BoardGame")){
                List<BoardGame> boardGames = executeQueryForMultipleBoardGames(st);
                for(BoardGame boardGame : boardGames) {
                    retrieveFromCategory(boardGame, conn);
                }
                return boardGames;
            }
        } catch (SQLException ex) {
            String msg = "Error when getting all graves from DB";
            logger.log(Level.SEVERE, msg, ex);
            throw new common.ServiceFailureException(msg, ex);
        }
    }

    @Override
    public List<BoardGame> findBoardGameByName(String name) {
        checkDataSource();
        
        if (name == null) {
            throw new IllegalArgumentException("name is null");
        }
        
        if (name == "") {
            throw new IllegalArgumentException("name is empty");
        }
        
        try (Connection conn = dataSource.getConnection()){
            try(PreparedStatement st = conn.prepareStatement(
                    "SELECT id, name, maxPlayers, minPlayers, pricePerDay FROM BoardGame WHERE name = ?")){
                st.setString(1, name);
                
                List<BoardGame> boardGames = executeQueryForMultipleBoardGames(st);
                
                for(BoardGame boardGame : boardGames) {
                    retrieveFromCategory(boardGame, conn);
                }
                return boardGames;
            }
        } catch (SQLException ex) {
            String msg = "Error when getting board game with name = " + name + " from DB";
            logger.log(Level.SEVERE, msg, ex);
            throw new ServiceFailureException(msg, ex);
        }
    }

    @Override
    public List<BoardGame> findBoardGameByPlayers(int players) {
        checkDataSource();
        
        if (players <= 0) {
            throw new IllegalArgumentException("players is not positive number");
        }
        
        try (Connection conn = dataSource.getConnection()){
            try(PreparedStatement st = conn.prepareStatement(
                    "SELECT id, name, maxPlayers, minPlayers, pricePerDay FROM BoardGame WHERE maxPlayers >= ? AND minPlayers <= ?")){
                st.setInt(1, players);
                st.setInt(2, players);
                
                List<BoardGame> boardGames = executeQueryForMultipleBoardGames(st);
                
                for(BoardGame boardGame : boardGames) {
                    retrieveFromCategory(boardGame, conn);
                }
                return boardGames;
            }
        } catch (SQLException ex) {
            String msg = "Error when getting board game with players = " + players + " from DB";
            logger.log(Level.SEVERE, msg, ex);
            throw new ServiceFailureException(msg, ex);
        }
    }

    @Override
    public List<BoardGame> findBoardGameByCategory(String category) {
        checkDataSource();
        List<BoardGame> bgs = new ArrayList<>();
        if (category == null) {
            throw new IllegalArgumentException("category is null");
        }
        
        if (category.equals("")) {
            throw new IllegalArgumentException("category is empty");
        }
        
        try (Connection conn = dataSource.getConnection()){
            try(PreparedStatement st1 = conn.prepareStatement(
                    "SELECT boardGameId, category FROM Category WHERE category = ?")) {
                st1.setString(1, category);
                List<Integer> ids = new ArrayList<>();
                try(ResultSet rs = st1.executeQuery()){
                    while(rs.next())
                        ids.add(rs.getInt("boardGameId"));
                }
                for(Integer id : ids)
                    bgs.add(getBoardGameById(id));
            }
            return bgs;
        } catch (SQLException ex) {
            String msg = "Error when getting board game with id = " + category + " from DB";
            logger.log(Level.SEVERE, msg, ex);
            throw new ServiceFailureException(msg, ex);
        }
    }

    @Override
    public List<BoardGame> findBoardGameByPricePerDay(BigDecimal pricePerDay) {
        checkDataSource();
        
        if (pricePerDay.compareTo(new BigDecimal(0)) <= 0) {
            throw new IllegalArgumentException("price per day is not positive number");
        }
        
        try (Connection conn = dataSource.getConnection()){
            try(PreparedStatement st = conn.prepareStatement(
                    "SELECT id, name, maxPlayers, minPlayers, pricePerDay FROM BoardGame WHERE pricePerDay <= ?")){
                st.setBigDecimal(1, pricePerDay);
                
                List<BoardGame> boardGames = executeQueryForMultipleBoardGames(st);
                
                for(BoardGame boardGame : boardGames) {
                    retrieveFromCategory(boardGame, conn);
                }
                return boardGames;
            }
        } catch (SQLException ex) {
            String msg = "Error when getting board game with pricePerDay = " + pricePerDay + " from DB";
            logger.log(Level.SEVERE, msg, ex);
            throw new ServiceFailureException(msg, ex);
        }
    }

    @Override
    public void updateBoardGame(BoardGame boardGame) {
        checkDataSource();
        validate(boardGame);
        if (boardGame.getId() == null) {
            throw new IllegalEntityException("board game id is null");            
        }
        
        try (Connection conn = dataSource.getConnection()) {
            try(PreparedStatement st = conn.prepareStatement(
                   "UPDATE BoardGame SET name = ?, maxPlayers = ?, minPlayers = ?, pricePerDay = ? WHERE id = ?")){
                st.setString(1, boardGame.getName());
                st.setInt(2, boardGame.getMaxPlayers());
                st.setInt(3, boardGame.getMinPlayers());
                st.setBigDecimal(4, boardGame.getPricePerDay());
                st.setInt(5, boardGame.getId());
                int updateCount = st.executeUpdate();
                if (updateCount == 0) {
                    throw new IllegalArgumentException("BoardGame " + boardGame + " does not exist in the db");
                }
                if (updateCount != 1) {
                    throw new ServiceFailureException("Internal Error: Internal integrity error:"
                            + "Unexpected rows count in database affected: " + updateCount);
                }
                try (PreparedStatement st1 = conn.prepareStatement("DELETE FROM Category WHERE boardGameId = ?")) {
                    st1.setInt(1, boardGame.getId());
                    st1.executeUpdate();
                }
                insertIntoCategory(boardGame, conn);
            }
        } catch (SQLException ex) {
            String msg = "Error when updating board game in the db";
            logger.log(Level.SEVERE, msg, ex);
            throw new ServiceFailureException(msg, ex);
        }
    }

    @Override
    public void deleteBoardGame(BoardGame boardGame) {
        checkDataSource();
        validate(boardGame);
        if (boardGame.getId() == null) {
            throw new IllegalEntityException("board game id null");            
        }
        
        try (Connection conn = dataSource.getConnection()){
            try (PreparedStatement st1 = conn.prepareStatement("DELETE FROM Category WHERE boardGameId = ?")){
                st1.setInt(1, boardGame.getId());
                st1.executeUpdate();
                }
            try (PreparedStatement st2 = conn.prepareStatement(
                    "DELETE FROM BoardGame WHERE id = ?")){
                st2.setInt(1, boardGame.getId());
                int updateCount = st2.executeUpdate();
                if (updateCount == 0) {
                    throw new IllegalArgumentException("BoardGame " + boardGame + " does not exist in the db");
                }
                if (updateCount != 1) {
                    throw new ServiceFailureException("Internal Error: Internal integrity error:"
                            + "Unexpected rows count in database affected: " + updateCount);
                }
            }
        } catch (SQLException ex) {
            String msg = "Error when deleting board game from the db";
            logger.log(Level.SEVERE, msg, ex);
            throw new ServiceFailureException(msg, ex);
        }
    }
    
    private void retrieveFromCategory(BoardGame boardGame, final Connection conn) throws SQLException {
        try (PreparedStatement st1 = conn.prepareStatement(
                "SELECT boardGameId, category FROM category WHERE boardGameId = ?")){
            st1.setInt(1, boardGame.getId());
            boardGame.setCategory(executeQueryForCategory(st1));
        }
    }
    
    private void insertIntoCategory(BoardGame boardGame, final Connection conn) throws IllegalEntityException, ServiceFailureException, SQLException {
        for(String cat : boardGame.getCategory())
            try (PreparedStatement st = conn.prepareStatement(
                    "INSERT INTO Category (boardGameId, category) VALUES (?,?)")){
                st.setInt(1, boardGame.getId());
                st.setString(2, cat);
                int count = st.executeUpdate();
                DBUtils.checkUpdatesCount(count, boardGame, true);
            }
    }
    
    private static List<BoardGame> executeQueryForMultipleBoardGames(PreparedStatement st) throws SQLException {
        List<BoardGame> result = new ArrayList<>();
        try (ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                result.add(rowToBoardGame(rs));
            }
        }
        return result;
    }
    
    private static BoardGame executeQueryForSingleBoardGame(PreparedStatement st) throws SQLException, ServiceFailureException {
        try (ResultSet rs = st.executeQuery()){
            if (rs.next()) {
                BoardGame result = rowToBoardGame(rs);                
                if (rs.next()) {
                    throw new ServiceFailureException(
                            "Internal integrity error: more board games with the same id found!");
                }
                return result;
            } else return null;
        }
    }
    
    private static BoardGame rowToBoardGame(ResultSet rs) throws SQLException {
        BoardGame result = new BoardGame(rs.getString("name"), rs.getInt("maxPlayers"), 
                rs.getInt("minPlayers"), new HashSet<String>(), rs.getBigDecimal("pricePerDay"));
        result.setId(rs.getInt("id"));
        return result;
    }
    
    private static Set<String> executeQueryForCategory(PreparedStatement st) throws SQLException {
        try (ResultSet rs = st.executeQuery()){
            Set<String> categories = new HashSet<>();
            while (rs.next())
                categories.add(rs.getString("category"));
            return categories;
        }  
    }
    
    private static void validate(BoardGame boardGame) {
        if (boardGame == null) {
            throw new IllegalEntityException("board game is null");
        }
        if (boardGame.getName() == null) {
            throw new ValidationException("name is null");
        }
        if (boardGame.getName().equals("")) {
            throw new ValidationException("name is empty");
        }
        if (boardGame.getMaxPlayers() <= 0) {
            throw new ValidationException("maxPlayers is negative number");
        }
        if (boardGame.getMinPlayers() <= 0) {
            throw new ValidationException("minPlayers is negative number");
        }
        if (boardGame.getMinPlayers() > boardGame.getMaxPlayers()) {
            throw new ValidationException("minPlayers is greater than maxPlayers");
        }
        if (boardGame.getCategory() == null) {
            throw new ValidationException("category is null");
        }
        if (boardGame.getCategory().isEmpty()) {
            throw new ValidationException("category is empty");
        }
        if (boardGame.getCategory().contains(null)) {
            throw new ValidationException("category contains null");
        }
        if (boardGame.getPricePerDay() == null) {
            throw new ValidationException("pricePerDay is null");
        }
        if (boardGame.getPricePerDay().compareTo(new BigDecimal(0)) <= 0) {
            throw new ValidationException("pricePerDay is not positive number");
        }
    }
}