package rs.ac.bg.etf.players;

public class Oscilator3 extends Player {
private int i=-1;
	 @Override
	    public Move getNextMove() {
		 i=(i+1)%2;
		 if(i%2==0)
	        return Move.PUT1COIN;
		 else return Move.DONTPUTCOINS;
	    }
}
