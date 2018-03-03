import java.io.*;
import java.util.*;

public class N {
    Scanner sc = new Scanner(System.in);
    Map<String, Integer> map = new HashMap<>();
    ArrayList<Integer>[] edge, edgeG;
    int n, m, sizeTop, count;
    boolean[] used, exist;
    String s;
    boolean error = false;
    int[] comp, top;
    String[] names;

    public void solve() throws IOException {
        n = sc.nextInt();
        m = sc.nextInt();
        used = new boolean[2 * n];
        exist = new boolean[n];
        names = new String[n];
        edge = new ArrayList[2 * n];
        edgeG = new ArrayList[2 * n];
        comp = new int[2 * n];
        top = new int[2 * n];
        for (int i = 0; i < n; i++) {
            s = sc.next();
            map.put(s, i);
            names[i] = s;
            edge[i] = new ArrayList<>();
            edgeG[i] = new ArrayList<>();
            comp[i] = -1;
        }
        for (int i = n; i < 2 * n; i++) {
            edge[i] = new ArrayList<>();
            edgeG[i] = new ArrayList<>();
            comp[i] = -1;
        }
        for (int i = 0; i < m; i++) {
            s = sc.next();
            int left = map.get(s.substring(1, s.length()));
            if (s.charAt(0) == '-') left += n;
            s = sc.next();
            s = sc.next();
            int right = map.get(s.substring(1, s.length()));
            if (s.charAt(0) == '-') right += n;
            edge[left].add(right);
            edgeG[right].add(left);
            left = left < n ? left + n : left - n;
			right = right < n ? right + n : right - n;
			edge[right].add(left);
            edgeG[left].add(right);
        }
        for (int i = 0; i < 2 * n; i++)
            if (!used[i])
                dfsF(i);
        for (int i = 0, j = 0; i < 2 * n; i++) {
            int v = top[2 * n - i - 1];
            if (comp[v] == -1)
                dfsG(v, j++);
        }
        for (int i = 0; i < n; i++)
            if (comp[i] == comp[i + n]) {
                System.out.print("-1");
                error = true;
                break;
            }
        if (!error) {
            for (int i = 0; i < n; i++) {
                int ans = comp[i] > comp[i + n] ? i : i + n;
                if (ans < n) {
                    count++;
                    exist[ans] = true;
                }
            }
            if (count > 0) {
                System.out.println(count);
                for (int i = 0; i < n; i++) {
                    if (exist[i]) {
                        System.out.println(names[i]);
                    }
                }
            } else System.out.println(-1);
        }
    }

    void dfsF(int v) {
        used[v] = true;
        for (int i = 0; i < edge[v].size(); i++) {
            int u = edge[v].get(i);
            if (!used[u])
                dfsF(u);
        }
        top[sizeTop++] = v;
    }

    void dfsG(int v, int com) {
        comp[v] = com;
        for (int i = 0; i < edgeG[v].size(); i++) {
            int u = edgeG[v].get(i);
            if (comp[u] == -1)
                dfsG(u, com);
        }
    }

    public void run() {
        try {

            solve();

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
        new N().run();
    }
}
