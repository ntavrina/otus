package otus.hw4;

import java.util.ArrayList;

public class Main {

    public static void main (String... args) {
        long _time = System.nanoTime();
        MyGarbageCollectorStats mgc = new MyGarbageCollectorStats(_time);

        try {
            int cycle = 1000;
            int max_size = 5 * 100 * 100;
            ArrayList bigList = new ArrayList();
            for(int j=0; j<cycle; ++j) {
                System.out.println("Cycle: "+j + "...");
                for (int i = 0; i < max_size; ++i) {
                    bigList.add(new char[10000]);
                }

                for (int i = bigList.size() / 2; i < bigList.size(); ++i) {
                    bigList.remove(i);
                }
                mgc.getGCStats();
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.toString());
        } finally {
            mgc.getGCStats();
            System.out.println("----------------------------------");
            System.out.println("Total time: " + (System.nanoTime() - _time)/1000/1000/1000 + "s");
            //total
            for(String key : mgc.totGCCountByName.keySet()) {
                System.out.println("Total count GCs " + key + ": " + mgc.totGCCountByName.get(key));
                System.out.println("Total time GCs " + key + ": " + mgc.totGCDurationByName.get(key)+"ms");
            }
        }

    }
}
