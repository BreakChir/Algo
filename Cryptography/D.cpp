#include <iostream>
#include <cmath>
#include <cstring>
 
using namespace std;
 
const int S = 50000;
 
int main() {
    std::ios::sync_with_stdio(false);
    int h = 0;
    int n, x;
    scanf("%d %d", &n, &x);
    auto nsqrt = (int) sqrt(n + 0.0);
 
    int count = 0;
    int primes[nsqrt];
    auto *notPrime = new bool[nsqrt];
    for (int i = 0; i <= nsqrt; ++i) {
        notPrime[i] = false;
    }
    for (int i = 2; i <= nsqrt; ++i) {
        if (!notPrime[i]) {
            primes[count++] = i;
            for (int k = i * i; k <= nsqrt; k += i) {
                notPrime[k] = true;
            }
        }
    }
 
 
    bool block[S];
    for (int k = 0, maxk = n / S; k <= maxk; ++k) {
        memset (block, 0, sizeof block);
        int start = k * S;
        for (int i = 0; i < count; ++i) {
            int j;
            if (k == 0) {
                j = 2 * primes[i];
            } else {
                int mm = (start - 1) % primes[i];
                if (mm == 0) {
                    j = primes[i] - 1;
                } else {
                    j = primes[i] - 1 - mm;
                }
            }
            for (; j < S; j += primes[i]) {
                block[j] = true;
            }
        }
 
        if (k == 0)
            block[0] = block[1] = true;
        for (int i = 0; i < S && start + i <= n; ++i) {
            if (!block[i]) {
                h = h * x + start + i;
            }
        }
 
    }
    printf("%d", h);
 
    return 0;
}