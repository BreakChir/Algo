import java.io.*;
import java.util.*;

public class H {
    FastScanner in;
    PrintWriter out;
    ArrayList<Integer> edge[];
    int n, m, time = 0, countBridge = 0;
    boolean[] used, numBridge;
    int[] timeIn, d;


    public void solve() throws IOException {
        n = in.nextInt();
        m = in.nextInt();
        edge = new ArrayList[n];
        timeIn = new int[n];
        numBridge = new boolean[n];
        used = new boolean[n];
        d = new int[n];
        for (int i = 0; i < n; i++) {
            edge[i] = new ArrayList<>();
        }
        for (int i = 0; i < m; i++) {
            int first = in.nextInt() - 1;
            int second = in.nextInt() - 1;
            edge[first].add(second);
            edge[second].add(first);
        }

        for (int i = 0; i < n; i++) {
            if (!used[i]) {
                time = 0;
                dfs(i, -1);
            }
        }
        out.println(countBridge);
        for (int i = 0; i < n; i++) {
            if (numBridge[i]) {
                out.print(i + 1);
                out.print(" ");
            }
        }
    }

    void dfs(int v, int p ) {
        used[v] = true;
        timeIn[v] = time;
        d[v] = time;
        time++;
        int children = 0;
        for (int i = 0; i < edge[v].size(); i++) {
            int u = edge[v].get(i);
            if (u == p) continue;
            if (used[u]) {
                d[v] = Math.min(d[v], timeIn[u]);
            } else {
                dfs(u, v);
                d[v] = Math.min(d[v], d[u]);
                if (timeIn[v] <= d[u] && p != - 1 && !numBridge[v]) {
                    numBridge[v] = true;
                    countBridge++;
                }
                children++;
            }
        }
        if (p == -1 && children > 1 && !numBridge[v]) {
            numBridge[v] = true;
            countBridge++;
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
            in = new FastScanner(new File("points.in"));
            out = new PrintWriter(new File("points.out"));

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
        new H().run();
    }
}
