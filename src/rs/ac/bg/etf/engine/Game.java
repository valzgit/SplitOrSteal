package rs.ac.bg.etf.engine;

import java.util.Comparator;
import java.util.Collections;
import java.util.AbstractMap;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import rs.ac.bg.etf.players.Player;
import java.util.Map;
import java.util.ArrayList;

public class Game
{
    private static ArrayList<Map.Entry<Player, Integer>> playerPoints;
    private static int roundCount;
    private static int subCount;
    private static int addCount;
    private static int memorizeResults;
    private static int errorRate;
    
    static {
        Game.playerPoints = new ArrayList<Map.Entry<Player, Integer>>();
    }
    
    private Game() {
    }
    
    public static void main(final String[] args) {
        if (args.length < 2) {
            System.err.println("Not enough command line arguments (2 expected)");
        }
        try {
            Throwable t = null;
            try {
                final BufferedReader br = new BufferedReader(new FileReader(args[0]));
                try {
                    final PrintWriter pw = new PrintWriter(new FileWriter(args[1]));
                    try {
                        String[] line = br.readLine().split(",[ ]*");
                        Game.roundCount = Integer.parseInt(line[0]);
                        Game.subCount = Integer.parseInt(line[1]);
                        Game.addCount = Integer.parseInt(line[2]);
                        Game.memorizeResults = Integer.parseInt(line[3]);
                        Game.errorRate = Integer.parseInt(line[4]);
                        if (Game.subCount < Game.addCount) {
                            throw new Exception("subCount greater than addCount");
                        }
                        while (true) {
                            final String playerLine = br.readLine();
                            if (playerLine == null) {
                                break;
                            }
                            line = playerLine.split(",[ ]*");
                            for (int cnt = Integer.parseInt(line[1]), i = 0; i < cnt; ++i) {
                                final Player p3 = (Player)Class.forName("rs.ac.bg.etf.players." + line[0]).getConstructor((Class<?>[])new Class[0]).newInstance(new Object[0]);
                                Game.playerPoints.add(new AbstractMap.SimpleEntry<Player, Integer>(p3, 0));
                            }
                        }
                        final Comparator<Map.Entry<Player, Integer>> playerCmp = (p1, p2) -> -p1.getValue().compareTo(p2.getValue());
                        int game = 0;
                        for (int playerCount = Game.playerPoints.size(); playerCount >= Game.subCount; playerCount += Game.addCount) {
                            pw.println("********************** GAME " + ++game + " **********************");
                            int j = 0;
                            int cnt2 = 0;
                            while (j < playerCount - 1) {
                                final Player player1 = Game.playerPoints.get(j).getKey();
                                for (int k = j + 1; k < playerCount; ++k, ++cnt2) {
                                    final Player player2 = Game.playerPoints.get(k).getKey();
                                    final Match match = new Match(Game.roundCount, player1, player2, Game.errorRate);
                                    final int[] points = match.playMatch();
                                    pw.println("Match " + (cnt2 + 1) + " - " + "|" + player1.getName() + " : " + points[0] + "| VS |" + player2.getName() + " : " + points[1] + "|");
                                    Game.playerPoints.get(j).setValue(Game.playerPoints.get(j).getValue() + points[0]);
                                    Game.playerPoints.get(k).setValue(Game.playerPoints.get(k).getValue() + points[1]);
                                }
                                ++j;
                            }
                            pw.println("=================== TOTAL POINTS ===================");
                            Collections.sort(Game.playerPoints, playerCmp);
                            final ArrayList<Map.Entry<Player, Integer>> playersToRemove = new ArrayList<Map.Entry<Player, Integer>>();
                            for (int l = 0; l < playerCount; ++l) {
                                final Map.Entry<Player, Integer> data = Game.playerPoints.get(l);
                                if (l == 0 || l < playerCount - Game.subCount) {
                                    if (l < Game.addCount) {
                                        pw.print("+");
                                    }
                                    else {
                                        pw.print("=");
                                    }
                                }
                                else {
                                    playersToRemove.add(Game.playerPoints.get(l));
                                    pw.print("-");
                                }
                                pw.println(String.valueOf(data.getKey().getName()) + " : " + data.getValue());
                                if (Game.memorizeResults == 0) {
                                    data.setValue(0);
                                }
                            }
                            Game.playerPoints.removeAll(playersToRemove);
                            playerCount -= Game.subCount;
                            if (playerCount <= 1) {
                                break;
                            }
                            if (Game.subCount == Game.addCount) {
                                break;
                            }
                            for (int l = 0; l < Game.addCount; ++l) {
                                final Player p4 = (Player)Game.playerPoints.get(l).getKey().getClass().getConstructor((Class<?>[])new Class[0]).newInstance(new Object[0]);
                                Game.playerPoints.add(new AbstractMap.SimpleEntry<Player, Integer>(p4, 0));
                            }
                        }
                    }
                    finally {
                        if (pw != null) {
                            pw.close();
                        }
                    }
                    if (br != null) {
                        br.close();
                    }
                }
                finally {
                    if (t == null) {
                        final Throwable exception = null;
                        t = exception;
                    }
                    else {
                        final Throwable exception = null;
                        if (t != exception) {
                            t.addSuppressed(exception);
                        }
                    }
                    if (br != null) {
                        br.close();
                    }
                }
            }
            finally {
                if (t == null) {
                    final Throwable exception2 = null;
                    t = exception2;
                }
                else {
                    final Throwable exception2 = null;
                    if (t != exception2) {
                        t.addSuppressed(exception2);
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}