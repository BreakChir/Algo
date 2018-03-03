#include <iostream>
#include <fstream>
#include <vector>
#include <cmath>
#include <iomanip>
#include <queue>
 
using namespace std;
 
size_t n, noEdge = 1000000000;
long long *d;
bool *used;
int *p;
bool cycle = false;
long long INF = 1000000000000000;
vector<vector<pair<int, int>>> edges;
 
int main() {
    std::ifstream in("negcycle.in");
    std::ofstream out("negcycle.out");
    in >> n;
    edges.resize(n);
    for (size_t i = 0; i < n; ++i) {
        for (size_t j = 0; j < n; ++j) {
            int w;
            in >> w;
            if (w != noEdge)
                edges[i].push_back(make_pair(j, w));
        }
 
    }
    d = new long long[n];
    p = new int[n];
    used = new bool[n];
    for (size_t i = 0; i < n; ++i) {
        d[i] = INF;
        used[i] = false;
        p[i] = -1;
    }
 
    int index = -1;
    for (size_t i = 0; i < n; ++i) {
        index = -1;
        for (size_t j = 0; j < n; ++j) {
            for (size_t k = 0; k < edges[j].size(); ++k) {
                int u = edges[j][k].first;
                int len = edges[j][k].second;
                if (d[j] + len < d[u]) {
                    d[u] = max(-INF, d[j] + len);
                    index = u;
                    p[u] = j;
                }
            }
        }
    }
 
    if (index != -1) cycle = true;
    if (cycle) {
        for (size_t i = 0; i < n; ++i)
            index = p[index];
        vector<int> negCycle;
        negCycle.push_back(index);
        for (int i = p[index]; i != index; i = p[i])
            negCycle.push_back(i);
        negCycle.push_back(index);
        out << "YES\n";
        out << negCycle.size() << '\n';
        for (int i = negCycle.size() - 1; i >= 0 ; --i)
            out << negCycle[i] + 1 << ' ';
    } else
        out << "NO";
 
    return 0;
}