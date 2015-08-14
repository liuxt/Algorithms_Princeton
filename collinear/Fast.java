/*************************************************************************
	> File Name: Fast.java
	> Author: 
	> Mail: 
	> Created Time: Thu Aug 13 12:13:43 2015
 ************************************************************************/
import java.util.Arrays;
import java.util.Comparator;
public class Fast {

    public static void main(String[] args){
        // basic settings for draw method 
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);
        StdDraw.setPenRadius(0.01);

        String filename = args[0];
        int count = 0;                                  
        In in = new In(filename);
        int N = in.readInt();
        Point[] p = new Point[N];
        for(int i = 0; i < N; i++){
            int x = in.readInt();
            int y = in.readInt();
            p[i] = new Point(x, y);
            p[i].draw();
        }
        Point[] temp = new Point[N];
        Arrays.sort(p, 0, N);                               // sort the array in the natural order 
        temp = Arrays.copyOf(p, N);                         // copy point array p to the array auxiliary temp
        for(int i = 0; i < N; i++){
            temp[i] = p[i];
            //StdOut.println("5: "+p[i]);                   // for test
        }
        for(int i = 0; i < N; i++){
            Point q = temp[i];
            //StdOut.println("1: " + q);                    // for test
            Arrays.sort(p, 0, N);
            Arrays.sort(p, 0, N, q.SLOPE_ORDER);
            /*for(int j = 0; j < N ; j++){
                StdOut.println("2: " + p[j] + " " + q.slopeTo(p[j])); // for test
            }*/
            for(int j = 0; j < N - 2; j+=(count+1)){
                 //       StdOut.println("3: " + q + " -> " + p[j] + " -> " + p[j+1] + " -> " + p[j+2]); // for test
                count = 0;                                  // number of the eaual points is count + 2
                for(int k = j; k < N - 1; k++){
                    if(q.SLOPE_ORDER.compare(p[k], p[k+1]) == 0){ // find equal pares
                        //if (q.compareTo(new Point(8000, 8000)) == 0) StdOut.println("count: " + count +" p[j] " + p[j] +" k: " + p[k] + p[k+1] + " slope " + q.slopeTo(p[k]) + " " + q.slopeTo(p[k+1]));                                                       // for test
                        count++;
                        if(k == N - 2) {                    // coner case
                            if(count >= 2){
                                if(q.compareTo(p[j]) <= 0){
                                    for(int l = 0; l < 2 + count;l++) { // standrad output
                                        if(l > 0 && l < 1 + count){
                                            StdOut.print(p[j + l - 1] + " -> ");
                                        }
                                        else if(l == count + 1){
                                            StdOut.print(p[j + l - 1] + "\n");
                                        }
                                        else    StdOut.print(q + " -> ");
                                    }
                                    q.drawTo(p[j + count]);
                                } 
                            }
                        }
                    }
                    else{                                   // if curPoint not equal with next point
                        if(count >= 2){                     // 4 or more points
                            if(q.compareTo(p[j]) <= 0){
                                for(int l = 0; l < 2 + count;l++) {
                                    if(l > 0 && l < 1 + count){
                                        StdOut.print(p[j + l - 1] + " -> ");
                                    }
                                    else if(l == count + 1){
                                        StdOut.print(p[j + l - 1] + "\n");
                                    }
                                    else    StdOut.print(q + " -> ");
                                }
                                q.drawTo(p[j + count]);
                                //StdOut.println("break: " + p[j] + " count: " + count);    // for test
                                break;
                            }
                            break;
                        }
                        else{
                            //StdOut.println("break2: " + p[j] + " count: " + count);       // for test
                            break;
                        }
                    }
                }
            }
        }
        StdDraw.show(0);
        StdDraw.setPenRadius();
    }
}

