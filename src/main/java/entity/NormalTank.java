package entity;

import entity.abst.AbstractTank;
import enums.Direct;
import enums.Group;
import java.awt.Graphics;
import t1.TankFrame;

/**
 * @author gu.sc
 */
public class NormalTank extends AbstractTank {

  public NormalTank(int x, int y, Direct direct, TankFrame tf, Group group) {
    this.x = x;
    this.y = y;
    this.direct = direct;
    this.tf = tf;
    this.group = group;
  }

  @Override
  public void paint(Graphics g) {
    //根据方向渲染坦克
    if (!this.living) {
      return;
    }
    if (this.group == Group.GOOD) {
      switch (direct) {
        case left:
          g.drawImage(ImgCache.lTank, x, y, null);
          break;
        case right:
          g.drawImage(ImgCache.rTank, x, y, null);
          break;
        case up:
          g.drawImage(ImgCache.uTank, x, y, null);
          break;
        case down:
          g.drawImage(ImgCache.dTank, x, y, null);
          break;
        default:
          break;
      }
    } else {
      switch (direct) {
        case left:
          g.drawImage(ImgCache.bdlTank, x, y, null);
          break;
        case right:
          g.drawImage(ImgCache.bdrTank, x, y, null);
          break;
        case up:
          g.drawImage(ImgCache.bduTank, x, y, null);
          break;
        case down:
          g.drawImage(ImgCache.bddTank, x, y, null);
          break;
        default:
          break;
      }
    }
    move();
  }

  /**
   * 开炮
   */
  @Override
  public void fire() {
    tf.getBullets().add(new Bullet(getBulletX(), getBulletY(), direct, this.group));
  }

}
