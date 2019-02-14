import java.io.*;
import java.util.*;

public class K {
    FastScanner in;
    PrintWriter out;


    int[] d;
    int[] cur;
    int n, k, m, one;
    public void solve() throws IOException {
        int i, ans, p;
        n = in.nextInt();
        k = in.nextInt();
        m = in.nextInt();
        cur = new int[m];
        d = new int[m];
        for (i = 0; i < m; i++)
            d[i] = in.nextInt();
        quickSort(d);
        for (i = 0; i < m; i++) {
            p = BinS(d, i);
            if (p != -1)
                cur[i] = cur[p] + d[i] - d[p] - k;
            else cur[i] = Math.max(0, d[i] - k);
        }
        ans = cur[m - 1] + n - d[m - 1];
        if (d[0] + k - 1 >= d[m - 1])
            ans = Math.max(ans, Math.max(0, n - d[0] - k + 1) + d[0] - 1 );
        for (i = 1; i < m; i++) {
            if (d[i] + k - 1 >= d[m - 1])
                ans = Math.max(ans, cur[i - 1] + Math.max(0, n - d[i] - k + 1) + d[i] - d[i - 1] - 1);
        }
        out.write(ans + "");
    }

    private int BinS(int[] a, int i) {
        int l = 0, r = i + 1, med, ans;
        while (l < r) {
            med = (l + r) / 2;
            if (a[i] - a[med] + 1 > k)
                l = med + 1;
            else r = med;
        }
        if (l == 0 && a[i] - a[l] + 1 <= k) ans = -1;
        else ans =  l == 0 ? 0 : l - 1;
        if (i == m - 1) one = ans + 1;
        return ans;
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
            in = new FastScanner(new File("ski.in"));
            out = new PrintWriter(new File("ski.out"));

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
        new K().run();
    }
}

