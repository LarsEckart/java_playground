package ee.lars.spielplatz.javaspecialists.issue56;

import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class BlockedOnIO extends Thread {

    private final InputStream in;

    public BlockedOnIO(InputStream in) {
        this.in = in;
    }

    public void interrupt() {
        super.interrupt();
        try {
            in.close();
        } catch (IOException e) {
        } // quietly close
    }

    @Override
    public void run() {
        try {
            System.out.println("Reading from input stream");
            in.read();
            System.out.println("Finished reading");
        } catch (InterruptedIOException e) {
            Thread.currentThread().interrupt();
            System.out.println("Interrupted via InterruptedIOException");
        } catch (IOException e) {
            if (!isInterrupted()) {
                e.printStackTrace();
            } else {
                System.out.println("Interrupted");
            }
        }
        System.out.println("Shutting down thread");
    }

    public static void main(String[] args)
            throws IOException, InterruptedException
    {
        ServerSocket ss = new ServerSocket(4444);
        Socket socket = new Socket("localhost", 4444);
        System.out.println("Made socket, now reading from socket");
        Thread t = new BlockedOnIO(socket.getInputStream());
        t.start();
        Thread.sleep(5000);
        t.interrupt();

        System.out.println("\n now using pipes");
        PipedInputStream in = new PipedInputStream(new PipedOutputStream());
        Thread t2 = new BlockedOnIO(in);
        t2.start();
        Thread.sleep(5000);
        t2.interrupt();
    }
}
