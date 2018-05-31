package com.github.slfotg.collection;

import java.util.*;

/**
 * Immutable class representing a collection of lists
 */
public class CompositeSequentialList<E> extends AbstractSequentialList<E> {

    private List<List<E>> lists;
    private int size;

    public CompositeSequentialList(Collection<List<E>> lists) {
        this.lists = new ArrayList<List<E>>(lists);
        size = this.lists.stream().mapToInt(l -> l.size()).sum();
    }

    @Override
    public boolean add(E elem) {
        return false;
    }

    @Override
    public void add(int index, E elem) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public E remove(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public ListIterator<E> listIterator(int i) {
        return new CompositeSequentialListIterator<>(this, i);
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
