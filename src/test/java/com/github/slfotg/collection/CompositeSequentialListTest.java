package com.github.slfotg.collection;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.BitSet;
import java.util.List;
import java.util.ListIterator;

import static org.junit.Assert.assertEquals;

public class CompositeSequentialListTest {

    CompositeSequentialList<Integer> compositeList;

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
}
