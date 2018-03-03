import java.io.*;
import java.util.*;

public class E {
    FastScanner in;
    PrintWriter out;
    int[] b, e;
    int n, m;
    int[] timeout;
    int[] comp;
    int countComp = 1;
    boolean[] used;
    int sizeL, sizeR;
    ArrayList<Integer> left[];
    ArrayList<Integer> right[];

    public void solve() throws IOException {
        n = in.nextInt();
        m = in.nextInt();
        b = new int[m];
        e = new int[m];
        used = new boolean[n];
        timeout = new int[n];
        comp = new int[n];
        left = new ArrayList[n];
        right = new ArrayList[n];
        for (int i = 0; i < n; ++i) {
            left[i] = new ArrayList<>();
            right[i] = new ArrayList<>();
        }
        for (int i = 0; i < m; i++) {
            b[i] = in.nextInt() - 1;
            e[i] = in.nextInt() - 1;
            left[b[i]].add(e[i]);
            right[e[i]].add(b[i]);
        }

        for (int i = 0; i < n; ++i) {
            if (!used[i])
                dfsF(i);
        }
        for (int i = 0; i < n; i++)
            used[i] = false;
        for (int i = 0; i < n; ++i) {
            int v = timeout[n - 1 - i];
            if (!used[v]) {
                dfsG(v);
                countComp++;
            }
        }
        out.println(countComp - 1);
        for (int i = 0; i < n; i++) {
            out.print(comp[i]);
            out.print(" ");
        }
    }

    void dfsF(int v) {
        used[v] = true;
        for (int j = 0; j < left[v].size(); j++) {
            int u = left[v].get(j);
            if (!used[u]) {
                dfsF(u);
            }
        }
        timeout[sizeL++] = v;
    }

    void dfsG(int v) {
        used[v] = true;
        comp[v] = countComp;
        for (int j = 0; j < right[v].size(); j++) {
            int u = right[v].get(j);
            if (!used[u]) {
                dfsG(u);
            }
        }
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
            in = new FastScanner(new File("strong.in"));
            out = new PrintWriter(new File("strong.out"));

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
