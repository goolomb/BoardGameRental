/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package boardgamerental;

import common.*;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;

/**
 *
 * @author Goolomb
 */
public class LendingManagerImpl implements LendingManager {
    private static final Logger logger = Logger.getLogger(
            LendingManagerImpl.class.getName());
    
    private BoardGameManager boardGameManager;
    private CustomerManager customerManager;

    private DataSource dataSource;

    
    public LendingManagerImpl() {
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    private void checkDataSource() {
        if (dataSource == null) {
            throw new IllegalStateException("DataSource is not set");
        }
    }
    
    public void createLending(Lending lending) {
        checkDataSource();
        validate(lending);
        if (lending.getId() != null) {
            throw new IllegalEntityException("lending id is already set");
        }
        
        BoardGameManagerImpl b = new BoardGameManagerImpl();
        b.setDataSource(dataSource);
        if(!this.isAvailable(b.getBoardGameById(lending.getBoardGame().getId()))) {
            throw new ValidationException("Cannot create lending, boardgame is already borrowed");
        }
        
        try (Connection conn = dataSource.getConnection()) {
            //conn.setAutoCommit(false);
            try (PreparedStatement st = conn.prepareStatement(
                    "INSERT INTO Lending (boardgameid, customerid, starttime, expectedendtime) VALUES (?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS)){
            st.setInt(1, lending.getBoardGame().getId());
            st.setInt(2, lending.getCustomer().getId());
            st.setDate(3, lending.getStartTime());
            st.setDate(4, lending.getExpectedEndTime());

            int count = st.executeUpdate();
            DBUtils.checkUpdatesCount(count, lending, true);

            Integer id = DBUtils.getId(st.getGeneratedKeys());
            lending.setId(id);
            }
            //conn.commit();
        } catch (SQLException ex) {
            String msg = "Error when inserting lending into db";
            logger.log(Level.SEVERE, msg, ex);
            throw new ServiceFailureException(msg, ex);
        }
    }

    public Lending getLendingById(Integer id) {
        checkDataSource();
        
        if (id == null) {
            throw new IllegalEntityException("id is null");
        }
        
        try (Connection conn = dataSource.getConnection()){
            try(PreparedStatement st = conn.prepareStatement(
                    "SELECT id, boardgameid, customerid, starttime, expectedendtime, realendtime FROM Lending WHERE id = ?")){
                st.setInt(1, id);
                
                return executeQueryForSingleLending(st);
            }
        } catch (SQLException ex) {
            String msg = "Error when getting lending with id = " + id + " from DB";
            logger.log(Level.SEVERE, msg, ex);
            throw new ServiceFailureException(msg, ex);
        }
    }

    public List<Lending> findAllLendings() {
        checkDataSource();
        try (Connection conn = dataSource.getConnection()){
            try (PreparedStatement st = conn.prepareStatement(
                    "SELECT id, boardgameid, customerid, starttime, expectedendtime, realendtime FROM Lending")){
            return executeQueryForMultipleLendings(st);
            }
        } catch (SQLException ex) {
            String msg = "Error when getting all lendings from DB";
            logger.log(Level.SEVERE, msg, ex);
            throw new ServiceFailureException(msg, ex);
        }
    }

    public List<Lending> findLendingsForCustomer(Customer customer) {
        checkDataSource();
        try (Connection conn = dataSource.getConnection()){
            try (PreparedStatement st = conn.prepareStatement(
                    "SELECT id, boardgameid, customerid, starttime, expectedendtime, realendtime FROM Lending WHERE customerid = ?")){
            st.setInt(1, customer.getId());
            return executeQueryForMultipleLendings(st);
            }
        } catch (SQLException ex) {
            String msg = "Error when getting lendings for customer from DB";
            logger.log(Level.SEVERE, msg, ex);
            throw new ServiceFailureException(msg, ex);
        }
    }

    public void updateLending(Lending lending) {
        checkDataSource();
        validate(lending);
        if (lending.getId() == null) {
            throw new IllegalEntityException("id is null");
        }        
        
        try (Connection conn = dataSource.getConnection()){
            try (PreparedStatement st = conn.prepareStatement(
                    "UPDATE Lending SET boardgameid = ?, customerid = ?, starttime = ?, expectedendtime = ?, realendtime = ? WHERE id = ?")) {
                st.setInt(1, lending.getBoardGame().getId());
                st.setInt(2, lending.getCustomer().getId());
                st.setDate(3, lending.getStartTime());
                st.setDate(4, lending.getExpectedEndTime());
                st.setDate(5, lending.getRealEndTime());
                st.setInt(6, lending.getId());
                
                int updateCount = st.executeUpdate();
                DBUtils.checkUpdatesCount(updateCount, lending, false);
            }
        }
        catch (SQLException ex) {
            String msg = "Error when updating lending";
            logger.log(Level.SEVERE, msg, ex);
            throw new ServiceFailureException(msg, ex);
        }
    }

    public void deleteLending(Lending lending) {
        checkDataSource();
        validate(lending);
        if (lending.getId() == null) {
            throw new IllegalEntityException("id is null");
        }
        
        try (Connection conn = dataSource.getConnection()){
            conn.setAutoCommit(false);
            try (PreparedStatement st = conn.prepareStatement(
                   "DELETE FROM lending WHERE id = ?")){
               st.setInt(1, lending.getId());
  
               int updateCount = st.executeUpdate();
               DBUtils.checkUpdatesCount(updateCount, lending, false);
               conn.commit();
            }
        }
        catch (SQLException ex) {
            String msg = "Error when deleting lending from DB";
            logger.log(Level.SEVERE, msg, ex);
            throw new ServiceFailureException(msg, ex);
        }
    }

    public BigDecimal calculateTotalPrice(Lending lending) {
        checkDataSource();
        validate(lending);
        if (lending.getId() == null) {
            throw new IllegalEntityException("id is null");
        }
        BoardGameManagerImpl b = new BoardGameManagerImpl();
        b.setDataSource(dataSource);
        if(b.getBoardGameById(lending.getBoardGame().getId()) == null)
            throw new ValidationException("boardgame not in DB");
        
        try (Connection conn = dataSource.getConnection()){
            try (PreparedStatement st = conn.prepareStatement(
                    "SELECT id, boardgameid FROM lending WHERE id = ?")){
                st.setInt(1, lending.getId());
                
                try (ResultSet rs = st.executeQuery()) {
                    if(!rs.next()) {
                        throw new ValidationException();
                    }

                    BoardGame game = b.getBoardGameById(rs.getInt("boardgameid"));
                    BigDecimal  perDay = game.getPricePerDay();
                    
                    Calendar countFromStart;
                    countFromStart = Calendar.getInstance();
                    countFromStart.setTime(lending.getStartTime());
                    int pastDays = 1;
                    int extraFee = 0;
                    
                    while(true) {
                        if(lending.getRealEndTime() == null) {
                            if(countFromStart.getTime().equals(lending.getExpectedEndTime())) {
                                return new BigDecimal(pastDays).multiply(perDay);
                            }
                        }
                        else if(countFromStart.getTime().after(lending.getExpectedEndTime())) {
                                extraFee += 50;
                             }
                        if(countFromStart.getTime().equals(lending.getRealEndTime())) {
                           return new BigDecimal(pastDays).multiply(perDay).add(new BigDecimal(extraFee));
                        }
                    countFromStart.add(Calendar.DATE, 1);
                    pastDays++;
                    }

                }
            }
        }
        catch (SQLException ex) {
            String msg = "Error when calculating total price of lenging " + lending + " from DB";
            logger.log(Level.SEVERE, msg, ex);
            throw new ServiceFailureException(msg, ex);
        }
    }

    public boolean isAvailable(BoardGame boardGame) {
        checkDataSource();
        
        if (boardGame == null) {
            throw new IllegalArgumentException("boardgame is null");
        }
        if (boardGame.getId() == null) {
            throw new IllegalEntityException("boardgame not in DB");
        }

        try (Connection conn = dataSource.getConnection()){
            try (PreparedStatement st = conn.prepareStatement(
                    "SELECT  boardgameid FROM lending WHERE boardgameid = ?")){
                st.setInt(1, boardGame.getId());
                
                try (ResultSet rs = st.executeQuery()) {
                    return !rs.next();
                }
            }
        }
        catch (SQLException ex) {
            String msg = "Error when asking if boardgame is available";
            logger.log(Level.SEVERE, msg, ex);
            throw new ServiceFailureException(msg, ex);
        }
    }

    private void validate(Lending lending) {
        if(lending == null) {
            throw new IllegalArgumentException("lending is null");
        }
        if (lending.getCustomer() == null) {
            throw new ValidationException("customer is null");
        }
        if (lending.getCustomer().getId() == null) {
            throw new ValidationException("customer not in DB");
        }
        if (lending.getBoardGame() == null) {
            throw new ValidationException("boardgame is null");
        }
        if (lending.getBoardGame().getId() == null) {
            throw new ValidationException("boardgame not in DB");
        }
        if (lending.getStartTime() == null) {
            throw new ValidationException("starttime is null");
        }
        if (lending.getExpectedEndTime() == null) {
            throw new ValidationException("expectedendtime is null");
        }
        if (lending.getStartTime().after(lending.getExpectedEndTime())) {
            throw new ValidationException("expected time is set to earlier date than start time");
        }
        if (lending.getBoardGame().getPricePerDay() == null) {
            throw new ValidationException("pricePerDay is null");
        }
        if (lending.getBoardGame().getPricePerDay().compareTo(new BigDecimal(0)) <= 0) {
            throw new ValidationException("pricePerDay is not positive number");
        }
        if (lending.getRealEndTime() != null) {
            if(lending.getStartTime().after(lending.getRealEndTime())) {
                throw new ValidationException("realtime before starttime");
            }
        }
    }

    private Lending executeQueryForSingleLending(PreparedStatement st) throws SQLException, ServiceFailureException {
        try (ResultSet rs = st.executeQuery()){
            if (rs.next()) {
                Lending result = rowToLending(rs);                
                if (rs.next()) {
                    throw new ServiceFailureException(
                            "Internal integrity error: more lendings with the same id found!");
                }
                return result;
            } else {
                return null;
            }
        }    
    }

    private Lending rowToLending(ResultSet rs) throws SQLException {
        Lending result = new Lending();
        BoardGameManagerImpl b = new BoardGameManagerImpl();
        CustomerManagerImpl c = new CustomerManagerImpl();
        b.setDataSource(dataSource);
        c.setDataSource(dataSource);
        result.setBoardGame(b.getBoardGameById(rs.getInt("boardgameid")));
        result.setCustomer(c.getCustomerById(rs.getInt("customerid")));
        result.setStartTime(rs.getDate("starttime"));
        result.setExpectedEndTime(rs.getDate("expectedendtime"));
        result.setRealEndTime(rs.getDate("realendtime"));
        result.setId(rs.getInt("id"));
        return result;
    }

    private List<Lending> executeQueryForMultipleLendings(PreparedStatement st) throws SQLException {
        List<Lending> result = new ArrayList<>();
        try (ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                result.add(rowToLending(rs));
            }
        }
        return result;
    }

    public void setBoardGameManager(BoardGameManager boardGameManager) {
        this.boardGameManager = boardGameManager;
    }

    public void setCustomerManager(CustomerManager customerManager) {
        this.customerManager = customerManager;
    }
}
