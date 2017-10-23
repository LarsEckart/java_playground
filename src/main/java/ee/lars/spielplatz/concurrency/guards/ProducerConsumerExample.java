package ee.lars.spielplatz.concurrency.guards;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

public class ProducerConsumerExample {

    public static void main(String[] args) {
        //Drop drop = new Drop();
        BlockingQueue<String> drop =
                new SynchronousQueue<String>();
        (new Thread(new Producer(drop))).start();
        (new Thread(new Consumer(drop))).start();
    }
}
