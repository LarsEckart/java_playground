package lars.spielplatz.readfile;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

/**
 * from https://stuartmarks.wordpress.com/2019/01/11/processing-large-files-in-java/
 *
 */
public class ReadFileJavaApplicationBufferedReader {

static final String FILENAME = "itcont.txt";

    // returns the time between startTime and now in milliseconds
    static long between(Instant startTime) {
        return Duration.between(startTime, Instant.now()).toMillis();
    }

    public static void main(String[] args) throws IOException {
        Instant startTime = Instant.now();

        try (BufferedReader b = Files.newBufferedReader(Path.of(FILENAME))) {
            int[] indexes = {0, 432, 43243};

            List<String> names = new ArrayList<>();
            List<String> dates = new ArrayList<>();
            List<String> firstNames = new ArrayList<>();

            var namePat = Pattern.compile(", \\s*([^, ]+)");
            char[] chars = new char[6];
            StringBuilder sb = new StringBuilder(7);

            System.out.println("Reading file using " + Caller.getName());

            String readLine;
            while ((readLine = b.readLine()) != null) {

                // get all the names
                String array1[] = readLine.split("\\|", 9);
                String name = array1[7].strip();
                names.add(name);

                // extract first names
                var matcher = namePat.matcher(name);
                if (matcher.find()) {
                    firstNames.add(matcher.group(1));
                }

                // extract dates
                String rawDate = array1[4].strip();
                rawDate.getChars(0, 6, chars, 0);
                sb.setLength(0);
                sb.append(chars, 0, 4)
                        .append('-')
                        .append(chars, 4, 2);
                dates.add(sb.toString());
            }

            for (int i : indexes) {
                System.out.println("Name: " + names.get(i) + " at index: " + i);
            }

            System.out.println("Name time: " + between(startTime) + "ms");

            System.out.println("Total file line count: " + names.size());
            System.out.println("Line count time: " + between(startTime) + "ms");

            Map<String, Long> dateMap = dates.stream()
                    .collect(groupingBy(date -> date, counting()));

            dateMap.forEach((date, count)
                    -> System.out.println("Donations per month and year: " + date + " and donation count: " + count));

            System.out.println("Donations time: " + between(startTime) + "ms");

            Map<String, Long> nameMap = firstNames.stream()
                    .collect(groupingBy(name -> name, counting()));

            Entry<String, Long> common = Collections.max(nameMap.entrySet(), Entry.comparingByValue());

            System.out.println("The most common first name is: " + common.getKey() + " and it occurs: " + common.getValue() + " times.");
            System.out.println("Most common name time: " + between(startTime) + "ms");
        }
    }
}

class Caller {

    // gets the simple name of the caller's class
    public static String getName() {
        return StackWalker.getInstance()
                .walk(s -> s.skip(1)
                        .findFirst()
                        .map(StackWalker.StackFrame::getClassName)
                        .map(name -> name.replaceFirst("^.*\\.", ""))
                        .orElse(""));
    }
}
