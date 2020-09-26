package bulletstrategy;

import entity.Bullet;
import entity.Tank;
import model.GameModel;

/**
 * @author gu.sc
 */
public class DefaultFireStrategy implements FireStrategy {

  private Tank tank;
  private GameModel gm;

  public DefaultFireStrategy(Tank tank) {
    this.tank = tank;
    this.gm = GameModel.getInstance();
  }

  @Override
  public void fire() {
    new Bullet(tank.getX(), tank.getY(), tank.getDirect(), tank.getGroup(), gm);
  }
}
