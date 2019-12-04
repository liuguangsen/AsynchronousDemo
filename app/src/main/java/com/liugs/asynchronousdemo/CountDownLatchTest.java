package com.liugs.asynchronousdemo;

import android.util.Log;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

/**
 * 主任务调用 await 阻塞
 * 等待的其他任务，调用countDown，不会阻塞
 * 完毕，主任务继续执行
 */
public class CountDownLatchTest {
public static final String TAG = "CountDownLatchTest";
    private static CountDownLatch downLatch;
    public static void test(){
        startOne();
        startTwo();
        startTwo();
    }

    private static void startOne(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG,"主任务等待其他任务");
                downLatch = new CountDownLatch(2);
                try {
                    downLatch.await();
                    Log.i(TAG,"主任务开始执行");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private static void startTwo(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(20000);
                    downLatch.countDown();
                    Log.i(TAG,"其他执行完毕" + System.currentTimeMillis());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
