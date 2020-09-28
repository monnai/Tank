package collider;

import entity.BaseGameObject;

/**
 * 对撞
 * @author gu.sc
 */
public interface Collider {

  /**
   * 判断A B是否相撞
   * @param o1 物体A
   * @param o2 物体B
   */
   boolean collide(BaseGameObject o1, BaseGameObject o2);
}
