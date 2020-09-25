package t1;

import bulletstrategy.DefaultFireStrategy;
import bulletstrategy.FireStrategy;
import bulletstrategy.FourDirectFireStrategy;
import conf.TankConf;
import entity.Bullet;
import entity.ImgCache;
import enums.Direct;
import enums.Group;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

/**
 * @author gu.sc
 */
public class Tank {

  private boolean living = true;

  boolean getLiving() {
    return this.living;
  }

  Tank(int x, int y, Direct direct, TankFrame tf, Group group) {
    this.x = x;
    this.y = y;
    this.direct = direct;
    this.tf = tf;
    this.group = group;
    if (this.group == Group.GOOD) {
      String clazz = TankConf.getString("FourDirectFireStrategy");
      try {
        Class<?> fsClazz = Class.forName(clazz);
        this.fs = (FireStrategy) fsClazz.getConstructor(Tank.class,TankFrame.class).newInstance(this,tf);
      } catch (Exception e) {
        e.printStackTrace();
      }
    } else {
      String clazz = TankConf.getString("DefaultFireStrategy");
      try {
        Class<?> fsClazz = Class.forName(clazz);
        this.fs = (FireStrategy) fsClazz.getConstructor(Tank.class,TankFrame.class).newInstance(this,tf);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  private TankFrame tf;
  /**
   * 发射子弹策略
   */
  private FireStrategy fs;

  /**
   * 初始横纵坐标
   */
  private int x, y;

  public int getX() {
    return this.x;
  }

  public int getY() {
    return this.y;
  }

  private int getBulletX() {
    return this.x + this.WIDTH / 2 - Bullet.WIDTH / 2;
  }

  private int getBulletY() {
    return this.y + this.WIDTH / 2 - Bullet.HEIGHT / 2;
  }

  /**
   * 方块的长和宽
   */
  private final int
      WIDTH = ImgCache.dTank.getWidth(),
      HEIGHT = ImgCache.dTank.getHeight();

  public int getwidth() {
    return this.WIDTH;
  }

  public int getheight() {
    return this.HEIGHT;
  }

  /**
   * 移动像素 默认5px
   */
  private static final int PX = 5;
  private static final int PX1 = 5;
  private Direct direct;

  public Direct getDirect() {
    return direct;
  }

  /**
   * 坦克的状态：静止 false 移动true
   */
  private boolean moving;

  /**
   * 敌我区分标志
   */
  private Group group;

  public Group getGroup() {
    return this.group;
  }

  private Random random = new Random();

  /**
   * 设置方向
   *
   * @param direct 键盘方向
   */
  private void setDirect(Direct direct) {
    this.direct = direct;
  }

  /**
   * 碰撞检测图形
   */
  private Rectangle rectangle = new Rectangle(WIDTH, HEIGHT);

  public Rectangle getRectangle() {
    return this.rectangle;
  }

  /**
   * 改变tank移动的方向
   *
   * @param e 键盘点击事件
   */
  void changeDirection(KeyEvent e) {
    int code = e.getKeyCode();
    moving = true;
    switch (code) {
      case KeyEvent.VK_LEFT:
        setDirect(Direct.left);
        break;
      case KeyEvent.VK_RIGHT:
        setDirect(Direct.right);
        break;
      case KeyEvent.VK_UP:
        setDirect(Direct.up);
        break;
      case KeyEvent.VK_DOWN:
        setDirect(Direct.down);
        break;
      default:
        moving = false;
        break;
    }
    //bug : 任意键触发后，都移动
//    moving = true;
  }

  void autoChangeDirection() {
    moving = true;
    int code = new Random().nextInt(12);
    switch (code) {
      case 1:
        setDirect(Direct.left);
        break;
      case 2:
        setDirect(Direct.right);
        break;
      case 3:
        setDirect(Direct.up);
        break;
      case 4:
        setDirect(Direct.down);
        break;
      case 5:
        moving = false;
        break;
      case 6:
        moving = false;
        break;
      default:
        break;
    }
  }

  /**
   * 坦克移动 ，根据变量direct的方向 ，更改横纵坐标
   */
  private void move() {
    //静止状态直接返回
    if (!moving) {
      return;
    }
    if (!canMove()) {
      return;
    }
    switch (direct) {
      case left:
        x -= PX;
        break;
      case right:
        x += PX;
        break;
      case up:
        y -= PX;
        break;
      case down:
        y += PX;
        break;
      default:
        break;
    }
    boundaryCheck();
  }

  /**
   * 边界检测
   */
  private void boundaryCheck() {
    if (x < 0) {
      x = 0;
    }
    if (x + WIDTH > TankFrame.WIDTH) {
      x = TankFrame.WIDTH - WIDTH;
    }
    if (y < 27) {
      y = 27;
    }
    if (y + HEIGHT > TankFrame.HEIGHT) {
      y = TankFrame.HEIGHT - HEIGHT;
    }
  }

  /**
   * 坦克之间碰撞检验
   *
   * @param tank tank
   */
  private boolean collideWith(Tank tank) {
    this.rectangle.x = this.x;
    this.rectangle.y = this.y;
    //更新自己的rectangle
    switch (direct) {
      case left:
        this.rectangle.x -= PX1;
        break;
      case right:
        this.rectangle.x += PX1;
        break;
      case up:
        this.rectangle.y -= PX1;
        break;
      case down:
        this.rectangle.y += PX1;
        break;
      default:
        break;
    }
    //更新其他坦克的rectangle
    Rectangle rectangle = tank.getRectangle();
    rectangle.x = tank.getX();
    rectangle.y = tank.getY();
    return this.rectangle.intersects(rectangle);
  }

  /**
   * 验证坦克之间是否重合，重合不移动
   */
  private boolean canMove() {
    ArrayList<Tank> enemies = tf.getEnemies();
    Tank userTank = tf.getTank();
    boolean result = false;
    //敌方坦克进行判断，如果和userTank碰撞了直接return
    //如果不是user 先用自己和user比一下，看碰撞了吗
    if (this != userTank) {
      if (this.collideWith(userTank)) {
        return false;
      }
    }
    //如果是user或者enemy，都和其他的enemy比较一下，需要排除自己和自己比较
    for (Tank enemy : enemies) {
      if (this == enemy) {
        continue;
      }
      if (this.collideWith(enemy)) {
        return false;
      }
    }
    return true;
  }

  /**
   * 和键盘抬起后停止
   */
  void stop() {
    this.moving = false;
  }

  void paint(Graphics g) {
    //根据方向渲染坦克
    if (!this.living) {
      return;
    }
    if (this.group == Group.GOOD) {
      switch (direct) {
        case left:
          g.drawImage(ImgCache.lTank, x, y, null);
          break;
        case right:
          g.drawImage(ImgCache.rTank, x, y, null);
          break;
        case up:
          g.drawImage(ImgCache.uTank, x, y, null);
          break;
        case down:
          g.drawImage(ImgCache.dTank, x, y, null);
          break;
        default:
          break;
      }
     /* Color before = g.getColor();
      g.setColor(Color.yellow);
      g.fillRect(x, y, WIDTH, HEIGHT);
      g.setColor(before);*/
    } else {
      switch (direct) {
        case left:
          g.drawImage(ImgCache.bdlTank, x, y, null);
          break;
        case right:
          g.drawImage(ImgCache.bdrTank, x, y, null);
          break;
        case up:
          g.drawImage(ImgCache.bduTank, x, y, null);
          break;
        case down:
          g.drawImage(ImgCache.bddTank, x, y, null);
          break;
        default:
          break;
      }
    /*  Color before = g.getColor();
      g.setColor(Color.yellow);
      g.fillRect(x, y, WIDTH, HEIGHT);
      g.setColor(before);*/
    }
    move();
  }

  /**
   * 开炮
   */
  void fire() {
    fs.fire();
  }

  private static final int AUTO_FILE_LIMIT = 99;
  private static final int AUTO_FILE_LIMIT_SEED = 3;

  /**
   * 自动开炮 1/3概率
   */
  void autoFire() {
    if (random.nextInt(AUTO_FILE_LIMIT) == AUTO_FILE_LIMIT_SEED) {
      this.fs.fire();
    }
  }

  public void die() {
    this.living = false;
  }

}
