package com.github.slfotg.prime;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class PrimeCounterTest {

    private static PrimeCounter counter = null;

    @BeforeAll
    static void initPrimeCounter() {
        counter = PrimeCounter.initialize();
    }

    /**
     * Tests the sizes up to 'n' are correct
     * Test data from https://primes.utm.edu/howmany.html
     */
    @ParameterizedTest
    @CsvSource({
        "                1,              0",
        "                2,              1",
        "                3,              2",
        "                4,              2",
        "                5,              3",
        "                6,              3",
        "                7,              4",
        "                8,              4",
        "                9,              4",
        "               10,              4",
        "              100,             25",
        "            1_000,            168",
        "           10_000,          1_229",
        "          100_000,          9_592",
        "        1_000_000,         78_498",
        "       10_000_000,        664_579",
        "      100_000_000,      5_761_455",
        "    1_000_000_000,     50_847_534",
        "   10_000_000_000,    455_052_511",
        "  100_000_000_000,  4_118_054_813"
    })
    public void testCountPrimesSmall(long n, long expected) {
        assertEquals(expected, counter.countPrimes(n));
    }
}
