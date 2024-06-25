import java.util.NoSuchElementException;
import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int logicalSize;
    private int actualSize;
    private Item[] arr;

    private static class rqIterator<T> implements Iterator<T> {
        RandomizedQueue<T> rq;
        int[] itrSeq;
        int curPos;

        rqIterator(RandomizedQueue<T> rq) {
            this.rq = rq;
            itrSeq = StdRandom.permutation(rq.actualSize);
            curPos = 0;
        }

        @Override
        public boolean hasNext() {
            return curPos<rq.actualSize;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Iterator reached the end of the queue!");
            }

            return rq.arr[itrSeq[curPos++]];
        }
    }

    // construct an empty randomized queue
    public RandomizedQueue() {
        logicalSize = 2;
        actualSize = 0;
        arr = (Item[]) new Object[logicalSize];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return actualSize==0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return actualSize;
    }

    // add the item
    public void enqueue(Item item) {
        if (actualSize==logicalSize) {
            logicalSize *= 2;
            Item[] arrNew = (Item[]) new Object[logicalSize];
            System.arraycopy(arr, 0, arrNew, 0, actualSize);
            arr = arrNew;
        }

        arr[actualSize] = item;
        actualSize++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty!");
        }
        int indx = StdRandom.uniform(actualSize);
        Item ret = arr[indx];
        arr[indx] = arr[actualSize-1];
        arr[actualSize-1] = null;
        actualSize--;

        if (actualSize==logicalSize/4) {
            logicalSize /= 2;
            Item[] arrNew = (Item[]) new Object[logicalSize];
            System.arraycopy(arr, 0, arrNew, 0, actualSize);
            arr = arrNew;
        }

        return ret;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty!");
        }

        return arr[StdRandom.uniform(actualSize)];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new rqIterator<>(this);
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> intRQ = new RandomizedQueue<>();
        intRQ.enqueue(1);
        intRQ.enqueue(2);
        intRQ.enqueue(3);
        intRQ.enqueue(4);
        intRQ.enqueue(5);
        intRQ.enqueue(6);

        Iterator<Integer> itr = intRQ.iterator();
        System.out.println("Random iteration 1...");
        while (itr.hasNext()) {
            System.out.printf("%s ", itr.next());
        }
        System.out.println();

        System.out.println("Random iteration 2...");
        itr = intRQ.iterator();
        while (itr.hasNext()) {
            System.out.printf("%s ", itr.next());
        }
        System.out.println();

        System.out.println("Testing the independence of 2 iterators...");
        Iterator<Integer> itr1 = intRQ.iterator(), itr2 = intRQ.iterator();
        while (itr1.hasNext()) {
            System.out.printf("%s ", itr1.next());
        }
        System.out.println();
        while (itr2.hasNext()) {
            System.out.printf("%s ", itr2.next());
        }
        System.out.println("\n");

        System.out.println("Testing dequeue...");
        System.out.printf("%s is popped, current size: %s\n", intRQ.dequeue(), intRQ.size());
        System.out.printf("%s is popped, current size: %s\n", intRQ.dequeue(), intRQ.size());
        System.out.printf("%s is popped, current size: %s\n", intRQ.dequeue(), intRQ.size());
        System.out.printf("%s is popped, current size: %s\n", intRQ.dequeue(), intRQ.size());
        System.out.printf("%s is popped, current size: %s\n", intRQ.dequeue(), intRQ.size());
        System.out.printf("%s is popped, current size: %s\n", intRQ.dequeue(), intRQ.size());
        try {
            intRQ.dequeue();
        }
        catch (NoSuchElementException e) {
            System.out.println(e.toString());
            System.out.println("The right exception was thrown...");
        }

        try {
            intRQ.sample();
        }
        catch (NoSuchElementException e) {
            System.out.println(e.toString());
            System.out.println("The right exception was thrown...");
        }


    }

}