package com.github.slfotg.collection;

import java.util.AbstractSequentialList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Immutable class representing a collection of lists
 */
public class CompositeSequentialList<E> extends AbstractSequentialList<E> {

    private List<List<E>> lists;
    private int size;

    public CompositeSequentialList(Collection<List<E>> lists) {
        this.lists = new ArrayList<>(lists);
        size = this.lists.stream().mapToInt(List::size).sum();
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        return new CompositeSequentialListIterator<>(this, index);
    }

    @Override
    public int size() {
        return size;
    }

    private static class CompositeSequentialListIterator<E> implements ListIterator<E> {

        private CompositeSequentialList<E> list;
        private int nextIndex;
        private ListIterator<E> currentIterator;
        private ListIterator<ListIterator<E>> iterators;

        public CompositeSequentialListIterator(CompositeSequentialList<E> list, int index) {
            this.list = list;
            List<ListIterator<E>> l = new ArrayList<>(list.size());
            for (int i = 0; i < list.lists.size(); i += 1) {
                l.add(list.lists.get(i).listIterator());
            }
            iterators = l.listIterator();
            currentIterator = iterators.next();
            nextIndex = 0;
            while (nextIndex < index) {
                next();
            }
        }

        @Override
        public boolean hasNext() {
            return nextIndex < list.size();
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            if (!currentIterator.hasNext()) {
                currentIterator = iterators.next();
                return next();
            }
            nextIndex += 1;
            return currentIterator.next();
        }

        @Override
        public boolean hasPrevious() {
            return nextIndex > 0;
        }

        @Override
        public E previous() {
            if (!hasPrevious()) {
                throw new NoSuchElementException();
            }
            if (!currentIterator.hasPrevious()) {
                currentIterator = iterators.previous();
                return previous();
            }
            nextIndex -= 1;
            return currentIterator.previous();
        }

        @Override
        public int nextIndex() {
            return nextIndex;
        }

        @Override
        public int previousIndex() {
            return nextIndex - 1;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void set(E e) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(E e) {
            throw new UnsupportedOperationException();
        }
    }
}
