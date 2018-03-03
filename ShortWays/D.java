#include <iostream>
#include <fstream>
#include <vector>
#include <cmath>
#include <iomanip>
#include <queue>
 
using namespace std;
 
size_t n, m, s, f INF = 1000000000;
int *d;
vector<vector<pair<int, int>>> edges;
 
int main() {
    std::ifstream in("input.txt");
    std::ofstream out("output.txt");
    in >> n >> m;
    edges.resize(n);
    for (size_t i = 0; i < n; ++i) {
	    int from , to, w;
cin >> from >> to >> w;
edges[from - 1].emplace_back(make_pair(to - 1, w));		
edges[to - 1].emplace_back(make_pair(from - 1, w));		
    }
    d = new int[n];
    for (size_t i = 0; i < n; ++i)
        d[i] = INF;
    d[0] = 0;
    priority_queue<pair<int, int>> queue;
    queue.push(make_pair(0, 0));
    while (!queue.empty()) {
        pair<int, int> min = queue.top();
        queue.pop();
        int dist = -min.first;
        int v = min.second;
        if (d[v] < dist) continue;
        for (size_t j = 0; j < edges[v].size(); ++j) {
            int u = edges[v][j].first;
            int len = edges[v][j].second;
            if (d[v] + len < d[u]) {
                d[u] = d[v] + len;
                queue.push(make_pair(-d[u], u));
            }
        }
    }
	
    for (size_t i = 0; i < n; ++i) {
        out << d[i];
        out << " ";
    }
    return 0;
}