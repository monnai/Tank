package entity;

import bulletstrategy.FireStrategy;
import conf.ImgCache;
import conf.TankConf;
import enums.Direct;
import enums.Group;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.Random;
import view.TankFrame;

/**
 * @author gu.sc
 */
public class Tank extends BaseMovableGameObject {

  public static int WIDTH = ImgCache.dTank.getWidth();
  public static int HEIGHT = ImgCache.dTank.getHeight();
  private static final int AUTO_FILE_LIMIT = 99;
  private static final int AUTO_FILE_LIMIT_SEED = 3;
  /**
   * 发射子弹策略
   */
  private FireStrategy fs;
  /**
   * tanktank碰撞处理时记录回滚的XY
   */
  private int rollX;
  private int rollY;
  private Random random = new Random();

  public Direct getDirect() {
    return direct;
  }

  public Tank(int x, int y, Direct direct, Group group) {
    super.x = x;
    super.y = y;
    super.direct = direct;
    this.group = group;
    //主战tank上来不移动初始化回滚点和碰撞图形
    rollX = x;
    rollY = y;
    rectangle = new Rectangle(x, y, WIDTH, HEIGHT);
    if (this.group == Group.GOOD) {
      String clazz = TankConf.getString("FourDirectFireStrategy");
      try {
        Class<?> fsClazz = Class.forName(clazz);
        this.fs = (FireStrategy) fsClazz.getConstructor(Tank.class).newInstance(this);
      } catch (Exception e) {
        e.printStackTrace();
      }
    } else {
      String clazz = TankConf.getString("DefaultFireStrategy");
      try {
        Class<?> fsClazz = Class.forName(clazz);
        this.fs = (FireStrategy) fsClazz.getConstructor(Tank.class).newInstance(this);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    SPEED = TankConf.getInt("tank.speed");
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

  private void autoChangeDirection() {
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

  public int i;

  @Override
  public void move(int speed) {
    i++;
    if (i == 133 && group == Group.GOOD) {
      this.stop();
    }
    if (i == 112 && group == Group.GOOD) {
      this.moving = true;
    }
    if (i == 3 && group == Group.GOOD) {
      this.stop();
    }
    //tank静止了，不移动
    if (!moving && group == Group.GOOD) {
      return;
    }
    //敌军tank能自动移动，随机打子弹
    if (this.group == Group.BAD) {
      autoChangeDirection();
      autoFire();
    }
    //为防止tank之间碰撞记录回滚点XY
    this.rollX = x;
    this.rollY = y;
    super.move(speed);
    //墙壁碰撞
    boundaryCheck();
  }

  /**
   * 边界检测
   */
  private void boundaryCheck() {
    int boundLimit = 27;
    if (x < 0) {
      x = 0;
    }
    if (x + WIDTH > TankFrame.WIDTH) {
      x = TankFrame.WIDTH - WIDTH;
    }
    if (y < boundLimit) {
      y = 27;
    }
    if (y + HEIGHT > TankFrame.HEIGHT) {
      y = TankFrame.HEIGHT - HEIGHT;
    }
    //碰到边界时会直接修改坐标，同时需要改碰撞图型，
    // 不然会引起碰撞检测失效一回合
    refreshRectangle();
  }


  /**
   * 和键盘抬起后停止
   */
  public void stop() {
    this.moving = false;
    //停止的时候，本来是1号追2号，如果2号突然停止，把y回退，1号也回退不会追尾，但是这里1号不会退
    //所以只能把2号的rollY和rollX设置为预计的xy的值，类似不退
    this.rollX = super.x;
    this.rollY = super.y;
    refreshRectangle();
  }

  public void rollback() {
    super.x = this.rollX;
    super.y = this.rollY;
    refreshRectangle();
  }

  @Override
  public void paint(Graphics g) {
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
      /*Color color = g.getColor();
      g.setColor(Color.pink);
      g.fillRect(x, y, WIDTH, WIDTH);
      g.setColor(color);*/

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
     /* Color color = g.getColor();
      g.setColor(Color.yellow);
      g.fillRect(x, y, WIDTH, WIDTH);
      g.setColor(color);*/
    }
    move(SPEED);
  }


  /**
   * 开炮
   */
  public void fire() {
    fs.fire();
  }

  /**
   * 自动开炮 1/3概率
   */
  private void autoFire() {
    if (random.nextInt(AUTO_FILE_LIMIT) == AUTO_FILE_LIMIT_SEED) {
      this.fs.fire();
    }
  }

  @Override
  public boolean showReject() {
    return !super.living;
  }

}
