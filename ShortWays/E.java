#include <iostream>
#include <fstream>
#include <vector>
 
using namespace std;
 
size_t n, m, s;
long long INF = 6000000000000000000;
long long *d;
bool *hasCycle, *used;
vector<vector<pair<int, long long>>> edges;
 
void dfs(int v) {
    hasCycle[v] = true;
    for (size_t i = 0; i < edges[v].size(); ++i) {
        if (!hasCycle[edges[v][i].first])
            dfs(edges[v][i].first);
    }
}
 
void dfsUsed(int v) {
    used[v] = true;
    for (size_t i = 0; i < edges[v].size(); ++i) {
        if (!used[edges[v][i].first])
            dfsUsed(edges[v][i].first);
    }
}
 
int main() {
    std::ifstream in("path.in");
    std::ofstream out("path.out");
    in >> n >> m >> s;
    edges.resize(n);
    for (size_t i = 0; i < m; ++i) {
        int from, to;
        long long w;
        in >> from >> to >> w;
        edges[from - 1].push_back(make_pair(to - 1, w));
    }
    d = new long long[n];
    hasCycle = new bool[n];
    used = new bool[n];
    for (size_t i = 0; i < n; ++i) {
        d[i] = INF;
        hasCycle[i] = false;
        used[i] = false;
    }
    d[s - 1] = 0;
 
    dfsUsed(s - 1);
    for (size_t i = 0; i < n; ++i)
        for (size_t j = 0; j < n; ++j)
            for (size_t k = 0; k < edges[j].size(); ++k) {
                int u = edges[j][k].first;
                long long len = edges[j][k].second;
                if (d[j] + len < d[u]) {
                    d[u] = max(-INF, d[j] + len);
                    if (i == n - 1 && used[u])
                        dfs(u);
                }
            }
 
    for (size_t i = 0; i < n; ++i) {
        if (!used[i]) out << '*';
        else if (hasCycle[i]) out << '-';
        else out << d[i];
        out << '\n';
    }
 
    return 0;
}