package com.liugs.asynchronousdemo;

import android.util.Log;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

/**
 * 主任务调用 await 阻塞
 * 等待的其他任务，调用countDown，不会阻塞
 * 完毕，主任务继续执行
 */
public class CyclicBarrierTest {
public static final String TAG = "CountDownLatchTest";
    private static CyclicBarrier barrier;

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
                barrier = new CyclicBarrier(2, new Runnable() {
                    @Override
                    public void run() {
                        Log.i(TAG,"下一个任务开始执行");
                    }
                });
            }
        }).start();
    }

    private static void startTwo(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    long l = System.currentTimeMillis();
                    Log.i(TAG,"其他任务执行中 " + l);
                    Thread.sleep(20000);
                    Log.i(TAG,"其他任务执行暂停 " + l);
                    barrier.await();
                    Log.i(TAG,"其他任务继续执行" + l);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
