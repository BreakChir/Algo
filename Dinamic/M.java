import java.io.*;
import java.util.*;

public class M {
    FastScanner in;
    PrintWriter out;

    int[][] d;
    int x, a, y, b, l;
    public void solve() throws IOException {
        int i, j, k;
        x = in.nextInt();
        a = in.nextInt();
        y = in.nextInt();
        b = in.nextInt();
        l = in.nextInt();
        d = new int[x + 1][y + 1];
        out.println(BinSearch(0, x * a + y * b + 1));
    }

    private int BinSearch(int left, int right) {
        int  i, j, k, med, kek;
        while (left + 1 < right) {
            med = (left + right) / 2;
            d = new int[x + 1][y + 1];
            for (i = 0; i <= x; i++)
                for (j = 0; j <= y; j++)
                    for (k = 0; k <= i; k++) {
                        int count = med - a * k;
                        if (count > 0) {
                            if (count % b == 0) count /= b;
                            else count = count / b + 1;
                        }
                        if (count <= j) d[i][j] = Math.max(d[i][j], d[i - k][j - (count < 0 ? 0 : count)] + 1);
                    }
            if (d[x][y] >= l) left = med;
            else right = med;
            /*for (i = 0; i <= x; i++) {
                for (j = 0; j <= y; j++)
                    out.write(d[i][j] + " ");
                out.write('\n');
            }*/
        }
        return left;
    }

    private int BINSUP(int i, int j, int k, int im, int jm) {
        int l = 0, r = i + 1, med;
        while (l + 1 < r) {
            med = (l + r) / 2;
            if (d[med][j] <= d[im - med][jm - j]) {
                l = med;
            } else {
                r = med;
            }
        }
        return l == 0 ? 0 : l - 1;
    }

    private int BINSLEFT(int i, int j, int k, int im, int jm) {
        int l = 0, r = j + 1, med;
        while (l + 1 < r) {
            med = (l + r) / 2;
            if (d[i][med] <= d[im - i][jm - med]) {
                l = med;
            } else {
                r = med;
            }
        }
        return l == 0 ? 0 : l - 1;
    }

    public void run() {
        try {
            in = new FastScanner(new File("bridge.in"));
            out = new PrintWriter(new File("bridge.out"));

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
        new M().run();
    }
}

