#include <iostream>
#include <complex>
#include <vector>
 
using namespace std;
 
double PI = 3.1415926535897932;
 
void fure(vector<complex<double>> &a, bool invert) {
    auto n = (int) a.size();
    if (n == 1) return;
 
    vector<complex<double>> a0((unsigned) (n / 2)), a1((unsigned) (n / 2));
    for (int i = 0, j = 0; i < n; i += 2, ++j) {
        a0[j] = a[i];
        a1[j] = a[i + 1];
    }
    fure(a0, invert);
    fure(a1, invert);
 
    double ang = 2 * PI / n;
    if (invert) {
        ang = -ang;
    }
    complex<double> w(1, 0);
    complex<double> wn(cos(ang), sin(ang));
    for (int i = 0; i < n / 2; ++i) {
        a[i] = a0[i] + w * a1[i];
        a[i + n / 2] = a0[i] - w * a1[i];
        if (invert)
            a[i] /= 2, a[i + n / 2] /= 2;
        w *= wn;
    }
}
 
void multiply(const vector<int> &a, const vector<int> &b, vector<int> &res) {
    vector<complex<double>> left(a.begin(), a.end());
    vector<complex<double>> right(b.begin(), b.end());
 
    int n = 1;
    size_t maxim = max(a.size(), b.size());
    for (; n < maxim; n <<= 1) {}
    n <<= 1;
 
    left.resize((unsigned) n);
    right.resize((unsigned) n);
 
    fure(left, false);
    fure(right, false);
    for (size_t i = 0; i < n; ++i)
        left[i] *= right[i];
 
    fure(left, true);
 
    res.resize((unsigned) n);
    for (size_t i = 0; i < n; ++i)
        res[i] = int(left[i].real() + 0.5);
 
    int md = 0;
    for (int i = 0; i < n; ++i) {
        res[i] += md;
        md = res[i] / 10;
        res[i] %= 10;
    }
}
 
int main() {
    std::ios::sync_with_stdio(false);
    string s1, s2;
    cin >> s1 >> s2;
    if (s1[0] == '0' || s2[0] == '0') {
        printf("%d", 0);
    } else {
        bool minus1 = s1[0] == '-', minus2 = s2[0] == '-';
        bool minus3 = (minus1 && minus2) || (!minus1 && !minus2);
        vector<int> a(minus1 ? s1.length() - 1 : s1.length());
        vector<int> b(minus2 ? s2.length() - 1 : s2.length());
        vector<int> res;
        if (!minus3) printf("-");
 
        for (int i = minus1 ? 1 : 0, sub = minus1 ? 0 : 1; i < s1.length(); ++i) {
            a[a.size() - sub - i] = s1[i] - '0';
        }
        for (int i = minus2 ? 1 : 0, sub = minus2 ? 0 : 1; i < s2.length(); ++i) {
            b[b.size() - sub - i] = s2[i] - '0';
        }
 
        multiply(a, b, res);
 
        bool print = false;
        for (int i = res.size() - 1; i >= 0; --i) {
            if (print) {
                printf("%d", res[i]);
            } else if (res[i] != 0) {
                printf("%d", res[i]);
                print = true;
            }
        }
    }
    return 0;
}