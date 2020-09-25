package entity.abst;

import entity.Bullet;
import entity.ImgCache;
import enums.Direct;
import enums.Group;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;
import t1.TankFrame;

/**
 * @author gu.sc
 */
public abstract class AbstractTank {

  public int x;

  protected int y;
  /**
   * 碰撞检测图形
   */
  private Rectangle rectangle = new Rectangle(WIDTH, HEIGHT);

  protected Direct direct;

  protected TankFrame tf;

  /**
   * 坦克的状态：静止 false 移动true
   */
  private boolean moving;

  private Random random = new Random();

  private static final int AUTO_FILE_LIMIT = 99;

  private static final int AUTO_FILE_LIMIT_SEED = 3;
  /**
   * 敌我区分标志
   */
  protected Group group;

  public Group getGroup() {
    return this.group;
  }

  protected boolean living = true;

  public boolean getLiving() {
    return this.living;
  }

  /**
   * 方块的长和宽
   */
  private static final int
      WIDTH = ImgCache.dTank.getWidth(),
      HEIGHT = ImgCache.dTank.getHeight();

  public int getX() {
    return this.x;
  }

  public int getY() {
    return this.y;
  }

  public Rectangle getRectangle() {
    return this.rectangle;
  }

  protected int getBulletX() {
    return this.x + WIDTH / 2 - Bullet.WIDTH / 2;
  }

  protected int getBulletY() {
    return this.y + WIDTH / 2 - Bullet.HEIGHT / 2;
  }


  public int getwidth() {
    return WIDTH;
  }

  public int getheight() {
    return HEIGHT;
  }

  /**
   * 移动像素 默认5px
   */
  private static final int PX = 5;
  private static final int PX1 = 5;

  /**
   * 和键盘抬起后停止
   */
  public void stop() {
    this.moving = false;
  }

  /**
   * 坦克之间碰撞检验
   *
   * @param tank tank
   */
  private boolean collideWith(AbstractTank tank) {
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
   * 坦克移动 ，根据变量direct的方向 ，更改横纵坐标
   */
  protected void move() {
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
    int boundLimitSize = 27;
    if (y < boundLimitSize) {
      y = boundLimitSize;
    }
    if (y + HEIGHT > TankFrame.HEIGHT) {
      y = TankFrame.HEIGHT - HEIGHT;
    }
  }

  /**
   * 验证坦克之间是否重合，重合不移动
   */
  private boolean canMove() {
    ArrayList<AbstractTank> enemies = tf.getEnemies();
    AbstractTank userTank = tf.getTank();
    //敌方坦克进行判断，如果和userTank碰撞了直接return
    //如果不是user 先用自己和user比一下，看碰撞了吗
    if (this != userTank) {
      if (this.collideWith(userTank)) {
        return false;
      }
    }
    //如果是user或者enemy，都和其他的enemy比较一下，需要排除自己和自己比较
    for (AbstractTank enemy : enemies) {
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
   * 改变tank移动的方向
   *
   * @param e 键盘点击事件
   */
  public void changeDirection(KeyEvent e) {
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
  }

  /**
   * 设置方向
   *
   * @param direct 键盘方向
   */
  private void setDirect(Direct direct) {
    this.direct = direct;
  }

  public void autoChangeDirection() {
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

  public void die() {
    this.living = false;
  }

  /**
   * 发射子弹
   */
  public abstract void fire();

  /**
   * 自动开炮 1/3概率
   */
  public void autoFire() {
    if (random.nextInt(AUTO_FILE_LIMIT) == AUTO_FILE_LIMIT_SEED) {
      tf.getBullets().add(new Bullet(getBulletX(), getBulletY(), direct, this.group));
    }
  }


  /**
   * 画自己
   *
   * @param g 画笔
   */
  public abstract void paint(Graphics g);


}
