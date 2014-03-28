/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//chichichi, jde to!!! :D:D:D
//tak jsem neco zmenil
package boardgamerental;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 *
 * @author Goolomb
 */
public class BoardGame {
    private Integer id;
    private String name;
    private int maxPlayers;
    private int minPlayers;
    private Set<String> category = new HashSet<>();
    private BigDecimal pricePerDay;

    public BoardGame() {
    
    }
    public BoardGame(String name, int maxPlayers, int minPlayers, Set<String> category, BigDecimal pricePerDay) {
        this.name = name;
        this.maxPlayers = maxPlayers;
        this.minPlayers = minPlayers;
        if(category != null)
            for(String elem : category)
                this.category.add(elem);
        this.pricePerDay = pricePerDay;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public int getMinPlayers() {
        return minPlayers;
    }

    public void setMinPlayers(int minPlayers) {
        this.minPlayers = minPlayers;
    }

    public Set<String> getCategory() {
        return category;
    }

    public void setCategory(Set<String> category) {
        for(String elem : category)
            this.category.add(elem);
    }

    public BigDecimal getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(BigDecimal pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) 
            return false;
        if (getClass() != obj.getClass())
            return false;
        final BoardGame other = (BoardGame) obj;
        return other.getId() == this.getId();
    }
    
    
}
