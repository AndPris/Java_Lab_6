import gemstones.Gemstone;
import gemstones.imp.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import static org.junit.jupiter.api.Assertions.*;

public class MyListTests {
    private MyList emptyConstructorList;
    private MyList singleElementConstructorList;
    private MyList collectionConstructorList;
    private Agate agate;
    private Diamond diamond;
    private Lazurite lazurite;

    @BeforeEach
    public void setUp() {
        agate = new Agate(1, 1, 1);
        diamond = new Diamond(1, 1, 1);
        lazurite = new Lazurite(1, 1, 1);

        emptyConstructorList = new MyList();

        singleElementConstructorList = new MyList(lazurite);

        List<Gemstone> gemstones = new ArrayList<Gemstone>();
        gemstones.add(agate);
        gemstones.add(diamond);
        collectionConstructorList = new MyList(gemstones);
    }

    @Test
    public void sizeTests() {
        assertEquals(0, emptyConstructorList.size());
        assertEquals(1, singleElementConstructorList.size());
        assertEquals(2, collectionConstructorList.size());
    }

    @Test
    public void isEmptyTest() {
        assertTrue(emptyConstructorList.isEmpty());
        assertFalse(singleElementConstructorList.isEmpty());
        assertFalse(collectionConstructorList.isEmpty());
    }

    @Test
    public void containsTest() {
        assertFalse(emptyConstructorList.contains(new String("test")));
        assertTrue(singleElementConstructorList.contains(new Lazurite(1, 1,1)));
        assertFalse(singleElementConstructorList.contains(agate));
        assertFalse(collectionConstructorList.contains(new Lazurite(1.5, 1,1)));
    }

    @Test
    public void toArrayTest1() {
        Gemstone[] expected = new Gemstone[2];
        expected[0] = agate;
        expected[1] = diamond;
        assertArrayEquals(expected, collectionConstructorList.toArray());
    }

    @Test
    public void toArrayTest2() {
        Gemstone[] expected = new Gemstone[2];
        expected[0] = agate;
        expected[1] = diamond;
        assertArrayEquals(expected, collectionConstructorList.toArray(new Gemstone[1]));
    }

    @Test
    public void toArrayTest3() {
        Gemstone[] expected = new Gemstone[3];
        expected[0] = agate;
        expected[1] = diamond;
        expected[2] = null;

        Gemstone[] actual = collectionConstructorList.toArray(new Gemstone[3]);
        assertArrayEquals(expected, actual);
    }

    @Test
    public void subListTest() {
        collectionConstructorList.add(new Emerald(1, 1, 1));

        Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {collectionConstructorList.subList(-1, 2);});
        assertEquals("Invalid index", exception.getMessage());

        exception = assertThrows(IndexOutOfBoundsException.class, () -> {collectionConstructorList.subList(1, 22);});
        assertEquals("Invalid index", exception.getMessage());

        exception = assertThrows(IndexOutOfBoundsException.class, () -> {collectionConstructorList.subList(2, 1);});
        assertEquals("Invalid index", exception.getMessage());

        List<Gemstone> expected = new ArrayList<>();
        expected.add(diamond);
        expected.add(new Emerald(1, 1, 1));

        assertEquals(expected, collectionConstructorList.subList(1, 3));
    }

    @Test
    public void addAllTest1() {
        Emerald emerald = new Emerald(1, 1, 1);
        List<Gemstone> elementsToAdd = new ArrayList<>();
        elementsToAdd.add(emerald);
        elementsToAdd.add(lazurite);

        assertTrue(collectionConstructorList.addAll(elementsToAdd));

        Gemstone[] expected = new Gemstone[4];
        expected[0] = agate;
        expected[1] = diamond;
        expected[2] = emerald;
        expected[3] = lazurite;

        assertArrayEquals(expected, collectionConstructorList.toArray());
    }

    @Test
    public void addAllTest2() {
        Emerald emerald = new Emerald(1, 1, 1);
        List<Gemstone> elementsToAdd = new ArrayList<>();
        elementsToAdd.add(emerald);
        elementsToAdd.add(lazurite);

        assertTrue(collectionConstructorList.addAll(1, elementsToAdd));

        Gemstone[] expected = new Gemstone[4];
        expected[0] = agate;
        expected[1] = emerald;
        expected[2] = lazurite;
        expected[3] = diamond;

        assertArrayEquals(expected, collectionConstructorList.toArray());
    }

    @Test
    public void removeTest1() {
        assertTrue(collectionConstructorList.remove(agate));
        assertFalse(collectionConstructorList.contains(agate));
        assertFalse(collectionConstructorList.remove(new Opal(1, 1, 1)));
        Gemstone[] expected = new Gemstone[1];
        expected[0] = diamond;
        assertArrayEquals(expected, collectionConstructorList.toArray());
    }

    @Test
    public void removeTest2() {
        Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {collectionConstructorList.remove(-1);});
        assertEquals("Index out of range", exception.getMessage());

        exception = assertThrows(IndexOutOfBoundsException.class, () -> {collectionConstructorList.remove(5);});
        assertEquals("Index out of range", exception.getMessage());

        assertEquals(agate, collectionConstructorList.remove(0));
        Gemstone[] expected = new Gemstone[1];
        expected[0] = diamond;
        assertArrayEquals(expected, collectionConstructorList.toArray());
    }

    @Test
    public void removeAllTest1() {
        List<Gemstone> elementsToRemove = new ArrayList<>();
        elementsToRemove.add(agate);
        elementsToRemove.add(diamond);
        assertTrue(collectionConstructorList.removeAll(elementsToRemove));
        assertTrue(collectionConstructorList.isEmpty());
        assertFalse(collectionConstructorList.contains(agate));
        assertFalse(collectionConstructorList.contains(diamond));
    }

    @Test
    public void removeAllTest2() {
        List<Gemstone> elementsToRemove = new ArrayList<>();
        elementsToRemove.add(new Emerald(1, 1, 1));
        elementsToRemove.add(new Rubin(1, 1, 1));
        assertFalse(collectionConstructorList.removeAll(elementsToRemove));
        assertTrue(collectionConstructorList.contains(agate));
        assertTrue(collectionConstructorList.contains(diamond));
    }

    @Test
    public void containsAllTest() {
        List<Gemstone> elementsToCheck = new ArrayList<>();
        elementsToCheck.add(agate);
        elementsToCheck.add(diamond);

        assertTrue(collectionConstructorList.containsAll(elementsToCheck));

        elementsToCheck.add(new Emerald(1, 1, 1));
        assertFalse(collectionConstructorList.containsAll(elementsToCheck));
    }

    @Test
    public void retainAllTest1() {
        List<Gemstone> elementsToRetain = new ArrayList<>();
        elementsToRetain.add(agate);
        elementsToRetain.add(lazurite);

        assertTrue(collectionConstructorList.retainAll(elementsToRetain));
        assertTrue(collectionConstructorList.contains(agate));
        assertFalse(collectionConstructorList.contains(diamond));
        assertEquals(1, collectionConstructorList.size());
    }

    @Test
    public void retainAllTest2() {
        List<Gemstone> elementsToRetain = new ArrayList<>();
        elementsToRetain.add(agate);
        elementsToRetain.add(diamond);

        assertFalse(collectionConstructorList.retainAll(elementsToRetain));
        assertTrue(collectionConstructorList.contains(agate));
        assertTrue(collectionConstructorList.contains(diamond));
        assertEquals(2, collectionConstructorList.size());
    }

    @Test
    public void clearTest() {
        collectionConstructorList.clear();
        assertTrue(collectionConstructorList.isEmpty());
        assertEquals(0, collectionConstructorList.size());
        assertFalse(collectionConstructorList.contains(agate));
        assertFalse(collectionConstructorList.contains(diamond));
    }

    @Test
    public void getTest() {
        Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {collectionConstructorList.get(-1);});
        assertEquals("Index out of range", exception.getMessage());

        exception = assertThrows(IndexOutOfBoundsException.class, () -> {collectionConstructorList.get(2);});
        assertEquals("Index out of range", exception.getMessage());

        assertEquals(agate, collectionConstructorList.get(0));
        assertEquals(diamond, collectionConstructorList.get(1));
    }

    @Test
    public void setTest() {
        Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {collectionConstructorList.set(-1, lazurite);});
        assertEquals("Index out of range", exception.getMessage());

        exception = assertThrows(IndexOutOfBoundsException.class, () -> {collectionConstructorList.set(2, lazurite);});
        assertEquals("Index out of range", exception.getMessage());

        collectionConstructorList.set(0, lazurite);
        assertTrue(collectionConstructorList.contains(lazurite));
        assertTrue(collectionConstructorList.contains(diamond));
        assertFalse(collectionConstructorList.contains(agate));
        assertEquals(lazurite, collectionConstructorList.get(0));
    }

    @Test
    public void addTest() {
        Lazurite lazurite = new Lazurite(1, 1, 1);

        Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {collectionConstructorList.add(-1, lazurite);});
        assertEquals("Index: -1, Size: 2", exception.getMessage());

        exception = assertThrows(IndexOutOfBoundsException.class, () -> {collectionConstructorList.add(3, lazurite);});
        assertEquals("Index: 3, Size: 2", exception.getMessage());
    }

    @Test
    public void indexOfTest() {
        assertEquals(0, collectionConstructorList.indexOf(agate));
        assertEquals(1, collectionConstructorList.indexOf(diamond));
        assertEquals(-1, collectionConstructorList.indexOf(lazurite));
    }

    @Test
    public void lastIndexOfTest() {
        collectionConstructorList.add(agate);
        assertEquals(2, collectionConstructorList.lastIndexOf(agate));
        assertEquals(1, collectionConstructorList.lastIndexOf(diamond));
        assertEquals(-1, collectionConstructorList.lastIndexOf(lazurite));
    }

    @Test
    public void iteratorTest() {
        Iterator<Gemstone> iterator = collectionConstructorList.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(agate, iterator.next());
        assertEquals(diamond, iterator.next());
        assertFalse(iterator.hasNext());
        iterator.remove();
        assertFalse(collectionConstructorList.contains(diamond));
        assertEquals(1, collectionConstructorList.size());
    }

    @Test
    public void listIteratorTest1() {
        ListIterator<Gemstone> iterator = collectionConstructorList.listIterator();

        assertTrue(iterator.hasNext());
        assertFalse(iterator.hasPrevious());
        assertEquals(agate, iterator.next());
        assertEquals(diamond, iterator.next());
        assertEquals(diamond, iterator.previous());

        iterator.next();
        assertFalse(iterator.hasNext());
        iterator.remove();
        assertFalse(collectionConstructorList.contains(diamond));
        assertEquals(1, collectionConstructorList.size());
        assertEquals(1, iterator.nextIndex());
        assertEquals(0, iterator.previousIndex());
    }

    @Test
    public void listIteratorTest2() {
        collectionConstructorList.listIterator();

        Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {collectionConstructorList.listIterator(-1);});
        assertEquals("Index: -1", exception.getMessage());

        exception = assertThrows(IndexOutOfBoundsException.class, () -> {collectionConstructorList.listIterator(3);});
        assertEquals("Index: 3", exception.getMessage());
    }
}
