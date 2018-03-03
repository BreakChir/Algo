import java.io.*;
import java.util.*;
 
public class A {
    FastScanner in;
    PrintWriter out;
    int[] d, queue;
    int n, m, head, tail;
    boolean[] used;
    int INF = Integer.MAX_VALUE;
    ArrayList<Integer>[] edge;
 
    public void solve() throws IOException {
        n = in.nextInt();
        m = in.nextInt();
        edge = new ArrayList[n];
        used = new boolean[n];
        d = new int[n];
        queue = new int[n];
        for (int i = 0; i < n; i++) {
            edge[i] = new ArrayList<>();
            d[i] = INF;
        }
        d[0] = 0;
        used[0] = true;
        queue[0] = 0;
        tail = 1;
        for (int i = 0; i < m; i++) {
            int first = in.nextInt() - 1;
            int second = in.nextInt() - 1;
            edge[first].add(second);
            edge[second].add(first);
        }
 
        while (head < tail) {
            int v = queue[head++];
            for (int i = 0; i < edge[v].size(); i++) {
                int u = edge[v].get(i);
                if (!used[u]) {
                    used[u] = true;
                    queue[tail++] = u;
                    d[u] = d[v] + 1;
                }
            }
        }
 
        for (int i = 0; i < n; i++) {
            out.print(d[i] + " ");
        }
    }
 
    public void run() {
        try {
            in = new FastScanner(new File("pathbge1.in"));
            out = new PrintWriter(new File("pathbge1.out"));
 
            solve();
 
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
    class FastScanner {
        BufferedReader br;
        StringTokenizer st;
 
        FastScanner(File f) {
            try {
                br = new BufferedReader(new FileReader(f));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
 
        String next() {
            while (st == null || !st.hasMoreTokens()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }
 
        int number() {
            while (st == null || !st.hasMoreTokens()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.countTokens();
        }
 
        int nextInt() {
            return Integer.parseInt(next());
        }
 
        long nextLong() {
            return Long.parseLong(next());
        }
    }
 
    public static void main(String[] arg) {
        new A().run();
    }
}