package entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * @author gu.sc
 */
public class Wall extends BaseGameObject {

  public Wall(int x, int y, int width, int height) {
    super.x = x;
    super.y = y;
    super.width = width;
    super.height = height;
    rectangle = new Rectangle(x, y, width, height);
  }

  @Override
  public void paint(Graphics g) {
    Color color = g.getColor();
    g.setColor(Color.cyan);
    g.fillRect(x, y, width, height);
    g.setColor(color);
  }

  @Override
  public boolean showReject() {
    return false;
  }
}
