/*************************************************************************
 * Name:
 * Email:
 *
 * Compilation:  javac Point.java
 * Execution:
 * Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/

import java.util.Comparator;

public class Point implements Comparable<Point> {

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER = new SLOPE_ORDER();       // YOUR DEFINITION HERE

    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    private class SLOPE_ORDER implements Comparator<Point> {
        public int compare(Point p, Point q){
            double slope_p = slopeTo(p);
            double slope_q = slopeTo(q);
            if(slope_p < slope_q)   return -1;
            else if (slope_p == slope_q)    return 0;
            else return 1;
        }
    }
    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {
        /* YOUR CODE HERE */
        if(this.x == that.x && this.y == that.y)    return Double.NEGATIVE_INFINITY;
        else if(this.y == that.y)   return +0.0;
        else if(this.x == that.x)  return Double.POSITIVE_INFINITY;
        else{
            return (double)(that.y - this.y)/(double)(that.x - this.x);
        }
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
        /* YOUR CODE HERE */
        if(this.y < that.y || (this.y == that.y && this.x < that.x))    return -1;
        else if(this.y == that.y && this.x == that.x) return 0;
        else return 1; 
    }

    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    // unit test
    public static void main(String[] args) {
        /* YOUR CODE HERE */
        Point p = new Point(3,4);
        if(p.x < p.y) StdOut.println("ok");
    }
}
