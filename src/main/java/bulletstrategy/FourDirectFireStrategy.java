package bulletstrategy;

import entity.Bullet;
import enums.Direct;
import entity.Tank;
import model.GameModel;

/**
 * @author gu.sc
 */
public class FourDirectFireStrategy implements FireStrategy {

  private Tank tank;
  private GameModel gm;

  public FourDirectFireStrategy(Tank tank ) {
    this.tank = tank;
    this.gm = GameModel.getInstance();
  }

  @Override
  public void fire() {
    int a =  tank.getwidth() /4;
    int b =  tank.getheight() /4;
    new Bullet(tank.getX()+a, tank.getY()+b, Direct.left, tank.getGroup(), this.gm);
    new Bullet(tank.getX()+a, tank.getY()+b, Direct.up, tank.getGroup(), this.gm);
    new Bullet(tank.getX()+a, tank.getY()+b, Direct.right, tank.getGroup(), this.gm);
    new Bullet(tank.getX()+a, tank.getY()+b, Direct.down, tank.getGroup(), this.gm);
  }
}
