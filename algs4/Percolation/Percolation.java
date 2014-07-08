/*************************************************************************
 * Name:  Jeremy Zhao
 * Email: 
 *
 * Compilation:  javac-algs4 Percolation.java
 * Execution:
 * Dependencies: WeightedQuickUnionUF.java
 *
 * Description: A data type for modelling a percolation system.
 *
 *************************************************************************/

public class Percolation { 
    private int base;
    private boolean [][] statuses;
    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF backwashUF;
    private int top;
    private int bottom;

    // create N-by-N grid, with all sites blocked
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException();
        }

        base = N;
        statuses = new boolean[base][base];

        // add two virtual sites
        uf = new WeightedQuickUnionUF(base*base + 2);
        backwashUF = new WeightedQuickUnionUF(base*base + 2);
        top = base*base;
        bottom = base*base + 1;
    }         
    
    // open site (row i, column j) if it is not already
    public void open(int i, int j) {
        if (i < 1 || i > base || j < 1 || j > base) {
            throw new IndexOutOfBoundsException();
        }

        int row = i - 1;
        int column = j - 1;
        if (!statuses[row][column]) {
            statuses[row][column] = true;
            int index = ufIndex(row, column);
            if (status(row, column - 1)) {
                uf.union(index, index - 1);
                backwashUF.union(index, index - 1);
            }
            if (status(row, column + 1)) {
                uf.union(index, index + 1);
                backwashUF.union(index, index + 1);
            }

            if (status(row - 1, column)) {
                uf.union(index, index - base);
                backwashUF.union(index, index - base);
            }
            else if (row == 0) {
                uf.union(top, index);
                backwashUF.union(top, index);
            }

            if (status(row + 1, column)) {
                uf.union(index, index + base);
                backwashUF.union(index, index + base);
            }
            else if (row == base - 1) {
                backwashUF.union(bottom, index);
            }
        }
    }

    // is site (row i, column j) open?
    public boolean isOpen(int i, int j) { 
        if (i < 1 || i > base || j < 1 || j > base) {
            throw new IndexOutOfBoundsException();
        }

        return status(i - 1, j - 1);
    }

    // is site (row i, column j) full?
    public boolean isFull(int i, int j) {
        if (i < 1 || i > base || j < 1 || j > base) {
            throw new IndexOutOfBoundsException();
        } 

        return uf.connected(ufIndex(i - 1, j - 1), top);
    }
    
    // does the system percolate?
    public boolean percolates() {
        return backwashUF.connected(top, bottom);
    }

    private boolean status(int i, int j) {
        if (i < 0 || i >= base || j < 0 || j >= base) {
            return false;
        }

        return statuses[i][j];
    }

    private int ufIndex(int i, int j) {
        return i * base + j;
    }

    public static void main(String[] args) {
        Percolation grid = new Percolation(3);
        grid.open(1, 1);
        System.out.println("T: " + grid.isOpen(1, 1));
        System.out.println("T: " + grid.isFull(1, 1));
        System.out.println("F: " + grid.percolates());

        grid.open(2, 2);
        System.out.println("T: " + grid.isOpen(2, 2));
        System.out.println("F: " + grid.isFull(2, 2));
        System.out.println("F: " + grid.percolates());

        grid.open(3, 3);
        System.out.println("T: " + grid.isOpen(3, 3));
        System.out.println("F: " + grid.isFull(3, 3));
        System.out.println("F: " + grid.percolates());

        grid.open(1, 2);
        System.out.println("T: " + grid.isOpen(1, 2));
        System.out.println("T: " + grid.isFull(1, 2));
        System.out.println("F: " + grid.percolates());

        grid.open(3, 2);
        System.out.println("T: " + grid.percolates());
    }
}
