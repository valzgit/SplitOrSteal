package rs.ac.bg.etf.engine;

import rs.ac.bg.etf.domain.PayoffMatrix;
import java.util.Random;
import rs.ac.bg.etf.players.Player;

public class Match
{
    private Player player1;
    private Player player2;
    private int roundsCount;
    private int errorRate;
    private static Random random;
    
    static {
        Match.random = new Random();
    }
    
    public Match(final int roundsCount, final Player player1, final Player player2, final int errorRate) {
        this.roundsCount = roundsCount;
        this.player1 = player1;
        this.player2 = player2;
        this.errorRate = errorRate;
    }
    
    public int[] playMatch() {
        final int[] gains = new int[2];
        for (int i = 0; i < this.roundsCount; ++i) {
            Player.Move player1Move = this.player1.getNextMove();
            Player.Move player2Move = this.player2.getNextMove();
            if (Match.random.nextInt(101) < this.errorRate) {
                Player.Move errMove;
                do {
                    errMove = Player.Move.values()[Match.random.nextInt(3)];
                } while (player1Move == errMove);
                player1Move = errMove;
            }
            if (Match.random.nextInt(101) < this.errorRate) {
                Player.Move errMove;
                do {
                    errMove = Player.Move.values()[Match.random.nextInt(3)];
                } while (player2Move == errMove);
                player2Move = errMove;
            }
            this.player1.addOpponentMove(player2Move);
            this.player2.addOpponentMove(player1Move);
            final int[] array = gains;
            final int n = 0;
            array[n] += PayoffMatrix.matrix[player1Move.ordinal()][player2Move.ordinal()][0];
            final int[] array2 = gains;
            final int n2 = 1;
            array2[n2] += PayoffMatrix.matrix[player1Move.ordinal()][player2Move.ordinal()][1];
        }
        this.player1.resetPlayerState();
        this.player2.resetPlayerState();
        return gains;
    }
}