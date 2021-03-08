
package rs.ac.bg.etf.players;

public class Avenger extends Player
{
    private Move myMove;
    
    public Avenger() {
        this.myMove = Move.PUT2COINS;
    }
    
    @Override
    public void resetPlayerState() {
        super.resetPlayerState();
        this.myMove = Move.PUT2COINS;
    }
    
    @Override
    public Move getNextMove() {
        if (this.opponentMoves.size() > 0 && this.opponentMoves.get(this.opponentMoves.size() - 1).ordinal() < this.myMove.ordinal()) {
            this.myMove = this.opponentMoves.get(this.opponentMoves.size() - 1);
        }
        return this.myMove;
    }
}