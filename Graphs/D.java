import java.io.*;
import java.util.*;

public class D {
    FastScanner in;
    PrintWriter out;
    int[] b, e;
    int n, m;
    int[] color;
    int size = 0;
    ArrayList<Integer> mas[];
    int startV = -1, endV;
    int[] p;
    int[] d;

    public void solve() throws IOException {
        n = in.nextInt();
        m = in.nextInt();
        b = new int[m];
        e = new int[m];
        color = new int[n];
        p = new int[n];
        d = new int[n];
        mas = new ArrayList[n];
        for (int i = 0; i < n; ++i) {
            mas[i] = new ArrayList<>();
        }
        for (int i = 0; i < m; i++) {
            b[i] = in.nextInt() - 1;
            e[i] = in.nextInt() - 1;
            mas[b[i]].add(e[i]);
        }
        for (int i = 0; i < n; i++) {
            if (color[i] == 0) {
                if (dfs(i)) break;
            }
        }
        if (startV == -1) {
            out.print("NO");
        } else {
            out.println("YES");
            for (int v = endV; v != startV; v = p[v]) {
                d[size++] = v + 1;
            }
            d[size++] = startV + 1;
            for (int i = size - 1; i >= 0; i--) {
                out.print(d[i]);
                out.print(" ");
            }
        }
    }

    boolean dfs(int v) {
        color[v] = 1;
        for (int i = 0; i < mas[v].size(); i++) {
            int u = mas[v].get(i);
            if (color[u] == 0) {
                p[u] = v;
                if (dfs(u)) return true;
            }
            if (color[u] == 1) {
                startV = u;
                endV = v;
                return true;
            }
        }
        color[v] = 2;
        return false;
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
            in = new FastScanner(new File("cycle.in"));
            out = new PrintWriter(new File("cycle.out"));

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



