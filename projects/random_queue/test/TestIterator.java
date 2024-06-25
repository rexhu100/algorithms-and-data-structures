import java.util.*;

public class TestIterator {
    public static void main(String[] args) {
        List<Integer> stuff = new ArrayList<>();

        stuff.add(1);
        stuff.add(2);

        Iterator<Integer> itr = stuff.listIterator();

        while (itr.hasNext()) {
            System.out.println(itr.next());
        }

        stuff.add(3);
        stuff.add(4);
        itr = stuff.listIterator();
        while (itr.hasNext()) {
            System.out.println(itr.next());
        }
    }
}