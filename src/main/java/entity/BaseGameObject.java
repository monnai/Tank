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

  boolean living = true;
  int width;
  int height;
  public Rectangle rectangle;


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
  public void die() {
    this.living = false;
  }

  /**
   * 是否应该被剔除
   * @return true 如果应该被剔除
   */
  public abstract boolean showReject();

  /**
   * 修改x和y的时候需要修改rectangle
   */
  void refreshRectangle() {
    rectangle.x = x;
    rectangle.y = y;
  }
}
