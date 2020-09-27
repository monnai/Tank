package bulletstrategy;

import entity.Bullet;
import entity.Tank;

/**
 * @author gu.sc
 */
public class DefaultFireStrategy implements FireStrategy {

  private Tank tank;

  public DefaultFireStrategy(Tank tank) {
    this.tank = tank;
  }

  @Override
  public void fire() {
    new Bullet(tank.x, tank.y, tank.getDirect(), tank.getGroup());
  }
}
