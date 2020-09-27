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
 * @author gu.sc
 */
public class Bullet extends BaseMovableGameObject {

  {
    WIDTH = ImgCache.dBullet.getWidth();
    HEIGHT = ImgCache.dBullet.getHeight();
    rectangle = new Rectangle(WIDTH, HEIGHT);
    gm = GameModel.getInstance();
    SPEED = TankConf.getInt("bullet.speed");
  }

  private Group group;

  public Bullet(int x, int y, Direct direct, Group group) {
    super.x = x;
    super.y = y;
    //初始化时加入容器
    gm.getBullets().add(this);
    this.direct = direct;
    this.group = group;
  }

  @Override
  public void paint(Graphics g) {
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

  public void move() {
    super.move(SPEED);
    //到达界面边界，消除该bullet
    if (x < 0 | x > TankFrame.WIDTH | y < 0 | y > TankFrame.HEIGHT) {
      die();
    }
  }

  /**
   * 碰撞检验 todo:责任链模式优化
   *
   * @param tank tank
   */
  public boolean collideWith(Tank tank) {
    //区分敌我，同方子弹不造成伤害
    if (this.group == tank.getGroup()) {
      return false;
    }
    //更新子弹的rectangle
    rectangle.x = x;
    rectangle.y = y;
    //更新坦克的rectangle
    Rectangle rectangle = tank.rectangle;
    rectangle.x = tank.x;
    rectangle.y = tank.y;
    if (super.rectangle.intersects(rectangle)) {
      tank.die();
      this.die();
      return true;
    }
    return false;
  }

}
