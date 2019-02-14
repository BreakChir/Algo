import java.io.*;
import java.util.*;

public class L {
    FastScanner in;
    PrintWriter out;


    int[] p, q, a, b;
    boolean[] exa, exb;
    int n;
    int INF = 2000000000;
    public void solve() throws IOException {
        int i, j, start = 0;
        n = in.nextInt();
        q = new int[n];
        p = new int[n];
        a = new int[n];
        b = new int[n];
        exa = new boolean[n];
        exb = new boolean[n];
        for (i = 0; i < n; i++) {
            p[i] = in.nextInt();
            q[i] = in.nextInt();
            if (p[i] == 0) start = i;
        }
        out.write(Math.max(Aret(start),Bret(start)) + "");
    }

    public int Aret (int i) {
        if (!exa[i]) {
            int sum = q[i], sumwithout = 0, ares, bres;
            boolean exist = false;
            for (int j = 0; j < n; j++) {
                if (p[j] == i + 1) {
                    bres = Bret(j);
                    ares = Aret(j);
                    sum += bres;
                    sumwithout += Math.max(ares, bres);
                    exist = true;
                }
            }
            if (exist) a[i] = Math.max(sum, sumwithout);
            else a[i] = Math.max(q[i], 0);
            exa[i] = true;
        }
        return a[i];
    }

    public int Bret (int i) {
        if (!exb[i]) {
            int sum = 0;
            boolean exist = false;
            for (int j = 0; j < n; j++) {
                if (p[j] == i + 1) {
                    exist = true;
                    sum += Aret(j);
                }
            }
            if (exist) b[i] = sum;
            else b[i] = 0;
            exb[i] = true;
        }
        return b[i];
    }

    public void run() {
        try {
            in = new FastScanner(new File("selectw.in"));
            out = new PrintWriter(new File("selectw.out"));

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
        new L().run();
    }
}
