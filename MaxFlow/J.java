import java.io.*;
import java.util.*;

public class J {
    long[][] len;
    int[] x1, x2, y1, y2;
    int n, W;

    private void solve() throws IOException {
        n = nextInt();
        W = nextInt();
        len = new long[n + 2][n + 2];
        x1 = new int[n];
        x2 = new int[n];
        y1 = new int[n];
        y2 = new int[n];

        for (int i = 0; i < n; i++) {
            x1[i] = nextInt();
            y1[i] = nextInt();
            x2[i] = nextInt();
            y2[i] = nextInt();
        }

        len[0][n + 1] = len[n + 1][0] = W;
        for (int i = 0; i < n; i++) {
            int minY = Math.min(y1[i], y2[i]);
            int maxY = Math.max(y1[i], y2[i]);
            len[n + 1][1 + i] = len[1 + i][n + 1] = minY;
            len[0][1 + i] = len[1 + i][0] = W - maxY;
            for (int j = 0; j < n; j++) {
                if (i == j) continue;
                int horiz = (x1[i] <= x1[j]) ? x1[j] - x2[i] : x1[i] - x2[j];
                int vert = (y1[i] <= y1[j]) ? y1[j] - y2[i] : y1[i] - y2[j];
                len[1 + i][1 + j] = len[1 + j][1 + i] = Math.max(0, Math.max(vert, horiz));
            }
        }

        long ans = dijkstra(n + 2, 0, n + 1);
        if (ans == Long.MAX_VALUE) out.write("0");
        else out.write(ans + "");
    }

    long dijkstra(int sz, int from, int to) {
        long[] dist = new long[sz];
        boolean[] used = new boolean[sz];
        Arrays.fill(dist, Long.MAX_VALUE);
        dist[from] = 0;

        for (int i = 0; i < sz; i++) {
            int v = -1;
            for (int j = 0; j < sz; j++) {
                if (!used[j] && (v == -1 || dist[j] < dist[v]))
                    v = j;
            }
            if (dist[v] == Long.MAX_VALUE) break;
            used[v] = true;
            for (int u = 0; u < sz; u++) {
                if (v == u) continue;
                long l = len[v][u];
                if (dist[v] + l < dist[u]) {
                    dist[u] = dist[v] + l;
                }
            }
        }
        return dist[to];
    }

    // ------------------

//    private BufferedReader br;
//    private PrintWriter out;
    private StringTokenizer st;
    private PrintWriter out = new PrintWriter(System.out);
    private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    private String next() throws IOException {
        while (st == null || !st.hasMoreTokens()) {
            st = new StringTokenizer(br.readLine());
        }
        return st.nextToken();
    }

    private int nextInt() throws IOException {
        return Integer.parseInt(next());
    }

    public static void main(String[] args) throws IOException {
        new J().run();
    }

    private void run() throws IOException {
        try {
//            br = new BufferedReader(new InputStreamReader(new FileInputStream("input.txt")));
//            out = new PrintWriter(new File("output.txt"));

            solve();
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}