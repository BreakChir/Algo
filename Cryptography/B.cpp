#include <iostream>
#include <vector>
#include <algorithm>
 
using namespace std;
 
int main() {
    std::ios::sync_with_stdio(false);
    int m = 1000001;
    auto *dividers = new int[m];
    auto *notPrime = new bool[m];
    for (int i = 0; i < m; ++i) {
        dividers[i] = i;
        notPrime[i] = false;
    }
    for (long long i = 2; i < m; ++i) {
        if (!notPrime[i]) {
            for (long long k = i * i; k < m; k += i) {
                notPrime[k] = true;
                dividers[k] = i;
            }
        }
    }
 
    int n;
    scanf("%d", &n);
    for (int i = 0; i < n; ++i) {
        int a;
        scanf("%d", &a);
        int cur = a;
        vector<int> div;
        while (cur != 1) {
            div.push_back(dividers[cur]);
            cur /= dividers[cur];
        }
        sort(div.begin(), div.end());
        for (int j = 0; j < div.size(); ++j) {
            printf("%d ", div[j]);
        }
        printf("\n");
    }
    return 0;
}