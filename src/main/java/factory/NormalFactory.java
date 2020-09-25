package factory;

import entity.Bullet;
import entity.Explode;
import entity.NormalTank;
import entity.abst.AbstractTank;
import enums.Direct;
import enums.Group;
import t1.TankFrame;

/**
 * @author gu.sc
 */
public class NormalFactory extends AbstractFactory {


  NormalFactory(TankFrame tf) {
    super(tf);
  }

  @Override
  public Bullet createBullet() {
    return null;
  }

  @Override
  public AbstractTank createTank(int x, int y, Direct direct, Group group) {
    return new NormalTank(x,y,direct,tf,group);
  }

  @Override
  public Explode createExplode() {
    return null;
  }
}
