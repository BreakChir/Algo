import java.io.*;
import java.util.*;
 
public class D {
    private String pattern, s;
    private int[] zFunc;
    private int count = 0;
 
    private void solve() throws IOException {
        InputReader in = new InputReader(System.in);
        OutputWriter out = new OutputWriter(System.out);
 
        StringBuilder sBuild = new StringBuilder();
        pattern = in.readString();
        sBuild.append(pattern);
        sBuild.append('#');
        sBuild.append(in.readString());
        s = sBuild.toString();
 
        preprocess();
 
        out.printLineln(count);
        for (int i = 0; i < zFunc.length; i++) {
            if (zFunc[i] == pattern.length()) {
                out.print(i - pattern.length());
                out.print(" ");
            }
        }
 
        out.close();
    }
 
    private void preprocess() {
        zFunc = new int[s.length()];
        int left = 0, right = 0;
        for (int i = 1; i <= s.length() - 1; i++) {
            zFunc[i] = Math.max(0, Math.min(right - i, zFunc[i - left]));
            while (i + zFunc[i] < s.length() && s.charAt(zFunc[i]) == s.charAt(i + zFunc[i]))
                zFunc[i]++;
            if (zFunc[i] == pattern.length()) count++;
            if (i + zFunc[i] > right) {
                left = i;
                right = i + zFunc[i];
            }
        }
    }
 
 
    private void run() {
        try {
 
            solve();
 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
    static class InputReader {
 
        private InputStream stream;
        private byte[] buf = new byte[1024];
        private int curChar;
        private int numChars;
        private SpaceCharFilter filter;
 
        public InputReader(InputStream stream) {
            this.stream = stream;
        }
 
        public int read() {
            if (numChars == -1)
                throw new InputMismatchException();
            if (curChar >= numChars) {
                curChar = 0;
                try {
                    numChars = stream.read(buf);
                } catch (IOException e) {
                    throw new InputMismatchException();
                }
                if (numChars <= 0)
                    return -1;
            }
            return buf[curChar++];
        }
 
        public int readInt() {
            int c = read();
            while (isSpaceChar(c))
                c = read();
            int sgn = 1;
            if (c == '-') {
                sgn = -1;
                c = read();
            }
            int res = 0;
            do {
                if (c < '0' || c > '9')
                    throw new InputMismatchException();
                res *= 10;
                res += c - '0';
                c = read();
            } while (!isSpaceChar(c));
            return res * sgn;
        }
 
        public String readString() {
            int c = read();
            while (isSpaceChar(c))
                c = read();
            StringBuilder res = new StringBuilder();
            do {
                res.appendCodePoint(c);
                c = read();
            } while (!isSpaceChar(c));
            return res.toString();
        }
 
        public boolean isSpaceChar(int c) {
            if (filter != null)
                return filter.isSpaceChar(c);
            return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
        }
 
        public String next() {
            return readString();
        }
 
        public interface SpaceCharFilter {
            boolean isSpaceChar(int ch);
        }
    }
 
    class OutputWriter {
        private final PrintWriter writer;
 
        public OutputWriter(OutputStream outputStream) {
            writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream)));
        }
 
        public OutputWriter(Writer writer) {
            this.writer = new PrintWriter(writer);
        }
 
        public void print(Object... objects) {
            for (int i = 0; i < objects.length; i++) {
                if (i != 0)
                    writer.print(' ');
                writer.print(objects[i]);
            }
        }
 
        public void printLine(Object... objects) {
            print(objects);
        }
 
        public void printLineln(Object... objects) {
            print(objects);
            writer.println();
        }
 
        public void close() {
            writer.close();
        }
 
        public void flush() {
            writer.flush();
        }
 
    }
 
    public static void main(String[] arg) {
        new D().run();
    }
}