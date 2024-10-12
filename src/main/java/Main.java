import gemstones.Gemstone;
import gemstones.imp.Agate;
import gemstones.imp.Diamond;
import gemstones.imp.Emerald;
import gemstones.imp.Opal;

/**
 *  C2 = 18 % 2 = 0
 *  C3 = 18 % 3 = 0
 */

public class Main {
    public static void main(String[] args) {
        Agate agate = new Agate(1, 1, 1);
        Diamond diamond = new Diamond(1, 1, 1);
        Emerald emerald = new Emerald(1, 1, 1);
        Opal opal = new Opal(1, 1, 1);

        MyList list = new MyList(agate);
        list.add(diamond);
        list.add(emerald);
        System.out.println("Initial list:");
        displayMyList(list);

        System.out.println("list.size(): " + list.size());
        System.out.println("list.contains(diamond): " + list.contains(diamond));
        System.out.println("list.contains(opal): " + list.contains(opal));
        System.out.println("list.isEmpty(): " + list.isEmpty());
        System.out.println("list.indexOf(agate): " + list.indexOf(agate));
        System.out.println("list.indexOf(opal): " + list.indexOf(opal));
        System.out.println("list.remove(opal): " + list.remove(opal));
        System.out.println("After list.remove(opal):");
        displayMyList(list);
        System.out.println("list.remove(agate): " + list.remove(agate));
        System.out.println("After list.remove(agate):");
        displayMyList(list);
        System.out.println("list.remove(0): " + list.remove(0));
        System.out.println("After list.remove(0):");
        displayMyList(list);
        System.out.println("list.get(0): " + list.get(0));
        System.out.println("list.set(0, opal): " + list.set(0, opal));
        System.out.println("After list.set(0, opal):");
        displayMyList(list);
        list.clear();
        System.out.println("After list.clear():");
        displayMyList(list);
    }

    public static void displayMyList(MyList list) {
        for(Object g : list.toArray())
            System.out.println(g);
        System.out.println("\n===================================\n");
    }
}
