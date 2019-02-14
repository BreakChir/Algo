import java.io.*;
import java.util.*;

public class B {
    FastScanner in;
    PrintWriter out;

    pair[][] prev;
    int len = 0;
    int[] b;
    int[] ans;
    public void solve() throws IOException {
        int i, j, n;
        n = in.nextInt();
        int[] a = new int[n + 1];
        for (i = 1; i <= n; i++) {
            a[i] = in.nextInt();
        }
        int m = in.nextInt();
        b = new int[m + 1];
        for (i = 1; i <= m; i++) {
            b[i] = in.nextInt();
        }
        int[][] lcs = new int [m + 1][n + 1];
        prev = new pair[m + 1][n + 1];
        for (i = 0; i <= m; i++)
            for (j = 0; j <=n; j++)
                prev[i][j] = new pair();
        for (i = 1; i <= m; i++)
            lcs[i][0] = 0;
        for (i = 0; i <= n; i++)
            lcs[0][i] = 0;
        for (i = 1; i <= m; i++) {
            for (j = 1; j <= n; j++) {
                if (b[i] == a[j]) {
                    lcs[i][j] = lcs[i - 1][j - 1] + 1;
                    prev[i][j].x = i - 1;
                    prev[i][j].y = j - 1;
                }
                else {
                    if (lcs[i - 1][j] >= lcs[i][j - 1]) {
                        lcs[i][j] = lcs[i - 1][j];
                        prev[i][j].x = i - 1;
                        prev[i][j].y = j;
                    }
                    else {
                        lcs[i][j] = lcs[i][j - 1];
                        prev[i][j].x = i;
                        prev[i][j].y = j - 1;
                    }
                }
            }
        }
        ans = new int[n];
        kek(m, n, ans);
        out.write(len + "" + '\n');
        for (i = 0; i < len; i++)
            out.write(ans[i] + " ");
    }

    void kek (int i, int j, int[] mas) {
        if (i == 0 || j == 0) return;
        if (prev[i][j].x == i - 1 && prev[i][j].y == j - 1) {
            kek(i - 1, j - 1, mas);
            ans[len++] = b[i];
        }
        else {
            if (prev[i][j].x == i - 1 && prev[i][j].y == j)
                kek(i - 1, j, mas);
            else
                kek(i, j - 1, mas);
        }
    }

    class pair {
        int x, y;
    }

    public void run() {
        try {
            in = new FastScanner(new File("lcs.in"));
            out = new PrintWriter(new File("lcs.out"));

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
        new B().run();
    }
}
