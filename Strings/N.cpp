#include <iostream>
#include <unordered_map>
 
using namespace std;
 
int *len;
int *link;
int last, sizeGL;
long *countWays;
bool *was;
unordered_map<char, int> *nextTo;
 
void init(int size) {
    len = new int[size];
    link = new int[size];
    countWays = new long[size];
    was = new bool[size];
    nextTo = new unordered_map<char, int>[size];
 
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
    freopen("common.in", "r", stdin);
    freopen("common.out", "w", stdout);
 
    init(2000000);
    string s, t;
    cin >> s;
    cin >> t;
    size_t length = s.length();
    for (size_t i = 0; i < length; ++i) {
        add(s[i]);
    }
 
//    for (int i = 0; i < sizeGL; ++i) {
//        cout << i << " " << len[i] << " " << link[i] << " ";
//        cout << "next :\n";
//        for (char c = 'a'; c <= 'z'; ++c) {
//            if (nextTo[i][c] != 0) cout << nextTo[i][c] << " " << c << "   ";
//        }
//        cout << '\n';
//    }
 
    int len1 = 0;
    int cur = 0;
    auto lenT = (int) t.length();
    int maxLen = 0;
    int pos = 0;
    for (int i = 0; i < lenT; ++i) {
        char c = t[i];
        if (nextTo[cur].count(c) != 0) {
            ++len1;
            cur = nextTo[cur][c];
        } else {
            while (cur != -1 && nextTo[cur].count(c) == 0) {
                cur = link[cur];
                len1 = len[cur];
            }
            if (cur == -1) {
                cur = 0;
                len1 = 0;
            } else if (nextTo[cur].count(c) != 0) {
                ++len1;
                cur = nextTo[cur][c];
            }
        }
        if (len1 > maxLen) {
            maxLen = len1;
            pos = i;
        } else if (len1 == maxLen) {
            for (int j1 = pos - maxLen + 1, j2 = i - len1 + 1; j1 <= pos; ++j1, ++j2) {
                if (t[j2] < t[j1]) {
                    pos = i;
                    break;
                } else if (t[j2] > t[j1]) break;
            }
        }
    }
 
    for (int i = pos - maxLen + 1; i <= pos; ++i) {
        cout << t[i];
    }
 
 
    return 0;
}