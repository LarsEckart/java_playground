package lars.spielplatz.java8;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.regex.Pattern;

public class SplitFileAsStream {

    public static void main(String[] args) throws FileNotFoundException {
        Pattern p = Pattern.compile("\\R");
        final CharSequence splitIt =
                new FileAsCharSequence(
                        new File("/Users/larse/github/log-stats/timing.log"));
        p.splitAsStream(splitIt).forEach(System.out::println);
    }
}
