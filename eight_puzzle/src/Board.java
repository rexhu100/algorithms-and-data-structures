import java.util.ArrayList;
import java.util.List;
import edu.princeton.cs.algs4.StdRandom;

public class Board {
    private final int[][] tiles;
    private final int sz;
    private int zeroRow;
    private int zeroCol;

    private void swap(int[][] m, int r0, int c0, int r1, int c1) {
        int temp = m[r0][c0];
        m[r0][c0] = m[r1][c1];
        m[r1][c1] = temp;
    }

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        this.sz = tiles.length;
        this.tiles = new int[sz][sz];
        for (int i=0; i<tiles.length; i++) {
            System.arraycopy(tiles[i], 0,  this.tiles[i], 0, sz);
        }

        for (int i=0; i<sz; i++) {
            for (int j=0; j<sz; j++) {
                if (tiles[i][j]==0) {
                    zeroRow = i;
                    zeroCol = j;
                    break;
                }
            }
        }
    }

    // string representation of this board
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(dimension());
        sb.append("\n");
        for (int i=0; i<sz; i++) {
            for (int j=0; j<sz; j++) {
                sb.append(String.format("%2s ", tiles[i][j]));
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    // board dimension n
    public int dimension() {
        return sz;
    }

    // number of tiles out of place
    public int hamming() {
        int res=0;
        for (int i=0; i<sz; i++) {
            for (int j=0; j<sz; j++) {
                if (tiles[i][j]!=0) {
                    int row = (tiles[i][j]-1)/sz, col = (tiles[i][j]-1)%sz;
                    res += row==i&&col==j?0:1;
                }
            }
        }

        return res;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int res=0;
        for (int i=0; i<sz; i++) {
            for (int j=0; j<sz; j++) {
                if (tiles[i][j]!=0) {
                    int row = (tiles[i][j]-1)/sz, col = (tiles[i][j]-1)%sz;
                    res += Math.abs(row-i) + Math.abs(col-j);
                }
            }
        }

        return res;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming()==0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y instanceof Board) {
            Board yBoard = (Board)y;
            return yBoard.toString().equals(this.toString());
        } else {
            return false;
        }
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        List<Board> nb = new ArrayList<>();
        if (zeroRow-1>=0) {
            swap(tiles, zeroRow, zeroCol, zeroRow-1, zeroCol);
            nb.add(new Board(tiles));
            swap(tiles, zeroRow, zeroCol, zeroRow-1, zeroCol);
        }
        if (zeroCol-1>=0) {
            swap(tiles, zeroRow, zeroCol, zeroRow, zeroCol-1);
            nb.add(new Board(tiles));
            swap(tiles, zeroRow, zeroCol, zeroRow, zeroCol-1);
        }
        if (zeroRow+1<sz) {
            swap(tiles, zeroRow, zeroCol, zeroRow+1, zeroCol);
            nb.add(new Board(tiles));
            swap(tiles, zeroRow, zeroCol, zeroRow+1, zeroCol);
        }
        if (zeroCol+1<sz) {
            swap(tiles, zeroRow, zeroCol, zeroRow, zeroCol+1);
            nb.add(new Board(tiles));
            swap(tiles, zeroRow, zeroCol, zeroRow, zeroCol+1);
        }

        return nb;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int zeroPos = zeroRow*dimension()+zeroCol;
        zeroPos = (zeroPos+1)%(dimension()*dimension());
        int r0=zeroPos/dimension(), c0=zeroPos%dimension();
//        System.out.printf("%s %s\n", r0, c0);


        zeroPos = (zeroPos+1)%(dimension()*dimension());
        int r1=zeroPos/dimension(), c1=zeroPos%dimension();
//        System.out.printf("%s %s\n", r1, c1);

        // Note that the empty (0) tile cannot be swapped.
        swap(tiles, r0, c0, r1, c1);
        Board res = new Board(tiles);
        swap(tiles, r0, c0, r1, c1);

        return res;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        int[][] boardArray = new int[3][3];
        boardArray[0][0] = 8;
        boardArray[0][1] = 1;
        boardArray[0][2] = 3;

        boardArray[1][0] = 4;
        boardArray[1][1] = 0;
        boardArray[1][2] = 2;

        boardArray[2][0] = 7;
        boardArray[2][1] = 6;
        boardArray[2][2] = 5;
        
        Board testBoard = new Board(boardArray);
        System.out.println(testBoard);
        System.out.println(testBoard.hamming());
        System.out.println(testBoard.manhattan());
        System.out.println(testBoard.isGoal());

        for (Board b: testBoard.neighbors()) {
            System.out.println(b);
        }

        System.out.println(testBoard);
        System.out.println(testBoard.twin());
        System.out.println(testBoard);

        boardArray[0][0] = 1;
        boardArray[0][1] = 2;
        boardArray[0][2] = 3;

        boardArray[1][0] = 4;
        boardArray[1][1] = 5;
        boardArray[1][2] = 6;

        boardArray[2][0] = 7;
        boardArray[2][1] = 8;
        boardArray[2][2] = 0;

        testBoard = new Board(boardArray);
        System.out.println(testBoard);
        System.out.println(testBoard.hamming());
        System.out.println(testBoard.manhattan());
        System.out.println(testBoard.isGoal());
    }

}