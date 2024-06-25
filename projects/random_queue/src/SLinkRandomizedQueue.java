/* This one doesn't meet the time complexity requirement. Need to use array instead of linked list */

import java.util.NoSuchElementException;
import java.util.Iterator;
import static edu.princeton.cs.algs4.StdRandom.uniform;

public class SLinkRandomizedQueue<Item> implements Iterable<Item> {
    static class ListNode<T> {
        T val;
        ListNode<T> next;

        ListNode() { }
    }

    static class QueueIterator<T> implements Iterator<T> {
        SLinkRandomizedQueue<T> q;
        ListNode<T> cur;
        public QueueIterator (SLinkRandomizedQueue<T> q) {
            this.q = q;
            cur = q.head.next;
        }

        @Override
        public boolean hasNext() {
            return cur!=null;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Iterator has reached the end of the queue!");
            }
            T ret = cur.val;
            cur = cur.next;

            return ret;
        }
    }

    private ListNode<Item> head;
    int sz;

    // construct an empty randomized queue
    public SLinkRandomizedQueue() {
        head = new ListNode<>();
        sz=0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return sz==0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return sz;
    }

    // add the item
    public void enqueue(Item item) {
        ListNode<Item> newHead = new ListNode<>();
        head.val = item;
        newHead.next = head;
        head = newHead;
        sz++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty!");
        }

        int k = uniform(sz);
        ListNode<Item> cur = head.next, prev=head;
        for (int i=0; i<k; i++) {
            prev = cur;
            cur = cur.next;
        }
        Item ret = cur.val;
        prev.next = cur.next;
        cur.next = null;

        sz--;
        return ret;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (sz == 0) {
            throw new NoSuchElementException("Queue is empty!");
        }

        int k = uniform(sz);
        ListNode<Item> cur = head.next;
        for (int i=0; i<k; i++) {
            cur = cur.next;
        }

        return cur.val;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return  new QueueIterator<Item>(this);
    }

    // unit testing (required)
    public static void main(String[] args) {
        // Initialize and add data to deque
        SLinkRandomizedQueue<Integer> intRQ = new SLinkRandomizedQueue<>();
        intRQ.enqueue(1);
        intRQ.enqueue(2);
        intRQ.enqueue(3);
        intRQ.enqueue(-3);
        intRQ.enqueue(-2);
        intRQ.enqueue(-1);
        Iterator<Integer> i = intRQ.iterator();

        System.out.printf("The size of the Random Queue is currently %s\n", intRQ.size());
        while (i.hasNext()) {
            System.out.printf("%s ", i.next());
        }
        System.out.println();

        // Testing dequeue method
        for (int j=0; j<6; j++) {
            System.out.printf("%s ", intRQ.dequeue());
        }
        System.out.println();
    }
}