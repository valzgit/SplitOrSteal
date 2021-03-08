package rs.ac.bg.etf.players;

public class Copycat extends Player
{
    @Override
    public Move getNextMove() {
        return (this.opponentMoves.size() == 0) ? Move.PUT1COIN : this.opponentMoves.get(this.opponentMoves.size() - 1);
    }
}