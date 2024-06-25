public class QuickFindUF implements UF{
    private int[] id;
    public QuickFindUF(int N) {
        id = new int[N];
        for (int i=0; i<N; i++) {
            id[i] = i;
        }
    }

    public void union (int p, int q) {
        for (int i=0; i<id.length; i++) {
            if (id[i]==id[p]&&id[i]!=id[q]){
                id[i] = id[q];
            }
        }
    }

    public boolean connected(int p, int q){
        return id[p]==id[q];
    }
}
