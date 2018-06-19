package com.github.slfotg.collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.function.Function;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ImmutableMappedListTest {

    List<Integer> originalList;
    List<Integer> mappedList;
    Function<Integer, Integer> mappingFunction;
    List<Integer> emptyList;

    @BeforeEach
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

    @Test
    public void testAdd() {
        assertThrows(UnsupportedOperationException.class, () -> mappedList.add(7));
    }

    @Test
    public void testAddToOriginalList() {
        originalList.add(0);
        assertEquals(6, mappedList.size());
    }

    @Test
    public void testRemove() {
        assertThrows(UnsupportedOperationException.class, () -> mappedList.remove(4));
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

    @Test
    public void testNegativeIndex() {
        assertThrows(IndexOutOfBoundsException.class, () -> mappedList.listIterator(-1));
    }

    @Test
    public void testLargeIndex() {
        assertThrows(IndexOutOfBoundsException.class, () -> mappedList.listIterator(Integer.MAX_VALUE));

    }

    @Test
    public void testSet() {
        assertThrows(UnsupportedOperationException.class, () -> mappedList.set(3, 4));
    }

    @Test
    public void testIteratorPassedSize() {
        ListIterator<Integer> mappedIterator = mappedList.listIterator(mappedList.size());
        assertThrows(NoSuchElementException.class, () -> mappedIterator.next());
    }

    @Test
    public void testEndOfIterator() {
        ListIterator<Integer> mappedIterator = mappedList.listIterator(mappedList.size());
        assertFalse(mappedIterator.hasNext());
        assertTrue(mappedIterator.hasPrevious());
        assertEquals(mappingFunction.apply(originalList.get(originalList.size() - 1)), mappedIterator.previous());
    }
}
