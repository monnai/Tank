package factory;

import entity.Bullet;
import entity.Explode;
import entity.LowTank;
import entity.abst.AbstractTank;
import enums.Direct;
import enums.Group;
import t1.TankFrame;

/**
 * @author gu.sc
 */
public class DefaultFactory extends AbstractFactory {

  public DefaultFactory(TankFrame tf) {
    super(tf);
  }

  @Override
  public Bullet createBullet() {
    return null;
  }

  @Override
  public AbstractTank createTank(int x, int y, Direct direct, Group groupe) {
    return new LowTank(x,y,direct,tf,groupe);
  }

  @Override
  public Explode createExplode() {
    return null;
  }
}
