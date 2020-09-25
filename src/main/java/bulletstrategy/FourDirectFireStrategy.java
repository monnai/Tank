package bulletstrategy;

import entity.Bullet;
import enums.Direct;
import t1.Tank;
import t1.TankFrame;

/**
 * @author gu.sc
 */
public class FourDirectFireStrategy implements FireStrategy {

  private Tank tank;
  private TankFrame tf;

  public FourDirectFireStrategy(Tank tank, TankFrame tf) {
    this.tank = tank;
    this.tf = tf;
  }

  @Override
  public void fire() {
    int a =  tank.getwidth() /4;
    int b =  tank.getheight() /4;
    new Bullet(tank.getX()+a, tank.getY()+b, Direct.left, tank.getGroup(), this.tf);
    new Bullet(tank.getX()+a, tank.getY()+b, Direct.up, tank.getGroup(), this.tf);
    new Bullet(tank.getX()+a, tank.getY()+b, Direct.right, tank.getGroup(), this.tf);
    new Bullet(tank.getX()+a, tank.getY()+b, Direct.down, tank.getGroup(), this.tf);
  }
}
