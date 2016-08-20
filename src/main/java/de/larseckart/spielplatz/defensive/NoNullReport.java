package de.larseckart.spielplatz.defensive;

import java.io.File;

public class NoNullReport implements Report {

    private final Report report;

    public NoNullReport(Report report) {
        this.report = report;
    }

    @Override
    public void export(File file) {
        if (file == null) {
            throw new IllegalArgumentException(
                "File is NULL; can't export."
            );
        }
        report.export(file);
    }
}
