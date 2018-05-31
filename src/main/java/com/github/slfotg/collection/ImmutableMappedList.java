package com.github.slfotg.collection;

import java.util.AbstractSequentialList;
import java.util.List;
import java.util.ListIterator;
import java.util.function.Function;

public class ImmutableMappedList<U, V> extends AbstractSequentialList<V> {

    protected List<U> mappedList;

    protected Function<U, V> mappingFunction;

    public ImmutableMappedList(List<U> mappedList, Function<U, V> mappingFunction) {
        this.mappedList = mappedList;
        this.mappingFunction = mappingFunction;
    }

    @Override
    public ListIterator<V> listIterator(int index) {
        return new ImmutableMappedListIterator<>(mappedList.listIterator(index), mappingFunction);
    }

    @Override
    public int size() {
        return mappedList.size();
    }

    static class ImmutableMappedListIterator<U, V> implements ListIterator<V> {

        protected ListIterator<U> listIterator;

        protected Function<U, V> mappingFunction;

        public ImmutableMappedListIterator(ListIterator<U> listIterator, Function<U, V> mappingFunction) {
            this.listIterator = listIterator;
            this.mappingFunction = mappingFunction;
        }

        @Override
        public void add(V v) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean hasNext() {
            return listIterator.hasNext();
        }

        @Override
        public boolean hasPrevious() {
            return listIterator.hasPrevious();
        }

        @Override
        public V next() {
            return mappingFunction.apply(listIterator.next());
        }

        @Override
        public int nextIndex() {
            return listIterator.nextIndex();
        }

        @Override
        public V previous() {
            return mappingFunction.apply(listIterator.previous());
        }

        @Override
        public int previousIndex() {
            return listIterator.previousIndex();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void set(V v) {
            throw new UnsupportedOperationException();
        }

    }
}
