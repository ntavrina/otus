package otus.hw4;

import java.lang.management.*;
import java.util.HashMap;

public class MyGarbageCollectorStats {

    //данные за последний вызов статистики
    private static HashMap<String, Long> lastGCCountByName = new HashMap(); //кол-во сборок по типам
    private static HashMap<String, Long> lastGCDurationByName = new HashMap(); //время сборок по типам
    //данные за весь период работы
    public static HashMap<String, Long> totGCCountByName = new HashMap();
    public static HashMap<String, Long> totGCDurationByName = new HashMap();
    //данные за ~1 минуту работы
    public static HashMap<String, Long> avgGCCountByName = new HashMap();
    public static HashMap<String, Long> avgGCDurationByName = new HashMap();
    //начало отсчета
    private long startTime = 0;



    public MyGarbageCollectorStats(long startTime) {
        this.startTime = startTime;
    }


    public void getGCStats() {
        try {
            HashMap<String, Long> currentGCCountByName = new HashMap();
            HashMap<String, Long> currentGCDurationByName = new HashMap();


            for (GarbageCollectorMXBean gc : ManagementFactory.getGarbageCollectorMXBeans()) {
                String gcName = gc.getName();
                gcName = gcName.toLowerCase().indexOf("marksweep") > -1 || gcName.toLowerCase().indexOf("old") > -1
                        ? "OLD" : "YOUNG";

                long count = gc.getCollectionCount();
                if (count >= 0) {
                    currentGCCountByName.put(gcName, (currentGCCountByName.containsKey(gcName) ?
                            currentGCCountByName.get(gcName).longValue() : 0L) + count);
                    totGCCountByName.put(gcName, (totGCCountByName.containsKey(gcName) ?
                            totGCCountByName.get(gcName).longValue() : 0L) + count);
                    avgGCCountByName.put(gcName, (avgGCCountByName.containsKey(gcName) ?
                            avgGCCountByName.get(gcName).longValue() : 0L) + count);
                }

                long time = gc.getCollectionTime();
                if (time >= 0) {
                    currentGCDurationByName.put(gcName, (currentGCDurationByName.containsKey(gcName) ?
                            currentGCDurationByName.get(gcName).longValue() : 0L) + time);
                    totGCDurationByName.put(gcName, (totGCDurationByName.containsKey(gcName) ?
                            totGCDurationByName.get(gcName).longValue() : 0L) + time);
                    avgGCDurationByName.put(gcName, (avgGCDurationByName.containsKey(gcName) ?
                            avgGCDurationByName.get(gcName).longValue() : 0L) + time);
                }
            }

            for (String key : currentGCCountByName.keySet()) {
                long lastCount = lastGCCountByName.containsKey(key) ? lastGCCountByName.get(key).longValue() : 0L;
                long lastTime = lastGCDurationByName.containsKey(key) ? lastGCDurationByName.get(key).longValue() : 0L;
                if (currentGCCountByName.get(key) - lastCount > 0) {
                    System.out.println("Number of GCs " + key + ": " + (currentGCCountByName.get(key) - lastCount) + ", " +
                            "" + (currentGCDurationByName.get(key) - lastTime) + "ms");
                }
            }

            for (String key : currentGCCountByName.keySet())
                lastGCCountByName.put(key, currentGCCountByName.get(key).longValue());
            for (String key : currentGCDurationByName.keySet())
                lastGCDurationByName.put(key, currentGCDurationByName.get(key).longValue());

            //посчитаем в среднем за 1 минуту
            long curTime = System.nanoTime();
            if ((curTime - startTime) / 1000 / 1000 / 1000 >= 60) {
                for (String key : avgGCCountByName.keySet()) {
                    long curAvgCount = avgGCCountByName.containsKey(key) ? avgGCCountByName.get(key).longValue() : 0L;
                    long curAvgTime = avgGCDurationByName.containsKey(key) ? avgGCDurationByName.get(key).longValue() : 0L;

                    System.out.println("Average of GCs " + key + ": " + (curAvgCount) + " per minute, " +
                            "" + curAvgTime / curAvgCount + "ms per collect");

                }
                for (String key : avgGCCountByName.keySet()) {
                    avgGCCountByName.put(key, 0L);
                    avgGCDurationByName.put(key, 0L);
                }
                startTime = curTime;
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
