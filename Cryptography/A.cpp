#include <iostream>
#include <vector>
#include <deque>
 
using namespace std;
 
int main() {
    std::ios::sync_with_stdio(false);
    int m = 1000000;
    auto *notPrime = new bool[m];
    for (int i = 0; i < m; ++i) {
        notPrime[i] = false;
    }
    for (int i = 2; i < m; ++i) {
        if (!notPrime[i]) {
            for (int j = 2; i * j < m; ++j) {
                notPrime[i * j] = true;
            }
        }
    }
 
    int n;
    cin >> n;
    for (int i = 0; i < n; ++i) {
        int a;
        cin >> a;
        if (notPrime[a]) {
            cout << "NO\n";
        } else {
            cout << "YES\n";
        }
    }
 
    return 0;
}