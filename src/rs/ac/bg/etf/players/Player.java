package rs.ac.bg.etf.players;

import java.util.ArrayList;
import java.util.List;

public abstract class Player
{
    private static int sid;
    private int id;
    protected List<Move> opponentMoves;
    
    public Player() {
        this.id = ++Player.sid;
        this.opponentMoves = new ArrayList<Move>();
    }
    
    public void resetPlayerState() {
        this.opponentMoves.clear();
    }
    
    public void addOpponentMove(final Move move) {
        this.opponentMoves.add(move);
    }
    
    public String getName() {
        return String.valueOf(this.getClass().getSimpleName()) + this.id;
    }
    
    public abstract Move getNextMove();
    
    public enum Move
    {
        DONTPUTCOINS("DONTPUTCOINS", 0), 
        PUT1COIN("PUT1COIN", 1), 
        PUT2COINS("PUT2COINS", 2);
        
        private Move(final String name, final int ordinal) {
        }
    }
}