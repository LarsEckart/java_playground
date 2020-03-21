package lars.spielplatz.javaspecialists.issue230;

import java.lang.management.ManagementFactory;
import javax.management.ObjectName;

public class Memory {

  public static long threadAllocatedBytes() {
    try {
      return (Long)
          ManagementFactory.getPlatformMBeanServer()
              .invoke(
                  new ObjectName(ManagementFactory.THREAD_MXBEAN_NAME),
                  "getThreadAllocatedBytes",
                  new Object[] {Thread.currentThread().getId()},
                  new String[] {long.class.getName()});
    } catch (Exception e) {
      throw new IllegalArgumentException(e);
    }
  }
}
