#include <iostream>
#include <map>
#include <unordered_map>
 
using namespace std;
 
int *len;
int *link;
int last, sizeGL;
long *countWays;
int *pos;
bool **exist;
bool **was;
unordered_map<char, int> *nextTo;
 
void init(int size, int k) {
    len = new int[size];
    link = new int[size];
    countWays = new long[size];
    exist = new bool *[size];
    was = new bool *[size];
    pos = new int[size];
    nextTo = new unordered_map<char, int>[size];
 
    for (int i = 0; i < size; ++i) {
        len[i] = 0;
        link[i] = 0;
        pos[i] = 0;
        countWays[i] = 0;
 
        exist[i] = new bool[k];
        was[i] = new bool[k];
        for (int j = 0; j < k; ++j) {
            exist[i][j] = false;
            was[i][j] = false;
        }
    }
 
    last = 0;
    link[0] = -1;
    sizeGL = 1;
}
 
 
bool dfs(int v, char c) {
    was[v][c - 'A'] = true;
    for (auto it : nextTo[v]) {
//        cout << v << " " << it.first << " " << it.second << '\n';
        if (it.first == c) {
            exist[v][c - 'A'] = true;
            return true;
        } else if (it.first < 'Z') {
            continue;
        } else if (was[it.second][c - 'A']) {
            if (exist[it.second][c - 'A']) {
                exist[v][c - 'A'] = true;
                return true;
            }
            continue;
        } else {
            if (dfs(it.second, c)) {
                exist[v][c - 'A'] = true;
                return true;
            }
        }
    }
    return exist[v][c - 'A'];
}
 
 
void add(char c, int position) {
    len[sizeGL] = len[last] + 1;
    pos[sizeGL] = position;
    int cur = sizeGL++;
 
    int p = last;
    while (p >= 0 && nextTo[p][c] == 0) {
        nextTo[p][c] = cur;
        p = link[p];
    }
 
    if (p != -1) {
        int q = nextTo[p][c];
        if (len[p] + 1 == len[q]) {
            link[cur] = q;
        } else {
            link[sizeGL] = link[q];
            nextTo[sizeGL] = nextTo[q];
            int node = sizeGL++;
            len[node] = len[p] + 1;
            pos[node] = position;
 
            link[q] = node;
            link[cur] = node;
 
            while (p >= 0 && nextTo[p][c] == q) {
                nextTo[p][c] = node;
                p = link[p];
            }
        }
    }
    last = cur;
}
 
int main() {
    std::ios::sync_with_stdio(false);
    freopen("substr3.in", "r", stdin);
    freopen("substr3.out", "w", stdout);
 
    int k;
    cin >> k;
    init(2000000, k);
    string s;
    char symbol = 'A';
    for (int i = 0; i < k; ++i) {
        string t;
        cin >> t;
        auto lenLast = (int) s.length();
        s += t;
        size_t length = t.length();
        for (size_t j = 0; j < length; ++j) {
            add(t[j], (int) (lenLast + j));
        }
        s += symbol;
        add(symbol, (int) s.length() - 1);
        ++symbol;
    }
 
    char c = 'A';
    for (int i = 0; i < k; ++i) {
        for (int j = 0; j < sizeGL; ++j) {
            if (!was[j][i]) {
                if (dfs(j, c)) {
                    exist[j][i] = true;
                }
//                cout << "kek\n";
            }
        }
        ++c;
    }
 
    int pos1 = 0;
    int maxLen = 0;
    for (int i = 0; i < sizeGL; ++i) {
        bool success = true;
        for (int j = 0; j < k; ++j) {
            if (!exist[i][j]) {
                success = false;
                break;
            }
        }
        if (success) {
            if (len[i] > maxLen) {
                maxLen = len[i];
                pos1 = i;
            }
        }
    }
 
    for (int i = pos[pos1] - maxLen + 1; i <= pos[pos1]; ++i) {
        cout << s[i];
    }
    cout << '\n';
 
//    cout << pos1;
 
//    for (int i = 0; i < sizeGL; ++i) {
//        for (int j = 0; j < k; ++j) {
//            cout << exist[i][j] << " ";
//        }
//        cout << '\n';
//    }
 
 
 
//    for (int i = 0; i < sizeGL; ++i) {
//        cout << i << " " << len[i] << " " << link[i] << " ";
//        cout << "next :\n";
//        for (char cc = 'a'; cc <= symbol; ++cc) {
//            if (nextTo[i][cc] != 0) cout << nextTo[i][cc] << " " << cc << "   ";
//        }
//        cout << '\n';
//    }
 
    return 0;
}