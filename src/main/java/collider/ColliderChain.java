package collider;

import entity.BaseGameObject;
import java.util.LinkedList;
import java.util.List;

/**
 * @author gu.sc
 */
public class ColliderChain implements Collider {

  private List<Collider> colliders = new LinkedList<>();

  public void add(Collider collider) {
    colliders.add(collider);
  }


  @Override
  public boolean collide(BaseGameObject o1, BaseGameObject o2) {
    for (Collider collider : colliders) {
      if (!collider.collide(o1, o2)) {
        //链子的节点处理了，直接返回false不再处理
        return false;
      }
    }
    //链子没处理，交给下游处理
    return true;
  }
}
