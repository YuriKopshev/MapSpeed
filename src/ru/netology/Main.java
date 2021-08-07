package ru.netology;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class Main {
    private static ConcurrentHashMap<Integer, Integer> map = new ConcurrentHashMap<>();
    private static Map<Integer, Integer> syncMap = Collections.synchronizedMap(new HashMap<>());
    private static final long PAUSE = 5000;

    public static int[] arrayGenerate() {
        int[] arr = new int[10000];
        Random random = new Random();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = random.nextInt(10);
        }
        return arr;
    }

    public static void writeSynchronizedMap(int[] array) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < array.length; i++) {
                    syncMap.put(i, array[i]);
                }
            }
        }, "1 поток").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < array.length; i++) {
                    syncMap.put(i, array[i]);
                }
            }
        }, "2 поток").start();

    }

    public static void writeConcurrentMap(int[] array) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < array.length; i++) {
                    map.put(i, array[i]);
                }
            }
        }, "1 поток").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < array.length; i++) {
                    map.put(i, array[i]);
                }
            }
        }, "2 поток").start();

    }

    public static void readConcurrentHashMap() {
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            System.out.println("Ключ: " + entry.getKey() + "значение: " + entry.getValue());
        }
    }

    public static void readSynchronizedHashMap() {
        for (Map.Entry<Integer, Integer> entry : syncMap.entrySet()) {
            System.out.println("Ключ: " + entry.getKey() + "значение: " + entry.getValue());
        }
    }

    public static void main(String[] args) {
        int[] array = Main.arrayGenerate();
        long time1 = System.currentTimeMillis();
        Main.writeConcurrentMap(array);
        long endTime1 = System.currentTimeMillis();
        System.out.println("Время записи для ConcurrentHashMap " + (endTime1 - time1));

        long time2 = System.currentTimeMillis();
        Main.writeSynchronizedMap(array);
        long endTime2 = System.currentTimeMillis();
        System.out.println("Время записи для SynchronizedMap " + (endTime2 - time2));

        System.out.println("Вычисления скорости чтения: ");
        long time3 = System.currentTimeMillis();
        new Thread(null, Main::readConcurrentHashMap, "Поток для чтения 1").start();
        new Thread(null, Main::readConcurrentHashMap, "Поток для чтения 2").start();
        try {
            Thread.sleep(PAUSE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long endTime3 = System.currentTimeMillis();
        System.out.println("Время чтения для ConcurrentHashMap " + (endTime3 - time3 - PAUSE));

        long time4 = System.currentTimeMillis();
        new Thread(null, Main::readSynchronizedHashMap, "Поток для чтения 3").start();
        new Thread(null, Main::readSynchronizedHashMap, "Поток для чтения 4").start();
        try {
            Thread.sleep(PAUSE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long endTime4 = System.currentTimeMillis();
        System.out.println("Время чтения для SynchronizedMap " + (endTime4 - time4 - PAUSE));

    }
}
