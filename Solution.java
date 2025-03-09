
import java.util.Arrays;

public class Solution {

    private static final int NOT_FOUND = -1;
    private static final int SMALLEST_PRIME_NUMBER = 2;

    /* 
     The first smallest difference between primes is 1 and it occurs
     only for prime = 2 and prime = 3. If these two primes are in the query range,
     their difference will be captured by just checking for difference <= 2.
     And by time this check is conducted, it is guaranteed to have at least
     two primes in the query range.
     */
    private static final int SECOND_SMALLEST_DIFFERENCE_BETWEEN_PRIMES = 2;
    private static final int[] RANGE_OF_VALUES = {1, (int) Math.pow(10, 6)};
    private static final boolean[] SIEVE_FOR_PRIMES = createSieveOfEratosthenes();

    public int[] closestPrimes(int lowerLimit, int upperLimit) {
        int[] closestPrimes = new int[]{RANGE_OF_VALUES[0], RANGE_OF_VALUES[1]};

        int previousPrime = findFirstPrimeNumberInRange(lowerLimit, upperLimit);
        if (previousPrime == RANGE_OF_VALUES[0]) {
            return new int[]{NOT_FOUND, NOT_FOUND};
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
            return new int[]{NOT_FOUND, NOT_FOUND};
        }
        return closestPrimes;
    }

    private int findFirstPrimeNumberInRange(int lowerLimit, int upperLimit) {
        int previousPrime = RANGE_OF_VALUES[0];
        for (int value = lowerLimit; value <= upperLimit; ++value) {
            if (SIEVE_FOR_PRIMES[value]) {
                previousPrime = value;
                break;
            }
        }
        return previousPrime;
    }

    private void updateClosestPrimes(int[] closestPrimes, int previousPrime, int currentPrime) {
        if (currentPrime - previousPrime < closestPrimes[1] - closestPrimes[0]) {
            closestPrimes[0] = previousPrime;
            closestPrimes[1] = currentPrime;
        }
    }

    private static boolean[] createSieveOfEratosthenes() {
        boolean[] sieveForPrimes = new boolean[RANGE_OF_VALUES[1] + 1];
        Arrays.fill(sieveForPrimes, true);

        for (int value = 0; value < SMALLEST_PRIME_NUMBER; ++value) {
            sieveForPrimes[value] = false;
        }

        int end = (int) Math.sqrt(RANGE_OF_VALUES[1]);
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
}
