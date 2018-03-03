import java.io.*;
import java.util.*;

public class H {
    FastScanner in;
    PrintWriter out;

    long[][] s;
    int[][] d;
    int n;
    long INF = 2000000000;
    public void solve() throws IOException {
        int i, j, m;
        long ans = INF;
        n = in.nextInt();
        m = in.nextInt();
        int[] w = new int[m + 1];
        int[] b = new int[m + 1];
        int[] a = new int[m + 1];
        d = new int[n][n];
        int pow = (int)Math.pow(2, n);
        s = new long[n][pow];
        for (i = 1; i <= m; i++) {
            a[i] = in.nextInt() - 1;
            b[i] = in.nextInt() - 1;
            w[i] = in.nextInt() ;
            d[a[i]][b[i]] = w[i];
            d[b[i]][a[i]] = w[i];
        }

        for (i = 0; i < n; i++)
            for (int z = 0; z < pow; z++)
                s[i][z] = INF;
        s[0][0] = 0;
        for (i = 0; i < n; i++)
            s[i][1] = 0;
        for (i = 0; i < n; i++)
            s[i][1 << i] = 0;
        for (int z = 0; z < pow; z++)
            for (i = 0; i < n; i++)
                if (getBit(z, i) == 1) {
                    for (j = 0; j < n; j++) {
                        if (d[j][i] != 0 && getBit(z, j) == 1) {
                            s[i][z] = Math.min(s[i][z], d[j][i] + s[j][z - (1 << i)]);
                        }
                    }
                }
        /*for (int z = 0; z < pow; z++) {
            out.write(Integer.toBinaryString(z) + " ");
            for (i = 0; i < n; i++)
                if (s[i][z] == INF) out.write("INF ");
                else out.write(s[i][z] + " ");
            out.write('\n');
        }*/
        for (i = 0; i < n; i++) {
            ans = Math.min(ans, s[i][pow - 1]);
        }
        if (ans == INF) out.write(-1 + "");
        else out.write(ans + "");
    }

    int getBit(int n, int k) {
        return (n >> k) & 1;
    }


    public void run() {
        try {
            in = new FastScanner(new File("salesman.in"));
            out = new PrintWriter(new File("salesman.out"));

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
        new H().run();
    }
}

