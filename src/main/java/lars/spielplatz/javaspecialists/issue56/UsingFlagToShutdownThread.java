package lars.spielplatz.javaspecialists.issue56;

public class UsingFlagToShutdownThread extends Thread {

    private volatile boolean running = true;

    @Override
    public void run() {
        while (this.running) {
            System.out.print(".");
            System.out.flush();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
            }
        }
        System.out.println("Shutting down thread");
    }

    public void shutdown() {
        this.running = false;
    }

    public static void main(String[] args) throws InterruptedException {
        UsingFlagToShutdownThread t = new UsingFlagToShutdownThread();
        t.start();
        Thread.sleep(5000);
        t.shutdown();
    }
}
