package rs.ac.bg.etf.domain;

public final class PayoffMatrix
{
    public static final int[][][] matrix;
    
    static {
        matrix = new int[][][] { { new int[2], { 3, -1 }, { 7, -2 } }, { { -1, 3 }, { 2, 2 }, { 6, 1 } }, { { -2, 7 }, { 1, 6 }, { 5, 5 } } };
    }
}