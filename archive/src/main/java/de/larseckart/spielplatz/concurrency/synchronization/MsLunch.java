package de.larseckart.spielplatz.concurrency.synchronization;

public class MsLunch {

    private long c1 = 0;
    private long c2 = 0;
    private final Object lock1 = new Object();
    private final Object lock2 = new Object();

    public void inc1() {
        // synchronized statement (instead of method)
        synchronized(lock1) {
            c1++;
        }
    }

    public void inc2() {
        synchronized(lock2) {
            c2++;
        }
    }
}