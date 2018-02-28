import java.io.*;
import java.util.*;

public class I {
    private FastScanner in;
    private PrintWriter out;
    private int n, logn;
    private int parent[], depth[], degreeTwo[], weightToRoot[];
    private int dp[][];
    private ArrayList<Integer> edges[], weight[];

    private void solve() throws IOException {
        n = in.nextInt();
        inisialisation(n);

        for (int i = 1; i < n; i++) {
            int v = in.nextInt();
            int u = in.nextInt();
            int w = in.nextInt();
            edges[u].add(v);
            edges[v].add(u);
            weight[u].add(w);
            weight[v].add(w);
        }

        preProcess();

        int m = in.nextInt();
        for (int i = 0; i < m; i++) {
            out.write(minWay(in.nextInt(), in.nextInt()) + "" + '\n');
        }
    }

    private void inisialisation(int size) {
        parent = new int[size];
        edges = new ArrayList[size];
        weight = new ArrayList[size];
        depth = new int[size];
        logn = (int) (Math.log(size) / Math.log(2)) + 1;
        dp = new int[size][logn + 1];
        weightToRoot = new int[size];
        degreeTwo = new int[logn + 1];

        for (int i = 0; i < size; i++) {
            edges[i] = new ArrayList<>();
            weight[i] = new ArrayList<>();
        }
    }

    private int minWay(int v, int u) {
        return weightToRoot[v] + weightToRoot[u] - 2 * weightToRoot[LCA(v, u)];
    }

    private void setDepth(int v, int d, int lenFromRoot) {
        depth[v] = d;
        weightToRoot[v] = weightToRoot[parent[v]] + lenFromRoot;
        for (int i = 0; i < edges[v].size(); i++) {
            int u = edges[v].get(i);
            int w = weight[v].get(i);
            if (u == parent[v]) continue;
            parent[u] = v;
            setDepth(u, d + 1, lenFromRoot + w);
        }
    }

    private void preProcess() {
        parent[0] = 0;
        setDepth(0, 0, 0);

        degreeTwo[0] = 1;
        for (int i = 1; i < logn + 1; i++) {
            degreeTwo[i] = degreeTwo[i - 1] * 2;
        }

        for (int i = 0; i < n; i++) {
            dp[i][0] = parent[i];
        }
        for (int j = 1; j < logn; j++) {
            for (int i = 0; i < n; i++) {
                dp[i][j] = dp[dp[i][j - 1]][j - 1];
            }
        }

    }

    private int LCA(int v, int u) {
        if (depth[v] >= depth[u]) {
            int tmp = v;
            v = u;
            u = tmp;
        }
        for (int i = logn - 1; i >= 0; i--) {
            if (depth[u] - depth[v] >= degreeTwo[i]) {
                u = dp[u][i];
            }
        }
        if (v == u) return v;
        for (int i = logn - 1; i >= 0; i--) {
            if (dp[v][i] != dp[u][i]) {
                v = dp[v][i];
                u = dp[u][i];
            }
        }
        return parent[v];
    }

    private void run() {
        try {
            in = new FastScanner(new File("tree.in"));
            out = new PrintWriter(new File("tree.out"));

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

        char nextChar() {
            try {
                return (char) br.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return 0;
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

        int nextInt() {
            return Integer.parseInt(next());
        }

        long nextLong() {
            return Long.parseLong(next());
        }

        void close() {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        void nextLine() {
            try {
                br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] arg) {
        new I().run();
    }
}