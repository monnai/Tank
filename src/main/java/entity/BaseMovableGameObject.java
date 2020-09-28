package entity;

import enums.Direct;
import enums.Group;

/**
 * @author gu.sc
 */
public abstract class BaseMovableGameObject extends BaseGameObject implements Movable {

  Direct direct;

  int SPEED;

  public boolean moving = true;

  public Group group;

  void setDirect(Direct direct) {
    this.direct = direct;
  }

  @Override
  public void move(int speed) {
    switch (direct) {
      case left:
        x -= speed;
        break;
      case right:
        x += speed;
        break;
      case up:
        y -= speed;
        break;
      case down:
        y += speed;
        break;
      default:
        break;
    }
    rectangle.x = x;
    rectangle.y = y;
  }
}
