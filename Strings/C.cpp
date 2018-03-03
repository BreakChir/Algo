#include <iostream>
 
using namespace std;
 
int *zFun;
string s;
 
 
int main() {
    std::ios::sync_with_stdio(false);
//    freopen("input.txt", "r", stdin);
//    freopen("output.txt", "w", stdout);
 
    cin >> s;
 
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
 
 
    for (int i = 1; i < n; i++) {
        cout << zFun[i] << " ";
    }
 
    return 0;
}