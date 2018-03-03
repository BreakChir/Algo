import java.io.*;
import java.util.*;

public class D {
    FastScanner in;
    PrintWriter out;


    public void solve() throws IOException {
        int i, j, n, m;
        String s, g;
        s = in.next();
        g = in.next();
        int[][] d = new int[s.length() + 1][g.length() + 1];

        for (i = 0; i <= s.length(); i++)
            for (j = 0; j <= g.length(); j++) {
                if (i == j && i == 0) d[i][j] = 0;
                else if (j == 0 && i > 0) d[i][j] = i;
                else if (i == 0 && j > 0) d[i][j] = j;
                else d[i][j] = Math.min(Math.min(d[i][j - 1] + 1, d[i - 1][j] + 1), d[i - 1][j - 1]
                            + kek(s.charAt(i - 1), g.charAt(j - 1)));
            }
        out.write(d[s.length()][g.length()]+"");
    }

    int kek (int a, int b) {
        return a == b ? 0 : 1;
    }

    public void run() {
        try {
            in = new FastScanner(new File("levenshtein.in"));
            out = new PrintWriter(new File("levenshtein.out"));

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
        new D().run();
    }
}
