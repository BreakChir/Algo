#include <iostream>
 
using namespace std;
 
int *zFun;
string s;
 
 
int main() {
    std::ios::sync_with_stdio(false);
    freopen("cyclic.in", "r", stdin);
    freopen("cyclic.out", "w", stdout);
 
    cin >> s;
    auto m = (int) s.length();
    s += s;
    auto n = (int) s.length();
    zFun = new int[n];
 
    int left = 0, right = 0;
    for (int i = 1; i <= n - 1; ++i) {
        zFun[i] = max(0, min(right - i, zFun[i - left]));
        while (i + zFun[i] < n && s[zFun[i]] == s[i + zFun[i]])
            ++zFun[i];
        if (i + zFun[i] > right) {
            left = i;
            right = i + zFun[i];
        }
    }
 
 
    int num = 0;
 
//    for (int i = 1; i < n; i++) {
//        cout << zFun[i] << " ";
//    }
//    cout << '\n';
    for (int i = 1; i < m; i++) {
        if (i + zFun[i] >= n) {
            continue;
        } else if (s[i + zFun[i]] < s[zFun[i]]) {
            ++num;
        }
    }
 
    cout << num + 1;
 
    return 0;
}