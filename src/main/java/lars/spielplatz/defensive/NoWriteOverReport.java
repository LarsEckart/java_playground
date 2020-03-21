package lars.spielplatz.defensive;

import java.io.File;

public class NoWriteOverReport implements Report {

  private final Report origin;

  public NoWriteOverReport(Report rep) {
    this.origin = rep;
  }

  @Override
  public void export(File file) {
    if (file.exists()) {
      throw new IllegalArgumentException("File already exists.");
    }
    this.origin.export(file);
  }
}
