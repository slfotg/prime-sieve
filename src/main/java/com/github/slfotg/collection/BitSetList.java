package com.github.slfotg.collection;

import java.util.AbstractSequentialList;
import java.util.BitSet;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.function.Function;

/**
 * Immutable List represented by a BitSet
 * @param <E>
 */
public class BitSetList<E> extends AbstractSequentialList<E> {

    private BitSet bitSet;
    private Function<Integer, E> mapping;
    private int size;

    public BitSetList(BitSet bitSet, Function<Integer, E> mapping) {
        this.bitSet = bitSet;
        this.mapping = mapping;
        this.size = bitSet.cardinality();
    }

    public ListIterator<E> listIterator(int i) {
        return new BitSetListIterator<E>(this, i);
    }

    public int size() {
        return size;
    }

    private static class BitSetListIterator<E> implements ListIterator<E> {

        private BitSetList<E> bitSetList;
        int bitSetIndex;
        int currentIndex;

        public BitSetListIterator(BitSetList<E> bitSetList, int i) {
            this.bitSetList = bitSetList;
            this.currentIndex = 0;
            this.bitSetIndex = -1;
            while (currentIndex < i) {
                next();
            }
        }

        @Override
        public boolean hasNext() {
            return currentIndex < bitSetList.size();
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            bitSetIndex = bitSetList.bitSet.nextSetBit(bitSetIndex + 1);
            currentIndex += 1;
            return bitSetList.mapping.apply(bitSetIndex);
        }

        @Override
        public boolean hasPrevious() {
            return currentIndex > 0;
        }

        @Override
        public E previous() {
            if (!hasPrevious()) {
                throw new NoSuchElementException();
            }
            E previous = bitSetList.mapping.apply(bitSetIndex);
            bitSetIndex = bitSetList.bitSet.previousSetBit(bitSetIndex - 1);
            currentIndex -= 1;
            return previous;
        }

        @Override
        public int nextIndex() {
            return currentIndex;
        }

        @Override
        public int previousIndex() {
            return currentIndex - 1;
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
