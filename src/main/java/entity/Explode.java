package entity;

import java.awt.Graphics;
import t1.TankFrame;

/**
 * @author gu.sc
 */
public class Explode {

  public static final int WIDTH = ImgCache.explodes[0].getWidth();
  public static final int HEIGHT = ImgCache.explodes[0].getHeight();
  private int index;
  private int x;
  private int y;

  public Explode(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public int getIndex() {
    return this.index;
  }
  public void paint(Graphics g) {
    g.drawImage(ImgCache.explodes[index], this.x, this.y, null);
    if (++index == ImgCache.explodes.length) {
      index = -1;
    }
  }

}
