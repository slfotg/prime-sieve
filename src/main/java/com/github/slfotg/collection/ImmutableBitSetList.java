package com.github.slfotg.collection;

import java.util.AbstractSequentialList;
import java.util.BitSet;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class ImmutableBitSetList extends AbstractSequentialList<Integer> {

    protected BitSet bitSet;
    protected int size;

    public ImmutableBitSetList(BitSet bitSet) {
        this.bitSet = bitSet;
        this.size = bitSet.cardinality();
    }

    @Override
    public ListIterator<Integer> listIterator(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        return new ImmutableBitSetListIterator(bitSet, size, index);
    }

    @Override
    public int size() {
        return size;
    }

    static class ImmutableBitSetListIterator implements ListIterator<Integer> {

        protected BitSet bitSet;
        protected int size;
        protected int index;
        protected int value;

        public ImmutableBitSetListIterator(BitSet bitSet, int size, int index) {
            this.bitSet = bitSet;
            this.size = size;
            this.index = -1;
            this.value = -1;
            while (this.index < index) {
                next();
            }
        }

        @Override
        public void add(Integer e) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean hasNext() {
            return index < size;
        }

        @Override
        public boolean hasPrevious() {
            return index > 0;
        }

        @Override
        public Integer next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            index += 1;
            int next = value;
            value = bitSet.nextSetBit(value + 1);
            return next;
        }

        @Override
        public int nextIndex() {
            return index;
        }

        @Override
        public Integer previous() {
            if (!hasPrevious()) {
                throw new NoSuchElementException();
            }
            index -= 1;
            if (value == -1) {
                value = bitSet.length();
            }
            value = bitSet.previousSetBit(value - 1);
            return value;
        }

        @Override
        public int previousIndex() {
            return index - 1;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void set(Integer e) {
            throw new UnsupportedOperationException();
        }

    }
}
