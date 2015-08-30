/*************************************************************************
	> File Name: PointSET.java
	> Author: liuxt
	> Mail: liuxt@mail.ustc.edu.cn
	> Created Time: Fri Aug 28 21:46:06 2015
 ************************************************************************/
import edu.princeton.cs.algs4.*;
import java.util.*;
public class PointSET
{
    private SET<Point2D> s;
    public         PointSET()                               // construct an empty set of points 
    {
        s = new SET<Point2D>();
    }
    public           boolean isEmpty()                      // is the set empty? 
    {
        return s.isEmpty();
    }
    public               int size()                         // number of points in the set 
    {
        return s.size();
    }
    public              void insert(Point2D p)              // add the point to the set (if it is not already in the set)
    {
        s.add(p);
    }
    public           boolean contains(Point2D p)            // does the set contain point p? 
    {
        return s.contains(p);
    }
    public              void draw()                         // draw all points to standard draw 
    {
        Iterator<Point2D> it = s.iterator();
        while(it.hasNext()){
            Point2D p = it.next();
            p.draw();
        }
    }
    public Iterable<Point2D> range(RectHV rect)             // all points that are inside the rectangle 
    {
        ArrayList<Point2D> arr = new ArrayList<Point2D>();
        Iterator<Point2D> it = s.iterator();
        while(it.hasNext()){
            Point2D p = it.next();
            if(rect.contains(p)){
                arr.add(p);
            }
        }
        return arr;
    }
    public           Point2D nearest(Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty 
    {
        Point2D nearest = new Point2D(100.0, 100.0);
        Iterator<Point2D> it = s.iterator();
        while(it.hasNext()){
            Point2D q = it.next();
            if(q.distanceSquaredTo(p) < nearest.distanceSquaredTo(p)){
                nearest = q;
            }
        }
        if(isEmpty()) return null;
        return nearest;
    }


    public static void main(String[] args)                  // unit testing of the methods (optional)
    {
        PointSET s = new PointSET();
        String filename = args[0];
        In in = new In(filename);
        while(!in.isEmpty()){
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            s.insert(p);
        }
        StdOut.println(s.nearest(new Point2D(.81, .31)));
    }
}

