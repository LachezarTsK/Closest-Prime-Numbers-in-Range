
package main

import "math"

const NOT_FOUND = -1
const SMALLEST_PRIME_NUMBER = 2

/*
The first smallest difference between primes is 1 and it occurs
only for prime = 2 and prime = 3. If these two primes are in the query range,
their difference will be captured by just checking for difference <= 2.
And by time this check is conducted, it is guaranteed to have at least
two primes in the query range.
*/
const SECOND_SMALLEST_DIFFERENCE_BETWEEN_PRIMES = 2

var RANGE_OF_VALUES = [2]int{1, int(math.Pow(10.0, 6.0))}
var SIEVE_FOR_PRIMES []bool = createSieveOfEratosthenes()

func closestPrimes(lowerLimit int, upperLimit int) []int {
    closestPrimes := []int{RANGE_OF_VALUES[0], RANGE_OF_VALUES[1]}

    previousPrime := findFirstPrimeNumberInRange(lowerLimit, upperLimit)
    if previousPrime == RANGE_OF_VALUES[0] {
        return []int{NOT_FOUND, NOT_FOUND}
    }

    for value := previousPrime + 1; value <= upperLimit; value++ {
        if !SIEVE_FOR_PRIMES[value] {
            continue
        }
        updateClosestPrimes(closestPrimes, previousPrime, value)
        previousPrime = value
        if closestPrimes[1]-closestPrimes[0] <= SECOND_SMALLEST_DIFFERENCE_BETWEEN_PRIMES {
            break
        }
    }

    if closestPrimes[0] == RANGE_OF_VALUES[0] {
        return []int{NOT_FOUND, NOT_FOUND}
    }
    return closestPrimes
}

func findFirstPrimeNumberInRange(lowerLimit int, upperLimit int) int {
    var previousPrime = RANGE_OF_VALUES[0]
    for value := lowerLimit; value <= upperLimit; value++ {
        if SIEVE_FOR_PRIMES[value] {
            previousPrime = value
            break
        }
    }
    return previousPrime
}

func updateClosestPrimes(closestPrimes []int, previousPrime int, currentPrime int) {
    if currentPrime-previousPrime < closestPrimes[1]-closestPrimes[0] {
        closestPrimes[0] = previousPrime
        closestPrimes[1] = currentPrime
    }
}

func createSieveOfEratosthenes() []bool {
    sieveForPrimes := make([]bool, RANGE_OF_VALUES[1]+1)
    for i := range sieveForPrimes {
        sieveForPrimes[i] = true
    }

    for value := range SMALLEST_PRIME_NUMBER {
        sieveForPrimes[value] = false
    }

    end := int(math.Sqrt(float64(RANGE_OF_VALUES[1])))
    for value := SMALLEST_PRIME_NUMBER; value <= end; value++ {
        if sieveForPrimes[value] {
            var current = value * value
            for current <= RANGE_OF_VALUES[1] {
                sieveForPrimes[current] = false
                current += value
            }
        }
    }
    return sieveForPrimes
}
