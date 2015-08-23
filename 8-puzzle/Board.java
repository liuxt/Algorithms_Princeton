/*************************************************************************
	> File Name: Board.java
	> Author: liuxt
	> Mail: liuxt@mail.ustc.edu.cn
	> Created Time: Sat Aug 22 12:32:24 2015
 ************************************************************************/
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.*;
public class Board{
    private int dim;                             // number of dimension N
    private int[][] blocks;                      // the Board array, where blocks[i][j] = block in row i, column j
    public Board(int[][] blocks){                // construct a board from an N-by-N array of blocks
        dim = blocks[0].length;                 
        this.blocks = new int[dim][dim];
        for(int i = 0;i < dim; i++)              // traverse to copy the Board array 
            for(int j = 0; j < dim; j++)
                this.blocks[i][j] = blocks[i][j];
    }
    public int dimension()                 // board dimension N
    {
        return dim;
    }
    public int hamming()                   // number of blocks out of place
    {
        int count = 0;
        for(int i = 0; i < dim; i++){
            for(int j = 0; j < dim; j++){
                int correct_num = i * dim + j + 1;
                if(blocks[i][j] != 0 && blocks[i][j] != correct_num){       // if the current block is not in the right position
                    count++;
                }
            }
        }
        return count;
    }
    public int manhattan()                 // sum of Manhattan distances between blocks and goal
    {
        int dist = 0;
        for(int i = 0; i < dim; i++){
            for(int j = 0; j < dim; j++){
                int cur_num = blocks[i][j];
                if(cur_num != 0){
                    int correct_row = (cur_num - 1) / dim;          
                   // StdOut.println("(i,j)" + "(" + i + "," + j + ")" + " correct_row " + correct_row);
                    int correct_col = cur_num - correct_row * dim - 1;
                    //StdOut.println(" correct_col " + correct_col + "\n");
                    dist += Math.abs(i - correct_row) + Math.abs(j - correct_col);
                } 
            }
        }
        return dist;
    }
    public boolean isGoal()                // is this board the goal board?
    {
        for(int i = 0; i < dim; i++){
            for(int j = 0; j < dim ; j++){
                int correct_num = i * dim + j + 1;
                int cur_num = blocks[i][j];
                if(correct_num == cur_num){
                    continue;
                }
                else if(i == dim - 1 && j == dim - 1){
                    if(cur_num == 0)    return true;
                    else    return false;
                }
                else/*if(correct_num != cur_num)*/{
                    return false;
                }
            }
        }
        StdOut.println("cant return correct isGoal");
        return false;
    }
    public Board twin()                    // a board that is obtained by exchanging any pair of blocks
    {
        int[][] new_blocks = new int[dim][dim];
        for(int i = 0; i < dim; i++){
            for(int j = 0; j< dim; j++){
                new_blocks[i][j] = blocks[i][j];
            }
        }
        if(blocks[0][0] != 0 && blocks[0][1] != 0){         //swap (0,0) ,(0,1) or (1,0), (1,1)
            new_blocks[0][0] = blocks[0][1];
            new_blocks[0][1] = blocks[0][0];
            return new Board(new_blocks);
        }
        else{
            new_blocks[1][0] = blocks[1][1];
            new_blocks[1][1] = blocks[1][0];
            return new Board(new_blocks);
        }
    }
    public Iterable<Board> neighbors()     // all neighboring boards
    {
        ArrayList<Board> neig = new ArrayList<Board>();
        int[] rac = findZero();
        if(rac[0] == 0 && rac[1] == 0){
            int[][] temp= copy();
            int right = blocks[0][1];
            int down = blocks[1][0];
            temp[0][0] = right;
            temp[0][1] = 0;
            neig.add(new Board(temp));
            temp[0][0] = down;
            temp[0][1] = right;
            temp[1][0] = 0;
            neig.add(new Board(temp));
        }
        else if(rac[0] == 0 && rac[1] == dim - 1){
            int[][] temp= copy();
            int left = blocks[0][dim - 2];
            int down = blocks[1][dim - 1];
            temp[0][dim - 1] = left;
            temp[0][dim - 2] = 0;
            neig.add(new Board(temp));
            temp[0][dim - 1] = down;
            temp[0][dim - 2] = left;
            temp[1][dim - 1] = 0;
            neig.add(new Board(temp));
        }
        else if(rac[0] == dim - 1 && rac[1] == 0){
            int[][] temp = copy();
            int up = blocks[dim - 2][0];
            int right = blocks[dim - 1][1];
            temp[dim - 2][0] = 0;
            temp[dim - 1][0] = up;
            neig.add(new Board(temp));
            temp[dim - 2][0] = up;
            temp[dim - 1][0] = right;
            temp[dim - 1][1] = 0;
            neig.add(new Board(temp));
        }
        else if(rac[0] == dim - 1 && rac[1] == dim - 1){
            int[][] temp = copy();
            int up = blocks[dim - 2][dim - 1];
            int left = blocks[dim - 1][dim - 2];
            temp[dim - 1][dim - 1] = up;
            temp[dim - 2][dim - 1] = 0;
            neig.add(new Board(temp));
            temp[dim - 1][dim - 1] = left;
            temp[dim - 2][dim - 1] = up;
            temp[dim - 1][dim - 2] = 0;
            neig.add(new Board(temp));
        }
        else if(rac[0] == 0){
            int row = rac[0];
            int col = rac[1];
            int[][] temp = copy();
            int left = blocks[row][col - 1];
            int right = blocks[row][col + 1];
            int down = blocks[row + 1][col];
            temp[row][col - 1] = 0;
            temp[row][col] = left;
            Board a = new Board(temp);
            neig.add(a);
            temp[row][col - 1] = left;
            temp[row][col] = right;
            temp[row][col + 1] = 0;
            a = new Board(temp);
            neig.add(a);
            temp[row][col + 1] = right;
            temp[row][col] = down;
            temp[row + 1][col] = 0;
            a = new Board(temp);
            neig.add(a);
       }
        else if(rac[0] == dim - 1){
            int row = rac[0];
            int col = rac[1];
            int[][] temp;
            temp = copy();
            int left = blocks[row][col - 1];
            int right = blocks[row][col + 1];
            int up = blocks[row - 1][col];
            temp[row][col - 1] = 0;
            temp[row][col] = left;
            neig.add(new Board(temp));
            temp[row][col - 1] = left;
            temp[row][col] = right;
            temp[row][col + 1] = 0;
            neig.add(new Board(temp));
            temp[row][col + 1] = right;
            temp[row][col] = up;
            temp[row - 1][col] = 0;
            neig.add(new Board(temp));
        }
        else if (rac[1] == 0){
            int row = rac[0];
            int col = rac[1];
            int[][] temp = copy();
            int down = blocks[row + 1][col];
            int right = blocks[row][col + 1];
            int up = blocks[row - 1][col];
            temp[row - 1][col] = 0;
            temp[row][col] = up;
            neig.add(new Board(temp));
            temp[row - 1][col] = up;
            temp[row][col] = down;
            temp[row + 1][col] = 0;
            neig.add(new Board(temp));
            temp[row + 1][col] = down;
            temp[row][col] = right;
            temp[row][col + 1] = 0;
            neig.add(new Board(temp));
        }
        else if (rac[1] == dim - 1){
            int row = rac[0];
            int col = rac[1];
            int[][] temp = copy();
            int down = blocks[row + 1][col];
            int left = blocks[row][col - 1];
            int up = blocks[row - 1][col];
            temp[row - 1][col] = 0;
            temp[row][col] = up;
            neig.add(new Board(temp));
            temp[row - 1][col] = up;
            temp[row][col] = down;
            temp[row + 1][col] = 0;
            neig.add(new Board(temp));
            temp[row + 1][col] = down;
            temp[row][col] = left;
            temp[row][col - 1] = 0;
            neig.add(new Board(temp));
        }
        else{
            int row = rac[0];
            int col = rac[1];
            int[][] temp = copy();
            int down = blocks[row + 1][col];
            int left = blocks[row][col - 1];
            int up = blocks[row - 1][col];
            int right = blocks[row][col + 1];
            temp[row - 1][col] = 0;
            temp[row][col] = up;
            neig.add(new Board(temp));
            temp[row - 1][col] = up;
            temp[row][col] = down;
            temp[row + 1][col] = 0;
            neig.add(new Board(temp));
            temp[row + 1][col] = down;
            temp[row][col] = left;
            temp[row][col - 1] = 0;
            neig.add(new Board(temp));
            temp[row][col - 1] = left;
            temp[row][col] = right;
            temp[row][col + 1] = 0;
            neig.add(new Board(temp));
        }
        return neig;        
    }
    public boolean equals(Object y)        // does this board equal y?
    {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        if(that.dim != this.dim)    return false;
        for(int i = 0; i < dim; i++){
            for(int j = 0; j < dim;j++){
                if(this.blocks[i][j] != that.blocks[i][j]){
                    return false;
                }
            }
        }
        return true;
    }
    public String toString()               // string representation of this board (in the output format specified below)
    {
        StringBuilder s = new StringBuilder();
        s.append(dim + "\n");
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                s.append(String.format("%2d ", blocks[i][j]));

            }
            s.append("\n");
        }
        return s.toString();
    }




/*#################################### helper function #########################################*/
    private int[][] copy(){                 // copy the Board array
        int[][] new_blocks = new int[dim][dim];
        for(int i = 0; i < dim; i++){
            for(int j = 0; j< dim; j++){
                new_blocks[i][j] = blocks[i][j];
            }
        }
            return new_blocks;
    }
    private int[] findZero(){               // locate where the zero(blank square) is 
        int[] rac = new int[2];
         for(int i = 0; i < dim; i++){
            for(int j = 0; j< dim; j++){
                if(blocks[i][j] == 0){
                    rac[0] = i;
                    rac[1] = j;
                    return rac;
                }
            }
        }
        StdOut.println("cant find Zero!");
        return rac;
    }
    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        StdOut.println(initial);
        StdOut.println(initial.dimension());
        StdOut.println(initial.manhattan());
        StdOut.println(initial.hamming());
        StdOut.println(initial.isGoal());
        StdOut.println(initial.twin());
        Iterable<Board> a = initial.neighbors();
        for(Board aa: a){
            StdOut.println(aa);
        }

        ArrayList<Integer> b = new ArrayList<Integer>();
        b.add(1);
        b.add(2);
        b.add(3);
        for(int c : b){
            StdOut.println(c);
        }
        Stack<Board> arr = new Stack<Board>();
        int[][] temp = new int[2][2];
        temp[0][0] = 1;
        Board t = new Board(temp);
        arr.push(t);
         temp[0][0] = 2;
        Board t2  = new Board(temp);
        arr.push(t2);
        StdOut.println(arr.pop());
        StdOut.println(arr.pop());
        
        StdOut.println(t.equals(t2));
        /* solve the puzzle
        Solver solver = new Solver(initial);
        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
        */
    }
}
