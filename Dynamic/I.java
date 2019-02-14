import java.io.*;
import java.util.*;

public class I {
    FastScanner in;
    PrintWriter out;

    int[][] d;
    int[] s;
    int[] t;
    int[] p;
    int[] change;
    int INF = 1000000000;
    public void solve() throws IOException {
        int i, j, n, T, R, tAns = 0, len = 0;
        n = in.nextInt();
        T = in.nextInt();
        R = in.nextInt();
        s = new int[n];
        t = new int[n];
        p = new int[n];
        change = new int[n];
        d = new int[n + 1][T + 1];
        int[][] way = new int[n + 1][T + 1];
        for (i = 0; i < n; i++) {
            s[i] = in.nextInt();
            p[i] = in.nextInt();
            t[i] = in.nextInt();
            change[i] = i;
        }
        quickSort(s);
        d[0][0] = R;
        for (i = 1; i <= n; i++) {
            for (j = 0; j <= T; j++) {
                if (j - t[change[i - 1]] >= 0 && s[i - 1] <= d[i - 1][j - t[change[i - 1]]]) {
                    d[i][j] = d[i - 1][j - t[change[i - 1]]] + p[change[i - 1]];
                }
                if (d[i][j] <= d[i - 1][j]) {
                    d[i][j] = d[i - 1][j];
                    way[i][j] = j;
                } else {
                    way[i][j] = j - t[change[i - 1]];
                }
            }
        }
        for (i = 1; i <= T; i++) {
            if (d[n][i] > d[n][tAns]) {
                tAns = i;
            }
        }
        j = tAns; i = n;
        while (i > 0) {
            if (d[i][j] != d[i - 1][j]) {
                s[len++] = change[i - 1] + 1;
            }
            j = way[i--][j];
        }
        /*for (i = 0; i <= n; i++) {
            for (j = 0; j <= T; j++) {
                out.write(d[i][j] + " ");
            }
            out.write('\n');
        }
        out.write('\n');
        for (i = 0; i <= n; i++) {
            for (j = 0; j <= T; j++) {
                out.write(way[i][j] + " ");
            }
            out.write('\n');
        }*/
        out.write(d[n][tAns] + "" + '\n');
        for (i = len - 1; i > 0; i--)
            out.write(s[i] + " ");
        if (i >=0) out.write(s[i] + "");
    }

    private void doSort(int[] array, int start, int end) {
        if (start >= end)
            return;
        int i = start, j = end;
        int cur = i - (i - j) / 2;
        while (i < j) {
            while (i < cur && (array[i] <= array[cur])) {
                i++;
            }
            while (j > cur && (array[cur] <= array[j])) {
                j--;
            }
            if (i < j) {
                int temp = array[i];
                array[i] = array[j];
                array[j] = temp;
                temp = change[i];
                change[i] = change[j];
                change[j] = temp;
                if (i == cur)
                    cur = j;
                else if (j == cur)
                    cur = i;
            }
        }
        doSort(array, start, cur);
        doSort(array, cur + 1, end);
    }

    public void quickSort(int[] array) {
        int startIndex = 0;
        int endIndex = array.length - 1;
        doSort(array, startIndex, endIndex);
    }


    public void run() {
        try {
            in = new FastScanner(new File("practice.in"));
            out = new PrintWriter(new File("practice.out"));

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
        new I().run();
    }
}


