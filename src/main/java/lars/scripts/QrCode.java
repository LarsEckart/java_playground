/*
from https://hollycummins.com/creating-QR-codes/
*/
package lars.scripts;

/// usr/bin/env jbang "$0" "$@" ; exit $?

// DEPS com.google.zxing:core:3.4.0
// DEPS com.google.zxing:javase:3.4.0

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import lars.spielplatz.nio.SanitizedPath;

public class QrCode {

  public static void main(String[] args) throws Exception {
    if (args.length != 3) {
      System.err.println(
          "Wrong number of arguments - should be text to encode, image path, output file"
              + " path");
      System.exit(1);
    } else {
      String text = args[0];
      Path imagePath;
      Path outPath;
      try {
        imagePath = new SanitizedPath(args[1]).value();
        outPath = new SanitizedPath(args[2]).value();
      } catch (SecurityException e) {
        System.err.printf("Invalid path: %s%n", e.getMessage());
        System.exit(1);
        return;
      }
      int width = 640;
      writeQrCode(text, imagePath, outPath, width);
    }
  }

  private static void writeQrCode(String text, Path imagePath, Path outPath, int width)
      throws Exception {

    Map<EncodeHintType, ErrorCorrectionLevel> hints = new HashMap<>();

    // Specify the error correction, to allow the QR code to tolerate errors, such as
    // a great big picture plunked in the middle
    hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);

    // QR codes are square
    int height = width;

    QRCodeWriter qrCodeWriter = new QRCodeWriter();
    BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height, hints);
    // Load QR image
    BufferedImage qrImage =
        MatrixToImageWriter.toBufferedImage(
            bitMatrix, new MatrixToImageConfig(0xFF000000, 0xFFFFFFFF));

    // Initialize combined image
    BufferedImage combined =
        new BufferedImage(qrImage.getHeight(), qrImage.getWidth(), BufferedImage.TYPE_INT_ARGB);
    Graphics2D g = (Graphics2D) combined.getGraphics();

    // Write QR code to new image at position 0/0
    g.drawImage(qrImage, 0, 0, null);
    g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

    addOverlayImage(g, qrImage, imagePath);

    ImageIO.write(combined, "png", outPath.toFile());
    System.out.println("Created QR code at " + outPath);
  }

  private static BufferedImage addOverlayImage(Graphics2D g, BufferedImage qrImage, Path imagePath)
      throws IOException {
    // Load logo image
    BufferedImage overlay = ImageIO.read(imagePath.toFile());

    // Calculate the delta height and width between QR code and the logo
    // Note that we don't do any scaling, so the sizes need to kind of
    // work together without obscuring too much logo
    int deltaHeight = qrImage.getHeight() - overlay.getHeight();
    int deltaWidth = qrImage.getWidth() - overlay.getWidth();

    int woffset = Math.round(deltaWidth / 2);
    int hoffset = Math.round(deltaHeight / 2);

    // Write the logo into the combined image at position (deltaWidth / 2) and
    // (deltaHeight / 2), so that it's centered
    g.drawImage(overlay, woffset, hoffset, null);
    return overlay;
  }
}
