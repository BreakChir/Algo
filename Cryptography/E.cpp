#include <iostream>
 
using namespace std;
 
long long mul(long long a, long long n, long long m) {
    long long r = 0;
    for (; n > 0; n /= 2, a = (a + a) % m) {
        if (n % 2 == 1) {
            r = (r + a) % m;
        }
    }
    return r;
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
 
int main() {
    std::ios::sync_with_stdio(false);
    long long x;
    auto *a = new int[2];
    auto *m = new int[2];
    scanf("%d %d %d %d", &a[0], &a[1], &m[0], &m[1]);
 
    long long mm = m[0] * m[1];
    x = 0;
    for (int i = 0; i < 2; ++i) {
        long long y = mm / m[i];
 
        long long x1, y1;
        gcd(y, m[i], x1, y1);
        long long s = (x1 % m[i] + m[i]) % m[i];
        long long c = (a[i] * s) % m[i];
        x = (x + c * y) % mm;
    }
 
    cout << x;
    return 0;
}