package ee.lars.spielplatz.defensive;

import java.io.File;

public class Main {

    public static void main(String[] args) {
        Report report = new NoNullReport(
            new NoWriteOverReport(
                new DefaultReport()
            )
        );
        report.export(new File("/tmp/whatever.txt"));
    }
}
