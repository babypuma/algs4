import java.util.ArrayList;
import java.util.Iterator;

public class Solver {
    private MinPQ<BoardWrapper> pq;
    private int moves;
    private ArrayList<Board> lst;
    private boolean solavable;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        pq = new MinPQ<BoardWrapper>();
        moves = 0;
        lst = new ArrayList<Board>();
        solavable = true;

        pq.insert(new BoardWrapper(initial, 0));
        while (!pq.isEmpty()) {
            BoardWrapper bw = pq.delMin();
            moves = bw.moves();
            if (bw.isGoal()) {
                lst.add(bw.nut());
                break;
            }
            else if (bw.twin().isGoal()) {
                solavable = false;
                moves = -1;
                break;
            } 
            
            lst.add(bw.nut());
            Iterator<BoardWrapper> it = bw.neighbors().iterator();
            while (it.hasNext()) {
                bw = it.next();
                if (!lst.contains(bw.nut())) {
                    pq.insert(new BoardWrapper(bw.nut(), bw.moves()));
                }
            }
        }
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return solavable;
    }

    // min number of moves to solve initial board; -1 if no solution
    public int moves() {
        return moves;
    }

    // sequence of boards in a shortest solution; null if no solution 
    public Iterable<Board> solution() { 
        if (isSolvable()) {
            return lst;
        }

        return null;
    }

    private class BoardWrapper implements Comparable<BoardWrapper> {
        private Board board;
        private int moves;

        BoardWrapper(Board board, int moves) {
            this.board = board;
            this.moves = moves;
        }

        public int moves() {
            return moves;
        }

        public int dimension() {
            return board.dimension();
        }

        public boolean isGoal() {
            return board.isGoal();
        }
            
        public BoardWrapper twin() {
            return new BoardWrapper(board.twin(), moves + 1);
        }

        public boolean equals(Object y) {
            if (y == null) return false;
            if (y == this) return true;
            if (y instanceof BoardWrapper) {
                return board.equals(((BoardWrapper) y).nut());
            } 

            return false;
        }

        public Iterable<BoardWrapper> neighbors() {
            ArrayList<BoardWrapper> list = new ArrayList<BoardWrapper>();
            Iterator<Board> iter = board.neighbors().iterator();
            while (iter.hasNext()) {
                list.add(new BoardWrapper(iter.next(), moves + 1));
            }

            return list;
        }

        public String toString() {
            return board.toString();
        }

        private int priority() {
            return board.manhattan() + moves;
        }

        public int compareTo(BoardWrapper bw) {
            int p1 = this.priority();
            int p2 = bw.priority();
            if (p1 < p2) {
                return -1;
            }
            if (p1 > p2) {
                return 1;
            }
            return 0;
        }

        public Board nut() {
            return board;
        }
    }

    // solve a slider puzzle (given below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
