package entity;

import conf.ImgCache;
import conf.TankConf;
import enums.Direct;
import enums.Group;
import java.awt.Graphics;
import java.awt.Rectangle;
import model.GameModel;
import view.TankFrame;

/**
 * 子弹类
 *
 * @author gu.sc
 */
public class Bullet extends BaseMovableGameObject {

  {
    width = ImgCache.dBullet.getWidth();
    height = ImgCache.dBullet.getHeight();
    gm = GameModel.getInstance();
    SPEED = TankConf.getInt("bullet.speed");
  }


  public Bullet(int x, int y, Direct direct, Group group) {
    super.x = x;
    super.y = y;
    this.direct = direct;
    this.group = group;
    //初始化时加入容器
    rectangle = new Rectangle(x,y, width, height);
    gm.getGos().add(this);
  }

  @Override
  public void paint(Graphics g) {
    //根据方向获取图片进行绘制
    switch (direct) {
      case up:
        g.drawImage(ImgCache.uBullet, x, y, null);
        break;
      case down:
        g.drawImage(ImgCache.dBullet, x, y, null);
        break;
      case left:
        g.drawImage(ImgCache.lBullet, x, y, null);
        break;
      case right:
        g.drawImage(ImgCache.rBullet, x, y, null);
        break;
      default:
        break;
    }
    move();
  }

  /**
   * 是否消除该子弹。如果living是false就从gos集合中移除掉
   *
   * @return true如果子弹死掉了
   */
  @Override
  public boolean showReject() {
    return !this.living;
  }

  /**
   * 移动子弹
   */
  private void move() {
    super.move(SPEED);
    //到达界面边界，消除该bullet由于边界还不是物体，没有加入到链子中统一处理
    if (x < 0 | x > TankFrame.WIDTH | y < 0 | y > TankFrame.HEIGHT) {
      die();
    }
  }

}
