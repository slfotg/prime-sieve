package com.github.slfotg.collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.function.Function;

import org.junit.Before;
import org.junit.Test;

public class ImmutableMappedListTest {

    List<Integer> originalList;
    List<Integer> mappedList;
    Function<Integer, Integer> mappingFunction;
    List<Integer> emptyList;

    @Before
    public void init() {
        originalList = new LinkedList<>(Arrays.asList(1, 2, 3, 4, 5));
        mappingFunction = x -> 2 * x;
        mappedList = new ImmutableMappedList<Integer, Integer>(originalList, mappingFunction);
        emptyList = new ImmutableMappedList<Integer, Integer>(new ArrayList<>(), mappingFunction);
    }

    @Test
    public void testEmptyList() {
        assertEquals(new ArrayList<>(), emptyList);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testAdd() {
        mappedList.add(7);
    }

    @Test
    public void testAddToOriginalList() {
        originalList.add(0);
        assertEquals(6, mappedList.size());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRemove() {
        mappedList.remove(4);
    }

    @Test
    public void testListIterator() {
        ListIterator<Integer> originalIterator = originalList.listIterator();
        ListIterator<Integer> mappedIterator = mappedList.listIterator();
        while (originalIterator.hasNext()) {
            assertTrue(mappedIterator.hasNext());
            assertEquals(originalIterator.nextIndex(), mappedIterator.nextIndex());
            assertEquals(mappingFunction.apply(originalIterator.next()), mappedIterator.next());
        }
        while (originalIterator.hasPrevious()) {
            assertTrue(mappedIterator.hasPrevious());
            assertEquals(originalIterator.previousIndex(), mappedIterator.previousIndex());
            assertEquals(mappingFunction.apply(originalIterator.previous()), mappedIterator.previous());
        }
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testNegativeIndex() {
        mappedList.listIterator(-1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testLargeIndex() {
        mappedList.listIterator(Integer.MAX_VALUE);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testSet() {
        mappedList.set(3, 4);
    }

    @Test(expected = NoSuchElementException.class)
    public void testIteratorPassedSize() {
        ListIterator<Integer> mappedIterator = mappedList.listIterator(mappedList.size());
        mappedIterator.next();
    }

    @Test
    public void testEndOfIterator() {
        ListIterator<Integer> mappedIterator = mappedList.listIterator(mappedList.size());
        assertFalse(mappedIterator.hasNext());
        assertTrue(mappedIterator.hasPrevious());
        assertEquals(mappingFunction.apply(originalList.get(originalList.size() - 1)), mappedIterator.previous());
    }
}
