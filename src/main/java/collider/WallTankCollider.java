package collider;

import entity.BaseGameObject;
import entity.Tank;
import entity.Wall;

/**
 * @author gu.sc
 */
public class WallTankCollider implements Collider {

  @Override
  public boolean collide(BaseGameObject o1, BaseGameObject o2) {
    if (o1 instanceof Wall && o2 instanceof Tank) {
      Wall w1 = (Wall) o1;
      Tank t1 = (Tank) o2;
      if (w1.rectangle.intersects(t1.rectangle)) {
        t1.rollback();
      }
      return false;
    } else if (o1 instanceof Tank && o2 instanceof Wall) {
      collide(o2, o1);
    }
    return true;
  }
}
