import java.io.*;
import java.util.*;

public class A {
        FastScanner in;
        PrintWriter out;

        public void solve() throws IOException {
            int i, j, n, len = 0;
            n = in.nextInt();
            int[] a = new int[n];
            int[] d = new int[n];
            int[] pos = new int[n];
            int[] prev = new int[n];
            for (i = 0; i < n; i++) {
                a[i] = in.nextInt();
                d[i] = Integer.MAX_VALUE;
            }
            pos[0] = -1;
            for (i = 0; i < n; i++) {
                j = binsearch(d, a[i]);
                if (j == 0 || d[j - 1] < a[i] && a[i] < d[j]) {
                    d[j] = a[i];
                    pos[j] = i;
                    if (j == 0 ) prev[i] = -1;
                    else prev[i] = pos[j - 1];
                    len = Math.max(len, j);
                }
            }
            j = pos[len];
            i = 0;
            out.write(len+1 + " " + '\n');
            while (j != -1) {
                pos[i] = a[j];
                j = prev[j];
                i++;
            }
            for (i = len; i >= 0; i--) {
                out.write(pos[i] + " ");
            }

        }

        int binsearch (int[] d, int x) {
            int l = 0, r = d.length, m;
            while (l < r) {
                m = (l + r) / 2;
                if (d[m] < x) {
                    l = m + 1;
                }
                else r = m;
            }
            return r;
        }

        public void run() {
            try {
                in = new FastScanner(new File("lis.in"));
                out = new PrintWriter(new File("lis.out"));

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
        new A().run();
    }
}
