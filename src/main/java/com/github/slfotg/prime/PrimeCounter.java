package com.github.slfotg.prime;

public class PrimeCounter {

    private static final int DEFAULT_MAX_PRIME = 1000000;

    private int maxPrime;
    private int[] primes;

    protected PrimeCounter(int maxPrime) {
        this.maxPrime = maxPrime;
        this.primes = Primes.getPrimesArray(maxPrime);
    }

    public static PrimeCounter initialize(int maxPrime) {
        return new PrimeCounter(maxPrime);
    }

    public static PrimeCounter initialize() {
        return initialize(DEFAULT_MAX_PRIME);
    }

    public long phi(long x, long a) {
        if (a == 1) {
            return (x + 1) / 2;
        }

        return phi(x, a - 1) - phi(x / primes[(int) a - 1], a - 1);
    }

    public long countPrimes(long x) {
        if (x < maxPrime) {
            return binarySearch((int) x);
        }

        long a = countPrimes((long) Math.pow(x, 1.0 / 4.0));
        long b = countPrimes((long) Math.pow(x, 1.0 / 2.0));
        long c = countPrimes((long) Math.pow(x, 1.0 / 3.0));

        long result = phi(x, a) + (b + a - 2) * (b - a + 1) / 2;

        for (int i = (int) a + 1; i <= b; i += 1) {
            long w = x / primes[i - 1];
            long bi = countPrimes((long) Math.pow(w, 1.0 / 2.0));
            result -= countPrimes(w);
            if (i <= c) {
                for (int j = i; j <= bi; j += 1) {
                    result -= countPrimes(w / primes[j - 1]);
                    result += j - 1;
                }
            }
        }

        return result;
    }

    public int binarySearch(int x) {
        return binarySearch(x, 0, primes.length - 1);
    }

    public int binarySearch(int x, int minIndex, int maxIndex) {
        if (minIndex > maxIndex) {
            return maxIndex + 1;
        }
        int midIndex = (minIndex + maxIndex) / 2;
        if (x < primes[midIndex]) {
            return binarySearch(x, minIndex, midIndex - 1);
        } else if (x == primes[midIndex]) {
            return midIndex + 1;
        } else {
            return binarySearch(x, midIndex + 1, maxIndex);
        }
    }
}
