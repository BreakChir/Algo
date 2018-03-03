#include <iostream>
#include <deque>
 
using namespace std;
 
string s;
long long *lcp;
long long *sufArr;
long long n;
 
void buildLCP() {
    long long k = 0;
    auto* pos = new long long[n];
    for (long long i = 0; i < n; ++i) {
        pos[sufArr[i]] = i;
    }
    for (long long i = 0; i < n; ++i) {
        if (k > 0) k--;
        if (pos[i] == n - 1) {
            lcp[n - 1] = -1;
            k = 0;
        } else {
            long long j = sufArr[pos[i] + 1];
            while (max(i + k, j + k) < n && s[i + k] == s[j + k])
                k++;
            lcp[pos[i]] = k;
        }
    }
}
 
void buildSufArr() {
    auto* eqClass = new long long[n];
    for (long long i = 0; i < n; ++i) {
        eqClass[i] = 0;
    }
    {
        auto* count = new long long['z' - '`' + 1];
        auto* used = new long long['z' - '`' + 1];
 
        for (long long i = 0; i < 'z' - '`' + 1; ++i) {
            count[i] = 0;
            used[i] = 0;
        }
 
        for (long long i = 0; i < n; ++i) {
            ++count[s[i] - '`'];
        }
        for (long long i = 0; i < 'z' - '`'; ++i) {
            count[i + 1] += count[i];
        }
 
        for (long long i = 0; i < n; ++i) {
            long long symbol = s[i] - '`';
            long long start = (symbol == 0 || count[symbol - 1] == 0) ? 0 : count[symbol - 1];
            sufArr[start + used[symbol]] = i;
            ++used[symbol];
        }
 
        eqClass[sufArr[0]] = 0;
        long long last = 0;
        for (long long i = 1; i < n; ++i) {
            eqClass[sufArr[i]] = last;
            if (s[sufArr[i]] != s[sufArr[i - 1]]) {
                ++eqClass[sufArr[i]];
            }
            last = eqClass[sufArr[i]];
        }
    }
 
    for (long long i = 1; i < n; i *= 2) {
        auto* count = new long long[n];
        auto* beforeFirst = new long long[n];
        for (long long j = 0; j < n; ++j) {
            count[j] = 0;
            beforeFirst[j] = 0;
        }
 
        for (long long j = 0; j < n; ++j) {
            beforeFirst[j] = (sufArr[j] - i + n) % n;
            ++count[eqClass[beforeFirst[j]]];
        }
        for (long long j = 0; j < n - 1; ++j) {
            count[j + 1] += count[j];
        }
 
        for (long long j = n - 1; j > -1; --j) {
            long long second = beforeFirst[j];
            --count[eqClass[second]];
            sufArr[count[eqClass[second]]] = second;
        }
 
 
        auto* newEqClass = new long long[n];
        for (long long j = 0; j < n; ++j) {
            newEqClass[j] = 0;
        }
        newEqClass[sufArr[0]] = 0;
        long long last = 0;
        for (long long j = 1; j < n; ++j) {
            newEqClass[sufArr[j]] = last;
            if (!(eqClass[sufArr[j]] == eqClass[sufArr[j - 1]] &&
                  eqClass[(sufArr[j] + i) % n] == eqClass[(sufArr[j - 1] + i) % n])) {
                ++newEqClass[sufArr[j]];
            }
            last = newEqClass[sufArr[j]];
        }
        if (last == n - 1) break;
        eqClass = newEqClass;
    }
 
}
 
struct triple {
    long long lcp;
    long long count;
    long long pos;
 
    triple(long long a, long long b, long long c) {
        this->lcp = a;
        this->count = b;
        this->pos = c;
    }
};
 
int main() {
    std::ios::sync_with_stdio(false);
    freopen("refrain.in", "r", stdin);
    freopen("refrain.out", "w", stdout);
 
    long long m;
    cin >> n >> m;
//!  a..j 1..10
    char symbol = 'a' - 1;
    for (long long i = 0; i < n; ++i) {
        long long a;
        cin >> a;
        auto c = (char)(symbol + a);
        s += c;
    }
    s += '`';
 
    n = (long long) s.length();
    sufArr = new long long[n];
    lcp = new long long[n];
    for (long long i = 0; i < n; ++i) {
        lcp[i] = sufArr[i] = 0;
    }
 
    buildSufArr();
    buildLCP();
 
    long long maxLcp = n - 1;
    long long maxCount = 1;
    long long pos = 0;
    long long curCount;
    std::deque<triple> deq;
    lcp[n - 1] = 0;
    for (long long i = 1; i < n; ++i) {
        curCount = 1;
        while (!deq.empty() && deq.back().lcp >= lcp[i]) {
            long long lcp = deq.back().lcp;
            long long count = deq.back().count;
            long long num = deq.back().pos;
            deq.pop_back();
            curCount += count;
 
            if (curCount * lcp > maxCount * maxLcp) {
                maxLcp = lcp;
                maxCount = curCount;
                pos = num;
            }
        }
        deq.emplace_back(triple(lcp[i], curCount, sufArr[i]));
    }
 
    cout << maxCount * maxLcp << '\n';
    cout << maxLcp<< '\n';
    for (long long i = pos; i < pos + maxLcp; ++i) {
        cout << (long long)(s[i] - symbol) << " ";
    }
 
    return 0;
}