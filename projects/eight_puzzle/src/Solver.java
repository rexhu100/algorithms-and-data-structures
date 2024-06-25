import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.Deque;
import java.util.LinkedList;

/*
The project is under priority queue (binary heap), but I think PQ only plays a small role in solving the puzzle.
The Solver is essentially a BFS algorithm
 */

public class Solver {
    private boolean canSolve=false, canSolveTwin=false;
    private int minMoves=0;
    private SolverNode solOrig;
    private MinPQ<SolverNode> origQ, twinQ;

    private static class SolverNode implements Comparable<SolverNode>{
        public Board b;
        public SolverNode parent = null;
        public int nMoves=0;
        public int manDist;

        SolverNode(Board _b, int nm) {
            b = _b;
            nMoves = nm;
            manDist = b.manhattan();
        }

        int getPriority() {
            return manDist+nMoves;
        }

        public String toString() {
            return "Priority: "+getPriority()+ " "+b.toString();
        }

        public boolean equals(SolverNode o) {
            return b.equals(o.b);
        }

        @Override
        public int compareTo(SolverNode o) {
            return Integer.compare(getPriority(), o.getPriority());
        }
    }

    private void solveOneStep() {
        // Solve 1 step in original board
        SolverNode temp = origQ.delMin();
//        System.out.println(temp);
        int mvCtr=temp.nMoves;
//        System.out.println(temp.b);
//        System.out.println(temp.b.hamming());

        if (temp.manDist==0) {
            canSolve = true;
            solOrig = temp;
            return;
        }

        for (Board n: temp.b.neighbors()) {
//            System.out.println(n);
            SolverNode nextNode = new SolverNode(n, mvCtr+1);
            nextNode.parent = temp;
            if (temp.parent==null || !nextNode.equals(temp.parent)) {
                origQ.insert(nextNode);
            }
        }

        // Solve 1 step in twin board
        temp = twinQ.delMin();
//        System.out.println(temp.b);

        if (temp.manDist==0) {
            canSolveTwin = true;
            return;
        }

        for (Board n: temp.b.neighbors()) {
//            System.out.println(n);
            SolverNode nextNode = new SolverNode(n, mvCtr+1);
            nextNode.parent = temp;
            if (temp.parent==null || !nextNode.equals(temp.parent)) {
                twinQ.insert(nextNode);
            }
        }
    }


    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial==null) {
            throw new IllegalArgumentException("Board cannot be null!");
        }

        SolverNode origBoard = new SolverNode(initial, 0);
        SolverNode twinBoard = new SolverNode(initial.twin(), 0);
        origQ = new MinPQ<>();
        twinQ = new MinPQ<>();

        origQ.insert(origBoard);
        twinQ.insert(twinBoard);

        while (!canSolve && !canSolveTwin) {
            solveOneStep();
        }
//        for (int i=0; i<3; i++) {
//            System.out.println(origQ);
//            solveOneStep();
//        }

        if (canSolve) {
            minMoves = solOrig.nMoves;
        }
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return canSolve;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (isSolvable()) {
            return minMoves;
        } else {
            return -1;
        }
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable()) return null;

        // Construct the solution chain by retracing through the parent pointer. Using Deque to make sure the order is right
        Deque<Board> ret = new LinkedList<>();
        SolverNode cur = solOrig;
        while (cur != null) {
            ret.push(cur.b);
            cur = cur.parent;
        }

        return ret;
    }

    // test client
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

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