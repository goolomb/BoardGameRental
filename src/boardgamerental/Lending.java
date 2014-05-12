/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package boardgamerental;

import java.sql.Date;
import java.util.Objects;

/**
 *
 * @author Goolomb
 */
public class Lending {
    private Integer id;
    private Customer customer;
    private BoardGame boardGame;
    private Date startTime;
    private Date expectedEndTime;
    private Date realEndTime;

    public Lending(Customer customer, BoardGame boardGame, Date startTime, Date expectedEndTime) {
        this.customer = customer;
        this.boardGame = boardGame;
        this.startTime = startTime;
        this.expectedEndTime = expectedEndTime;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Lending other = (Lending) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "Lending{" + "id=" + id + ", customer=" + customer.getName() + ", boardGame=" + boardGame.getName() + '}';
    }
    
    public Lending() {
        
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public BoardGame getBoardGame() {
        return boardGame;
    }

    public void setBoardGame(BoardGame boardGame) {
        this.boardGame = boardGame;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getExpectedEndTime() {
        return expectedEndTime;
    }

    public void setExpectedEndTime(Date expectedEndTime) {
        this.expectedEndTime = expectedEndTime;
    }

    public Date getRealEndTime() {
        return realEndTime;
    }

    public void setRealEndTime(Date realEndTime) {
        this.realEndTime = realEndTime;
    }
    
}
