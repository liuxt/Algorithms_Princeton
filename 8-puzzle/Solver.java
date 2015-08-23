/*************************************************************************
	> File Name: Solver.java
	> Author: liuxt
	> Mail: liuxt@mail.ustc.edu.cn
	> Created Time: Sun Aug 23 09:01:08 2015
 ************************************************************************/
import java.util.*;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.MinPQ;
public class Solver {
    private class Node{                                             // inner class game tree Node
        private int moves;                                          // how many steps the Node in
        private int manhattan;                                      // manhattan value
        private int priority;                                       // the priority of the Node
        private Board b;                                            
        private Node pre;                                           // the parent of the Node
        public final Comparator<Node> order = new Node_order();     
        private int comareTo(Node that){
            int priority_p = this.priority;
            int priority_q = that.priority;
            if(priority_p < priority_q)     return -1;
            else if(priority_p == priority_q) return 0;
            else    return 1;
        }
        private class Node_order implements Comparator<Node> {
            public int compare(Node p, Node q){
                int priority_p = p.priority;
                int priority_q = q.priority;
                if(priority_p < priority_q)     return -1;
                else if(priority_p == priority_q) return 0;
                else    return 1;
            }
        }
        public Node(Board b, int moves, Node pre){
            this.moves = moves;
            manhattan = b.manhattan();
            priority = manhattan + moves;
            this.b = b;
            this.pre = pre;
        }
    }
    private MinPQ<Node> pq1;                                        // priority queue
    private MinPQ<Node> pq2;
    private Node n1;                                                // not use actually
    private Node n2;
    private Node res_Node;                                          // the result Node
    private int count;                                              // for test
    private int res;                                                // 0 means not know the result yet, 1 means found one solution, 2 means has no solution
    public Solver(Board initial)           // find a solution to the initial board (using the A* algorithm)
    {
       if(initial == null)  
           throw new java.lang.NullPointerException();
       Board twin = initial.twin();                                 // construct twin Node
       n1 = new Node(initial, 0, null);
       n2 = new Node(twin, 0, null);
       pq1 = new MinPQ<Node>(n1.order);
       pq2 = new MinPQ<Node>(n2.order);
       pq1.insert(n1);
       pq2.insert(n2);
       res = 0;
       count = 0;
       while(res == 0){
          //StdOut.println("Step:" + count);
          //print();
          Node pq1_out = pq1.delMin();
          //StdOut.println("node1 " + pq1_out.b);
          if(pq1_out.b.isGoal()){
              res = 1;
              res_Node = pq1_out;
              break;
          }
          count++;
          for(Board q: pq1_out.b.neighbors()){                      // insert the different neighbors
              //StdOut.println("neig1 " + q);
              if(pq1_out.pre == null || !q.equals(pq1_out.pre.b)){  // critical optimization
                  Node new_Node = new Node(q, pq1_out.moves + 1, pq1_out);
                  pq1.insert(new_Node);
              }
          }
          Node pq2_out = pq2.delMin();                              // same as the initial Node
          if(pq2_out.b.isGoal()){
              res = 2;
              break;
          }
          for(Board q: pq2_out.b.neighbors()){
              if(pq2_out.pre == null || !q.equals(pq2_out.pre.b)){
                  Node new_Node = new Node(q, pq2_out.moves+1, pq2_out);
                  pq2.insert(new_Node);
              }
          }

       }
    }
    public boolean isSolvable()            // is the initial board solvable?
    {
        return res == 1;
    }
    public int moves()                     // min number of moves to solve initial board; -1 if unsolvable
    {
        if(res != 1){
            return -1;
        }
        return res_Node.moves;
    }
    public Iterable<Board> solution()      // sequence of boards in a shortest solution; null if unsolvable
    {
        if(res != 1){
            return null;
        }
        ArrayDeque<Board> arr = new ArrayDeque<Board>();
        for(Node i = res_Node;i != null; i = i.pre)
        {
            Board b = i.b;
            arr.addFirst(b);
        }
        return arr;
    }
    private void print(){
        Iterator it = pq1.iterator();
        while(it.hasNext())
        {
            Node i = (Node)it.next();
            StdOut.println("priority: " + i.priority);
            StdOut.println("moves :" + i.moves);
            StdOut.println("manhattan" + i.manhattan);
            StdOut.println(i.b);
        }
    }
        public static void main(String[] args) // solve a slider puzzle (given below)
        {
            
        }

}
