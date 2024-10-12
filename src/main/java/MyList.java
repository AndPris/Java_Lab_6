import gemstones.Gemstone;

import java.util.*;

/**
 * The {@code MyList} class is a custom implementation of a dynamic array
 * that stores {@code Gemstone} objects. It provides methods to manipulate
 * the collection of gemstones, including adding, removing, and iterating
 * over the elements. This class implements the {@code List<Gemstone>} interface.
 *
 * The internal array grows automatically when the number of gemstones exceeds
 * the current capacity. By default, the array has a capacity of 15 elements,
 * and it increases by 30% when the array is full.
 *
 * This class also provides two custom iterators: {@code GemstoneIterator} and
 * {@code GemstoneListIterator}, which allow traversing the list in forward and
 * backward directions.
 *
 * <p>
 * Example usage:
 * <pre>
 *     MyList gemstones = new MyList();
 *     gemstones.add(new Gemstone(...));
 *     gemstones.remove(0);
 * </pre>
 * </p>
 *
 * @see Gemstone
 * @see List
 */
public class MyList implements List<Gemstone> {

    /**
     * Array that holds the {@code Gemstone} elements.
     */
    private Gemstone[] elements;

    /**
     * Default initial capacity of the list.
     */
    private final int DEFAULT_MAX_SIZE = 15;

    /**
     * The current maximum size of the list.
     */
    private int currentMaxSize = DEFAULT_MAX_SIZE;

    /**
     * The number of gemstones currently in the list.
     */
    private int currentAmountOfElements = 0;

    /**
     * The rate at which the internal array grows when resizing is required.
     * The array size increases by 30% when full.
     */
    private final double extensionRate = 1.3;

    /**
     * The {@code GemstoneIterator} class is an implementation of the
     * {@code Iterator<Gemstone>} interface, allowing forward traversal
     * over the elements of the {@code MyList}.
     */
    private class GemstoneIterator implements Iterator<Gemstone> {
        private int cursor = 0;

        /**
         * Returns {@code true} if the iteration has more elements.
         *
         * @return {@code true} if there are more elements to iterate over
         */
        @Override
        public boolean hasNext() {
            return cursor < currentAmountOfElements;
        }

        /**
         * Returns the next gemstone in the iteration.
         *
         * @return the next gemstone in the list
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public Gemstone next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return elements[cursor++];
        }

        /**
         * Removes from the underlying collection the last element returned
         * by this iterator.
         *
         * @throws IllegalStateException if {@code next()} has not yet been called
         */
        @Override
        public void remove() {
            if (cursor <= 0) {
                throw new IllegalStateException();
            }
            MyList.this.remove(--cursor);
        }
    }

    /**
     * The {@code GemstoneListIterator} class is an implementation of the
     * {@code ListIterator<Gemstone>} interface, allowing bidirectional traversal
     * over the elements of the {@code MyList}.
     */
    private class GemstoneListIterator implements ListIterator<Gemstone> {
        private int cursor;
        private int lastReturned = -1;

        /**
         * Constructs a {@code GemstoneListIterator} starting at the specified index.
         *
         * @param index the index to start the iteration from
         */
        public GemstoneListIterator(int index) {
            this.cursor = index;
        }

        /**
         * Returns {@code true} if the iteration has more elements when traversing forward.
         *
         * @return {@code true} if there are more elements to iterate over in the forward direction
         */
        @Override
        public boolean hasNext() {
            return cursor < currentAmountOfElements;
        }

        /**
         * Returns the next gemstone in the forward iteration.
         *
         * @return the next gemstone in the list
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public Gemstone next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            lastReturned = cursor;
            return elements[cursor++];
        }

        /**
         * Returns {@code true} if the iteration has more elements when traversing backward.
         *
         * @return {@code true} if there are more elements to iterate over in the backward direction
         */
        @Override
        public boolean hasPrevious() {
            return cursor > 0;
        }

        /**
         * Returns the previous gemstone in the backward iteration.
         *
         * @return the previous gemstone in the list
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public Gemstone previous() {
            if (!hasPrevious()) {
                throw new NoSuchElementException();
            }
            lastReturned = --cursor;
            return elements[cursor];
        }

        /**
         * Returns the index of the next element to be returned by the iterator.
         *
         * @return the index of the next element in the list
         */
        @Override
        public int nextIndex() {
            return cursor;
        }

        /**
         * Returns the index of the previous element to be returned by the iterator.
         *
         * @return the index of the previous element in the list
         */
        @Override
        public int previousIndex() {
            return cursor - 1;
        }

        /**
         * Removes the last element returned by the iterator. This method can be called
         * only once per call to {@link #next} or {@link #previous}. If the remove operation
         * is not supported or the list has been structurally modified, it throws an
         * {@link IllegalStateException}.
         *
         * @throws IllegalStateException if {@code next()} or {@code previous()} hasn't been called,
         * or if the element has already been removed.
         */
        @Override
        public void remove() {
            if (lastReturned < 0) {
                throw new IllegalStateException();
            }

            MyList.this.remove(lastReturned);
            cursor = lastReturned;
            lastReturned = -1;
        }

        /**
         * Replaces the last element returned by {@link #next()} or {@link #previous()}
         * with the specified gemstone. This method can be called only if {@link #next()}
         * or {@link #previous()} has been called and the list hasn't been structurally modified.
         *
         * @param gemstone the gemstone to replace the last element returned by the iterator
         * @throws IllegalStateException if {@code next()} or {@code previous()} hasn't been called,
         * or if the element has already been replaced or removed.
         */
        @Override
        public void set(Gemstone gemstone) {
            if (lastReturned < 0) {
                throw new IllegalStateException();
            }

            elements[lastReturned] = gemstone;
        }

        /**
         * Inserts the specified gemstone into the list at the position of the cursor.
         * The gemstone is added immediately before the next element that would be
         * returned by {@link #next()}, if any, and after the element that would be
         * returned by {@link #previous()}, if any.
         *
         * @param gemstone the gemstone to be added to the list
         */
        @Override
        public void add(Gemstone gemstone) {
            MyList.this.add(cursor++, gemstone);
            lastReturned = -1;
        }

    }

    /**
     * Constructs an empty {@code MyList} with the default initial capacity.
     */
    public MyList() {
        elements = new Gemstone[currentMaxSize];
    }

    /**
     * Constructs a {@code MyList} containing a single gemstone.
     *
     * @param element the gemstone to be added to the list
     */
    public MyList(Gemstone element) {
        this();
        add(element);
    }

    /**
     * Constructs a {@code MyList} containing all gemstones from the specified collection.
     *
     * @param gemstones the collection of gemstones to be added to the list
     */
    public MyList(Collection<Gemstone> gemstones) {
        this();
        this.addAll(gemstones);
    }

    /**
     * Returns the number of elements in this list.
     *
     * @return the number of elements in the list
     */
    @Override
    public int size() {
        return currentAmountOfElements;
    }

    /**
     * Returns {@code true} if this list contains no elements.
     *
     * @return {@code true} if the list is empty, otherwise {@code false}
     */
    @Override
    public boolean isEmpty() {
        return currentAmountOfElements == 0;
    }

    /**
     * Returns {@code true} if this list contains all the elements in the specified collection.
     *
     * @param c the collection to be checked for containment in this list
     * @return {@code true} if this list contains all the elements of the specified collection, otherwise {@code false}
     * @throws NullPointerException if the specified collection is null
     */
    @Override
    public boolean containsAll(Collection<?> c) {
        Object[] cArray = c.toArray();
        for (Object o : cArray) {
            if (!contains(o)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns {@code true} if this list contains the specified element.
     *
     * @param o element whose presence in this list is to be tested
     * @return {@code true} if this list contains the specified element, otherwise {@code false}
     * @throws ClassCastException if the specified object is not of type {@code Gemstone}
     */
    @Override
    public boolean contains(Object o) {
        if (!(o instanceof Gemstone gemstone)) {
            return false;
        }

        for (int i = 0; i < currentAmountOfElements; ++i) {
            if (elements[i].equals(gemstone)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Returns an array containing all the elements in this list in proper sequence.
     *
     * @return an array containing all the elements of this list
     */
    @Override
    public Object[] toArray() {
        return Arrays.copyOf(elements, currentAmountOfElements);
    }

    /**
     * Returns an array containing all the elements in this list in proper sequence;
     * the runtime type of the returned array is that of the specified array.
     * If the list fits in the specified array, it is returned; otherwise, a new
     * array is allocated with the runtime type of the specified array and the size
     * of this list.
     *
     * @param a the array into which the elements of this list are to be stored, if it is big enough;
     *          otherwise, a new array of the same runtime type is allocated for this purpose
     * @param <T> the runtime type of the array to contain the list
     * @return an array containing the elements of this list
     * @throws ArrayStoreException if the runtime type of the specified array is not a supertype of the runtime type of every element in this list
     * @throws NullPointerException if the specified array is null
     */
    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length < currentAmountOfElements) {
            return (T[]) Arrays.copyOf(elements, currentAmountOfElements, a.getClass());
        }

        System.arraycopy(elements, 0, a, 0, currentAmountOfElements);
        if (a.length > currentAmountOfElements) {
            a[currentAmountOfElements] = null;
        }

        return a;
    }



    /**
     * Returns a view of the portion of this list between the specified {@code fromIndex}, inclusive,
     * and {@code toIndex}, exclusive. The returned list is immutable and backed by the array.
     *
     * @param fromIndex low endpoint (inclusive) of the subList
     * @param toIndex high endpoint (exclusive) of the subList
     * @return a list containing the elements between the specified indices
     * @throws IndexOutOfBoundsException if {@code fromIndex} is negative, {@code toIndex} is greater
     *         than the size of the list, or {@code fromIndex} is greater than {@code toIndex}
     */
    @Override
    public List<Gemstone> subList(int fromIndex, int toIndex) {
        if (fromIndex < 0 || toIndex > currentAmountOfElements || fromIndex > toIndex) {
            throw new IndexOutOfBoundsException("Invalid index");
        }

        Gemstone[] elementsToReturn = new Gemstone[toIndex - fromIndex];
        System.arraycopy(elements, fromIndex, elementsToReturn, 0, toIndex - fromIndex);

        return List.of(elementsToReturn);
    }

    /**
     * Appends the specified element to the end of this list.
     *
     * @param element element to be appended to this list
     * @return {@code true} if the element was added successfully, otherwise {@code false}
     */
    @Override
    public boolean add(Gemstone element) {
        try {
            if (currentAmountOfElements == currentMaxSize) {
                extend();
            }
            elements[currentAmountOfElements++] = element;
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Doubles the size of the internal array when the list's capacity is reached.
     */
    private void extend() {
        currentMaxSize = (int) (currentMaxSize * extensionRate);
        Gemstone[] newElements = new Gemstone[currentMaxSize];
        System.arraycopy(elements, 0, newElements, 0, currentAmountOfElements);
        elements = newElements;
    }

    /**
     * Inserts the specified element at the specified position in this list.
     * Shifts the element currently at that position (if any) and any subsequent
     * elements to the right (adds one to their indices).
     *
     * @param index index at which the specified element is to be inserted
     * @param element element to be inserted
     * @throws IndexOutOfBoundsException if the index is out of range
     *         ({@code index < 0 || index > size()})
     */
    @Override
    public void add(int index, Gemstone element) {
        if (index < 0 || index > currentAmountOfElements) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + currentAmountOfElements);
        }

        if (currentAmountOfElements == currentMaxSize) {
            extend();
        }

        Gemstone[] copy = new Gemstone[currentMaxSize];
        System.arraycopy(elements, 0, copy, 0, index);
        copy[index] = element;
        System.arraycopy(elements, index, copy, index + 1, currentAmountOfElements - index - 1);

        ++currentAmountOfElements;
        elements = copy;
    }

    /**
     * Appends all the elements in the specified collection to the end of this list,
     * in the order that they are returned by the specified collection's iterator.
     *
     * @param c collection containing elements to be added to this list
     * @return {@code true} if the list changed as a result of the call
     */
    @Override
    public boolean addAll(Collection<? extends Gemstone> c) {
        return addAll(currentAmountOfElements, c);
    }

    /**
     * Inserts all the elements in the specified collection into this list, starting at
     * the specified position. Shifts the element currently at that position (if any) and any
     * subsequent elements to the right (increases their indices).
     *
     * @param index index at which to insert the first element from the specified collection
     * @param c collection containing elements to be added to this list
     * @return {@code true} if the list changed as a result of the call
     * @throws IndexOutOfBoundsException if the index is out of range
     *         ({@code index < 0 || index > size()})
     */
    @Override
    public boolean addAll(int index, Collection<? extends Gemstone> c) {
        if (index < 0 || index > currentAmountOfElements) {
            throw new IndexOutOfBoundsException("Index out of range");
        }

        try {
            while (currentMaxSize < currentAmountOfElements + c.size()) {
                currentMaxSize = (int) (currentMaxSize * extensionRate);
            }

            Gemstone[] newElements = new Gemstone[currentMaxSize];
            System.arraycopy(elements, 0, newElements, 0, index);

            int j = 0;
            for (Gemstone g : c) {
                newElements[index + j] = g;
                ++j;
            }

            System.arraycopy(elements, index, newElements, index + c.size(), currentAmountOfElements - index);

            elements = newElements;
            currentAmountOfElements += c.size();
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    /**
     * Removes the first occurrence of the specified element from this list, if it is present.
     * If the list does not contain the element, it is unchanged.
     *
     * @param o element to be removed from this list, if present
     * @return {@code true} if this list contained the specified element
     */
    @Override
    public boolean remove(Object o) {
        int indexToRemove = -1;
        for (int i = 0; i < currentAmountOfElements; ++i) {
            if (elements[i].equals(o)) {
                indexToRemove = i;
                break;
            }
        }

        if (indexToRemove == -1) {
            return false;
        }

        remove(indexToRemove);
        return true;
    }

    /**
     * Removes the element at the specified position in this list. Shifts any subsequent elements
     * to the left (subtracts one from their indices).
     *
     * @param index the index of the element to be removed
     * @return the element that was removed from the list
     * @throws IndexOutOfBoundsException if the index is out of range
     *         ({@code index < 0 || index >= size()})
     */
    @Override
    public Gemstone remove(int index) {
        if (index < 0 || index >= currentAmountOfElements) {
            throw new IndexOutOfBoundsException("Index out of range");
        }

        Gemstone elementToRemove = elements[index];

        for (int i = index; i < currentAmountOfElements - 1; ++i) {
            elements[i] = elements[i + 1];
        }

        --currentAmountOfElements;
        elements[currentAmountOfElements] = null;

        return elementToRemove;
    }

    /**
     * Removes from this list all of its elements that are contained in the specified collection.
     *
     * @param c collection containing elements to be removed from this list
     * @return {@code true} if this list changed as a result of the call
     */
    @Override
    public boolean removeAll(Collection<?> c) {
        int currentAmountOfElementsAtStart = currentAmountOfElements;
        for (Object g : c) {
            remove(g);
        }
        return currentAmountOfElementsAtStart != currentAmountOfElements;
    }

    /**
     * Retains only the elements in this list that are contained in the specified collection.
     * In other words, removes from this list all of its elements that are not contained in the specified collection.
     *
     * @param c collection containing elements to be retained in this list
     * @return {@code true} if this list changed as a result of the call
     */
    @Override
    public boolean retainAll(Collection<?> c) {
        int currentAmountOfElementsAtStart = currentAmountOfElements;

        for (int i = 0; i < currentAmountOfElements; ++i) {
            if (!c.contains(elements[i])) {
                remove(i);
                --i;
            }
        }

        return currentAmountOfElementsAtStart != currentAmountOfElements;
    }

    /**
     * Removes all the elements from this list. The list will be empty after this call returns.
     */
    @Override
    public void clear() {
        currentMaxSize = DEFAULT_MAX_SIZE;
        elements = new Gemstone[currentMaxSize];
        currentAmountOfElements = 0;
    }

    /**
     * Returns the element at the specified position in this list.
     *
     * @param index index of the element to return
     * @return the element at the specified position in this list
     * @throws IndexOutOfBoundsException if the index is out of range
     *         ({@code index < 0 || index >= size()})
     */
    @Override
    public Gemstone get(int index) {
        if (index < 0 || index >= currentAmountOfElements) {
            throw new IndexOutOfBoundsException("Index out of range");
        }
        return elements[index];
    }

    /**
     * Replaces the element at the specified position in this list with the specified element.
     *
     * @param index index of the element to replace
     * @param element element to be stored at the specified position
     * @return the element previously at the specified position
     * @throws IndexOutOfBoundsException if the index is out of range
     *         ({@code index < 0 || index >= size()})
     */
    @Override
    public Gemstone set(int index, Gemstone element) {
        if (index < 0 || index >= currentAmountOfElements) {
            throw new IndexOutOfBoundsException("Index out of range");
        }

        Gemstone elementToReturn = elements[index];
        elements[index] = element;
        return elementToReturn;
    }

    /**
     * Returns the index of the first occurrence of the specified element in this list, or -1 if this list does not contain the element.
     *
     * @param o element to search for
     * @return the index of the first occurrence of the specified element, or -1 if this list does not contain the element
     */
    @Override
    public int indexOf(Object o) {
        int index = -1;

        for (int i = 0; i < currentAmountOfElements; ++i) {
            if (elements[i].equals(o)) {
                index = i;
                break;
            }
        }

        return index;
    }

    /**
     * Returns the index of the last occurrence of the specified element in this list, or -1 if this list does not contain the element.
     *
     * @param o element to search for
     * @return the index of the last occurrence of the specified element, or -1 if this list does not contain the element
     */
    @Override
    public int lastIndexOf(Object o) {
        int index = -1;

        for (int i = currentAmountOfElements - 1; i >= 0; --i) {
            if (elements[i].equals(o)) {
                index = i;
                break;
            }
        }

        return index;
    }

    /**
     * Returns an iterator over the elements in this list in proper sequence.
     *
     * @return an iterator over the elements in this list in proper sequence
     */
    @Override
    public Iterator<Gemstone> iterator() {
        return new GemstoneIterator();
    }

    /**
     * Returns a list iterator over the elements in this list (in proper sequence), starting at the specified position in the list.
     *
     * @param index index of the first element to be returned from the list iterator (by a call to {@code next})
     * @return a list iterator over the elements in this list (in proper sequence), starting at the specified position in the list
     * @throws IndexOutOfBoundsException if the index is out of range
     *         ({@code index < 0 || index > size()})
     */
    @Override
    public ListIterator<Gemstone> listIterator(int index) {
        if (index < 0 || index > currentAmountOfElements) {
            throw new IndexOutOfBoundsException("Index: " + index);
        }

        return new GemstoneListIterator(index);
    }

    /**
     * Returns a list iterator over the elements in this list (in proper sequence).
     *
     * @return a list iterator over the elements in this list (in proper sequence)
     */
    @Override
    public ListIterator<Gemstone> listIterator() {
        return new GemstoneListIterator(0);
    }

}
