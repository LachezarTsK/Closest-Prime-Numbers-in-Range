
const NOT_FOUND = -1;
const SMALLEST_PRIME_NUMBER = 2;

/* 
 The first smallest difference between primes is 1 and it occurs
 only for prime = 2 and prime = 3. If these two primes are in the query range,
 their difference will be captured by just checking for difference <= 2.
 And by time this check is conducted, it is guaranteed to have at least
 two primes in the query range.
 */
const SECOND_SMALLEST_DIFFERENCE_BETWEEN_PRIMES = 2;
const RANGE_OF_VALUES = [1, Math.pow(10, 6)];
const SIEVE_FOR_PRIMES = createSieveOfEratosthenes();

/**
 * @param {number} lowerLimit
 * @param {number} upperLimit
 * @return {number[]}
 */
var closestPrimes = function (lowerLimit, upperLimit) {
    const closestPrimes = [RANGE_OF_VALUES[0], RANGE_OF_VALUES[1]];

    let previousPrime = findFirstPrimeNumberInRange(lowerLimit, upperLimit);
    if (previousPrime === RANGE_OF_VALUES[0]) {
        return [NOT_FOUND, NOT_FOUND];
    }

    for (let value = previousPrime + 1; value <= upperLimit; ++value) {
        if (!SIEVE_FOR_PRIMES[value]) {
            continue;
        }
        updateClosestPrimes(closestPrimes, previousPrime, value);
        previousPrime = value;
        if (closestPrimes[1] - closestPrimes[0] <= SECOND_SMALLEST_DIFFERENCE_BETWEEN_PRIMES) {
            break;
        }
    }

    if (closestPrimes[0] === RANGE_OF_VALUES[0]) {
        return [NOT_FOUND, NOT_FOUND];
    }
    return closestPrimes;
};

/**
 * @param {number} lowerLimit
 * @param {number} upperLimit
 * @return {number}
 */
function findFirstPrimeNumberInRange(lowerLimit, upperLimit) {
    let previousPrime = RANGE_OF_VALUES[0];
    for (let value = lowerLimit; value <= upperLimit; ++value) {
        if (SIEVE_FOR_PRIMES[value]) {
            previousPrime = value;
            break;
        }
    }
    return previousPrime;
}

/**
 * @param {number[]} closestPrimes
 * @param {number} previousPrime
 * @param {number} currentPrime 
 * @return {void}
 */
function updateClosestPrimes(closestPrimes, previousPrime, currentPrime) {
    if (currentPrime - previousPrime < closestPrimes[1] - closestPrimes[0]) {
        closestPrimes[0] = previousPrime;
        closestPrimes[1] = currentPrime;
    }
}

/**
 * @return {number[]}
 */
function createSieveOfEratosthenes() {
    const sieveForPrimes = new Array(RANGE_OF_VALUES[1] + 1).fill(true);

    for (let value = 0; value < SMALLEST_PRIME_NUMBER; ++value) {
        sieveForPrimes[value] = false;
    }

    const end = Math.sqrt(RANGE_OF_VALUES[1]);
    for (let value = SMALLEST_PRIME_NUMBER; value <= end; ++value) {
        if (sieveForPrimes[value]) {
            let current = value * value;
            while (current <= RANGE_OF_VALUES[1]) {
                sieveForPrimes[current] = false;
                current += value;
            }
        }
    }
    return sieveForPrimes;
}
