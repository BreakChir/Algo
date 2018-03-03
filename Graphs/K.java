import java.io.*;
import java.util.*;

public class K {
    FastScanner in;
    PrintWriter out;
    int[] d;
    boolean[] used;
    boolean[] exist;
    int s, t, n, m;
    int INF = Integer.MAX_VALUE;
    ArrayList<Integer>[] edge, weight;

    public void solve() throws IOException {
        n = in.nextInt();
        m = in.nextInt();
        s = in.nextInt() - 1;
        t = in.nextInt() - 1;
        edge = new ArrayList[n];
        weight = new ArrayList[n];
        used = new boolean[n];
        exist = new boolean[n];
        d = new int[n];
        for (int i = 0; i < n; i++) {
            edge[i] = new ArrayList<>();
            weight[i] = new ArrayList<>();
            d[i] = INF;
        }
        d[t] = 0;
        for (int i = 0; i < m; i++) {
            int first = in.nextInt() - 1;
            int second = in.nextInt() - 1;
            int w = in.nextInt();
            edge[first].add(second);
            weight[first].add(w);
        }

        if (dfs(s)) out.println(d[s]);
        else out.println("Unreachable");
    }

    boolean dfs(int v) {
        used[v] = true;
        if (v == t) return true;
        for (int i = 0; i < edge[v].size(); i++) {
            int u = edge[v].get(i);
            int len = weight[v].get(i);
            if (!used[u]) {
                exist[u] = dfs(u);
                if (exist[u]) {
                    exist[v] = true;
                    d[v] = Math.min(d[v], d[u] + len);
                }
            } else {
                if (d[v] > d[u] + len) {
                    if (exist[u] || !exist[v]) {
                        exist[v] = exist[u];
                        d[v] = d[u] + len;
                    }
                }
            }
        }
        return exist[v];
    }

    public void run() {
        try {
            in = new FastScanner(new File("shortpath.in"));
            out = new PrintWriter(new File("shortpath.out"));

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
        new K().run();
    }
}