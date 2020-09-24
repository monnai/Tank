package entity;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import util.ImageUtil;

/**
 * @author gu.sc
 */
public class ImgCache {

  /**
   * 好坦克图片
   */
  public static BufferedImage lTank, uTank, rTank, dTank;
  /**
   * 坏坦克图片
   */
  public static BufferedImage bdlTank, bduTank, bdrTank, bddTank;
  /**
   * 子弹图片
   */
  static BufferedImage lBullet, uBullet, rBullet, dBullet;
  /**
   * 爆炸图片
   */
  static BufferedImage[] explodes = new BufferedImage[16];

  static {
    try {
      //好坦克初始化
      uTank = ImageIO.read(ImgCache.class.getClassLoader().getResourceAsStream("images/GoodTank1.png"));
      lTank = ImageUtil.rotateImage(uTank, -90);
      rTank = ImageUtil.rotateImage(uTank, 90);
      dTank = ImageUtil.rotateImage(uTank, 180);
      //坏坦克初始化
      bduTank = ImageIO.read(ImgCache.class.getClassLoader().getResourceAsStream("images/BadTank1.png"));
      bdlTank = ImageUtil.rotateImage(bduTank, -90);
      bdrTank = ImageUtil.rotateImage(bduTank, 90);
      bddTank = ImageUtil.rotateImage(bduTank, 180);
      //子弹初始化
      uBullet = ImageIO.read(ImgCache.class.getClassLoader().getResourceAsStream("images/bulletU.png"));
      lBullet = ImageUtil.rotateImage(uBullet, -90);
      rBullet = ImageUtil.rotateImage(uBullet, 90);
      dBullet = ImageUtil.rotateImage(uBullet, 180);
      //爆炸图片初始化
      for (int i = 0; i < explodes.length; i++) {
        explodes[i] = ImageIO
            .read(ImgCache.class.getClassLoader()
                .getResourceAsStream("images/e" + (i + 1) + ".gif"));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
