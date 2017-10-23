package ee.lars.spielplatz.defensive;

import java.io.File;

public interface Report {
    void export(File file);
}

/*
previous devensive implementation

public class Report {

    public void export(File file) {
        if (file == null) {
            throw new IllegalArgumentException(
                "File is NULL; can't export."
            );
        }
        if (file.exists()) {
            throw new IllegalArgumentException(
                "File already exists."
            );
        }
        // Export the report to the file
    }
}
 */