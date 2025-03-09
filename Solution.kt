
import kotlin.math.pow
import kotlin.math.sqrt

class Solution {

    private companion object {
        const val NOT_FOUND = -1
        const val SMALLEST_PRIME_NUMBER = 2

        /*
         The first smallest difference between primes is 1 and it occurs
         only for prime = 2 and prime = 3. If these two primes are in the query range,
         their difference will be captured by just checking for difference <= 2.
         And by time this check is conducted, it is guaranteed to have at least
         two primes in the query range.
         */
        const val SECOND_SMALLEST_DIFFERENCE_BETWEEN_PRIMES = 2
        val RANGE_OF_VALUES = intArrayOf(1, (10.0).pow(6).toInt())
        val SIEVE_FOR_PRIMES: BooleanArray = createSieveOfEratosthenes()

        fun createSieveOfEratosthenes(): BooleanArray {
            val sieveForPrimes = BooleanArray(RANGE_OF_VALUES[1] + 1) { true }

            for (value in 0..<SMALLEST_PRIME_NUMBER) {
                sieveForPrimes[value] = false
            }

            val end = sqrt(RANGE_OF_VALUES[1].toDouble()).toInt()
            for (value in SMALLEST_PRIME_NUMBER..end) {
                if (sieveForPrimes[value]) {
                    var current = value * value
                    while (current <= RANGE_OF_VALUES[1]) {
                        sieveForPrimes[current] = false
                        current += value
                    }
                }
            }
            return sieveForPrimes
        }
    }

    fun closestPrimes(lowerLimit: Int, upperLimit: Int): IntArray {
        val closestPrimes = intArrayOf(RANGE_OF_VALUES[0], RANGE_OF_VALUES[1])

        var previousPrime = findFirstPrimeNumberInRange(lowerLimit, upperLimit)
        if (previousPrime == RANGE_OF_VALUES[0]) {
            return intArrayOf(NOT_FOUND, NOT_FOUND)
        }

        for (value in previousPrime + 1..upperLimit) {
            if (!SIEVE_FOR_PRIMES[value]) {
                continue
            }
            updateClosestPrimes(closestPrimes, previousPrime, value)
            previousPrime = value
            if (closestPrimes[1] - closestPrimes[0] <= SECOND_SMALLEST_DIFFERENCE_BETWEEN_PRIMES) {
                break
            }
        }

        if (closestPrimes[0] == RANGE_OF_VALUES[0]) {
            return intArrayOf(NOT_FOUND, NOT_FOUND)
        }
        return closestPrimes
    }

    private fun findFirstPrimeNumberInRange(lowerLimit: Int, upperLimit: Int): Int {
        var previousPrime = RANGE_OF_VALUES[0]
        for (value in lowerLimit..upperLimit) {
            if (SIEVE_FOR_PRIMES[value]) {
                previousPrime = value
                break
            }
        }
        return previousPrime
    }

    private fun updateClosestPrimes(closestPrimes: IntArray, previousPrime: Int, currentPrime: Int) {
        if (currentPrime - previousPrime < closestPrimes[1] - closestPrimes[0]) {
            closestPrimes[0] = previousPrime
            closestPrimes[1] = currentPrime
        }
    }
}
