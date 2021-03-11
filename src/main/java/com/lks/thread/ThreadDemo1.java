package com.lks.thread;

import java.util.concurrent.*;

/**
 * 四种线程之一
 *
 * @author lks
 * @E-mail 1056224715@qq.com.
 * @Since 1.0
 * @Date 2021/3/5 21:52
 */
public class ThreadDemo1 {


    /**
     * 会将创建的线程缓存起来
     */
    public static void newCachedDemo() {
        ExecutorService service = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            service.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println("ThreadDemo1.run" + (finalI + 1));
                }
            });
        }
    }


    /**
     * dan
     */
    public static void newSingleDemo() {
        ExecutorService service = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            service.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("ThreadDemo1.run" + (finalI + 1));
                }
            });
        }
    }

    /**
     * N秒之后执行
     */
    public static void newScheduleDemo() {
        ScheduledExecutorService service = Executors.newScheduledThreadPool(2);
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            ScheduledFuture<?> schedule = service.schedule(new Runnable() {
                @Override
                public void run() {
                    System.out.println("ThreadDemo1.run" + (finalI + 1));
                }
            }, 10, TimeUnit.SECONDS);
            System.out.println(schedule);
        }
    }


    /**
     * submit设置有返回值
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static void newSingleDemo2() throws ExecutionException, InterruptedException {
        ExecutorService service = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 10; i++) {
            Future<?> submit = service.submit(new Callable<String>() {

                @Override
                public String call() throws Exception {
                    return "hello everyone";
                }
            });
            //返回的callback数据
            System.out.println(submit.get());
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        newSingleDemo2();
    }
}
