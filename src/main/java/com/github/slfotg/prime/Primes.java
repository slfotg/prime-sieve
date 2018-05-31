package com.github.slfotg.prime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import com.github.slfotg.collection.CompositeSequentialList;
import com.github.slfotg.collection.ImmutableBitSetList;
import com.github.slfotg.collection.ImmutableMappedList;

public final class Primes {

    protected static final int MOD = 30;
    protected static final List<Integer> SMALL_PRIMES = Arrays.asList(2, 3, 5);
    protected static final List<Integer> RELATIVE_PRIMES = new ArrayList<>(Arrays.asList(1, 7, 11, 13, 17, 19, 23, 29));

    // begin multiplication table
    protected static final int[] DIFFS = new int[] { 6, 4, 2, 4, 2, 4, 6, 2 };
    protected static final int[][] MULTIPLICATION_TABLE = new int[][] {
            new int[] { 0, 1, 2, 3, 4, 5, 6, 7 },
            new int[] { 1, 5, 4, 0, 7, 3, 2, 6 },
            new int[] { 2, 4, 0, 6, 1, 7, 3, 5 },
            new int[] { 3, 0, 6, 5, 2, 1, 7, 4 },
            new int[] { 4, 7, 1, 2, 5, 6, 0, 3 },
            new int[] { 5, 3, 7, 1, 6, 0, 4, 2 },
            new int[] { 6, 2, 3, 7, 0, 4, 5, 1 },
            new int[] { 7, 6, 5, 4, 3, 2, 1, 0 }
    };
    // end multiplication table

    protected static final UnaryOperator<Integer> MAPPING = n -> {
        int div = n / RELATIVE_PRIMES.size();
        int mod = n % RELATIVE_PRIMES.size();
        return MOD * div + RELATIVE_PRIMES.get(mod);
    };

    protected Primes() {
    }

    public static List<Integer> getPrimes(int max) {
        if (max < 7) {
            return SMALL_PRIMES.stream().filter(n -> n <= max).collect(Collectors.toList());
        }
        BitSet bitSet = sieve(max);
        List<Integer> largerPrimes = new ImmutableMappedList<>(new ImmutableBitSetList(bitSet), MAPPING);
        return new CompositeSequentialList<>(Arrays.asList(SMALL_PRIMES, largerPrimes));
    }

    protected static BitSet sieve(int max) {
        int size = calculateBitSetSize(max);
        BitSet bitSet = new BitSet(size);
        bitSet.set(1, size);
        for (int i = 1; i != -1; i = bitSet.nextSetBit(i + 1)) {
            int prime = MAPPING.apply(i);
            int composite = prime * prime;
            if (composite > max) {
                break;
            }
            int iMod = i % RELATIVE_PRIMES.size();
            for (int j = iMod; j < RELATIVE_PRIMES.size() && composite <= max; j += 1) {
                int compositeIndex = calculateIndex(composite, iMod, j);
                removeComposites(bitSet, size, compositeIndex, prime);
                composite += (prime * DIFFS[j]);
            }
            for (int j = 0; j < iMod && composite <= max; j += 1) {
                int compositeIndex = calculateIndex(composite, iMod, j);
                removeComposites(bitSet, size, compositeIndex, prime);
                composite += (prime * DIFFS[j]);
            }
        }
        return bitSet;
    }

    protected static int calculateIndex(int n, int index1, int index2) {
        int index = (n / MOD);
        index = index * RELATIVE_PRIMES.size() + MULTIPLICATION_TABLE[index1][index2];
        return index;
    }

    protected static void removeComposites(BitSet bitSet, int bitSetSize, int startIndex, int prime) {
        int d = prime * RELATIVE_PRIMES.size();
        for (int i = startIndex; i < bitSetSize; i += d) {
            bitSet.clear(i);
        }
    }

    protected static int calculateBitSetSize(int max) {
        int size = (max / MOD) * RELATIVE_PRIMES.size();
        int mod = max % MOD;
        for (int i = 0; i < RELATIVE_PRIMES.size(); i += 1) {
            if (RELATIVE_PRIMES.get(i) > mod) {
                return size;
            }
            size += 1;
        }
        return size;
    }
}
