package lars.refactoring;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
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

    static long run(String[] args) throws java.io.IOException {
        return countOrders(parseCommandLine(args));
    }

    private static CommandLine parseCommandLine(String[] args) {
        if (args.length == 0) {
            throw new RuntimeException("must supply a filename");
        }
        CommandLine result = new CommandLine();
        result.onlyCountReady = Arrays.asList(args).contains("-r");
        result.filename = args[args.length - 1];
        return result;
    }

    private static long countOrders(CommandLine commandLine) throws java.io.IOException {
        File input = Paths.get(commandLine.filename).toFile();
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
        String filename;
    }

    private static class Order {

        public String status;
    }
}
