package otus.hw3;

import java.util.Collections;

public class Main {

    public static void main(String... args) {


        MyArrayList<String> mal = new MyArrayList<>();
        mal.add("2");
        mal.add("1");

        Collections.addAll(mal,"4", "3", "8", "0");
        System.out.println("addAll: "+mal.toString());

        Collections.sort(mal, (a, b) -> a.compareToIgnoreCase(b));
        System.out.println("sort before copy: "+mal.toString());

        MyArrayList<String> mal2 = new MyArrayList<>();
        mal2.add("44");

        Collections.copy(mal,mal2);
        System.out.println("copy: " + mal.toString());

        Collections.sort(mal, (a, b) -> a.compareToIgnoreCase(b));
        System.out.println("sort after copy: "+mal.toString());

    }
}
