package com.liugs.asynchronousdemo;

import android.util.Log;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

/**
 * 主任务调用 await 阻塞
 * 等待的其他任务，调用countDown，不会阻塞
 * 完毕，主任务继续执行
 */
public class SemaphoreTest {
public static final String TAG = "CountDownLatchTest";
    private static Semaphore semaphore;

    public static void test(){
        startOne();
        startTwo();
        startTwo();
        startTwo();
    }

    private static void startOne(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG,"主任务等待其他任务");
                semaphore = new Semaphore(2);
            }
        }).start();
    }

    private static void startTwo(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    long l = System.currentTimeMillis();
                    Log.i(TAG,"其他任务开始执行 " + l);
                    semaphore.acquire();
                    Log.i(TAG,"其他任务执行中 " + l);
                    Thread.sleep(20000);
                    semaphore.release();
                    Log.i(TAG,"其他任务执行完毕" + l);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
