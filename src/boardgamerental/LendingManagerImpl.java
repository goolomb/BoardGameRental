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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
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
    
    private DataSource dataSource;

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
            conn.commit();
        } catch (SQLException ex) {
            String msg = "Error when inserting lending into db";
            logger.log(Level.SEVERE, msg, ex);
            throw new ServiceFailureException(msg, ex);
        }
    }

    public Lending getLendingById(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<Lending> findAllLendings() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<Lending> findLendingsForCustomer(Customer customer) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void updateLending(Lending lending) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void deleteLending(Lending lending) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public BigDecimal calculateTotalPrice(Lending lending) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean isAvailable(BoardGame boardGame) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void validate(Lending lending) {
    if (lending == null) {
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
    if (lending.getRealEndTime() != null) {
       throw new ValidationException("realendtime already set");
    }
    if (!lending.getStartTime().before(lending.getExpectedEndTime())) {
       throw new ValidationException("expected time is set to earlier date than start time");
    }
    if (lending.getBoardGame().getPricePerDay() == null) {
       throw new ValidationException("pricePerDay is null");
    }
    if (lending.getBoardGame().getPricePerDay().compareTo(new BigDecimal(0)) <= 0) {
       throw new ValidationException("pricePerDay is not positive number");
    }
//(boardGameNotInDB)
//(customerNotInDB)
    }
    
}
