package lars.spielplatz.concurrency;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * The AutoLock lets us combine explicit locks with AutoCloseable. We only have to call the static
 * lock() method, which creates an instance of AutoLock and locks the explicit lock. In the close()
 * method, which is called by the try-with-resource construct, the explicit lock is unlocked again.
 *
 * <p>We can use it like this:
 *
 * <pre>
 *     Lock lock = new ReentrantLock();
 *     try (AutoLock al = AutoLock.lock(lock)) {
 *         // do something
 *     }
 *     // the lock is automatically unlocked here
 * </pre>
 *
 * <p>We can also lock interruptibly:
 *
 * <pre>
 *     Lock lock = new ReentrantLock();
 *     try (AutoLock al = AutoLock.lockInterruptibly(lock)) {
 *         // do something
 *     }
 *     // the lock is automatically unlocked here
 * </pre>
 *
 * <p>This construct is also compatible with the ReadWriteLock. We can lock on a read lock with the
 * read() method and on a write lock with the write() method. In addition, the AutoLock guards
 * against accidental upgrading from a read lock to a write lock. If that is attempted, instead of
 * suspending the thread, we throw an IllegalMonitorStateException.
 *
 * <p>Criticisms: It was suggested that creating objects at a time-critical place such as locking
 * would be a bad idea. However, in our experiments, we observed that when we ran our code with the
 * Server HotSpot with Escape Analysis enabled, that the objects were not created on the heap. Thus
 * the cost of object creation was negligible, if not completely eliminated.
 *
 * <p>From http://www.javaspecialists.eu/archive/Issue190.html
 */
public final class AutoLock implements AutoCloseable {

  public static AutoLock lock(Lock lock) {
    return new AutoLock(lock);
  }

  public static AutoLock lockInterruptibly(Lock lock) throws InterruptedException {
    return new AutoLock(lock, true);
  }

  public static AutoLock read(ReadWriteLock lock) {
    return new AutoLock(lock.readLock());
  }

  public static AutoLock write(ReadWriteLock lock) {
    if (lock instanceof ReentrantReadWriteLock rwlock) {
      if (rwlock.getReadHoldCount() > 0 && !rwlock.isWriteLockedByCurrentThread()) {
        throw new IllegalMonitorStateException(
            "ReentrantReadWriteLocks cannot be upgraded from " + "read lock to write lock");
      }
    }
    return new AutoLock(lock.writeLock());
  }

  private final Lock lock;

  public void close() {
    lock.unlock();
  }

  private AutoLock(Lock lock) {
    this.lock = lock;
    lock.lock();
  }

  private AutoLock(Lock lock, boolean allowInterrupts) throws InterruptedException {
    this.lock = lock;
    lock.lockInterruptibly();
  }
}
