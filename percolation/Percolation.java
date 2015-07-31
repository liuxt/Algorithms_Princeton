/*************************************************************************
	> File Name: Percolation.java
	> Author: liuxt
	> Mail: liuxt@mail.ustc.edu.cn
	> Created Time: Wed Jul 29 18:18:54 2015
 ************************************************************************/
public class Percolation {
    private int[][] site;                   // 2D sites, site[i][j]==0 means this site is blocked while 1 means it's open
    private WeightedQuickUnionUF uf;        // data structure used for quickunion
    private int num;                        //space for WeightedQuickUnionUF uf
    private int rowNum;                     
    private int columnNum;
    public Percolation(int N) {
        if(N <= 0)                          //N needs to be no smaller than 0
            throw new IllegalArgumentException("N = " + N + " is not positive ");
        site = new int[N + 1][N + 1];
        rowNum = N;
        columnNum = N;
        num = (N + 1) * (N + 1);
        for(int i = 0; i < N + 1; i++) {
            for(int j = 0; j < N + 1; j++) {
                site[i][j] = 0;             // 0 means blocked, 1 means open
            }
        }
        uf = new WeightedQuickUnionUF(num); // initialize uf which has (N + 1)^2 elements from 0 to (N + 1)^2 - 1
        for(int i = 0;i < N;i++) {
            uf.union(i, N*N);               //virtual top site is connected to the top line(i = 1)
            uf.union(N*N - i - 1, N*N + 1); //virtual bottom site is connected to the bottom line(i = rowNum)
        }
    }                                       // create N-by-N grid, with all sites blocked
    private void validate(int i, int j) {
        if(!(i == 0 && j == 0 || i == 0 && j ==1))     //deal with 2 virtual sites (0, 0) and (0, 1), (0, 0) is the virtual
            if(i < 1 || i > rowNum || j < 1 || j > columnNum)//top site ,(0, 1) is the virtual bottom site
                throw new IndexOutOfBoundsException("index " + i + " is not between 1 and " + rowNum + " or index " + j + " is not between 1 and " + columnNum);
    }
    private int xyTo1D(int i, int j) {
        if(i == 0 && j ==0)     return columnNum * rowNum;  //virtual top site which is mapped to N*N
        else if(i == 0 && j == 1)   return columnNum * rowNum + 1;//virtual bottom site which is maped to N*N+1
        else return columnNum * (i - 1) + j - 1;//usual map
    }
    public void open(int i, int j) {
        validate(i, j);
        int index1D = xyTo1D(i, j);
        if(site[i][j] == 0){
           site[i][j] = 1;
           if(rowNum == 1) {                //deal with the circumstance that N == 1
               //do nothing
           }
           else if(i != 1 && i != rowNum && j != 1 && j != columnNum) {
               if(isOpen(i,j+1))    uf.union(index1D, index1D + 1);
               if(isOpen(i,j-1))    uf.union(index1D, index1D - 1);
               if(isOpen(i+1,j))    uf.union(index1D, index1D + columnNum);
               if(isOpen(i-1,j))    uf.union(index1D, index1D - columnNum);
           }
           else if(i == 1 && j != 1 && j != columnNum) {
               if(isOpen(i,j+1))    uf.union(index1D, index1D + 1);
               if(isOpen(i,j-1))    uf.union(index1D, index1D - 1);
               if(isOpen(i+1,j))    uf.union(index1D, index1D + columnNum);
           }
           else if(i == rowNum && j != 1 && j != columnNum) {
               if(isOpen(i,j+1))    uf.union(index1D, index1D + 1);
               if(isOpen(i,j-1))    uf.union(index1D, index1D - 1);
               if(isOpen(i-1,j))    uf.union(index1D, index1D - columnNum);
           }
           else if(i != 1 && i != rowNum && j == 1 ) {
               if(isOpen(i,j+1))    uf.union(index1D, index1D + 1);
               if(isOpen(i+1,j))    uf.union(index1D, index1D + columnNum);
               if(isOpen(i-1,j))    uf.union(index1D, index1D - columnNum);
           }
           else if(i != 1 && i != rowNum && j == columnNum) {
               if(isOpen(i,j-1))    uf.union(index1D, index1D - 1);
               if(isOpen(i+1,j))    uf.union(index1D, index1D + columnNum);
               if(isOpen(i-1,j))    uf.union(index1D, index1D - columnNum);
           }
           else if(i == 1 && j ==1) {
               if(isOpen(i,j+1))    uf.union(index1D, index1D + 1);
               if(isOpen(i+1,j))    uf.union(index1D, index1D + columnNum);
           }
           else if(i == 1 && j ==columnNum) {
               if(isOpen(i,j-1))    uf.union(index1D, index1D - 1);
               if(isOpen(i+1,j))    uf.union(index1D, index1D + columnNum);
           }           
           else if(i == rowNum && j ==1) {
               if(isOpen(i,j+1))    uf.union(index1D, index1D + 1);
               if(isOpen(i-1,j))    uf.union(index1D, index1D - columnNum);
           }
           else/*(i == rowNum && j ==columnNum)*/ {
               if(isOpen(i,j-1))    uf.union(index1D, index1D - 1);
               if(isOpen(i-1,j))    uf.union(index1D, index1D - columnNum);
           }
        }
    }          // open site (row i, column j) if it is not open already
    public boolean isOpen(int i, int j) {
        validate(i, j);
        return site[i][j] == 1;
    }     // is site (row i, column j) open?
    public boolean isFull(int i, int j) {
        validate(i, j);
        int index1D = xyTo1D(i, j);       
        if(uf.connected(rowNum * columnNum, index1D)) {
            if(i != 0 || j != 1) return isOpen(i, j);
            else return true;           //specail case when deal with isFull(0, 1)
        }
        else{
            return false;
        }
    }     // is site (row i, column j) full?
    public boolean percolates() {
        if(rowNum == 1)                 //deal with N == 1
            return isOpen(1, 1);
        else
            return isFull(0, 1);
    }             // does the system percolate?
    public static void main(String[] args) {
    }   // test client (optional))
}
