#include <iostream>
#include <vector>
 
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
 
bool miller_rab(long long n) {
    if (n == 2 || n == 3) {
        return true;
    }
    if (n < 2 || n % 2 == 0) {
        return false;
    }
 
    long long t = n - 1;
    long long s = 0;
 
    while ((t & 1) == 0) {
        t /= 2;
        ++s;
    }
 
    for (int i = 0; i < 2; i++) {
        long long a = (rand() % (n - 2)) + 2;
        long long x = mod_pow(a, t, n);
 
        if (x == 1 || x == n - 1)
            continue;
 
        for (int r = 1; r < s; ++r) {
            x = mod_pow(x, 2, n);
            if (x == 1)
                return false;
            if (x == n - 1)
                break;
        }
 
        if (x != n - 1)
            return false;
    }
 
    return true;
}
 
int main() {
    std::ios::sync_with_stdio(false);
    int n;
    cin >> n;
    for (int i = 0; i < n; ++i) {
        long long a;
        cin >> a;
        if (miller_rab(a)) {
            cout << "YES\n";
        } else {
            cout << "NO\n";
        }
    }
 
 
    return 0;
}