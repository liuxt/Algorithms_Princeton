/*************************************************************************
	> File Name: RandomizedQueue.java
	> Author: liuxt
	> Mail: liuxt@mail.ustc.edu.cn
	> Created Time: Tue Aug  4 21:12:09 2015
 ************************************************************************/
import java.util.Iterator;
import java.util.NoSuchElementException;
class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] q;
    private int N = 0;
    public RandomizedQueue()                 // construct an empty randomized queue
    {
       q = (Item[]) new Object[2];
    }
    public boolean isEmpty()                 // is the queue empty?
    {
        return N == 0;
    }
    public int size()                        // return the number of items on the queue
    {
        return N;
    }
    public void enqueue(Item item)           // add the item
    {
        if(item == null) {
            throw new java.lang.NullPointerException("the item to enqueue is null");
        }
        if(N == q.length) {
            q.resize(2 * q.length);
        }
        q[N++] = item;
    }
    public Item dequeue()                    // remove and return a random item
    {
        if(isEmpty()){
            throw new java.util.NoSuchElementException("the RandomizedQueue is empty, can't dequeue");
        }
        int r = StdRandom.uniform(N);
        Item item = q[r];
        q[r] = q[N-1];
        q[--N] = null;
        if(N > 0 && N == q.length / 4) {
            resize(q.length / 2);
        }
        return item;
    }
    public Item sample()                     // return (but do not remove) a random item
    {
        if(isEmpty()) {
            throw new java.util.NoSuchElementException("the RandomizedQueue is empty, can't sample()");
        }
        int r = StdRandom.uniform(N);
        Item item = q[r];
        return item; 
    }
    private void resize(int capacity)
    {
        assert capacity >= N;
        Item[] temp = (Item[]) new Object[capacity];
        for(int i = 0;i < N;i++) {
            temp[i] = q[i];
        }
        q = temp;
    }
    public Iterator<Item> iterator()         // return an independent iterator over items in random order
    {
         
    }
    public static void main(String[] args)   // unit testing
    {

    }
}
