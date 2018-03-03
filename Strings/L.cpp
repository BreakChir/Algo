#include <iostream>
#include <map>
 
using namespace std;
 
int *len;
int *link;
int last, sizeGL;
long *countWays;
bool *was;
map<char, int> *nextTo;
 
void init(int size) {
    len = new int[size];
    link = new int[size];
    countWays = new long[size];
    was = new bool[size];
    nextTo = new map<char, int>[size];
 
    for (int i = 0; i < size; ++i) {
        len[i] = 0;
        link[i] = 0;
        countWays[i] = 0;
        was[i] = false;
    }
 
    last = 0;
    link[0] = -1;
    sizeGL = 1;
}
 
long dfs(int v) {
    was[v] = true;
    if (nextTo[v].empty())
        ++countWays[v];
    for (auto it : nextTo[v]) {
        if (was[it.second]) countWays[v] += countWays[it.second];
        else countWays[v] += dfs(it.second);
    }
    return countWays[v];
}
 
void add(char c) {
    len[sizeGL] = len[last] + 1;
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
    freopen("shifts.in", "r", stdin);
    freopen("shifts.out", "w", stdout);
 
    init(2000000);
    string s;
    cin >> s;
    size_t length = s.length();
    for (size_t i = 0; i < length; ++i) {
        add(s[i]);
    }
    for (size_t i = 0; i < length; ++i) {
        add(s[i]);
    }
 
    countWays[0] = dfs(0);
    int k;
    cin >> k;
    bool exist = true;
    int cur = 0;
    string ans;
 
    bool yes;
    while (true) {
        bool good = false;
        yes = false;
        for (auto it : nextTo[cur]) {
            yes = true;
            int to = it.second;
            if (countWays[to] < k) {
                k -= countWays[to];
            } else {
                good = true;
                cur = to;
                ans += it.first;
                if (ans.length() == s.length()) {
                    yes = false;
                    break;
                }
                break;
            }
        }
        if (!yes) break;
        if (!good) {
            exist = false;
            break;
        }
    }
 
    if (exist) cout << ans;
    else cout << "IMPOSSIBLE";
 
    return 0;
}