package t1;

import conf.TankConf;
import entity.Bullet;
import entity.Explode;
import entity.abst.AbstractTank;
import enums.Direct;
import enums.Group;
import factory.AbstractFactory;
import factory.DefaultFactory;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import sun.security.krb5.Config;

/**
 * @author gu.sc
 */
public class TankFrame extends Frame {

  class MyKeyListener extends KeyAdapter {

    /**
     * 键盘监听事件
     *
     * @param e 按键
     */
    @Override
    public void keyPressed(KeyEvent e) {
      int code = e.getKeyCode();
      if (code == KeyEvent.VK_CONTROL) {
        tank.fire();
      }
      tank.changeDirection(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
      tank.stop();
    }
  }

  /**
   * 游戏屏幕宽度高度
   */
  public static int WIDTH = 800;
  public static int HEIGHT = 600;

  /**
   * 子弹集合
   */
  private ArrayList<Bullet> bullets = new ArrayList<>();

  public ArrayList<Bullet> getBullets() {
    return bullets;
  }

  private TankFrame() {
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
  }

  private Image img = null;

  private AbstractFactory factory;

  {
    String className = TankConf.getString("Factory-Default");
    try {
      factory  = (AbstractFactory) Class.forName(className).getConstructor(TankFrame.class).newInstance(this);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


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
   * 刷新画板 改变画板上的显示
   *
   * @param g 画笔
   */
  @Override
  public void paint(Graphics g) {
    //控制的坦克
    tank.paint(g);
    Color before = g.getColor();
    g.setColor(Color.RED);
    //显示子弹数
    g.drawString("子弹数：\t" + this.bullets.size(), 10, 60);
    g.setColor(before);
    //敌人们画出来
    enemies.forEach(tank -> tank.paint(g));

    for (AbstractTank enemy : enemies) {
      //敌人随机打子弹，运动
      enemy.autoChangeDirection();
      enemy.autoFire();
      //碰撞检验
      for (Bullet bullet : bullets) {
        if (bullet.collideWith(enemy)) {
          explodes.add(new Explode(enemy.getX() + enemy.getwidth() / 2 - Explode.WIDTH / 2,
              enemy.getY() + enemy.getheight() / 2 - Explode.HEIGHT / 2));
          break;
        }
      }
    }
    //渲染子弹
    bullets.forEach(x -> x.paint(g));
    //剔除越界的子弹
    this.rejectBullets();
    this.rejectTanks();
    //爆炸
    explodes.forEach(explode -> explode.paint(g));
    this.rejectExplode();
  }

  /**
   * 去除失效的子弹
   */
  private void rejectBullets() {
    bullets.removeIf(bullet -> !bullet.getLiving());
  }

  /**
   * 去除失效的坦克
   */
  private void rejectTanks() {
    enemies.removeIf(enemy -> !enemy.getLiving());
  }

  private void rejectExplode() {
    explodes.removeIf(explode -> explode.getIndex() == -1);
  }

  private AbstractTank tank = factory.createTank(200, 400, Direct.down, Group.GOOD);

  public AbstractTank getTank() {
    return this.tank;
  }

  private ArrayList<AbstractTank> enemies = new ArrayList<>();

  public ArrayList<AbstractTank> getEnemies() {
    return this.enemies;
  }

  private ArrayList<Explode> explodes = new ArrayList<>();

  public static void main(String[] args) throws InterruptedException {
    TankFrame tankFrame = new TankFrame();
    for (int i = 0; i < TankConf.getInt("tank.number"); i++) {
      tankFrame.enemies.add(tankFrame.factory.createTank(50 + i * 80, 200, Direct.down, Group.BAD));
    }
    do {
      tankFrame.repaint();
      TimeUnit.MILLISECONDS.sleep(30);
    } while (true);
  }
}
