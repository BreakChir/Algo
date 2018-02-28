import java.io.*;
import java.util.*;

public class B {
    private FastScanner in;
    private PrintWriter out;
    private int n, logn, MAXLOGN, size;
    private int parent[], depth[], degreeTwo[];
    private int dp[][];

    private void solve() throws IOException {
        int k = in.nextInt();
        n = 500_000;
        parent = new int[n];
        depth = new int[n];
        MAXLOGN = (int) (Math.log(n) / Math.log(2)) + 1;
        logn = 1;
        size = 1;
        dp = new int[n][MAXLOGN];
        degreeTwo = new int[MAXLOGN + 1];

        preProcess();

        for (int i = 0; i < k; i++) {
            String str = in.next();
            int v = in.nextInt() - 1;
            int u = in.nextInt() - 1;
            if (str.charAt(0) == 'A') {
                set(v, u);
            } else {
                out.write(LCA(v, u) + 1 + "" + '\n');
            }
        }
    }

    private void set(int v, int u) {
        size++;
        depth[u] = depth[v] + 1;
        parent[u] = v;
        dp[u][0] = v;
        if (size == degreeTwo[logn]) logn++;
        for (int j = 1; j < logn; j++) {
            dp[u][j] = dp[dp[u][j - 1]][j - 1];
        }
    }

    private void preProcess() {
        degreeTwo[0] = 1;
        for (int i = 1; i < MAXLOGN; i++) {
            degreeTwo[i] = degreeTwo[i - 1] * 2;
        }
    }

    private int LCA(int v, int u) {
        if (depth[v] > depth[u]) {
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
            in = new FastScanner(new File("lca.in"));
            out = new PrintWriter(new File("lca.out"));

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
        new B().run();
    }
}
