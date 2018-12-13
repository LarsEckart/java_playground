package lars.refactoring;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.nio.file.Paths;
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

    static long run(String[] args) throws java.io.IOException {
        if (args.length == 0) {
            throw new RuntimeException("must supply a filename");
        }
        CommandLine commandLine = new CommandLine();
        commandLine.onlyCountReady = Stream.of(args).anyMatch(arg -> "-r".equals(arg));
        String filename = args[args.length - 1];
        return countOrders(commandLine, args, filename);
    }

    private static long countOrders(CommandLine commandLine, String[] args, String filename) throws java.io.IOException {
        File input = Paths.get(filename).toFile();
        ObjectMapper mapper = new ObjectMapper();
        Order[] orders = mapper.readValue(input, Order[].class);
        if (commandLine.onlyCountReady) {
            return Stream.of(orders).filter(o -> "ready".equals(o.status)).count();
        } else {
            return orders.length;
        }
    }

    private static class CommandLine {

        boolean onlyCountReady;
    }

    private static class Order {

        public String status;
    }
}
