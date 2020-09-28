package entity;

import conf.ImgCache;
import java.awt.Graphics;

/**
 * 爆炸
 *
 * @author gu.sc
 */
public class Explode extends BaseGameObject {

  private int index;

  public Explode(int x, int y) {
    this.x = x;
    this.y = y;
    gm.getGos().add(this);
  }


  @Override
  public void paint(Graphics g) {
    g.drawImage(ImgCache.explodes[index], this.x, this.y, null);
    if (++index == ImgCache.explodes.length) {
      index = -1;
    }
  }

  @Override
  public boolean showReject() {
    return this.index == -1;
  }

}
