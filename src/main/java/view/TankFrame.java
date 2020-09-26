package view;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.TimeUnit;
import model.GameModel;

/**
 * view 游戏的视图，操作都交给了GameModel
 *
 * @author gu.sc
 */
public class TankFrame extends Frame {

  public static int WIDTH = 800;
  public static int HEIGHT = 600;
  private GameModel gm = GameModel.getInstance();

  class MyKeyListener extends KeyAdapter {

    @Override
    public void keyPressed(KeyEvent e) {
      gm.getTank().changeDirection(e);
      int code = e.getKeyCode();
      if (code == KeyEvent.VK_CONTROL) {
        gm.getTank().fire();
      }
    }
    @Override
    public void keyReleased(KeyEvent e) {
      gm.getTank().stop();
    }

  }


  @SuppressWarnings("InfiniteLoopStatement")
  public TankFrame() {
    setSize(WIDTH, HEIGHT);
    setResizable(false);
    setTitle("tank war");
    setVisible(true);
    addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });
    addKeyListener(new MyKeyListener() {
    });
    do {
      this.repaint();
      try {
        TimeUnit.MILLISECONDS.sleep(30);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    } while (true);

  }

  private Image img;

  @Override
  public void update(Graphics g) {
    if (img == null) {
      img = this.createImage(WIDTH, HEIGHT);
    }
    Graphics g1 = img.getGraphics();
    Color c = g1.getColor();
    g1.setColor(Color.BLACK);
    g1.fillRect(0, 0, WIDTH, HEIGHT);
    g1.setColor(c);
    paint(g1);
    g.drawImage(img, 0, 0, null);
  }

  /**
   * 画出子弹 坦克 爆炸 墙等物品 交给gm去处理
   *
   * @param g 画笔
   */
  @Override
  public void paint(Graphics g) {
    gm.init();
    this.gm.paint(g);
  }


}
