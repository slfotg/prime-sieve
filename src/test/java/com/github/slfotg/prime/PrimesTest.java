package com.github.slfotg.prime;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

class PrimesTest {

    @Test
    void testMultiplicationTable() {
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
    void testCalculateBitSetSize() {
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
    void testGetPrimes() {
        List<Integer> primes = Arrays.asList(2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97);
        assertEquals(primes, Primes.getPrimes(100));
    }

    @Test
    void testGetPrimesArray() {
        int[] primes = new int[]{2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97};
        assertArrayEquals(primes, Primes.getPrimesArray(100));
    }

    /**
     * Tests the sizes up to 'n' are correct
     * Test data from https://primes.utm.edu/howmany.html
     */
    @ParameterizedTest
    @CsvSource({
        "       100,     25",
        "     1_000,    168",
        "    10_000,  1_229",
        "   100_000,  9_592",
        " 1_000_000, 78_498"
    })
    void testGetPrimesSizes(int n, int size) {
        assertEquals(size, Primes.getPrimes(n).size());
    }

    @ParameterizedTest
    @MethodSource
    void testGetSmallPrimes(int n, int[] primes) {
        assertEquals(Arrays.stream(primes).boxed().collect(Collectors.toList()), Primes.getPrimes(n));
    }

    @ParameterizedTest
    @MethodSource("testGetSmallPrimes")
    void testGetSmallPrimesArray(int n, int[] primes) {
        assertArrayEquals(primes, Primes.getPrimesArray(n));
    }

    static Stream<Arguments> testGetSmallPrimes() {
        return Stream.of(
            Arguments.of(1, new int[]{}),
            Arguments.of(2, new int[]{2}),
            Arguments.of(3, new int[]{2, 3}),
            Arguments.of(4, new int[]{2, 3}),
            Arguments.of(5, new int[]{2, 3, 5}),
            Arguments.of(6, new int[]{2, 3, 5}),
            Arguments.of(7, new int[]{2, 3, 5, 7})
        );
    }
}
