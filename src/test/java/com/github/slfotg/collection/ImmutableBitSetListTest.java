package com.github.slfotg.collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

public class ImmutableBitSetListTest {

    List<Integer> bitSetList;

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

    @Test
    public void testEmptyList() {
        ImmutableBitSetList emptyList = new ImmutableBitSetList(new BitSet(10));
        assertEquals(new ArrayList<>(), emptyList);

        Iterator<Integer> iterator = emptyList.iterator();
        assertFalse(iterator.hasNext());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testNegativeIteratorIndex() {
        ImmutableBitSetList emptyList = new ImmutableBitSetList(new BitSet(10));
        emptyList.listIterator(-1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testLargeIndex() {
        bitSetList.listIterator(Integer.MAX_VALUE);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRemove() {
        bitSetList.remove(4);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testAdd() {
        bitSetList.add(7);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testSet() {
        bitSetList.set(3, 4);
    }

    @Test(expected = NoSuchElementException.class)
    public void testIteratorPassedSize() {
        ListIterator<Integer> iterator = bitSetList.listIterator(bitSetList.size());
        iterator.next();
    }

    @Test
    public void testEndOfIterator() {
        ListIterator<Integer> iterator = bitSetList.listIterator(bitSetList.size());
        assertFalse(iterator.hasNext());
        assertTrue(iterator.hasPrevious());
        assertEquals(Integer.valueOf(9), iterator.previous());
    }
}
