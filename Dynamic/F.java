import java.io.*;
import java.util.*;

public class F {
    FastScanner in;
    PrintWriter out;
    int[][] L;
    String s;
    char[] palindrome;
    public void solve() throws IOException {
        int i, j, n;
        s = in.next();
        L = new int[s.length()][s.length()];
        for (i = 0; i < s.length(); i++)
            for (j = 0; j < s.length(); j++) {
                if (i == j) L[i][j] = 1;
                if (i > j) L[i][j] = 0;
                if (i < j) L[i][j] = -1;
            }
        int len = pal(0, s.length() - 1);
        len = L[0][s.length() - 1];
        out.write(len + " " + '\n');
        palindrome = new char[s.length()];
        int l = 0, r = s.length() - 1, pall = l, palr = r;
        while (l <= r) {
            if (l == r && L[l][r] == 1)
                palindrome[pall++] = s.charAt(l++);
            else {
                if (s.charAt(l) == s.charAt(r)) {
                    palindrome[pall++] = s.charAt(l++);
                    palindrome[palr--] = s.charAt(r--);
                }
                else {
                    if (L[l + 1][r] > L[l][r - 1]) l++;
                    else r--;
                }
            }
        }
        j = len / 2;
        if (len % 2 == 0) {
            for (i = 0; i < j; i++)
                out.write(palindrome[i] + "");
            for (i = len - j - 1; i >= 0; i--)
                out.write(palindrome[i] + "");
        }
        else {
            for (i = 0; i < j; i++)
                out.write(palindrome[i] + "");
            for (i = len - j - 1; i >= 0; i--)
                out.write(palindrome[i] + "");
        }
    }

    int pal(int l, int r) {
        if (L[l][r] == - 1) {
            if (s.charAt(l) == s.charAt(r)) {
                L[l][r] = pal(l + 1, r - 1) + 2;
            } else {
                L[l][r] = Math.max(pal(l + 1, r), pal(l, r - 1));
            }
        }
        return L[l][r];
    }



    public void run() {
        try {
            in = new FastScanner(new File("palindrome.in"));
            out = new PrintWriter(new File("palindrome.out"));

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
        new F().run();
    }
}

