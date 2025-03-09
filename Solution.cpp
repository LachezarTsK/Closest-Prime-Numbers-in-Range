
#include <span>
#include <cmath>
#include <array>
#include <vector>
using namespace std;


class Solution {

    static vector<bool> createSieveOfEratosthenes() {
        vector<bool> sieveForPrimes(RANGE_OF_VALUES[1] + 1, true);

        for (int value = 0; value < SMALLEST_PRIME_NUMBER; ++value) {
            sieveForPrimes[value] = false;
        }

        int end = sqrt(RANGE_OF_VALUES[1]);
        for (int value = SMALLEST_PRIME_NUMBER; value <= end; ++value) {
            if (sieveForPrimes[value]) {
                int current = value * value;
                while (current <= RANGE_OF_VALUES[1]) {
                    sieveForPrimes[current] = false;
                    current += value;
                }
            }
        }
        return sieveForPrimes;
    }

    static const int NOT_FOUND = -1;
    static const int SMALLEST_PRIME_NUMBER = 2;

    /*
     The first smallest difference between primes is 1 and it occurs
     only for prime = 2 and prime = 3. If these two primes are in the query range,
     their difference will be captured by just checking for difference <= 2.
     And by time this check is conducted, it is guaranteed to have at least
     two primes in the query range.
     */
    static const int SECOND_SMALLEST_DIFFERENCE_BETWEEN_PRIMES = 2;
    inline static const array<int, 2> RANGE_OF_VALUES = { 1, 1000000 };
    inline static const vector<bool> SIEVE_FOR_PRIMES = createSieveOfEratosthenes();

public:
    vector<int> closestPrimes(int lowerLimit, int upperLimit) const {
        vector<int> closestPrimes{ RANGE_OF_VALUES[0], RANGE_OF_VALUES[1] };

        int previousPrime = findFirstPrimeNumberInRange(lowerLimit, upperLimit);
        if (previousPrime == RANGE_OF_VALUES[0]) {
            return { NOT_FOUND, NOT_FOUND };
        }

        for (int value = previousPrime + 1; value <= upperLimit; ++value) {
            if (!SIEVE_FOR_PRIMES[value]) {
                continue;
            }
            updateClosestPrimes(closestPrimes, previousPrime, value);
            previousPrime = value;
            if (closestPrimes[1] - closestPrimes[0] <= SECOND_SMALLEST_DIFFERENCE_BETWEEN_PRIMES) {
                break;
            }
        }

        if (closestPrimes[0] == RANGE_OF_VALUES[0]) {
            return { NOT_FOUND, NOT_FOUND };
        }
        return closestPrimes;
    }

private:
    int findFirstPrimeNumberInRange(int lowerLimit, int upperLimit) const {
        int previousPrime = RANGE_OF_VALUES[0];
        for (int value = lowerLimit; value <= upperLimit; ++value) {
            if (SIEVE_FOR_PRIMES[value]) {
                previousPrime = value;
                break;
            }
        }
        return previousPrime;
    }

    void updateClosestPrimes(span<int> closestPrimes, int previousPrime, int currentPrime) const {
        if (currentPrime - previousPrime < closestPrimes[1] - closestPrimes[0]) {
            closestPrimes[0] = previousPrime;
            closestPrimes[1] = currentPrime;
        }
    }
};
