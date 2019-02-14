import java.io.*;
import java.util.*;

public class C {
    FastScanner in;
    PrintWriter out;

    int n, m;
    int[] w;
    int[] c;
    int[][] d;
    int[] answer;
    public void solve() throws IOException {
        int i, j, k;
        n = in.nextInt();
        m = in.nextInt();
        w = new int[n + 1];
        c = new int[n + 1];
        d = new int[n + 1][m + 1];
        answer = new int[n];
        for (i = 1; i <= n; i++)
            w[i] = in.nextInt();
        for (i = 1; i <= n; i++)
            c[i] = in.nextInt();
        for (i = 0; i < m; i++)
            d[0][i] = 0;
        for (i = 0; i < n; i++)
            d[i][0] = 0;
        for (j = 1; j <= n; j++) {
            for (k = 1; k <= m; k++) {
                if (k >= w[j]) {
                    d[j][k] = Math.max(d[j -1][k], d[j - 1][k - w[j]] + c[j]);
                }
                else {
                    d[j][k] = d[j - 1][k];
                }
            }
        }
        j = n; k = m;
        int[] ans = new int[n];
        int len = 0;
        while (j > 0) {
            if (j > 0 && d[j][k] == d[j - 1][k]){
                j--;
            }
            else {
                k -= w[j];
                ans[len] = j;
                j--;
                len++;
            }
        }
        out.write(len + "" + '\n');
        for (i = len - 1; i >= 0; i--)
            out.write(ans[i] + " ");


    }

    public void run() {
        try {
            in = new FastScanner(new File("knapsack.in"));
            out = new PrintWriter(new File("knapsack.out"));

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
        new C().run();
    }
}
