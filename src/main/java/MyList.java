import gemstones.Gemstone;

import java.util.*;

public class MyList implements List<Gemstone> {
    private Gemstone[] elements;
    private final int DEFAULT_MAX_SiZE=15;
    private int currentMaxSize = DEFAULT_MAX_SiZE;
    private int currentAmountOfElements = 0;
    private final double extensionRate = 1.3;


    private class GemstoneIterator implements Iterator<Gemstone> {
        private int cursor = 0;

        @Override
        public boolean hasNext() {
            return cursor < currentAmountOfElements;
        }

        @Override
        public Gemstone next() {
            if (!hasNext())
                throw new NoSuchElementException();

            return elements[cursor++];
        }

        @Override
        public void remove() {
            if (cursor <= 0)
                throw new IllegalStateException();

            MyList.this.remove(--cursor);
        }
    }


    private class GemstoneListIterator implements ListIterator<Gemstone> {
        private int cursor;
        private int lastReturned = -1;

        public GemstoneListIterator(int index) {
            this.cursor = index;
        }

        @Override
        public boolean hasNext() {
            return cursor < currentAmountOfElements;
        }

        @Override
        public Gemstone next() {
            if (!hasNext())
                throw new NoSuchElementException();

            lastReturned = cursor;
            return elements[cursor++];
        }

        @Override
        public boolean hasPrevious() {
            return cursor > 0;
        }

        @Override
        public Gemstone previous() {
            if (!hasPrevious())
                throw new NoSuchElementException();

            lastReturned = --cursor;
            return elements[cursor];
        }

        @Override
        public int nextIndex() {
            return cursor;
        }

        @Override
        public int previousIndex() {
            return cursor - 1;
        }

        @Override
        public void remove() {
            if (lastReturned < 0)
                throw new IllegalStateException();

            MyList.this.remove(lastReturned);
            cursor = lastReturned;
            lastReturned = -1;
        }

        @Override
        public void set(Gemstone gemstone) {
            if (lastReturned < 0)
                throw new IllegalStateException();

            elements[lastReturned] = gemstone;
        }

        @Override
        public void add(Gemstone gemstone) {
            MyList.this.add(cursor++, gemstone);
            lastReturned = -1;
        }
    }


    public MyList() {
        elements = new Gemstone[currentMaxSize];
    }

    public MyList(Gemstone element) {
        this();
        add(element);
    }

    public MyList(Collection<Gemstone> gemstones) {
        this();
        this.addAll(gemstones);
    }

    @Override
    public int size() {
        return currentAmountOfElements;
    }

    @Override
    public boolean isEmpty() {
        return currentAmountOfElements == 0;
    }

    @Override
    public boolean contains(Object o) {
        if(!(o instanceof Gemstone gemstone))
            return false;

        for(int i = 0; i < currentAmountOfElements; ++i)
            if(elements[i].equals(gemstone))
                return true;

        return false;
    }

    @Override
    public Iterator<Gemstone> iterator() {
        return new GemstoneIterator();
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(elements, currentAmountOfElements);
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if(a.length < currentAmountOfElements)
            return (T[]) Arrays.copyOf(elements, currentAmountOfElements, a.getClass());

        System.arraycopy(elements, 0, a, 0, currentAmountOfElements);
        if(a.length > currentAmountOfElements)
            a[currentAmountOfElements] = null;

        return a;
    }

    @Override
    public boolean add(Gemstone element) {
        try {
            if (currentAmountOfElements == currentMaxSize)
                extend();

            elements[currentAmountOfElements++] = element;
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void extend() {
        currentMaxSize = (int) (currentMaxSize * extensionRate);
        Gemstone[] newElements = new Gemstone[currentMaxSize];
        System.arraycopy(elements, 0, newElements, 0, currentAmountOfElements);
        elements = newElements;
    }

    @Override
    public boolean remove(Object o) {
        int indexToRemove = -1;
        for(int i = 0; i < currentAmountOfElements; ++i)
            if(elements[i].equals(o)) {
                indexToRemove = i;
                break;
            }

        if(indexToRemove == -1)
            return false;

        remove(indexToRemove);
        return true;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        Object[] cArray = c.toArray();
        for (Object o : cArray)
            if (!contains(o))
                return false;

        return true;
    }

    @Override
    public boolean addAll(Collection<? extends Gemstone> c) {
        return addAll(currentAmountOfElements, c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends Gemstone> c) {
        if(index < 0 || index > currentAmountOfElements)
            throw new IndexOutOfBoundsException("Index out of range");

        try {
            while (currentMaxSize < currentAmountOfElements + c.size())
                currentMaxSize = (int) (currentMaxSize * extensionRate);

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

    @Override
    public boolean removeAll(Collection<?> c) {
        int currentAmountOfElementsAtStart = currentAmountOfElements;
        for(Object g : c)
            remove(g);
        return currentAmountOfElementsAtStart != currentAmountOfElements;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        int currentAmountOfElementsAtStart = currentAmountOfElements;

        for(int i = 0; i < currentAmountOfElements; ++i) {
            if(!c.contains(elements[i])) {
                remove(i);
                --i;
            }
        }

        return currentAmountOfElementsAtStart != currentAmountOfElements;
    }

    @Override
    public void clear() {
        currentMaxSize = DEFAULT_MAX_SiZE;
        elements = new Gemstone[currentMaxSize];
        currentAmountOfElements = 0;
    }

    @Override
    public Gemstone get(int index) {
        if(index < 0 || index >= currentAmountOfElements)
            throw new IndexOutOfBoundsException("Index out of range");
        return elements[index];
    }

    @Override
    public Gemstone set(int index, Gemstone element) {
        if(index < 0 || index >= currentAmountOfElements)
            throw new IndexOutOfBoundsException("Index out of range");

        Gemstone elementToReturn = elements[index];
        elements[index] = element;
        return elementToReturn;
    }

    @Override
    public void add(int index, Gemstone element) {
        if(index < 0 || index > currentAmountOfElements)
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + currentAmountOfElements);

        if(currentAmountOfElements == currentMaxSize)
            extend();

        Gemstone[] copy = new Gemstone[currentMaxSize];
        System.arraycopy(elements, 0, copy, 0, index);
        copy[index] = element;
        System.arraycopy(elements, index, copy, index+1, currentAmountOfElements - index - 1);

        ++currentAmountOfElements;
        elements = copy;
    }

    @Override
    public Gemstone remove(int index) {
        if(index < 0 || index >= currentAmountOfElements)
            throw new IndexOutOfBoundsException("Index out of range");

        Gemstone elementToRemove = elements[index];

        for(int i = index; i < currentAmountOfElements - 1; ++i)
            elements[i] = elements[i+1];

        --currentAmountOfElements;
        elements[currentAmountOfElements] = null;

        return elementToRemove;
    }

    @Override
    public int indexOf(Object o) {
        int index = -1;

        for(int i = 0; i < currentAmountOfElements; ++i) {
            if(elements[i].equals(o)) {
                index = i;
                break;
            }
        }

        return index;
    }

    @Override
    public int lastIndexOf(Object o) {
        int index = -1;

        for(int i = currentAmountOfElements-1; i >= 0; --i) {
            if(elements[i].equals(o)) {
                index = i;
                break;
            }
        }

        return index;
    }

    @Override
    public ListIterator<Gemstone> listIterator() {
        return new GemstoneListIterator(0);
    }

    @Override
    public ListIterator<Gemstone> listIterator(int index) {
        if (index < 0 || index > currentAmountOfElements)
            throw new IndexOutOfBoundsException("Index: " + index);

        return new GemstoneListIterator(index);
    }

    @Override
    public List<Gemstone> subList(int fromIndex, int toIndex) {
        if(fromIndex < 0 || toIndex >= currentAmountOfElements || fromIndex > toIndex)
            throw new IndexOutOfBoundsException("Invalid index");

        Gemstone[] elementsToReturn = new Gemstone[toIndex-fromIndex];
        System.arraycopy(elements, fromIndex, elementsToReturn, 0, toIndex - fromIndex);

        return List.of(elementsToReturn);
    }
}
