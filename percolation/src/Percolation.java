import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int[][] grid;
    private final int gridSize;
    private int nOpen=0;
    private final WeightedQuickUnionUF uf;
    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        grid = new int[n+1][n+1];
        gridSize = n+1;
        uf = new WeightedQuickUnionUF((n+1)*(n+1));
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row<1 || row>gridSize-1 || col<1 || col>gridSize-1) {
            throw new IllegalArgumentException();
        }
        if (grid[row][col] == 0) {
            grid[row][col] = 1;
            if (row-1>=1 && grid[row-1][col]==1) {
                uf.union(row*gridSize+col, (row-1)*gridSize+col);
            }
            if (row+1<gridSize && grid[row+1][col]==1) {
                uf.union(row*gridSize+col, (row+1)*gridSize+col);
            }
            if (col-1>=1 && grid[row][col-1]==1) {
                uf.union(row*gridSize+col, row*gridSize+col-1);
            }
            if (col+1<gridSize && grid[row][col+1]==1) {
                uf.union(row*gridSize+col, row*gridSize+col+1);
            }

            if (row==1) {
                uf.union(row*gridSize+col, 0);
            }

            if (row==gridSize-1) {
                uf.union(row*gridSize+col, gridSize-1);
            }

            nOpen++;
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
//        System.out.printf("%s, %s\n", row, col);
        if (row<1 || row>gridSize-1 || col<1 || col>gridSize-1) {
//            System.out.printf("%s, %s\n", row, col);
            throw new IllegalArgumentException(String.format("%s, %s\n", row, col));
        }
        return grid[row][col]==1;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row<1 || row>gridSize-1 || col<1 || col>gridSize-1) {
            throw new IllegalArgumentException();
        }
        return uf.find(row*gridSize+col) == uf.find(0);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return nOpen;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.find(gridSize-1) == uf.find(0);
    }
}
