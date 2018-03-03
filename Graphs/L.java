import java.io.*;
import java.util.*;

public class L {
    FastScanner in;
    PrintWriter out;
    int n, m, count;
    boolean[] used;
    boolean good = false;
    ArrayList<Integer> edge[];
    ArrayList<Integer> top;


    public void solve() throws IOException {
        n = in.nextInt();
        m = in.nextInt();
        edge = new ArrayList[n];
        used = new boolean[n];
        for (int i = 0; i < n; i++) {
            edge[i] = new ArrayList<>();
        }
        for (int i = 0; i < m; i++) {
            int first = in.nextInt() - 1;
            int second = in.nextInt() - 1;
            edge[first].add(second);
        }
        top = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (!used[i])
                dfs(i);
        }
        count = 1;
        for (int i = 0; i < n; i++) used[i] = false;
        dfsF(top.get(n - 1), 0);
        if (good) out.print("YES");
        else out.print("NO");
    }

    void dfs(int v) {
        used[v] = true;
        for (int i = 0; i < edge[v].size(); i++) {
            int u = edge[v].get(i);
            if (!used[u])
                dfs(u);
        }
        top.add(v);
    }

    void dfsF(int v, int cout) {
        if (good) return;
        if (edge[v].size() == 0 && top.get(0) == v) {
            good = true;
            return;
        }
        for (int i = 0; i < edge[v].size(); i++) {
            int u = edge[v].get(i);
            if (top.get(n - 1 - cout) == v && top.get(n - 1 - cout - 1) == u) {
                dfsF(u, cout + 1);
                return;
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

    public void run() {
        try {
            in = new FastScanner(new File("hamiltonian.in"));
            out = new PrintWriter(new File("hamiltonian.out"));

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

