package rs.ac.bg.etf.players;

public class Goody extends Player
{
    @Override
    public Move getNextMove() {
        return Move.PUT2COINS;
    }
}