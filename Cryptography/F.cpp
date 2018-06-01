#include <iostream>
#include <cmath>
 
using namespace std;
 
long long mul(long long a, long long n, long long m) {
    long long r = 0;
    while (n > 0) {
        if (n % 2 == 1)
            r = (r + a) % m;
        a = (a + a) % m;
        n /= 2;
    }
    return r;
}
 
long long mod_pow(long long a, long long n, long long m) {
    long long res = 1;
    while (n > 0) {
        if ((n & 1) > 0)
            res = mul(res, a, m);
        a = mul(a, a, m);
        n >>= 1;
    }
    return res;
}
 
long long gcd(long long a, long long b, long long &x, long long &y) {
    if (a == 0) {
        x = 0;
        y = 1;
        return b;
    }
    long long x1, y1;
    long long d = gcd(b % a, a, x1, y1);
    x = y1 - (b / a) * x1;
    y = x1;
    return d;
}
 
long long gcd(long long a, long long b) {
    while (b != 0) {
        long long t = b;
        b = a % b;
        a = t;
    }
    return a;
}
 
int main() {
    std::ios::sync_with_stdio(false);
    int n, e, C;
    scanf("%d %d %d", &n, &e, &C);
    auto nsqrt = (int) sqrt(n + .0);
 
    auto *notPrime = new bool[nsqrt + 5];
    for (int i = 0; i <= nsqrt; ++i) {
        notPrime[i] = false;
    }
    for (int i = 2; i <= nsqrt; ++i) {
        if (!notPrime[i]) {
            for (int k = i * i; k <= nsqrt; k += i) {
                notPrime[k] = true;
            }
        }
    }
 
    int p = 0, q = 0;
 
    for (int p1 = 2; p1 <= nsqrt; ++p1) {
        if (!notPrime[p1]) {
            if (n % p1 != 0) continue;
            q = n / p1;
            p = p1;
        }
    }
 
    long long x1, y1;
    long long mm = (p - 1) * (q - 1);
    gcd(e, mm, x1, y1);
    long long d = (x1 % mm + mm) % mm;
    long long M = mod_pow(C, d, n);
 
    cout << M;
    return 0;
}