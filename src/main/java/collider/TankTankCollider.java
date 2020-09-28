package collider;

import entity.BaseGameObject;
import entity.Tank;

/**
 * @author gu.sc
 */
public class TankTankCollider implements Collider {

  @Override
  public boolean collide(BaseGameObject o1, BaseGameObject o2) {
    if (o1 instanceof Tank && o2 instanceof Tank) {
      Tank t1 = (Tank) o1;
      Tank t2 = (Tank) o2;
      if (t1.rectangle.intersects(t2.rectangle)) {
        t1.rollback();
        t2.rollback();
      }
      return false;
    }
    return true;
  }
}
