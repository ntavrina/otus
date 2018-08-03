package agent;

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
        for(int i=0; i<cycle; ++i) {
            for(int j=0; j<max_size; ++j) {
                al2.add(new String());
            }
            System.out.println("Size of array of "+al2.size()+" elements:");
            printSize(al2);
        }

    }

    private static void printSize(Object o) throws NullPointerException {
        if(o == null)
            throw new NullPointerException();
        System.out.println(o.getClass().getSimpleName()+": " + Agent.getSize(o) + " bytes");
    }
}
