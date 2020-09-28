package collider;

import entity.BaseGameObject;
import entity.Bullet;
import entity.Wall;

/**
 * @author gu.sc
 */
public class WallBulletCollider implements Collider {

  @Override
  public boolean collide(BaseGameObject o1, BaseGameObject o2) {
    if (o1 instanceof Wall && o2 instanceof Bullet) {
      Wall w1 = (Wall) o1;
      Bullet b1 = (Bullet) o2;
      if (w1.rectangle.intersects(b1.rectangle)) {
        b1.die();
      }
    } else if (o1 instanceof Bullet && o2 instanceof Wall) {
      collide(o2, o1);
    }
    return true;
  }
}
