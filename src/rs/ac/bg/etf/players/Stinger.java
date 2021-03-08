package rs.ac.bg.etf.players;

public class Stinger extends Player
{
    @Override
    public Move getNextMove() {
        return Move.DONTPUTCOINS;
    }
}