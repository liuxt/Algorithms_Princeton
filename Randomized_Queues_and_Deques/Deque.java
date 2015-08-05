/*************************************************************************
	> File Name: Deque.java
	> Author: liuxt
	> Mail: liuxt@mail.ustc.edu.cn
	> Created Time: Tue Aug  4 20:02:33 2015
 ************************************************************************/

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private int N;
    private Node first;
    private Node last;
    private class Node
    {
        private Item item;
        private Node next;
        private Node prev;
    }
    public Deque()                           // construct an empty deque
    {
       first = null;
       last = null;
       N = 0;
    }
    public boolean isEmpty()                 // is the deque empty?
    {
        return N == 0;
    }
    public int size()                        // return the number of items on the deque
    {
        return N;
    }
    public void addFirst(Item item)          // add the item to the front
    {
        if(item == null) {
            throw new java.lang.NullPointerException("the item to add to the begining is null");
        }
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;

        first.prev = null;
        if(isEmpty())   
        {
            last = first;
        }
        else    oldfirst.prev = first;
        N++;
    }
    public void addLast(Item item)           // add the item to the end
    {
        if(item == null) {
            throw new java.lang.NullPointerException("the item to add to the end is null");
        }
        Node oldlast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        last.prev = oldlast;
        if(isEmpty())   first = last;
        else    oldlast.next = last;
        N++;
    }
    public Item removeFirst()                // remove and return the item from the front
    {
        if(isEmpty()) {
            throw new java.util.NoSuchElementException("can't remove the first Node, the deque is empty");
        }
        Item item = first.item;
        if(N == 1) {
            first = null;
            last = null;
            N = 0;
        }
        else {
            first = first.next;
            first.prev = null;
            N--;
        }
        return item;
    }
    public Item removeLast()                 // remove and return the item from the end
    {
        if(isEmpty()) {
            throw new java.util.NoSuchElementException("can't remove the first Node, the deque is empty");
        }
        Item item = last.item;
        if(N == 1) {
            first = null;
            last = null;
            N = 0;
        }
        else {
            last = last.prev;
            last.next = null;
            N--;
        }
        return item;
    }
    public Iterator<Item> iterator()         // return an iterator over items in order from front to end
    {
        return new ListIterator();
    }
    private class ListIterator implements Iterator<Item>
    {
        private Node current = first;
        public boolean hasNext()
        {
            return current != null;
        }
        public void remove()
        {
            throw new UnsupportedOperationException();
        }
        public Item next() {
            if(!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;
            return item;
        }
    }
    public static void main(String[] args)   // unit testing
    {
        Deque<String> q = new Deque<String>();
        int count = 0;
        StdOut.println(count);
        while(!StdIn.isEmpty()){
            String item = StdIn.readString();
            q.addFirst(item);
            StdOut.println(count);
            count++;
        }
        while(!q.isEmpty()){
            String item = q.removeFirst();
            StdOut.println(item);
        }
        
    }

}
