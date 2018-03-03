import java.io.*;
import java.util.*;

public class A {
    FastScanner in;
    PrintWriter out;
    int[] b, e, w;
    int ans;
    int[] num;
    int[] rank;

    public void solve() throws IOException {
        int n = in.nextInt();
        int m = in.nextInt();
        b = new int[m];
        e = new int[m];
        w = new int[m];
        num = new int[n];
        rank = new int[n];
        for (int i = 0; i < m; i++) {
            b[i] = in.nextInt() - 1;
            e[i] = in.nextInt() - 1;
            w[i] = in.nextInt();
        }
        quickSort(w);
        for (int i = 0; i < n; i++) {
            num[i] = i;
        }
        for (int i = 0; i < m; i++) {
            if (union(b[i], e[i])) {
                ans += w[i];
            }
        }
        out.print(ans);
    }

    int set(int x) {
        return x == num[x] ? x : (num[x] = set(num[x]));
    }

    boolean union(int u, int v) {
        u = set(u);
        v = set(v);
        if (u == v)
            return false;
        if (rank[u] < rank[v]) {
            num[u] = v;
        } else {
            num[v] = u;
            if (rank[u] == rank[v])
                rank[u]++;
        }
        return true;
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
                temp = b[i];
                b[i] = b[j];
                b[j] = temp;
                temp = e[i];
                e[i] = e[j];
                e[j] = temp;
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

    int binsearch(int[] d, int x) {
        int l = 0, r = d.length, m;
        while (l < r) {
            m = (l + r) / 2;
            if (d[m] < x) {
                l = m + 1;
            } else r = m;
        }
        return r;
    }

    public void run() {
        try {
            in = new FastScanner(new File("spantree2.in"));
            out = new PrintWriter(new File("spantree2.out"));

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

