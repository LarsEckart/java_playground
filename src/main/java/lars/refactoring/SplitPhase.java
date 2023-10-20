package lars.refactoring;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

public class SplitPhase {

  public static void main(String[] args) {
    try {
      System.out.println(run(args));
    } catch (Exception e) {
      System.err.println(e);
      System.exit(1);
    }
  }

  static long run(String[] args) throws IOException {
    CommandLine commandLine = new CommandLine(args);
    return countOrders(commandLine);
  }

  private static long countOrders(CommandLine commandLine) throws IOException {
    File input = Paths.get(commandLine.filename()).toFile();
    ObjectMapper mapper = new ObjectMapper();
    Order[] orders = mapper.readValue(input, Order[].class);
    if (commandLine.onlyCountReady()) {
      return Stream.of(orders).filter(o -> "ready".equals(o.status)).count();
    } else {
      return orders.length;
    }
  }

  private static class Order {

    public String status;
  }

  static class CommandLine {

    String[] args;

    CommandLine(String[] args) {
      if (args.length == 0) {
        throw new RuntimeException("must supply a filename");
      }
      this.args = args;
    }

    boolean onlyCountReady() {
      return Arrays.asList(args).contains("-r");
    }

    String filename() {
      return args[args.length - 1];
    }
  }
}
