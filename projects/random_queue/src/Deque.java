import java.util.NoSuchElementException;
import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    private static class ListNode<T> {
        T val;
        ListNode<T> next;
        ListNode<T> prev;

        ListNode() { }
    }

    private static class DListIterator<T> extends Deque<T> implements Iterator<T> {
        private final Deque<T> dlist;
        private ListNode<T> cur;
        private int curPos;
        private DListIterator(Deque<T> d) {
            dlist = d;
            cur = d.head.next;
            curPos = 0;
        }

        @Override
        public boolean hasNext() {
            return curPos<dlist.sz;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Iterator reached the end of the deque!");
            }

            ListNode<T> ret = cur;
            cur = cur.next;
            curPos++;
            return ret.val;
        }
    }

    private ListNode<Item> head;
    private ListNode<Item> tail;
    private int sz;
    // construct an empty deque
    public Deque() {
        head = new ListNode<>();
        tail = new ListNode<>();
        head.next = tail;
        tail.prev = head;
        sz = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return sz==0;
    }

    // return the number of items on the deque
    public int size() {
        return sz;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item==null) {
            throw new IllegalArgumentException("Input cannot be null!");
        }

        ListNode<Item> newHead = new ListNode<>();
        newHead.next = head;
        head.val = item;
        head.prev = newHead;
        head = newHead;
        sz++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item==null) {
            throw new IllegalArgumentException("Input cannot be null!");
        }

        ListNode<Item> newTail = new ListNode<>();
        newTail.prev = tail;
        tail.next = newTail;
        tail.val = item;
        tail = newTail;
        sz++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()){
            throw new NoSuchElementException("Deque is empty!");
        }

        ListNode<Item> temp = head.next;
        head.next = temp.next;
        temp.next.prev = head;

        temp.next = null;
        temp.prev = null;

        sz--;

        return temp.val;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()){
            throw new NoSuchElementException("Deque is empty!");
        }

        ListNode<Item> temp = tail.prev;
        tail.prev = temp.prev;
        temp.prev.next = tail;

        temp.prev = null;
        temp.next = null;

        sz--;

        return temp.val;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DListIterator<>(this);
    }

    // unit testing (required)
    public static void main(String[] args) {
        // Initialize and add data to deque
        Deque<Integer> intDQ = new Deque<>();
        intDQ.addFirst(1);
        intDQ.addFirst(2);
        intDQ.addFirst(3);
        intDQ.addLast(-3);
        intDQ.addLast(-2);
        intDQ.addLast(-1);
        Iterator<Integer> i = intDQ.iterator();

        System.out.printf("The size of the Deque is currently %s\n", intDQ.size());
        while (i.hasNext()) {
            System.out.printf("%s ", i.next());
        }
        System.out.println();

        // Testing removeFirst
        System.out.println(intDQ.removeFirst());
        System.out.printf("The size of the Deque is currently %s\n", intDQ.size());
        System.out.println(intDQ.removeFirst());
        System.out.printf("The size of the Deque is currently %s\n", intDQ.size());
        System.out.println(intDQ.removeFirst());
        System.out.printf("The size of the Deque is currently %s\n", intDQ.size());
        System.out.println(intDQ.removeFirst());
        System.out.printf("The size of the Deque is currently %s\n", intDQ.size());
        System.out.println(intDQ.removeFirst());
        System.out.printf("The size of the Deque is currently %s\n", intDQ.size());
        System.out.println(intDQ.removeFirst());
        System.out.printf("The size of the Deque is currently %s\n", intDQ.size());

        try {
            intDQ.removeFirst();
        }
        catch (NoSuchElementException e) {
//            System.out.println(e);
            System.out.println("Exception expected. Good!");
        }

        // Shouldn't be any output
        i = intDQ.iterator();
        while (i.hasNext()) {
            System.out.printf("%s ", i.next());
        }
        System.out.println();

        // Reconstructing the deque
        intDQ.addFirst(1);
        intDQ.addFirst(2);
        intDQ.addFirst(3);
        intDQ.addLast(-3);
        intDQ.addLast(-2);
        intDQ.addLast(-1);
        i = intDQ.iterator();

        System.out.printf("The size of the Deque is currently %s\n", intDQ.size());
        while (i.hasNext()) {
            System.out.printf("%s ", i.next());
        }
        System.out.println();


        // Testing removeLast
        System.out.println(intDQ.removeLast());
        System.out.printf("The size of the Deque is currently %s\n", intDQ.size());
        System.out.println(intDQ.removeLast());
        System.out.printf("The size of the Deque is currently %s\n", intDQ.size());
        System.out.println(intDQ.removeLast());
        System.out.printf("The size of the Deque is currently %s\n", intDQ.size());
        System.out.println(intDQ.removeLast());
        System.out.printf("The size of the Deque is currently %s\n", intDQ.size());
        System.out.println(intDQ.removeLast());
        System.out.printf("The size of the Deque is currently %s\n", intDQ.size());
        System.out.println(intDQ.removeLast());
        System.out.printf("The size of the Deque is currently %s\n", intDQ.size());

        // Testing Exception throwing
        try {
            intDQ.removeFirst();
        }
        catch (NoSuchElementException e) {
//            System.out.println(e);
            System.out.println("Exception expected. Good!");
        }

        // Shouldn't be any output
        i = intDQ.iterator();
        while (i.hasNext()) {
            System.out.printf("%s ", i.next());
        }
        System.out.println();
    }
}