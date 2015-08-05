/*************************************************************************
	> File Name: Subset.java
	> Author: liuxt
	> Mail: liuxt@mail.ustc.edu.cn
	> Created Time: Wed Aug  5 09:13:39 2015
 ************************************************************************/

public class Subset {
    public static void main(String[] args)
    {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> q = new RandomizedQueue();
        while(!StdIn.isEmpty())
        {
            String item = StdIn.readString();
            q.enqueue(item);
        }
        for(int i = 0;i < k;i++) {
            String item = q.dequeue();
            StdOut.println(item);
        }
    }
}
