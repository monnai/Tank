package collider;

import entity.BaseGameObject;
import entity.Bullet;
import entity.Explode;
import entity.Tank;
import enums.Group;

/**
 * @author gu.sc
 */
public class BulletTankCollider implements Collider {

  @Override
  public boolean collide(BaseGameObject o1, BaseGameObject o2) {
    if (o1 instanceof Bullet && o2 instanceof Tank) {
      Bullet b1 = (Bullet) o1;
      Tank t1 = (Tank) o2;
      //主战坦克不会被打倒
      if (t1.group == Group.GOOD) {
        return true;
      }
      //同阵营tank和bullet不判断碰撞
      if (b1.group == t1.group) {
        return false;
      }
      if (b1.rectangle.intersects(t1.rectangle)) {
        b1.die();
        t1.die();
        new Explode(t1.x, t1.y);
        return false;
      }
    } else if (o1 instanceof Tank && o2 instanceof Bullet) {
      collide(o2, o1);
    }
    return true;
  }
}
