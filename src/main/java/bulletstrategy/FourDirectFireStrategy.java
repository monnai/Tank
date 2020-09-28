package bulletstrategy;

import entity.Bullet;
import enums.Direct;
import entity.Tank;

/**
 * @author gu.sc
 */
public class FourDirectFireStrategy implements FireStrategy {

  private Tank tank;
  private int x_;
  private int y_;

  public FourDirectFireStrategy(Tank tank) {
    this.tank = tank;
    x_ = Tank.WIDTH >> 2;
    y_ = Tank.HEIGHT >> 2;
  }

  @Override
  public void fire() {

    new Bullet(tank.x + x_, tank.y + y_, Direct.left, tank.group);
    new Bullet(tank.x + x_, tank.y + y_, Direct.up, tank.group);
    new Bullet(tank.x + x_, tank.y + y_, Direct.right, tank.group);
    new Bullet(tank.x + x_, tank.y + y_, Direct.down, tank.group);
  }
}
