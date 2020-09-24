package entity;

import enums.Direct;
import enums.Group;
import java.awt.Graphics;
import java.awt.Rectangle;
import t1.Tank;
import t1.TankFrame;

/**
 * @author gu.sc
 */
public class Bullet {

  /**
   * The direction of bullet's move
   */
  private Direct direct;
  /**
   * The speed of bullet's move
   */
  private static final int SPEED = 10;
  /**
   * bullet's weight and height
   */
  public static int
      HEIGHT = ImgCache.dBullet.getHeight(),
      WIDTH = ImgCache.dBullet.getWidth();
  /**
   * live or death
   */
  private boolean living = true;

  public boolean getLiving() {
    return this.living;
  }

  /**
   * bullet's coordinates
   */
  private int x, y;

  public Bullet(int x, int y, Direct direct, Group group) {
    this.x = x;
    this.y = y;
    this.direct = direct;
    this.group = group;
  }

  private Group group;

  /**
   * bullet碰撞检测图型
   */
  private Rectangle rectangle = new Rectangle(WIDTH,HEIGHT);


  /**
   * paint itself
   */
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

  private void move() {
    //静止状态直接返回
    switch (direct) {
      case left:
        x -= SPEED;
        break;
      case right:
        x += SPEED;
        break;
      case up:
        y -= SPEED;
        break;
      case down:
        y += SPEED;
        break;
      default:
        break;
    }
    //增加越界处理：集合中
    if (x < 0 | x > TankFrame.WIDTH | y < 0 | y > TankFrame.HEIGHT) {
      die();
    }
  }

  /**
   * 碰撞检验
   *
   * @param tank tank
   */
  public boolean collideWith(Tank tank) {
    //区分敌我，同方子弹不造成伤害
    if (this.group == tank.getGroup()) {
      return false;
    }
    //更新子弹的rectangle
    this.rectangle.x = x;
    this.rectangle.y = y;
    //更新坦克的rectangle
    Rectangle rectangle = tank.getRectangle();
    rectangle.x = tank.getX();
    rectangle.y = tank.getY();
    if (this.rectangle.intersects(rectangle)) {
      tank.die();
      this.die();
      return true;
    }
    return false;
  }

  /**
   * 子弹死掉
   */
  private void die() {
    this.living = false;
  }
}
