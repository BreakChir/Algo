#include <iostream>
#include <map>
 
using namespace std;
 
string s;
long long *degX;
long long *HASH;
 
bool isEquals(int a, int b, int c, int d) {
    if (b - a != d - c) return false;
    if (a > c) {
        int cur = a;
        a = c;
        c = cur;
        cur = b;
        b = d;
        d = cur;
    }
    long long h1 = (a > 0) ? (HASH[b] - HASH[a - 1]) * degX[c - a]: HASH[b] * degX[c - a];
    long long h2 = (c > 0) ? (HASH[d] - HASH[c - 1]): HASH[d];
    return h1 == h2;
}
 
void preprocess(size_t size) {
    degX = new long long[size];
    HASH = new long long[size];
 
    int X = 31;
    HASH[0] = s[0] - 'a' + 1;
    degX[0] = 1;
    size_t n = s.length();
    for (int i = 1; i < n; ++i) {
        degX[i] = degX[i - 1] * X;
        HASH[i] = HASH[i - 1] + (s[i] - 'a' + 1) * degX[i];
    }
}
 
int main() {
    std::ios::sync_with_stdio(false);
 
    cin >> s;
    int m;
    cin >> m;
 
    preprocess(s.length());
 
    for (int i = 0; i < m; ++i) {
        int a, b, c ,d;
        cin >> a >> b >> c >> d;
        --a;
        --b;
        --c;
        --d;
        if (isEquals(a, b, c, d)) {
            cout << "Yes\n";
        } else {
            cout << "No\n";
        }
    }
 
    return 0;
}