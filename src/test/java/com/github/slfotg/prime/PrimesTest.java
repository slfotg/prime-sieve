package com.github.slfotg.prime;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class PrimesTest {

    @Test
    public void testMultiplicationTable() {
        for (int i = 0; i < Primes.RELATIVE_PRIMES.size(); i += 1) {
            for (int j = 0; j < Primes.RELATIVE_PRIMES.size(); j += 1) {
                int n = (Primes.RELATIVE_PRIMES.get(i) * Primes.RELATIVE_PRIMES.get(j)) % Primes.MOD;
                int m = Primes.RELATIVE_PRIMES.get(Primes.MULTIPLICATION_TABLE[i][j]);
                int l = Primes.RELATIVE_PRIMES.get(Primes.MULTIPLICATION_TABLE[j][i]);
                assertEquals(n, m);
                assertEquals(n, l);
            }
        }
    }

    @Test
    public void testCalculateBitSetSize() {
        assertEquals( 8, Primes.calculateBitSetSize( 30));
        assertEquals( 9, Primes.calculateBitSetSize( 31));
        assertEquals( 9, Primes.calculateBitSetSize( 32));
        assertEquals( 9, Primes.calculateBitSetSize( 33));
        assertEquals( 9, Primes.calculateBitSetSize( 36));
        assertEquals(10, Primes.calculateBitSetSize( 37));
        assertEquals(24, Primes.calculateBitSetSize( 90));
        assertEquals(26, Primes.calculateBitSetSize(100));
    }

    @Test
    public void testGetPrimes() {
        List<Integer> primes = Arrays.asList(2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97);
        assertEquals(primes, Primes.getPrimes(100));
    }

    /**
     * Tests the sizes up to 'n' are correct
     * Test data from https://primes.utm.edu/howmany.html
     */
    @Test
    public void testGetPrimesSizes() {
        assertEquals(    4, Primes.getPrimes(     10).size());
        assertEquals(   25, Primes.getPrimes(    100).size());
        assertEquals(  168, Primes.getPrimes(   1000).size());
        assertEquals( 1229, Primes.getPrimes(  10000).size());
        assertEquals( 9592, Primes.getPrimes( 100000).size());
        assertEquals(78498, Primes.getPrimes(1000000).size());
    }

    @Test
    public void testGetSmallPrimes() {
        assertEquals(Arrays.asList(),           Primes.getPrimes(1));
        assertEquals(Arrays.asList(2),          Primes.getPrimes(2));
        assertEquals(Arrays.asList(2, 3),       Primes.getPrimes(3));
        assertEquals(Arrays.asList(2, 3),       Primes.getPrimes(4));
        assertEquals(Arrays.asList(2, 3, 5),    Primes.getPrimes(5));
        assertEquals(Arrays.asList(2, 3, 5),    Primes.getPrimes(6));
        assertEquals(Arrays.asList(2, 3, 5, 7), Primes.getPrimes(7));
    }
}
