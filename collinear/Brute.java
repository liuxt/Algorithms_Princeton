/*************************************************************************
	> File Name: Brute.java
	> Author: liuxt
	> Mail: liuxt@mail.ustc.edu.cn
	> Created Time: Thu Aug 13 11:32:00 2015
 ************************************************************************/

import java.util.Comparator;
import java.util.Arrays;
public class Brute{
    private static class order implements Comparator<Point>{
        public int compare(Point p, Point q){
            return p.compareTo(q);
        }
    }
    public static void main(String[] args) {
        Comparator<Point> order = new order();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);
        StdDraw.setPenRadius(0.01);
        
        String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();
        Point[] p = new Point[N];
        for(int i = 0; i < N; i++){
            int x = in.readInt();
            int y = in.readInt();
            p[i] = new Point(x, y);
            p[i].draw();

           // StdOut.println(p[i]);
        }
        for(int i = 0; i < N; i++){
            for(int j = 0; j < N; j++){
                for(int k = 0; k < N; k++){
                    for(int l = 0; l < N; l++){
                        //if(p[i].compareTo(p[j]) < 0 && p[j].compareTo(p[k]) < 0
                         //&& p[k].compareTo(p[l]) < 0){
                            if(p[i].SLOPE_ORDER.compare(p[j], p[k]) == 0 && 
                            p[i].SLOPE_ORDER.compare(p[j], p[l]) == 0 ){
                        if(p[i].compareTo(p[j]) < 0 && p[j].compareTo(p[k]) < 0
                         && p[k].compareTo(p[l]) < 0){

                                 StdOut.println(p[i] + " -> " + p[j] + " -> " + p[k] + " -> " + p[l]);
                                 Point[] temp = new Point[4];
                                 temp[0] = p[i];
                                 temp[1] = p[j];
                                 temp[2] = p[k];
                                 temp[3] = p[l];
                                 Arrays.sort(temp, 0, 4, order);
                                 temp[0].drawTo(temp[3]);
                                    }
                            }
                                //}
                    }
                }
            }
        }
        StdDraw.show(0);
        StdDraw.setPenRadius();
    }
    
}

