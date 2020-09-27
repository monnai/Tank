package bulletstrategy;

import entity.Bullet;
import enums.Direct;
import entity.Tank;

/**
 * @author gu.sc
 */
public class FourDirectFireStrategy implements FireStrategy {

  private Tank tank;

  public FourDirectFireStrategy(Tank tank) {
    this.tank = tank;
  }

  @Override
  public void fire() {
    int a = tank.WIDTH / 4;
    int b = tank.HEIGHT / 4;
    new Bullet(tank.x + a, tank.y + b, Direct.left, tank.getGroup());
    new Bullet(tank.x + a, tank.y + b, Direct.up, tank.getGroup());
    new Bullet(tank.x + a, tank.y + b, Direct.right, tank.getGroup());
    new Bullet(tank.x + a, tank.y + b, Direct.down, tank.getGroup());
  }
}
