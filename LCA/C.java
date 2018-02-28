import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class C {
    private FastScanner in;
    private PrintWriter out;
    private int n, logn, vertexSize;
    private int depth[], degreeTwo[], placeInArray[], vertex[];
    private ArrayList<Integer> edges[];
    private SparseTable GraphLCA;

    private void solve() throws IOException {
        n = in.nextInt();
        int m = in.nextInt();
        inisialisation(n);

        for (int i = 1; i < n; i++) {
            int x = in.nextInt();
            edges[x].add(i);
        }

        preProcess();

        long a1 = in.nextInt(), a2 = in.nextInt();
        long x = in.nextInt(), y = in.nextInt(), z = in.nextInt();
        long left = a1;
        long sum = 0;

        for (int i = 0; i < m; i++) {
            int min = GraphLCA.min((int) left, (int) a2);
            sum += min;
            long a1New = ((x * a1 + y * a2 + z) % n);
            long a2New = ((x * a2 + y * a1New + z) % n);
            left = ((a1New + min) % n);
            a1 = a1New;
            a2 = a2New;
        }

        out.write(sum + "");

    }

    private void inisialisation(int size) {
        edges = new ArrayList[size];
        depth = new int[size];
        vertex = new int[2 * size - 1];
        placeInArray = new int[size];
        logn = (int) (Math.log(2 * size - 1) / Math.log(2)) + 1;
        degreeTwo = new int[logn + 1];
        GraphLCA = new SparseTable(2 * size - 1);

        for (int i = 0; i < n; i++) {
            edges[i] = new ArrayList<>();
        }
    }

    private void preProcess() {
        degreeTwo[0] = 1;
        for (int i = 1; i < logn + 1; i++) {
            degreeTwo[i] = degreeTwo[i - 1] * 2;
        }

        setValues(0, 0);
        GraphLCA.buildSparseTable(vertex);
    }

    private void setValues(int v, int d) {
        placeInArray[v] = vertexSize;
        depth[v] = d;
        vertex[vertexSize++] = v;
        for (int u : edges[v]) {
            setValues(u, d + 1);
            vertex[vertexSize++] = v;
        }
    }

    class SparseTable {
        int ST[][];
        int logTable[];
        int size;

        SparseTable(int size) {
            this.size = size;

            logTable = new int[size + 1];

            for (int i = 2; i <= 2 * n - 1; i++) {
                logTable[i] = logTable[i >> 1] + 1;
            }

            ST = new int[size][logTable[size] + 1];
        }

        void buildSparseTable(int[] arr) {
            for (int i = 0; i < 2 * n - 1; i++) {
                ST[i][0] = arr[i];
            }

            for (int j = 1; degreeTwo[j] <= size; j++) {
                for (int i = 0; i + degreeTwo[j - 1] < size; i++) {
                    if (depth[ST[i][j - 1]] < depth[ST[i + degreeTwo[j - 1]][j - 1]]) {
                        ST[i][j] = ST[i][j - 1];
                    } else {
                        ST[i][j] = ST[i + degreeTwo[j - 1]][j - 1];
                    }
                }
            }
        }

        int min(int v, int u) {
            int l = placeInArray[v], r = placeInArray[u];

            if (l > r) {
                int tmp = l;
                l = r;
                r = tmp;
            }

            int j = logTable[r - l + 1];
            int value;

            if (depth[ST[l][j]] < depth[ST[r - degreeTwo[j] + 1][j]]) {
                value = ST[l][j];
            } else {
                value = ST[r - degreeTwo[j] + 1][j];
            }

            return value;
        }
    }

    private void run() {
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
        new C().run();
    }
}