package bulletstrategy;

import entity.Bullet;
import t1.Tank;
import t1.TankFrame;

/**
 * @author gu.sc
 */
public class DefaultFireStrategy implements FireStrategy {

  private Tank tank;
  private TankFrame tf;

  public DefaultFireStrategy(Tank tank, TankFrame tf) {
    this.tank = tank;
    this.tf = tf;
  }

  @Override
  public void fire() {
    new Bullet(tank.getX(), tank.getY(), tank.getDirect(), tank.getGroup(), tf);
  }
}
