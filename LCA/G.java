import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class G {
    private FastScanner in;
    private PrintWriter out;
    private int n, logn, number;
    private int parent[], depth[], degreeTwo[];
    private int dp[][];
    private DSU DSU_tree;
    private ArrayList<Integer> edges[];

    private void solve() throws IOException {
        int m = in.nextInt();
        n = 200_000;
        inisialisation(n);

        preProcess();

        for (int i = 0; i < m; i++) {
            String str = in.next();
            if (str.charAt(0) == '+') {
                addEdge(in.nextInt() - 1);
            } else if (str.charAt(0) == '?') {
                out.write(LCA(in.nextInt() - 1, in.nextInt() - 1) + 1 + "" + '\n');
            } else {
                deleteVertex(in.nextInt() - 1);
            }
        }

    }

    class DSU {
        private int parent[], rang[];
        private boolean isDead[];
        int nearLive[];

        DSU(int size) {
            parent = new int[size];
            rang = new int[size];
            nearLive = new int[size];
            isDead = new boolean[size];

            for (int i = 0; i < size; i++) {
                rang[i] = 0;
                parent[i] = i;
                nearLive[i] = i;
            }
        }

        int get(int x) {
            if (parent[x] != x)
                parent[x] = get(parent[x]);
            return parent[x];
        }

        void union(int x, int y) {
            int oldX = x, oldY = y;
            x = get(x);
            y = get(y);

            int nearL = depth[oldX] < depth[oldY] ? nearLive[x] : nearLive[y];

            if (x == y) return;

            if (rang[x] == rang[y])
                rang[x]++;
            if (rang[x] < rang[y]) {
                parent[x] = y;
                nearLive[y] = nearL;
            } else {
                parent[y] = x;
                nearLive[x] = nearL;
            }
        }

        int getNearLive(int x) {
            return nearLive[get(x)];
        }

        boolean isDead(int x) {
            return isDead[x];
        }
    }

    private void deleteVertex(int v) {
        DSU_tree.isDead[v] = true;
        DSU_tree.nearLive[v] = DSU_tree.nearLive[parent[v]];

        if (DSU_tree.isDead(parent[v])) {
            DSU_tree.union(v, parent[v]);
        }

        for (int u : edges[v]) {
            if (DSU_tree.isDead(u)) {
                DSU_tree.union(u, v);
            }
        }
    }

    private void addEdge(int v) {
        number++;
        parent[number] = v;
        edges[v].add(number);
        depth[number] = depth[v] + 1;

        dp[number][0] = v;
        for (int j = 1; j < logn; j++) {
            dp[number][j] = dp[dp[number][j - 1]][j - 1];
        }
    }

    private void inisialisation(int size) {
        number = 0;
        parent = new int[size];
        DSU_tree = new DSU(size);
        edges = new ArrayList[size];
        depth = new int[size];
        logn = (int) (Math.log(size) / Math.log(2)) + 1;
        dp = new int[size][logn + 1];
        degreeTwo = new int[logn + 1];

        for (int i = 0; i < size; i++) {
            edges[i] = new ArrayList<>();
        }
    }

    private void preProcess() {
        degreeTwo[0] = 1;
        for (int i = 1; i < logn + 1; i++) {
            degreeTwo[i] = degreeTwo[i - 1] * 2;
        }
    }

    private int LCA(int v, int u) {
        if (depth[v] > depth[u]) {
            int tmp = v;
            v = u;
            u = tmp;
        }
        for (int i = logn - 1; i >= 0; i--) {
            if (depth[u] - depth[v] >= degreeTwo[i]) {
                u = dp[u][i];
            }
        }
        if (v == u) return v;
        for (int i = logn - 1; i >= 0; i--) {
            if (dp[v][i] != dp[u][i]) {
                v = dp[v][i];
                u = dp[u][i];
            }
        }
        return DSU_tree.getNearLive(parent[v]);
    }

    private void run() {
        try {
            in = new FastScanner(new File("carno.in"));
            out = new PrintWriter(new File("carno.out"));

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
        new G().run();
    }
}