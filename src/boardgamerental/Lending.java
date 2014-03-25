/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package boardgamerental;

import java.util.Calendar;
import java.util.Objects;

/**
 *
 * @author Goolomb
 */
public class Lending {
    private Integer id;
    private Customer customer;
    private BoardGame boardGame;
    private Calendar startTime;
    private Calendar expectedEndTime;
    private Calendar realEndTime;

    public Lending(Integer id, Customer customer, BoardGame boardGame, Calendar startTime, Calendar expectedEndTime, Calendar realEndTime) {
        this.customer = customer;
        this.boardGame = boardGame;
        this.startTime = startTime;
        this.expectedEndTime = expectedEndTime;
        this.realEndTime = realEndTime;
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
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
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

    public Calendar getStartTime() {
        return startTime;
    }

    public void setStartTime(Calendar startTime) {
        this.startTime = startTime;
    }

    public Calendar getExpectedEndTime() {
        return expectedEndTime;
    }

    public void setExpectedEndTime(Calendar expectedEndTime) {
        this.expectedEndTime = expectedEndTime;
    }

    public Calendar getRealEndTime() {
        return realEndTime;
    }

    public void setRealEndTime(Calendar realEndTime) {
        this.realEndTime = realEndTime;
    }
    
}
