import java.io.*;
import java.util.*;

public class J {


    boolean[][] d;
    int INF = 2000000000;

    public void solve() throws IOException {
        int i, j, n;
        String P = "1", S = "1";
        Scanner in = new Scanner(System.in);
        if (in.hasNextLine())
        P = P.concat(in.nextLine());
        if (in.hasNextLine())
        S = S.concat(in.nextLine());

        if (P.length() > 1 && S.length() > 1) {
            d = new boolean[P.length()][S.length()];
            d[0][0] = true;
            for (i = 1; i < P.length(); i++)
                d[i][0] = false;
            for (i = 1; i < S.length(); i++)
                d[0][i] = false;

            for (i = 1; i < P.length(); i++) {
                if (P.charAt(i) == '*') {
                    d[i][0] = d[i - 1][0];
                }
            }
            for (i = 1; i < P.length(); i++) {
                for (j = 1; j < S.length(); j++) {
                    if (P.charAt(i) == '*') {
                        d[i][j] = d[i - 1][j] || d[i][j - 1] || d[i -1][j - 1];
                    } else if (P.charAt(i) == '?') {
                        d[i][j] = d[i - 1][j - 1];
                    }
                    else {
                        if (P.charAt(i) == S.charAt(j))
                            d[i][j] = d[i - 1][j - 1];
                    }
                }
            }
            if (d[P.length() - 1][S.length() - 1]) System.out.print("YES");
            else System.out.print("NO");
        }
        else if (S.length() == 1 && P.length() > 1) {
            int kek = 0;
            for (i = 1; i < P.length(); i++)
                if (P.charAt(i) != '*') {
                    kek = 1;
                    break;
                }
            if (kek == 0)
            System.out.print("YES");
            else System.out.print("NO");
        }
        else if (S.length() > 1 && P.length() == 1){
            System.out.print("NO");
        }
        else System.out.print("YES");
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
        new J().run();
    }
}

