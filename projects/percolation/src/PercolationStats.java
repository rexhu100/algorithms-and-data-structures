import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final static double CONF_THRESH=1.96;
    private final double[] threshold;
    private final int trials;
    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new java.lang.IllegalArgumentException();
        }

        this.trials = trials;
        threshold = new double[trials];
        for (int i=0; i<trials; i++) {
//            System.out.println(i);
            Percolation curPerc = new Percolation(n);
            while (!curPerc.percolates()) {
                int curRow=StdRandom.uniform(1, n+1), curCol=StdRandom.uniform(1, n+1);

                while (curPerc.isOpen(curRow, curCol)) {
                    curRow=StdRandom.uniform(1, n+1);
                    curCol=StdRandom.uniform(1, n+1);
//                    System.out.printf("%s, %s\n", curRow, curCol);
                }
                curPerc.open(curRow, curCol);
            }
            threshold[i] = (double)curPerc.numberOfOpenSites()/(double)(n*n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(threshold);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(threshold);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean()-CONF_THRESH*stddev()/Math.sqrt(trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean()+CONF_THRESH*stddev()/Math.sqrt(trials);
    }

    // test client (see below)
    public static void main(String[] args) {
        int n=Integer.parseInt(args[0]), trials=Integer.parseInt(args[1]);
        PercolationStats exp = new PercolationStats(n, trials);
        System.out.printf("%-24s= %s\n", "mean", exp.mean());
        System.out.printf("%-24s= %s\n", "stddev", exp.stddev());
        System.out.printf("%-24s= [%s, %s]\n", "95% confidence interval", exp.confidenceLo(), exp.confidenceHi());
    }
}