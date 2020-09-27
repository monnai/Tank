package entity;

import java.awt.Graphics;
import java.awt.Rectangle;
import model.GameModel;

/**
 * wall & tank & bullet abstract class
 *
 * @author gu.sc
 */
public abstract class BaseGameObject {

  public boolean living = true;
  public int WIDTH;
  public int HEIGHT;
  Rectangle rectangle;


  GameModel gm = GameModel.getInstance();

  /**
   * bullet's coordinates
   */
  public int x, y;

  /**
   * paint itself
   *
   * @param g graphics
   */
  public abstract void paint(Graphics g);

  /**
   * 在碰撞后，将living属性设置为false （默认为true）
   */
  void die() {
    this.living = false;
  }

}
