package ee.lars.spielplatz.javaspecialists.issue56;

public class UsingInterruptToShutdownThread extends Thread {

    @Override
    public void run() {
        while (true) {
            System.out.print(".");
            System.out.flush();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt(); // very important
                break;
            }
        }
        System.out.println("Shutting down thread");
    }

    public static void main(String[] args)
            throws InterruptedException
    {
        Thread t = new UsingInterruptToShutdownThread();
        t.start();
        Thread.sleep(5000);
        t.interrupt();
    }
}