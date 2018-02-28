import java.io.*;
import java.util.*;

public class H {
    private FastScanner in;
    private PrintWriter out;
    private int n, logn, root;
    private int parent[], depth[], degreeTwo[];
    private int dp[][];
    private ArrayList<Integer> edges[];

    private void solve() throws IOException {
        n = in.nextInt();
        inisialisation(n);

        for (int i = 0; i < n; i++) {
            int x = in.nextInt() - 1;
            parent[i] = x;
            if (x != -1)
                edges[x].add(i);
            else {
                root = i;
                parent[i] = i;
            }
        }

        preProcess();

        int m = in.nextInt();
        for (int i = 0; i < m; i++) {
            if (Ancestor(in.nextInt() - 1, in.nextInt() - 1))
                out.write("1" + '\n');
            else out.write("0" + '\n');
        }
    }

    private void inisialisation(int size) {
        parent = new int[size];
        edges = new ArrayList[size];
        depth = new int[size];
        logn = (int) (Math.log(size) / Math.log(2)) + 1;
        dp = new int[size][logn + 1];
        degreeTwo = new int[logn + 1];

        for (int i = 0; i < size; i++) {
            edges[i] = new ArrayList<>();
        }
    }

    private void setDepth(int v, int d) {
        depth[v] = d;
        for (int u : edges[v]) {
            setDepth(u, d + 1);
        }
    }

    private void preProcess() {
        setDepth(root, 0);

        degreeTwo[0] = 1;
        for (int i = 1; i < logn + 1; i++) {
            degreeTwo[i] = degreeTwo[i - 1] * 2;
        }

        for (int i = 0; i < n; i++) {
            dp[i][0] = parent[i];
        }
        for (int j = 1; j < logn; j++) {
            for (int i = 0; i < n; i++) {
                dp[i][j] = dp[dp[i][j - 1]][j - 1];
            }
        }

    }

    private boolean Ancestor(int v, int u) {
        if (depth[v] >= depth[u]) return false;
        for (int i = logn - 1; i >= 0; i--) {
            if (depth[u] - depth[v] >= degreeTwo[i]) {
                u = dp[u][i];
            }
        }
        return (v == u);
    }



    private void run() {
        try {
            in = new FastScanner(new File("ancestor.in"));
            out = new PrintWriter(new File("ancestor.out"));

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

        char nextChar() {
            try {
                return (char) br.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return 0;
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

        int nextInt() {
            return Integer.parseInt(next());
        }

        long nextLong() {
            return Long.parseLong(next());
        }

        void close() {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        void nextLine() {
            try {
                br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] arg) {
        new H().run();
    }
}