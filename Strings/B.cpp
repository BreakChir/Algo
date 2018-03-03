#include <iostream>
#include <fstream>
#include <vector>
#include <cmath>
#include <iomanip>
#include <queue>
 
using namespace std;
 
int main() {
    std::ios::sync_with_stdio(false);
    string s;
    cin >>s;
 
    auto *pi = new int[s.size()];
    pi[0] = 0;
    for (int i = 1; i < s.size(); i++) {
        int k = pi[i - 1];
        while (k > 0 && s[i] != s[k]) {
            k = pi[k - 1];
        }
        if (s[i] == s[k])
            k++;
        pi[i] = k;
    }
 
    for (int i = 0; i < s.size(); ++i) {
        cout << pi[i] << " ";
    }
    return 0;
}