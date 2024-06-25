import org.junit.*;

public class TestQuickUnionUF {
    @Test
    public void testUnion(){
        QuickUnionUF uf = new QuickUnionUF(9);
        for (int i=0; i<9; i++) {
            Assert.assertTrue(uf.connected(i, i));
            for (int j=i+1; j<9; j++) {
                Assert.assertFalse(uf.connected(i, j));
            }
        }
    }

    @Test
    public void testUnionConnect(){
        QuickUnionUF uf = new QuickUnionUF(9);
        Assert.assertFalse(uf.connected(1, 2));

        uf.union(1, 2);
        Assert.assertTrue(uf.connected(1, 2));

        Assert.assertFalse(uf.connected(1, 3));
        uf.union(1, 3);
        Assert.assertTrue(uf.connected(1, 3));
    }
}
