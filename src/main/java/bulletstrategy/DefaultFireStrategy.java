package bulletstrategy;

import entity.Bullet;
import entity.Tank;
import enums.Direct;
import enums.Group;

/**
 * @author gu.sc
 */
public class DefaultFireStrategy implements FireStrategy {

  private Tank tank;
  private int x_, y_;

  public DefaultFireStrategy(Tank tank) {
    this.tank = tank;
    x_ = Tank.WIDTH >> 2;
    y_ = Tank.HEIGHT >> 2;
  }

  @Override
  public void fire() {

    new Bullet(tank.x + x_, tank.y + y_, tank.getDirect(), tank.group);
  }

}
