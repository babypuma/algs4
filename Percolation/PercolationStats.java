/*************************************************************************
 * Name:    Jeremy Zhao
 * Email:
 *
 * Compilation:  javac-algs4 Percolation.java PercolationStats.java
 * Execution:
 * Dependencies: Percolation.java
 *
 * Description: A data type for performing a series of computational
 *              experiments by Monte Carlo simulation.
 *
 *************************************************************************/

public class PercolationStats {
    private double[] percolationThreshold;
    private int experimentTimes;
    private int gridBase;

    // perform T independent computational experiments on an N-by-N grid
    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException();
        }
        gridBase = N;
        experimentTimes = T;

        percolationThreshold = new double[experimentTimes];
        for (int i = 0; i < experimentTimes; i++) {
            Percolation grid = new Percolation(gridBase);
            int openSitesNum = 0;
            while (!grid.percolates()) {
                int rowNum = (int) (Math.random() * gridBase) + 1;
                int colNum = (int) (Math.random() * gridBase) + 1;
                if (!grid.isOpen(rowNum, colNum)) {
                    grid.open(rowNum, colNum);
                    openSitesNum++;
                }
            }
            percolationThreshold[i] =  
                (double) openSitesNum / (gridBase * gridBase);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        double sum = 0.0;
        for (int i = 0; i < experimentTimes; i++) {
            sum += percolationThreshold[i];
        }

        return sum / experimentTimes;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        double sampleMean = mean();
        double sum = 0.0;
        for (int i = 0; i < experimentTimes; i++) {
            sum += (percolationThreshold[i] - sampleMean)
                   * (percolationThreshold[i] - sampleMean);
        }

        return Math.sqrt(sum / (experimentTimes - 1));
    }

    // returns lower bound of the 95% confidence interval
    public double confidenceLo() {
        return mean() - 1.96 * stddev() / Math.sqrt(experimentTimes);
    }

    // returns upper bound of the 95% confidence interval
    public double confidenceHi() {
        return mean() + 1.96 * stddev() / Math.sqrt(experimentTimes);
    }

    // test client, described below
    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats experiment = new PercolationStats(N, T);

        System.out.println("mean = " + experiment.mean());
        System.out.println("stddev = " + experiment.stddev());
        System.out.println("95% confidence interval = " 
                          + experiment.confidenceLo() + ", " 
                          + experiment.confidenceHi());
    }
}
