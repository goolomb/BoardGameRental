/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package boardgamerental;

import java.math.BigDecimal;
import java.util.List;


/**
 *
 * @author Goolomb
 */
public interface BoardGameManager {
    public void createBoardGame(BoardGame boardGame);
    
    BoardGame getBoardGameById(Integer id);
    
    List<BoardGame> findAllBoardGames();
    
    List<BoardGame> findBoardGameByName(String name);
    
    List<BoardGame> findBoardGameByPlayers(int players);
    
    List<BoardGame> findBoardGameByCategory(String category);
    
    List<BoardGame> findBoardGameByPricePerDay(BigDecimal pricePerDay);
    
    void updateBoardGame(BoardGame boardGame);
    
    void deleteBoardGame(BoardGame boardGame);
    
}
