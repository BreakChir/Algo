import java.io.*;
import java.util.*;

public class F {
    FastScanner in;
    PrintWriter out;
    int n, m;
    int[] timeout;
    int[] comp;

    boolean[] used;
    int sizeL;
    boolean[][] exist;
    int countV, countE;
    ArrayList<Integer> left[];
    ArrayList<Integer> right[];
    int countComp = 0;
    public void solve() throws IOException {
        n = in.nextInt();
        m = in.nextInt();
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
            int kek = in.nextInt() - 1;
            int lol = in.nextInt() - 1;
            left[kek].add(lol);
            right[lol].add(kek);
        }

        for (int i = 0; i < n; ++i) {
            if (!used[i])
                dfsF(i);
        }
        for (int i = 0; i < n; i++)
            used[i] = false;
        exist = new boolean[n][n];
        for (int i = 0; i < n; ++i) {
            int v = timeout[n - 1 - i];
            if (!used[v]) {
                countV++;
                dfsG(v);
                countComp++;
            }
        }
        out.println(countV + " " + countE);
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
            } else if (!exist[comp[v]][comp[u]] && comp[v] != comp[u]){
                exist[comp[v]][comp[u]] = true;
                countE++;
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
        new F().run();
    }
}
