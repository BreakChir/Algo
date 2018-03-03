#include <iostream>
#include <iomanip>
 
using namespace std;
 
int main() {
    std::ios::sync_with_stdio(false);
    cin.tie(nullptr);
    cout.tie(nullptr);
 
    string s;
    cin >> s;
    int n = s.length();
    auto *pi = new int[n];
 
    pi[0] = 0;
    for (int i = 1; i < n; ++i) {
        int k = pi[i - 1];
        while (k > 0 && s[i] != s[k]) {
            k = pi[k - 1];
        }
        if (s[i] == s[k])
            ++k;
        pi[i] = k;
    }
 
//    int k = n - 1;
//    int T = n - pi[k];
//    while (n % T != 0) {
//        k = pi[k - 1];
//        T = n - pi[k];
//    }
 
    int k = pi[n - 1];
    int T = n;
    while (k != 0) {
        if (n % (n - k) == 0) {
            T = n - k;
            break;
        }
        k = pi[k - 1];
    }
 
    cout << T;
 
    return 0;
}