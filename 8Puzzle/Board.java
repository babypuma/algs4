import java.util.Iterator;
import java.util.ArrayList;

public class Board {
    private int[][] board;
    private int dimension;
    private int total;
    private int blackRow;
    private int blankColumn;

    // construct a board from an N-by-N array of blocks 
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        dimension = blocks.length;
        total = dimension * dimension;
        board = new int[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                board[i][j] = blocks[i][j];
                if (board[i][j] == 0) {
                    blackRow = i;
                    blankColumn = j;
                }
            }
        }
    }

    // board dimension N 
    public int dimension() {
        return dimension;
    }

    // number of blocks out of place 
    public int hamming() {
        int sum = 0;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (board[i][j] != 0 
                    && board[i][j] != (i * dimension + j + 1) % total) {
                    sum++;
                }
            }
        }

        return sum;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int sum = 0;
        int realRow;
        int realColumn;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (board[i][j] == 0) {
                    continue;
                } 
                
                realRow = (board[i][j] - 1) / dimension; 
                realColumn = (board[i][j] - 1) % dimension;
                sum += abs(realRow - i) + abs(realColumn - j);
            }
        }

        return sum;
    }

    // is this board the goal board?
    public boolean isGoal() {
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (board[i][j] != (i * dimension + j + 1) % total) {
                    return false;
                }
            }
        }

        return true;
    }

    // a board obtained by exchanging two adjacent blocks in the same row
    public Board twin() { 
        int[][] rep = replication();
        int tmp;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (board[i][j] != 0 && board[i][j] != i * dimension + j + 1) { 
                    int m;
                    if (j < dimension - 1) {
                        m = j + 1;
                    }
                    else {
                        m = j - 1;
                    } 
                    
                    if (rep[i][m] == 0) { 
                        continue; 
                    }
                    
                    tmp = rep[i][m];
                    rep[i][m] = rep[i][j]; 
                    rep[i][j] = tmp;

                    return new Board(rep);
                }
            }
        } 

        tmp = rep[0][1];
        rep[0][1] = rep[0][0];
        rep[0][0] = tmp;
        return new Board(rep);
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null) return false;
        if (y == this) return true;
        if (y instanceof Board) { 
            Board z = (Board) y;
            if (this.dimension == z.dimension) {
                for (int i = 0; i < dimension; i++) {
                    for (int j = 0; j < dimension; j++) {
                        if (this.board[i][j] != z.board[i][j]) {
                            return false;
                        }
                    }
                }

                return true;
            }
        }

        return false;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        ArrayList<Board> list = new ArrayList<Board>();
        if (blackRow > 0) {
            int[][] rep = replication();
            rep[blackRow][blankColumn] = rep[blackRow - 1][blankColumn];
            rep[blackRow - 1][blankColumn] = 0;
            list.add(new Board(rep));
        }
        if (blankColumn < dimension - 1) {
            int[][] rep = replication();
            rep[blackRow][blankColumn] = rep[blackRow][blankColumn + 1];
            rep[blackRow][blankColumn + 1] = 0;
            list.add(new Board(rep));
        }
        if (blackRow < dimension - 1) {
            int[][] rep = replication();
            rep[blackRow][blankColumn] = rep[blackRow + 1][blankColumn];
            rep[blackRow + 1][blankColumn] = 0;
            list.add(new Board(rep));
        }
        if (blankColumn > 0) {
            int[][] rep = replication();
            rep[blackRow][blankColumn] = rep[blackRow][blankColumn - 1];
            rep[blackRow][blankColumn - 1] = 0;
            list.add(new Board(rep));
        }

        return list;
    }

    // string representation of the board (in the output format specified below)
    public String toString() {
        StringBuffer strBuf = new StringBuffer();
        strBuf.append(dimension);
        strBuf.append('\n');
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                strBuf.append(' ');
                strBuf.append(board[i][j]);
            } 
            strBuf.append('\n');
        }
        return strBuf.toString();
    }

    private int[][] replication() {
        int [][] rep = new int[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                rep[i][j] = board[i][j];
            }
        }

        return rep;
    }

    private int abs(int num) {
        if (num >= 0) {
            return num; 
        }
        else {
            return -num;
        }
    }

    public static void main(String[] args) {
        int d = 3;
        int[][] data = new int[d][d];
        data[0][0] = 1;
        data[0][1] = 2;
        data[0][2] = 3;
        data[1][0] = 4;
        data[1][1] = 0;
        data[1][2] = 6;
        data[2][0] = 7;
        data[2][1] = 8;
        data[2][2] = 5;
        Board b = new Board(data);

        System.out.println(b.toString()); 
        System.out.println("Hamming priority is " + b.hamming());
        System.out.println("Manhattan priority is " + b.manhattan()); 
        System.out.println("Is it goal? " + b.isGoal());
        Iterator<Board> it = b.neighbors().iterator(); 
        while (it.hasNext()) {
            Board tmp = it.next();
            System.out.println(tmp.toString());
            System.out.println("Hamming priority is " + tmp.hamming());
            System.out.println("Manhattan priority is " + tmp.manhattan());
            System.out.println("Is it goal? " + tmp.isGoal());
        }

        for (int i = 0; i < d; i++) {
            for (int j = 0; j < d; j++) {
                data[i][j] = (i * d + j + 1) % (d * d);
            }
        }
        b = new Board(data);
        System.out.println(b.toString());
        System.out.println("Hamming priority is " + b.hamming());
        System.out.println("Manhattan priority is " + b.manhattan());
        System.out.println("Is it goal? " + b.isGoal());
        it = b.neighbors().iterator(); 
        while (it.hasNext()) {
            Board tmp = it.next();
            System.out.println(tmp.toString());
            System.out.println("Hamming priority is " + tmp.hamming());
            System.out.println("Manhattan priority is " + tmp.manhattan());
            System.out.println("Is it goal? " + tmp.isGoal());
        }
    }
}
