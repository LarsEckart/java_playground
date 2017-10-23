package ee.lars;

import java.util.stream.IntStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        IntStream.range(1, 10)
                .mapToDouble(i -> Math.pow(Double.valueOf(i), 3.0))
                .forEach(d -> System.out.println(d));

        logger.error("hello error");
        logger.warn("hello warn");
        logger.info("hello info");
        logger.debug("hello debug");
        logger.trace("hello trace");
    }
}
