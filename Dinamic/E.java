import java.io.*;
import java.util.*;

public class E {
    FastScanner in;
    PrintWriter out;

    long INF = 1000000000;
    long[][] d;
    exp[][] k;
    int[] v;
    String s = "";

    class exp {
        int ll, lr, rl, rr;
    }
    public void solve() throws IOException {
        int i, j, n;
        n = in.nextInt();
        v = new int[n + 1];
        d = new long[n + 2][n + 2];
        k = new exp[n + 2][n + 2];
        for (i = 0; i <= n; i++)
            for (j = 0; j <= n; j++) {
                if (i == j) d[i][j] = 0;
                else d[i][j] = -1;
            }
        v[0] = in.nextInt(); v[1] = in.nextInt();
        for (i = 1; i < n; i++) {
            j = in.nextInt();
            j = in.nextInt();
            v[i + 1] = j;
        }

        long a = kek(0,n);
        go(0,n);
        out.write(s + "");
    }

    void go(int l, int r) {
        if (k[l][r] == null) s = s.concat("A");
        else {
            s = s.concat("(");
            go(k[l][r].ll, k[l][r].lr);
            go(k[l][r].rl, k[l][r].rr);
            s = s.concat(")");
        }
    }


    long kek(int l, int r) {
        if (d[l][r] == -1) {
            if (l == r - 1) {
                d[l][r] = 0;
            }
            else {
                d[l][r] = INF;
                for (int i = l + 1; i < r; i++)
                    if (v[l] * v[i] * v[r] + kek(l, i) + kek(i, r) < d[l][r]) {
                        d[l][r] = v[l] * v[i] * v[r] + kek(l, i) + kek(i, r);
                        k[l][r] = new exp();
                        k[l][r].ll = l;
                        k[l][r].lr = i;
                        k[l][r].rl = i;
                        k[l][r].rr = r;
                    }

            }
        }
        return d[l][r];
    }


    public void run() {
        try {
            in = new FastScanner(new File("matrix.in"));
            out = new PrintWriter(new File("matrix.out"));

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
        new E().run();
    }
}
