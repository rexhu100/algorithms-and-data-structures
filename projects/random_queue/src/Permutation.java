import edu.princeton.cs.algs4.StdIn;
import java.util.Iterator;

public class Permutation {
    public static void main(String[] args) {
        RandomizedQueue<String> stringRQ = new RandomizedQueue<>();
        int limit = Integer.parseInt(args[0]);
        while (!StdIn.isEmpty()) {
            stringRQ.enqueue(StdIn.readString());
        }

        Iterator<String> itr = stringRQ.iterator();
        for (int i=0; i<limit; i++) {
            System.out.println(itr.next());
        }
    }
}