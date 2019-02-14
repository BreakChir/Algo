import java.io.*;
import java.util.*;

public class G {
    FastScanner in;
    PrintWriter out;

    int[] h;
    int[] w;
    int[] d;
    int[][] maxim;
    int INF = 1000000000;
    public void solve() throws IOException {
        int i, j, k, n, l, kek, b, lch;
        n = in.nextInt();
        l = in.nextInt();
        h = new int [n];
        w = new int [n];
        d = new int [n];
        maxim = new int[n][n];
        for (i = 0; i < n; i++) {
            h[i] = in.nextInt();
            w[i] = in.nextInt();
            d[i] = INF;
            maxim[i][i] = h[i];
        }
        d[0] = h[0];
        for (i = 0; i < n; i++) {
            for (j = i + 1; j < n; j++) {
                maxim[i][j] = Math.max(maxim[i][j - 1], h[j]);
            }
        }
        for (i = 1; i < n; i++) {
            b = i;
            lch = 0;
            while (b >= 0 && lch <= l) {
                lch += w[b];
                b--;
            }
            if (lch <= l) b = -2;
            for (k = b + 2; k <= i; k++) {
                kek = k == 0 ? 0 : d[k - 1];
                d[i] = Math.min(maxim[k][i] + kek, d[i]);
            }
        }
        out.write(d[n - 1] + "");
    }

    public void run() {
        try {
            in = new FastScanner(new File("input.txt"));
            out = new PrintWriter(new File("output.txt"));

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
        new G().run();
    }
}
