package otus.hw14;

import java.util.ArrayList;
import java.util.Arrays;

public class SortArray {

    public static void main(String[] args) throws InterruptedException {

        int max_size = 1000000;
        int[] arr = new int[max_size];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int)(Math.random()*max_size);
        }


        //------------------------
        //разбиваем массив на 4 части для многопоточной сортировки
        int middle = arr.length/2;
        int[] left = new int[middle];
        int[] right = new int[arr.length - middle];
        System.arraycopy(arr,0, left, 0, middle);
        System.arraycopy(arr, middle, right, 0, arr.length - middle);

        int middleLeft = left.length/2;
        int[] leftLeft = new int[middleLeft];
        int[] leftRight = new int[left.length - middleLeft];
        System.arraycopy(left,0, leftLeft, 0, middleLeft);
        System.arraycopy(left, middleLeft, leftRight, 0, left.length - middleLeft);

        int middleRight = right.length/2;
        int[] rightLeft = new int[middleRight];
        int[] rightRight = new int[right.length - middleRight];
        System.arraycopy(right,0, rightLeft, 0, middleRight);
        System.arraycopy(right, middleRight, rightRight, 0, right.length - middleRight);
        //------------------------

        //простая сортировка
        long _time1 = System.nanoTime();
        Arrays.sort(arr);
        System.out.println("simple sort: "+(System.nanoTime() - _time1)/1000+"mks");

        //------------------------
        //многопоточная сортировка
        ArrayList<Thread> alThreads = new ArrayList();
        alThreads.add(new Thread(new SortThread(leftLeft)));
        alThreads.add(new Thread(new SortThread(leftRight)));
        alThreads.add(new Thread(new SortThread(rightLeft)));
        alThreads.add(new Thread(new SortThread(rightRight)));

        long _time2 = System.nanoTime();
        for (Thread thread : alThreads)
            thread.start();

        for (Thread thread : alThreads)
            thread.join();

        int[] result1 = mergeArr(leftLeft,leftRight);
        int[] result2 = mergeArr(rightLeft, rightRight);
        int[] result = mergeArr(result1, result2);

        //for(int i=0; i<result.length; ++i)
          //  System.out.println("after sort: "+result[i]);

        System.out.println("multi thread sort: "+(System.nanoTime() - _time2)/1000+"mks");
    }

    static int[] mergeArr(int[] partLeft, int[] partRight) {

        int[] mergedArr = new int[partLeft.length + partRight.length];

        int c = 0, cLeft = 0, cRight = 0;
        while (cLeft < partLeft.length && cRight < partRight.length) {
            if (partLeft[cLeft] <= partRight[cRight]) {
                mergedArr[c] = partLeft[cLeft];
                cLeft++;
            } else {
                mergedArr[c] = partRight[cRight];
                cRight++;
            }
            ++c;
        }

        if(cLeft < partLeft.length)
            System.arraycopy( partLeft, cLeft, mergedArr, c, mergedArr.length - c);
        if(cRight < partRight.length)
            System.arraycopy( partRight, cRight, mergedArr, c, mergedArr.length - c );

        return mergedArr;
    }

    static class SortThread extends Thread {

        public int[] unsorted;

        public SortThread(int[] arr) {
            this.unsorted = arr;
        }

        public void run() {
            Arrays.sort(this.unsorted);
        }
    }
}
