package util;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

/**
 * @author Administrator
 */
public class ImageUtil {
  public static BufferedImage rotateImage(final BufferedImage bufferedimage,
      final int degree) {
    int w = bufferedimage.getWidth();
    int h = bufferedimage.getHeight();
    int type = bufferedimage.getColorModel().getTransparency();
    BufferedImage img;
    Graphics2D graphics2d;
    (graphics2d = (img = new BufferedImage(w, h, type))
        .createGraphics()).setRenderingHint(
        RenderingHints.KEY_INTERPOLATION,
        RenderingHints.VALUE_INTERPOLATION_BILINEAR);
    graphics2d.rotate(Math.toRadians(degree), w >> 1, h >> 1);
    graphics2d.drawImage(bufferedimage, 0, 0, null);
    graphics2d.dispose();
    return img;
  }

  public static void main(String[] args) {
    double v = Math.toRadians(90);
    System.out.println(v);
  }
}
