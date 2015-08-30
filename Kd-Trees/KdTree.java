/*************************************************************************
	> File Name: KdTree.java
	> Author: liuxt
	> Mail: liuxt@mail.ustc.edu.cn
	> Created Time: Sat Aug 29 09:17:04 2015
 ************************************************************************/
import java.util.*;
import edu.princeton.cs.algs4.*;
public class KdTree
{
    private Node root;
    private static class Node{
        private Point2D p;
        private RectHV rect;
        private int size;
        private int vec;                            // -1 means horizontal, 1 means vertical
        private Node lb;                            // left and bottom
        private Node rt;                            // right and top
        public Node(Point2D p, RectHV rect, int size, int vec)
        {
            this.p = p;
            this.rect = rect;
            this.size = size;
            this.vec = vec;
        }
    }
    public KdTree()
    {
        // do nothing
    }
    public           boolean isEmpty()                      // is the set empty? 
    {
        return size() == 0;
    }
    public               int size()                         // number of points in the set
    {
        return size(root);
    }
    public              void insert(Point2D p)              // add the point to the set (if it is not already in the set)
    {
        root = put(root, p, -1, null, null);
    }
    public void draw(){
        draw(root);
    }
    public boolean contains(Point2D p){
        return get(root, p) != null;
    }
    public Iterable<Point2D> range(RectHV rect)             // all points that are inside the rectangle 
    {
        ArrayList<Point2D> arr = new ArrayList<Point2D>();
        return collect(root, rect, arr);
    }
    public           Point2D nearest(Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty
    {
        double min = 100.0;
        Point2D nearest = new Point2D(100.0, 100.0);
        if(isEmpty())   return null;
        return findNearest(root, p, nearest, min);
    }
    private Point2D findNearest(Node x, Point2D p, Point2D nearest, double min){
        //StdOut.println("check###############");
        //print(x);
        //StdOut.println("min: " + min);
        //StdOut.println("nearest point: " + nearest);
        if(x == null){
            return nearest;
        }
        else{
            double dist = p.distanceSquaredTo(x.p);
            double rect_dist = x.rect.distanceSquaredTo(p);
            min = nearest.distanceSquaredTo(p);
        //    StdOut.println("rect_dist: " + rect_dist);
            if(min >= rect_dist){
                if(dist < min){
                nearest = x.p;
                min = dist;
                }
                if(x.lb!= null && x.lb.rect.contains(p)){
         //           StdOut.println("left first");
                    nearest = findNearest(x.lb, p, nearest, min);
                    nearest = findNearest(x.rt, p, nearest, min);
                }
                else{ 
          //          StdOut.println("right first");
                    nearest = findNearest(x.rt, p, nearest, min);
                    nearest = findNearest(x.lb, p, nearest, min);
                }
            }
            else{
                return nearest;
            }
            //StdOut.println("wrong!");
            return nearest;
        }
    }
    private ArrayList<Point2D> collect(Node x, RectHV rect, ArrayList<Point2D> a){
        //StdOut.println("loop++++++++++");
        //print(x);
        if(x == null){
            return a;
        }
        else{
            ArrayList<Point2D> arr;
            if(rect.contains(x.p)){
                //StdOut.println("1: ");
                //print(x);
                arr = a;
                arr.add(x.p);
                arr = collect(x.lb, rect, arr);
                arr = collect(x.rt, rect, arr);
                return arr;
            }
            else if(rect.intersects(x.rect)){
                arr = a;
                arr = collect(x.lb, rect, arr);
                arr = collect(x.rt, rect, arr);
                return arr;
            }
            else{
                return a;
            }
        }
    }
    private Node get(Node x, Point2D p){
        Node res;
        if(x == null)   return null;
        else{
            int cmp;
            if(x.vec == 1){
                cmp = compare(p.x(), x.p.x());
            }
            else{
                cmp = compare(p.y(), x.p.y());
            }
            if(cmp < 0){
                res = get(x.lb, p);
            }
            else if(cmp >= 0 && !p.equals(x.p)){
                res = get(x.rt, p);
            }
            else {
                //StdOut.println("get:");
                //print(x);
                return x;
            }
        }
        return res;
    }
    private void draw(Node node){
        if(node == null){
            return;
        }
        else{
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(.01);
            node.p.draw();
            if(node.vec == 1){
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.line(node.p.x(), node.rect.ymin(), node.p.x(), node.rect.ymax());
            }
            else{
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.line(node.rect.xmin(), node.p.y(), node.rect.xmax(), node.p.y());
            }
            draw(node.lb);
            draw(node.rt);
        } 
    }
    private int size(Node x){
        if(x == null)   return 0;
        else return x.size;
    }
    private Node put(Node node, Point2D p, int flag_pre, RectHV rect_pre, Point2D p_pre)
    {
        if(node == null){
            if(flag_pre == -1){
                RectHV r = new RectHV(0.0, 0.0, 1.0, 1.0);
                return new Node(p, r, 1, 1);
            }
            else if(flag_pre == 0){
                RectHV r = new RectHV(rect_pre.xmin(), rect_pre.ymin(), p_pre.x(), rect_pre.ymax());
                return new Node(p, r, 1, -1);
            }
            else if(flag_pre == 1){
                RectHV r = new RectHV(p_pre.x(), rect_pre.ymin(), rect_pre.xmax(), rect_pre.ymax());
                return new Node(p, r, 1, -1);
            }
            else if(flag_pre == 2){
                RectHV r = new RectHV(rect_pre.xmin(), rect_pre.ymin(), rect_pre.xmax(), p_pre.y());
                return new Node(p, r, 1, 1);
            }
            else /*if(flag_pre == 3)*/{
                RectHV r = new RectHV(rect_pre.xmin(), p_pre.y(), rect_pre.xmax(), rect_pre.ymax());
                return new Node(p, r, 1, 1);
            }
        }
        int cmp;
        if(node.vec == 1){
            cmp = compare(p.x(), node.p.x());        
            if(cmp < 0){//left
                node.lb = put(node.lb, p, 0, node.rect, node.p);
            }
            else if(cmp >= 0 && !p.equals(node.p)){//right
                node.rt =  put(node.rt, p, 1, node.rect, node.p);
            }
            else{
                return node;
            }
        }
        else{
            cmp = compare(p.y(), node.p.y());
            if(cmp < 0){//bottom
                node.lb = put(node.lb, p, 2, node.rect, node.p);
            }
            else if(cmp >= 0 && !p.equals(node.p)){//top
                node.rt = put(node.rt, p, 3, node.rect, node.p);
            }
            else{
                return node;
            }
        }
        node.size = 1 + size(node.lb) + size(node.rt);
        return node;

    }
    private void print(Node x){
        if(x == null) StdOut.println("null");
        else{
            StdOut.println("rect: " + x.rect);
            StdOut.println("point: " + x.p);
            StdOut.println("vec: " + x.vec);
            StdOut.println("size: " + x.size);
        }
    }
    private void print_all(){
        print_all(root);
    }
    private void print_all(Node x){
        if(x == null) {
            return;
        }
        else{
            print(x);
            print_all(x.lb);
            print_all(x.rt);
        }
    }
    private int compare(double a, double b){
        if(a < b)   return -1;
        else if(a > b)  return 1;
        else return 0;
    } 
    public static void main(String[] args){
        String Filename = args[0];
        In in = new In(Filename);
        KdTree t = new KdTree();
        while(!in.isEmpty()){
            double x = in.readDouble();
            double y = in.readDouble();
            t.insert(new Point2D(x, y));
            StdOut.println(t.contains(new Point2D(x, y)));           
        }
        t.print_all();
        StdOut.println(t.contains(new Point2D(0.5, 0.8)));
        /*while(true){
            StdDraw.clear();
            t.draw();
            StdDraw.show(50);
        }*/
        Iterable<Point2D> it = t.range(new RectHV(0.0, 0.0, 0.5, 0.5));
        for(Point2D p: it){
            StdOut.println(p);
        }
        Point2D nearest = t.nearest(new Point2D(0.81, 0.30));
        StdOut.println("nearest: "+nearest);
    }



}

