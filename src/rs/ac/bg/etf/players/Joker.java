package rs.ac.bg.etf.players;

import rs.ac.bg.etf.players.Player.Move;

public class Joker extends Player
{
    @Override
    public Move getNextMove() {
       double broj= Math.random()*100;
       if(broj<34)return Move.DONTPUTCOINS;
       else if(broj<60)return Move.PUT1COIN;
       else return Move.PUT2COINS;
       
    }
}