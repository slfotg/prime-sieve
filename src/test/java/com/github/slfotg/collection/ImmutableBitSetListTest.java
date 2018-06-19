package com.github.slfotg.collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ImmutableBitSetListTest {

    List<Integer> bitSetList;

    @BeforeEach
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

    @Test
    public void testNegativeIteratorIndex() {
        ImmutableBitSetList emptyList = new ImmutableBitSetList(new BitSet(10));
        assertThrows(IndexOutOfBoundsException.class, () -> emptyList.listIterator(-1));
    }

    @Test
    public void testLargeIndex() {
        assertThrows(IndexOutOfBoundsException.class, () -> bitSetList.listIterator(Integer.MAX_VALUE));
    }

    @Test
    public void testRemove() {
        assertThrows(UnsupportedOperationException.class, () -> bitSetList.remove(4));
    }

    @Test
    public void testAdd() {
        assertThrows(UnsupportedOperationException.class, () -> bitSetList.add(7));
    }

    @Test
    public void testSet() {
        assertThrows(UnsupportedOperationException.class, () -> bitSetList.set(3, 4));
    }

    @Test
    public void testIteratorPassedSize() {
        ListIterator<Integer> iterator = bitSetList.listIterator(bitSetList.size());
        assertThrows(NoSuchElementException.class, () -> iterator.next());
    }

    @Test
    public void testIteratorPrevious() {
        ListIterator<Integer> iterator = bitSetList.listIterator();
        assertFalse(iterator.hasPrevious());
        assertTrue(iterator.hasNext());
        assertThrows(NoSuchElementException.class, () -> iterator.previous());
    }

    @Test
    public void testEndOfIterator() {
        ListIterator<Integer> iterator = bitSetList.listIterator(bitSetList.size());
        assertFalse(iterator.hasNext());
        assertTrue(iterator.hasPrevious());
        assertEquals(Integer.valueOf(9), iterator.previous());
    }
}
