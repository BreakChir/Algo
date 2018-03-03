import java.io.*;
import java.util.HashMap;
import java.util.StringTokenizer;
 
public class J {
    private FastScanner in;
    private PrintWriter out;
    private String s;
    private int[] sufArr;
    private int[] lcp;
    int n;
    int countSub = 0;
 
    private void solve() throws IOException {
        s = in.next();
        s += '`';
        n = s.length();
        sufArr = new int[n];
        lcp = new int[n];
        buildSufArr();
        buildLCP();
 
        for (int i = 1; i < n; i++) {
            out.print(sufArr[i] + 1);
            out.print(" ");
        }
        out.print('\n');
        for (int i = 1; i < n - 1; i++) {
            out.print(lcp[i]);
            out.print(" ");
        }
        out.print('\n');
 
    }
 
    private void calculate() {
        for (int i = 1; i < n; i++) {
            countSub += (n - sufArr[i]);
        }
        for (int i = 1; i < n - 1; i++) {
            countSub -= lcp[i];
        }
    }
 
    private void buildLCP() {
        int k = 0;
        int[] pos = new int[n];
        for (int i = 0; i < n; i++) {
            pos[sufArr[i]] = i;
        }
        for (int i = 0; i < n; i++) {
            if (k > 0) k--;
            if (pos[i] == n - 1) {
                lcp[n - 1] = -1;
                k = 0;
            } else {
                int j = sufArr[pos[i] + 1];
                while (Math.max(i + k, j + k) < n && s.charAt(i + k) == s.charAt(j + k))
                    k++;
                lcp[pos[i]] = k;
            }
        }
    }
 
    private void buildSufArr() {
        int[] eqClass = new int[n];
        {
            int[] count = new int['z' - '`' + 1];
 
            for (int i = 0; i < n; i++) {
                count[s.charAt(i) - '`']++;
            }
            for (int i = 0; i < 'z' - '`'; i++) {
                count[i + 1] += count[i];
            }
 
            int[] used = new int['z' - '`' + 1];
            for (int i = 0; i < n; i++) {
                int symbol = s.charAt(i) - '`';
                int start = (symbol == 0 || count[symbol - 1] == 0) ? 0 : count[symbol - 1];
                sufArr[start + used[symbol]] = i;
                used[symbol]++;
            }
 
            eqClass[sufArr[0]] = 0;
            int last = 0;
            for (int i = 1; i < n; i++) {
                eqClass[sufArr[i]] = last;
                if (s.charAt(sufArr[i]) != s.charAt(sufArr[i - 1])) {
                    eqClass[sufArr[i]]++;
                }
                last = eqClass[sufArr[i]];
            }
        }
 
        for (int i = 1; i < n; i *= 2) {
            int[] count = new int[n];
            int[] beforeFirst = new int[n];
 
            for (int j = 0; j < n; j++) {
                beforeFirst[j] = (sufArr[j] - i + n) % n;
                count[eqClass[beforeFirst[j]]]++;
            }
            for (int j = 0; j < n - 1; j++) {
                count[j + 1] += count[j];
            }
 
            for (int j = n - 1; j > -1; j--) {
                int second = beforeFirst[j];
                count[eqClass[second]]--;
                sufArr[count[eqClass[second]]] = second;
            }
 
 
            int[] newEqClass = new int[n];
            newEqClass[sufArr[0]] = 0;
            int last = 0;
            for (int j = 1; j < n; j++) {
                newEqClass[sufArr[j]] = last;
                if (!(eqClass[sufArr[j]] == eqClass[sufArr[j - 1]] &&
                        eqClass[(sufArr[j] + i) % n] == eqClass[(sufArr[j - 1] + i) % n])) {
                    newEqClass[sufArr[j]]++;
                }
                last = newEqClass[sufArr[j]];
            }
            if (last == n - 1) break;
            eqClass = newEqClass;
        }
 
    }
 
    private void run() {
        try {
            in = new FastScanner(new File("array.in"));
            out = new PrintWriter(new File("array.out"));
 
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
        new J().run();
    }
}