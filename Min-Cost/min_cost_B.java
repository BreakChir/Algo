import java.io.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class min_cost_B {

    private void solve() throws IOException {
        int n = nextInt();
        int[][] a = new int[n + 1][n + 1];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                a[i][j] = nextInt();
            }
        }

        Hungary hungary = new Hungary(n, n, a);
        hungary.calc();
        int[] ans = hungary.getAns();

        out.write(hungary.getCost() + "\n");
        for (int i = 1; i < ans.length; i++) {
            out.write(i + " " + ans[i] + "\n");
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
            v = new int[n + 1];
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

    private BufferedReader br;
    private StringTokenizer st;
    private PrintWriter out;
//    private PrintWriter out = new PrintWriter(System.out);
//    private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

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
        new min_cost_B().run();
    }

    private void run() throws IOException {
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream("assignment.in")));
            out = new PrintWriter(new File("assignment.out"));

            solve();
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}