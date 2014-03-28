/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package boardgamerental;

import cz.muni.fi.pv168.common.*;
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
    public void createBoardGame(BoardGame boardGame) {
        checkDataSource();
        validate(boardGame);
        if (boardGame.getId() != null) {
            throw new IllegalEntityException("board game id is already set");
        }        
        try (Connection conn = dataSource.getConnection()) {
            //conn.setAutoCommit(false);
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
            for(String cat : boardGame.getCategory())
                try (PreparedStatement st = conn.prepareStatement(
                    "INSERT INTO Category (boardGameId, category) VALUES (?,?)")){
                    st.setInt(1, boardGame.getId());
                    st.setString(2, cat);
                    int count = st.executeUpdate();
                    DBUtils.checkUpdatesCount(count, boardGame, true);
                }
            conn.commit();
        } catch (SQLException ex) {
            String msg = "Error when inserting board game into db";
            logger.log(Level.SEVERE, msg, ex);
            throw new ServiceFailureException(msg, ex);
        }
    }

    @Override
    public BoardGame getBoardGameById(Integer id) {
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
                    try (PreparedStatement st1 = conn.prepareStatement(
                            "SELECT boardGameId, category FROM category WHERE boardGameId = ?")){
                        st1.setInt(1, boardGame.getId());
                        boardGame.setCategory(executeQueryForCategory(st1));
                    }
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
                    try (PreparedStatement st1 = conn.prepareStatement(
                               "SELECT boardGameId, category FROM category WHERE boardGameId = ?")){
                           st1.setInt(1, boardGame.getId());
                           boardGame.setCategory(executeQueryForCategory(st1));
                    }
                }
                return boardGames;
            }
        } catch (SQLException ex) {
            String msg = "Error when getting all graves from DB";
            logger.log(Level.SEVERE, msg, ex);
            throw new cz.muni.fi.pv168.common.ServiceFailureException(msg, ex);
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
                    try (PreparedStatement st1 = conn.prepareStatement(
                            "SELECT boardGameId, category FROM category WHERE boardGameId = ?")){
                        st1.setInt(1, boardGame.getId());
                        boardGame.setCategory(executeQueryForCategory(st1));
                    }
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
                    try (PreparedStatement st1 = conn.prepareStatement(
                            "SELECT boardGameId, category FROM category WHERE boardGameId = ?")){
                        st1.setInt(1, boardGame.getId());
                        boardGame.setCategory(executeQueryForCategory(st1));
                    }
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
        
        if (category == "") {
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateBoardGame(BoardGame boardGame) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteBoardGame(BoardGame boardGame) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

    private static List<BoardGame> executeQueryForMultipleBoardGames(PreparedStatement st) throws SQLException {
        List<BoardGame> result = new ArrayList<>();
        try (ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                result.add(rowToBoardGame(rs));
            }
        }
        return result;
    }
    
    private static BoardGame rowToBoardGame(ResultSet rs) throws SQLException {
        BoardGame result = new BoardGame(rs.getString("name"), rs.getInt("maxPlayers"), 
                rs.getInt("minPlayers"), new HashSet<String>(), rs.getBigDecimal("pricePerDay"));
        result.setId(rs.getInt("id"));
        return result;
    }
    
    private static Set<String> executeQueryForCategory(PreparedStatement st) throws SQLException, ServiceFailureException {
        try (ResultSet rs = st.executeQuery()){
            return rowToCategory(rs);
        }
        
    }
    
    private static Set<String> rowToCategory(ResultSet rs) throws SQLException, ServiceFailureException {
        Set<String> categories = new HashSet<>();
        while (rs.next())
            categories.add(rs.getString("category"));
        return categories;
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
