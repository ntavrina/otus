package agent;

import java.lang.reflect.Field;
import java.util.*;

public class MemorySizeTester {

    public static void main(String[] args) {

        printSize(new Object());
        printSize(new String());
        printSize("Hello!");
        printSize("");
        printSize(1);
        printSize(1L);
        printSize(1d);
        printSize(1f);

        printSize(new String[0]);
        printSize(new ArrayList<>());
        printSize(new TreeSet<>());

        printSize(new HashMap<>());
        printSize(new TreeMap<>());
        printSize(new Hashtable<>());

        int cycle = 3;
        int max_size = 1000 * 1000 * 5;
        ArrayList<String> al2 = new ArrayList<>();
        Field[] F = ArrayList.class.getDeclaredFields();
        for(int i=0; i<cycle; ++i) {
            for(int j=0; j<max_size; ++j) {
                al2.add(new String());
            }
            long size = 0;
            for(Field f : F) {
                f.setAccessible(true);
                try {
                    size += getSize(f.get(al2));
                } catch (Exception e) {}
            }
            System.out.println("Size of arraylist of "+al2.size()+" elements: " + size + " bytes");
        }

    }

    private static void printSize(Object o) throws IllegalArgumentException {
        if(o == null)
            throw new IllegalArgumentException();
        System.out.println(o.getClass().getSimpleName()+": " + Agent.getSize(o) + " bytes");
    }

    private static long getSize(Object o) {
        if(o == null)
            return 0;
        return Agent.getSize(o);
    }
}
