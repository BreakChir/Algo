import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class min_cost_C {
    private ArrayList<Integer>[] edges;
    private ArrayList<Integer>[] child;
    private int[][][] dp;
    private int[][] cost;
    private int n, k, p;

    private void solve() throws IOException {
        n = nextInt();
        k = nextInt();
        p = nextInt();
        edges = new ArrayList[n + 1];
        child = new ArrayList[n + 1];
        for (int i = 0; i <= n; i++) {
            edges[i] = new ArrayList<>();
            child[i] = new ArrayList<>();
        }
        dp = new int[n + 1][k + 1][k + 1];

        cost = new int[n + 1][k + 1];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= k; j++) {
                cost[i][j] = nextInt();
            }
        }

        for (int i = 0; i < n - 1; i++) {
            int A = nextInt();
            int B = nextInt();
            edges[A].add(B);
            edges[B].add(A);
        }

        setChild(1, -1);
        dfs(1);
        int ans = Integer.MAX_VALUE;
        for (int c = 1; c <= k; c++) {
            ans = Math.min(ans, dp[1][c][1]);
        }

        out.write(ans + "\n");
    }

    private void dfs(int v) {
        for (int u : child[v]) {
            dfs(u);
        }

        for (int pc = 1; pc <= k; pc++) {
            for (int c = 1; c <= k; c++) {
                int sum = 0;
                for (int ch : child[v]) {
                    int min = Integer.MAX_VALUE;
                    for (int color = 1; color <= k; color++) {
                        min = Math.min(min, dp[ch][color][c]);
                    }
                    sum += min;
                }
                dp[v][c][pc] = p + cost[v][c] + sum;
            }

            int sz = v == 1 ? child[v].size() : child[v].size() + 1;
            if (sz <= k) {
                int newK = v == 1 ? k : k - 1;
                for (int c = 1; c <= k; c++) {
                    int[][] a = new int[child[v].size() + 1][newK + 1];

                    for (int i = 1; i < child[v].size() + 1; i++) {
                        int shift = 0;
                        for (int j = 1; j <= k; j++) {
                            if (j == pc && v != 1) {
                                shift = 1;
                                continue;
                            }
                            a[i][j - shift] = dp[child[v].get(i - 1)][j][c];
                        }
                    }

                    Hungary hungary = new Hungary(child[v].size(), newK, a);
                    hungary.calc();
                    dp[v][c][pc] = Math.min(dp[v][c][pc], hungary.getCost() + cost[v][c]);
                }
            }

            if (v == 1) break;
        }
    }

    private void setChild(int v, int p) {
        for (int u : edges[v]) {
            if (u == p) continue;
            child[v].add(u);
            setChild(u, v);
        }
    }

    class Hungary {
        int n;
        int m;
        int[][] a;
        int[] u;
        int[] v;
        int[] match;
        int[] way;

        Hungary(int n, int m, int[][] a) {
            this.a = a;
            this.n = n;
            this.m = m;

            u = new int[n + 1];
            v = new int[m + 1];
            match = new int[m + 1];
            way = new int[m + 1];
        }

        void calc() {
            for (int i = 1; i <= n; i++) {
                match[0] = i;
                int j0 = 0;
                int[] minv = new int[m + 1];
                boolean[] used = new boolean[m + 1];
                Arrays.fill(minv, Integer.MAX_VALUE);

                do {
                    used[j0] = true;
                    int i0 = match[j0];
                    int delta = Integer.MAX_VALUE;
                    int j1 = 0;

                    for (int j = 1; j <= m; j++) {
                        if (used[j]) continue;
                        int cur = a[i0][j] - u[i0] - v[j];
                        if (cur < minv[j]) {
                            minv[j] = cur;
                            way[j] = j0;
                        }
                        if (minv[j] < delta) {
                            delta = minv[j];
                            j1 = j;
                        }
                    }

                    for (int j = 0; j <= m; j++) {
                        if (used[j]) {
                            u[match[j]] += delta;
                            v[j] -= delta;
                        } else {
                            minv[j] -= delta;
                        }
                    }
                    j0 = j1;
                } while (match[j0] != 0);

                do {
                    int j1 = way[j0];
                    match[j0] = match[j1];
                    j0 = j1;
                } while (j0 > 0);
            }
        }

        int[] getAns() {
            int[] ans = new int[n + 1];
            for (int j = 1; j <= m; j++) {
                ans[match[j]] = j;
            }
            return ans;
        }

        int getCost() {
            return -v[0];
        }

    }

    // ------------------

    private StringTokenizer st;
    //    private BufferedReader br;
//    private PrintWriter out;
    private PrintWriter out = new PrintWriter(System.out);
    private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    private String next() throws IOException {
        while (st == null || !st.hasMoreTokens()) {
            st = new StringTokenizer(br.readLine());
        }
        return st.nextToken();
    }

    private int nextInt() throws IOException {
        return Integer.parseInt(next());
    }

    private double nextDouble() throws IOException {
        return Double.parseDouble(next());
    }

    public static void main(String[] args) throws IOException {
        new min_cost_C().run();
    }

    private void run() throws IOException {
        try {
//            br = new BufferedReader(new InputStreamReader(new FileInputStream("input.txt")));
//            out = new PrintWriter(new File("output.txt"));

            solve();
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}