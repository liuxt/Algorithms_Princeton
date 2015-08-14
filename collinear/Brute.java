/*************************************************************************
	> File Name: Brute.java
	> Author: liuxt
	> Mail: liuxt@mail.ustc.edu.cn
	> Created Time: Thu Aug 13 11:32:00 2015
 ************************************************************************/

import java.util.Comparator;
import java.util.Arrays;
public class Brute{
    public static void main(String[] args) {
        StdDraw.setXscale(0, 32768);                            // basic settings for draw method
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);
        StdDraw.setPenRadius(0.01);
        
        String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();                                   // number of the points
        Point[] p = new Point[N];                               // point array
        //draw all the points
        for(int i = 0; i < N; i++){
            int x = in.readInt();
            int y = in.readInt();
            p[i] = new Point(x, y);
            p[i].draw();
        }

        for(int i = 0; i < N; i++){
            for(int j = 0; j < N; j++){
                for(int k = 0; k < N; k++){
                    for(int l = 0; l < N; l++){
                        if(p[i].SLOPE_ORDER.compare(p[j], p[k]) == 0 && 
                                p[i].SLOPE_ORDER.compare(p[j], p[l]) == 0 ){ 
                            if(p[i].compareTo(p[j]) < 0 && p[j].compareTo(p[k]) < 0
                                    && p[k].compareTo(p[l]) < 0){
                                StdOut.println(p[i] + " -> " + p[j] + " -> " + p[k] + " -> " + p[l]); // standard output
                                Point[] temp = new Point[4];
                                temp[0] = p[i];
                                temp[1] = p[j];
                                temp[2] = p[k];
                                temp[3] = p[l];
                                Arrays.sort(temp, 0, 4);        // sort the array in natural order
                                temp[0].drawTo(temp[3]);        //draw from the beginning point and ending point as instructed
                                    }
                                }
                }
            }
        }
    }
        StdDraw.show(0);
        StdDraw.setPenRadius();
    }
    
}

