/*************************************************************************
	> File Name: Fast.java
	> Author: 
	> Mail: 
	> Created Time: Thu Aug 13 12:13:43 2015
 ************************************************************************/
import java.util.Arrays;
import java.util.Comparator;
public class Fast {
    /*private static class order implements Comparator<Point>{
        public int compare(Point p, Point q){
            return p.compareTo(q);
        }
    }*/
    public static void main(String[] args){
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);
        StdDraw.setPenRadius(0.01);


      //  Comparator<Point> order = new order();
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
        Arrays.sort(p, 0, N); 
        temp = Arrays.copyOf(p, N);
        for(int i = 0; i < N; i++){
            temp[i] = p[i];
            //StdOut.println("5: "+p[i]);
        }
        for(int i = 0; i < N; i++){
            Point q = temp[i];
            //StdOut.println("1: " + q);
            Arrays.sort(p, 0, N);
            Arrays.sort(p, 0, N, q.SLOPE_ORDER);
            /*for(int j = 0; j < N ; j++){
                StdOut.println("2: " + p[j] + " " + q.slopeTo(p[j]));
            }*/
            for(int j = 0; j < N - 2; j+=(count+1)){
                //if(q.compareTo(p[j]) < 0 && q.compareTo(p[j+1]) < 0 && q.compareTo(p[j+2]) < 0){
                 //       StdOut.println("3: " + q + " -> " + p[j] + " -> " + p[j+1] + " -> " + p[j+2]);
                count = 0;
                for(int k = j; k < N - 1; k++){
                    if(q.SLOPE_ORDER.compare(p[k], p[k+1]) == 0){
                        //if (q.compareTo(new Point(8000, 8000)) == 0) StdOut.println("count: " + count +" p[j] " + p[j] +" k: " + p[k] + p[k+1] + " slope " + q.slopeTo(p[k]) + " " + q.slopeTo(p[k+1]));
                        count++;
                        if(k == N - 2) {
                        if(count >= 2){
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
                            } 
                        }
                        }
                    }
                    else{
                        if(count >= 2){
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
                            /*r[0] = q;
                            for(int l = 1; l < 2 + count;l++){
                                r[l] = p[j + l - 1];
                            //    StdOut.println("4: "+ r[l]);
                            }
                            Arrays.sort(r, 0, r.length);
                                //StdOut.println("3: " + "i: " + i + "j: " + j + "k: " + k);
                                for(int l = 0; l < r.length; l++){
                                    if(l == 0){
                                        StdOut.print(r[l] + " -> ");
                                    }
                                    else if(l == r.length - 1){
                                        StdOut.print(r[l] + "\n");
                                        r[0].drawTo(r[l]);
                                    }
                                    else StdOut.print(r[l] + " -> ");
                                }
                               */ 
                                //StdOut.println("break: " + p[j] + " count: " + count);
                                break;
                            }
                            break;
                        }
                        else{
                            //StdOut.println("break2: " + p[j] + " count: " + count);                           
                            break;
                        }
                    }
                }
                //}

                 
            }
        }
        StdDraw.show(0);
        StdDraw.setPenRadius();
    }
}

