import com.sun.org.apache.xalan.internal.xsltc.dom.MultiValuedNodeHeapIterator;
 
import java.io.*;
import java.util.*;
 
public class C {
    FastScanner in;
    PrintWriter out;
    int[][] d;
    int n, m;
    int INF = 1000000;
 
    public void solve() throws IOException {
        n = in.nextInt();
        m = in.nextInt();
        d = new int[n][n];
 
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                d[i][j] = INF;
        for (int i = 0; i < n; i++) d[i][i] = 0;
 
        for (int i = 0; i < m; i++) {
            int first = in.nextInt() - 1;
            int second = in.nextInt() - 1;
            int w = in.nextInt();
            d[first][second] = w;
        }
 
        for (int k = 0; k < n; k++)
            for (int i = 0; i < n; i++)
                for (int j = 0; j < n; j++)
                    d[i][j] = Math.min(d[i][j], d[i][k] + d[k][j]);
 
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++)
                out.print(d[i][j] + " ");
            out.println();
        }
    }
 
 
    public void run() {
        try {
            in = new FastScanner(new File("pathsg.in"));
            out = new PrintWriter(new File("pathsg.out"));
 
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
        new C().run();
    }
}