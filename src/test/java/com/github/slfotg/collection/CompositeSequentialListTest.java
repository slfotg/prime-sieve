package com.github.slfotg.collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

public class CompositeSequentialListTest {

    List<Integer> compositeList;

    @Before
    public void init() {
        List<Integer> a = Arrays.asList(0, 1, 2);
        List<Integer> b = Arrays.asList();
        List<Integer> c = Arrays.asList(3, 4, 5, 6);
        List<Integer> d = Arrays.asList(7);
        List<Integer> e = Arrays.asList(8, 9);
        compositeList = new CompositeSequentialList<>(Arrays.asList(a, b, c, d, e));
    }

    @Test
    public void testIterator() {
        List<Integer> list = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        ListIterator<Integer> it1 = list.listIterator();
        ListIterator<Integer> it2 = compositeList.listIterator();

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

    @Test(expected = IndexOutOfBoundsException.class)
    public void testNegativeIndex() {
        compositeList.listIterator(-1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testLargeIndex() {
        compositeList.listIterator(Integer.MAX_VALUE);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRemove() {
        compositeList.remove(4);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testAdd() {
        compositeList.add(7);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testSet() {
        compositeList.set(3, 4);
    }

    @Test(expected = NoSuchElementException.class)
    public void testIteratorPassedSize() {
        ListIterator<Integer> iterator = compositeList.listIterator(compositeList.size());
        iterator.next();
    }

    @Test(expected = NoSuchElementException.class)
    public void testIteratorPrevious() {
        ListIterator<Integer> iterator = compositeList.listIterator();
        assertFalse(iterator.hasPrevious());
        assertTrue(iterator.hasNext());
        iterator.previous();
    }

    @Test
    public void testEndOfIterator() {
        ListIterator<Integer> iterator = compositeList.listIterator(compositeList.size());
        assertFalse(iterator.hasNext());
        assertTrue(iterator.hasPrevious());
        assertEquals(Integer.valueOf(9), iterator.previous());
    }
}
