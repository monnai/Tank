package entity;

import entity.abst.AbstractTank;
import enums.Direct;
import enums.Group;
import java.awt.Color;
import java.awt.Graphics;
import t1.TankFrame;

/**
 * @author gu.sc
 */
public class LowTank extends AbstractTank {


  public LowTank(int x, int y, Direct direct, TankFrame tf, Group group) {
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
      Color color = g.getColor();
      g.setColor(Color.blue);
      g.fillRect(x,y,getwidth(),getheight());
      g.setColor(color);
    } else {
      Color color = g.getColor();
      g.setColor(Color.pink);
      g.fillRect(x,y,getwidth(),getheight());
      g.setColor(color);
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
