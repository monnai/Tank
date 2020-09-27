package entity;

import conf.ImgCache;
import java.awt.Graphics;

/**
 * @author gu.sc
 */
public class Explode extends BaseGameObject {

  public static int WIDTH = ImgCache.explodes[0].getWidth();
  public static int HEIGHT = ImgCache.explodes[0].getHeight();
  public Explode(int x, int y) {
    this.x = x;
    this.y = y;
  }

  private int index;

  public int getIndex() {
    return this.index;
  }


  @Override
  public void paint(Graphics g) {
    g.drawImage(ImgCache.explodes[index], this.x, this.y, null);
    if (++index == ImgCache.explodes.length) {
      index = -1;
    }
  }

}
