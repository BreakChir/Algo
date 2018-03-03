#include <iostream>
#include <vector>
#include <queue>
 
using namespace std;
 
int size = 'z' - 'a' + 2;
int *parent;
int *suffLink;
int *up;
int *countForCur;
bool *isLeaf;
bool *was;
char *from;
int **nextTo;
int **go;
vector<vector<int>> leafNumOfStr;
 
void init() {
    int len = 500000;
    parent = new int[len];
    suffLink = new int[len];
    up = new int[len];
    countForCur = new int[len];
    isLeaf = new bool[len];
    was = new bool[500000];
    from = new char[len];
    nextTo = new int *[len];
    go = new int *[len];
    leafNumOfStr.resize((unsigned) len);
 
    for (int i = 0; i < len; i++) {
        nextTo[i] = new int[size];
        go[i] = new int[size];
 
        parent[i] = 0;
        suffLink[i] = 0;
        up[i] = 0;
        countForCur[i] = 0;
        was[i] = false;
        isLeaf[i] = false;
        for (int j = 0; j < size; j++) {
            go[i][j] = 0;
            nextTo[i][j] = 0;
        }
    }
}
 
void setLinks() {
    std::queue<int> queue;
    queue.push(1);
    parent[1] = 0;
    from[1] = 'z' + 1;
    while (!queue.empty()) {
        int cur = queue.front();
        queue.pop();
        int u = parent[cur];
        char symbol = from[cur];
        u = suffLink[u];
        int index = symbol - 'a';
        while (u != 0 && nextTo[u][index] == 0) {
            u = suffLink[u];
        }
 
        if (cur != 1) {
            if (u == 0 || parent[cur] == 1) {
                suffLink[cur] = 1;
            } else {
                suffLink[cur] = nextTo[u][index];
            }
        }
 
        for (char c = 'a'; c <= 'z'; ++c) {
            index = c - 'a';
            if (nextTo[cur][index] != 0) {
                go[cur][index] = nextTo[cur][index];
            } else if (cur == 1) {
                go[cur][index] = 1;
            } else {
                go[cur][index] = go[suffLink[cur]][index];
            }
        }
 
        if (isLeaf[suffLink[cur]]) {
            up[cur] = suffLink[cur];
        } else if (suffLink[cur] == 1) {
            up[cur] = 1;
        } else {
            up[cur] = up[suffLink[cur]];
        }
 
        for (int i = 0; i < size; ++i) {
            if (nextTo[cur][i] != 0) {
                queue.push(nextTo[cur][i]);
            }
        }
    }
}
 
int main() {
    std::ios::sync_with_stdio(false);
    freopen("search5.in", "r", stdin);
    freopen("search5.out", "w", stdout);
 
    int k;
    cin >> k;
    auto *count = new int[k];
    for (int i = 0; i < k; i++) {
        count[i] = 0;
    }
    init();
 
    int size = 2;
    for (int i = 0; i < k; ++i) {
        int cur = 1;
        string s;
        cin >> s;
        size_t n = s.length();
        for (size_t j = 0; j < n; ++j) {
            char c = s[j];
            int index = c - 'a';
            if (nextTo[cur][index] == 0) {
                nextTo[cur][index] = size;
                parent[size] = cur;
                from[size] = c;
                ++size;
            }
            cur = nextTo[cur][index];
        }
        isLeaf[cur] = true;
        leafNumOfStr[cur].push_back(i);
    }
 
    setLinks();
 
    int cur = 1;
    string s;
    cin >> s;
    size_t n = s.length();
    for (size_t j = 0; j < n; ++j) {
        char c = s[j];
        cur = go[cur][c - 'a'];
        if (!was[cur]) {
            int copy = cur;
            while (copy > 1) {
                if (was[copy]) {
                    ++countForCur[copy];
                    break;
                }
                was[copy] = true;
                for (int num : leafNumOfStr[copy]) {
                    ++count[num];
                }
                copy = up[copy];
            }
        } else {
            ++countForCur[cur];
        }
    }
 
    for (int i = 0; i < size; ++i) {
        int copy = i;
        while (copy > 1) {
            for (int num : leafNumOfStr[copy]) {
                count[num] += countForCur[i];
            }
            copy = up[copy];
        }
    }
 
 
    for (int i = 0; i < k; i++) {
        cout << count[i] << '\n';
    }
 
    return 0;
}