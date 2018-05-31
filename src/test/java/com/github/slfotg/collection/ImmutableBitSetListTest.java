package com.github.slfotg.collection;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.BitSet;
import java.util.List;
import java.util.ListIterator;

import org.junit.Before;
import org.junit.Test;

public class ImmutableBitSetListTest {

    ImmutableBitSetList bitSetList;

    @Before
    public void init() {
        BitSet bitSet = new BitSet(10);
        for (int i = 0; i < 10; i += 1) {
            bitSet.set(i);
        }
        bitSetList = new ImmutableBitSetList(bitSet);
    }

    @Test
    public void testIterator() {
        List<Integer> list = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        ListIterator<Integer> it1 = list.listIterator();
        ListIterator<Integer> it2 = bitSetList.listIterator();

        while (it1.hasNext()) {
            assertEquals(it1.nextIndex(), it2.nextIndex());
            assertEquals(it1.next(), it2.next());
        }
        assertEquals(false, it2.hasNext());

        while (it1.hasPrevious()) {
            assertEquals(it1.previousIndex(), it2.previousIndex());
            assertEquals(it1.previous(), it2.previous());
        }
        assertEquals(false, it2.hasPrevious());
    }
}
