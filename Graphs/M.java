import java.io.*;
import java.util.*;

public class M {
    FastScanner in;
    PrintWriter out;
    ArrayList<Integer> edge[];
    int n, m, s;
    boolean[] used;
    boolean winner;


    public void solve() throws IOException {
        n = in.nextInt();
        m = in.nextInt();
        s = in.nextInt() - 1;
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

        winner = dfs(s, true);
        if (winner) out.print("First player wins");
        else out.print("Second player wins");

    }

    boolean dfs(int v, boolean step) {
        for (int i = 0; i < edge[v].size(); i++) {
            int u = edge[v].get(i);
            if (dfs(u, !step) == step)
                return step;
        }
        return !step;
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
            in = new FastScanner(new File("game.in"));
            out = new PrintWriter(new File("game.out"));

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
        new M().run();
    }
}
