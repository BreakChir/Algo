#include <iostream>
#include <fstream>
#include <vector>
#include <cmath>
#include <iomanip>
#include <queue>
 
using namespace std;
 
size_t n, s, f;
long long *d;
bool *used;
long long INF = 1000000000000000;
vector<vector<pair<int, long long>>> edges;
 
int main() {
    std::ifstream in("pathmgep.in");
    std::ofstream out("pathmgep.out");
    in >> n >> s >> f;
    s--;
    f--;
    edges.resize(n);
    for (size_t i = 0; i < n; ++i) {
        for (size_t j = 0; j < n; ++j) {
            long long w;
            in >> w;
            if (w >= 0)
                edges[i].push_back(make_pair(j, w));
        }
 
    }
    d = new long long[n];
    used = new bool[n];
    for (size_t i = 0; i < n; ++i) {
        d[i] = INF;
        used[i] = false;
    }
    d[s] = 0;
    for (int i = 0; i < n; i++) {
        int v = -1;
        for (int j = 0; j < n; j++) {
            if (!used[j] && (v == -1 || d[j] < d[v]))
                v = j;
        }
        if (d[v] == INF) break;
        used[v] = true;
        for (int j = 0; j < edges[v].size(); j++) {
            int u = edges[v][j].first;
            long long len = edges[v][j].second;
            if (d[v] + len < d[u]) d[u] = d[v] + len;
        }
    }
 
    if (d[f] == INF) out << -1;
    else out << d[f];
    return 0;
}