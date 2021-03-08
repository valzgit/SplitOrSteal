package rs.ac.bg.etf.players;

public class Forgiver extends Player
{
    private Move myMove;
    private Move lastOppMove;
    
    public Forgiver() {
        this.myMove = Move.PUT1COIN;
        this.lastOppMove = null;
    }
    
    @Override
    public void resetPlayerState() {
        super.resetPlayerState();
        this.myMove = Move.PUT1COIN;
        this.lastOppMove = null;
    }
    
    @Override
    public Move getNextMove() {
        if (this.opponentMoves.size() > 0) {
            final Move oppMove = this.opponentMoves.get(this.opponentMoves.size() - 1);
            if (this.opponentMoves.size() > 1) {
                this.lastOppMove = this.opponentMoves.get(this.opponentMoves.size() - 2);
            }
            if (oppMove != Move.DONTPUTCOINS || this.lastOppMove == oppMove) {
                this.myMove = oppMove;
            }
        }
        return this.myMove;
    }
}