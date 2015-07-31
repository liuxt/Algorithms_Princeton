/*************************************************************************
	> File Name: PercolationStats.java
	> Author: liuxt
	> Mail: liuxt@mail.ustc.edu.cn
	> Created Time: Thu Jul 30 08:38:37 2015
 ************************************************************************/

public class PercolationStats {
    private double[] xt;                        //every percolation threshold
    private int perc_T;                         //total experiment times
    public PercolationStats(int N, int T){
        if(N <= 0 || T <= 0)
            throw new IllegalArgumentException("N " + N + " or T " + T + "is not positive");
        perc_T = T;
        xt = new double[T];
        for(int i = 0; i < T; i++){
            int count = 0;
            Percolation perc = new Percolation(N);
            while(!perc.percolates()){          //until the graph percolates
                int x = StdRandom.uniform(1, N+1);
                int y = StdRandom.uniform(1, N+1);
                if(!perc.isOpen(x, y)){
                perc.open(x, y);
                count++;
                }
            }
            xt[i] = (double)count/(N*N);        
        }    
    }     // perform T independent experiments on an N-by-N grid
    public double mean(){
        return StdStats.mean(xt);
    }                      // sample mean of percolation threshold
    public double stddev(){
        return StdStats.stddev(xt);
    }                    // sample standard deviation of percolation threshold
    public double confidenceLo(){
        return mean() - 1.96 * stddev() / Math.sqrt(perc_T);
    }              // low  endpoint of 95% confidence interval 
    public double confidenceHi(){
         return mean() + 1.96 * stddev() / Math.sqrt(perc_T);   
    }              // high endpoint of 95% confidence 
    public static void main(String[] args){
        int N = StdIn.readInt();
        int T = StdIn.readInt();
        PercolationStats st = new PercolationStats(N, T);
        StdOut.println(st.mean());
        StdOut.println(st.stddev());
    }    // test client (described below)

}
