import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class F {
    private FastScanner in;
    private PrintWriter out;
    private int n, logn;
    private int parent[], depth[], degreeTwo[];
    private int dp[][], dpWeight[][];
    private ArrayList<Integer> edges[];

    private void solve() throws IOException {
        n = in.nextInt();
        inisialisation(n);

        for (int i = 1; i < n; i++) {
            int v = in.nextInt() - 1;
            int w = in.nextInt();
            parent[i] = v;
            dpWeight[i][0] = w;
            edges[v].add(i);
        }

        preProcess();

        int m = in.nextInt();
        for (int i = 0; i < m; i++) {
            out.write(minOnWay(in.nextInt() - 1, in.nextInt() - 1) + "" + '\n');
        }
    }

    private void inisialisation(int size) {
        parent = new int[size];
        edges = new ArrayList[size];
        depth = new int[size];
        logn = (int) (Math.log(size) / Math.log(2)) + 1;
        dp = new int[size][logn + 1];
        dpWeight = new int[size][logn + 1];
        degreeTwo = new int[logn + 1];

        for (int i = 0; i < size; i++) {
            edges[i] = new ArrayList<>();
        }
    }

    private void setDepth(int v, int d) {
        depth[v] = d;
        for (int u : edges[v]) {
            setDepth(u, d + 1);
        }
    }

    private void preProcess() {
        setDepth(0, 0);

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
            for (int i = 0; i < n; i++) {
                dpWeight[i][j] = Math.min(dpWeight[i][j - 1], dpWeight[dp[i][j - 1]][j - 1]);
            }
        }

    }

    private int minOnWay(int v, int u) {
        int min = 1_000_000_000;
        if (depth[v] > depth[u]) {
            int tmp = v;
            v = u;
            u = tmp;
        }
        for (int i = logn - 1; i >= 0; i--) {
            if (depth[u] - depth[v] >= degreeTwo[i]) {
                min = Math.min(dpWeight[u][i], min);
                u = dp[u][i];
            }
        }
        if (v == u) return min;
        for (int i = logn - 1; i >= 0; i--) {
            if (dp[v][i] != dp[u][i]) {
                min = Math.min(dpWeight[u][i], min);
                min = Math.min(dpWeight[v][i], min);
                v = dp[v][i];
                u = dp[u][i];
            }
        }
        min = Math.min(dpWeight[u][0], min);
        min = Math.min(dpWeight[v][0], min);
        return min;
    }

    private void run() {
        try {
            in = new FastScanner(new File("minonpath.in"));
            out = new PrintWriter(new File("minonpath.out"));

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
        new F().run();
    }
}