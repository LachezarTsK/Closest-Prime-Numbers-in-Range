
using System;

public class Solution
{
    private static readonly int NOT_FOUND = -1;
    private static readonly int SMALLEST_PRIME_NUMBER = 2;

    /* 
     The first smallest difference between primes is 1 and it occurs
     only for prime = 2 and prime = 3. If these two primes are in the query range,
     their difference will be captured by just checking for difference <= 2.
     And by time this check is conducted, it is guaranteed to have at least
     two primes in the query range.
     */
    private static readonly int SECOND_SMALLEST_DIFFERENCE_BETWEEN_PRIMES = 2;
    private static readonly int[] RANGE_OF_VALUES = { 1, (int)Math.Pow(10, 6) };
    private static readonly bool[] SIEVE_FOR_PRIMES = CreateSieveOfEratosthenes();

    public int[] ClosestPrimes(int lowerLimit, int upperLimit)
    {
        int[] closestPrimes = new int[] { RANGE_OF_VALUES[0], RANGE_OF_VALUES[1] };

        int previousPrime = FindFirstPrimeNumberInRange(lowerLimit, upperLimit);
        if (previousPrime == RANGE_OF_VALUES[0])
        {
            return new int[] { NOT_FOUND, NOT_FOUND };
        }

        for (int value = previousPrime + 1; value <= upperLimit; ++value)
        {
            if (!SIEVE_FOR_PRIMES[value])
            {
                continue;
            }
            UpdateClosestPrimes(closestPrimes, previousPrime, value);
            previousPrime = value;
            if (closestPrimes[1] - closestPrimes[0] <= SECOND_SMALLEST_DIFFERENCE_BETWEEN_PRIMES)
            {
                break;
            }
        }

        if (closestPrimes[0] == RANGE_OF_VALUES[0])
        {
            return new int[] { NOT_FOUND, NOT_FOUND };
        }
        return closestPrimes;
    }

    private int FindFirstPrimeNumberInRange(int lowerLimit, int upperLimit)
    {
        int previousPrime = RANGE_OF_VALUES[0];
        for (int value = lowerLimit; value <= upperLimit; ++value)
        {
            if (SIEVE_FOR_PRIMES[value])
            {
                previousPrime = value;
                break;
            }
        }
        return previousPrime;
    }

    private void UpdateClosestPrimes(int[] closestPrimes, int previousPrime, int currentPrime)
    {
        if (currentPrime - previousPrime < closestPrimes[1] - closestPrimes[0])
        {
            closestPrimes[0] = previousPrime;
            closestPrimes[1] = currentPrime;
        }
    }

    private static bool[] CreateSieveOfEratosthenes()
    {
        bool[] sieveForPrimes = new bool[RANGE_OF_VALUES[1] + 1];
        Array.Fill(sieveForPrimes, true);

        for (int value = 0; value < SMALLEST_PRIME_NUMBER; ++value)
        {
            sieveForPrimes[value] = false;
        }

        int end = (int)Math.Sqrt(RANGE_OF_VALUES[1]);
        for (int value = SMALLEST_PRIME_NUMBER; value <= end; ++value)
        {
            if (sieveForPrimes[value])
            {
                int current = value * value;
                while (current <= RANGE_OF_VALUES[1])
                {
                    sieveForPrimes[current] = false;
                    current += value;
                }
            }
        }
        return sieveForPrimes;
    }
}
