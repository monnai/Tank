package entity;

import enums.Direct;

/**
 * @author gu.sc
 */
public abstract class BaseMovableGameObject extends BaseGameObject implements Movable {

  Direct direct;

  public int SPEED;

  public boolean moving = true;

  public void setDirect(Direct direct) {
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
  }
}
