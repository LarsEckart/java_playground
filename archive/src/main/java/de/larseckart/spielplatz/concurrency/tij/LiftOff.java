package de.larseckart.spielplatz.concurrency.tij;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class LiftOff implements Runnable{

    protected int countDown = 10;
    private static int taskCount = 0;
    private final int id = taskCount++;

    public LiftOff() {

    }

    public LiftOff(int countDown) {
        this.countDown = countDown;
    }

    public String status() {
        return "#" + id + "(" + (countDown > 0 ? countDown : "Liftoff!") + ")";
    }

    @Override
    public void run() {
        while (countDown-- > 0) {
            System.out.println(status());
            //Thread.yield();
            try {
                TimeUnit.MILLISECONDS.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        /*
        LiftOff launch = new LiftOff();
        launch.run();

        Thread t = new Thread(new LiftOff(5));
        t.start();
        System.out.println("waiting for launch");

        for (int i = 0; i < 5; i++) {
            new Thread(new LiftOff()).start();
        }
        */
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < 5; i++) {
            exec.execute(new LiftOff());
        }
        exec.shutdown();
    }
}
