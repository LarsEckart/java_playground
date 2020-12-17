package lars.executearound;

import java.util.function.Consumer;
import java.util.function.Function;

class Resource {

  private Resource() {
    System.out.println("creating...");
  }

  public void fn1() {
    System.out.println("fn1 called...");
  }

  public int fn2() {
    System.out.println("fn2 called...");
    return 42;
  }

  private void close() {
    System.out.println("cleaning up....");
  }

  public static void use(Consumer<Resource> useResource) {
    Resource resource = new Resource();
    try {
      useResource.accept(resource);
    } finally {
      resource.close();
    }
  }

  public static int get(Function<Resource, Integer> useResource) {
    Resource resource = new Resource();
    try {
      return useResource.apply(resource);
    } finally {
      resource.close();
    }
  }
}
