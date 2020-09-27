package entity;

import bulletstrategy.FireStrategy;
import conf.ImgCache;
import conf.TankConf;
import enums.Direct;
import enums.Group;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;
import view.TankFrame;

/**
 * @author gu.sc
 */
public class Tank extends BaseMovableGameObject {

  {
    WIDTH = ImgCache.dTank.getWidth();
    HEIGHT = ImgCache.dTank.getHeight();
    rectangle = new Rectangle(WIDTH, HEIGHT);
  }


  /**
   * 敌我区分标志
   */
  private Group group;

  /**
   * 发射子弹策略
   */
  private FireStrategy fs;

  public Group getGroup() {
    return this.group;
  }

  public Tank(int x, int y, Direct direct, Group group) {
    super.x = x;
    super.y = y;
    super.direct = direct;
    this.group = group;
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
   * 移动像素 默认5px
   */
  private static final int PX = 5;

  public Direct getDirect() {
    return direct;
  }

  private Random random = new Random();

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

  @Override
  public void move(int speed) {
    //静止状态直接返回
    if (!moving) {
      return;
    }
    //不能够移动直接返回
    if (!canMove()) {
      return;
    }
    super.move(speed);
    //墙壁碰撞
    //todo 责任链优化
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
        this.rectangle.x -= PX;
        break;
      case right:
        this.rectangle.x += PX;
        break;
      case up:
        this.rectangle.y -= PX;
        break;
      case down:
        this.rectangle.y += PX;
        break;
      default:
        break;
    }
    //更新其他坦克的rectangle
    Rectangle rectangle = tank.rectangle;
    rectangle.x = tank.x;
    rectangle.y = tank.y;
    return this.rectangle.intersects(rectangle);
  }

  /**
   * 验证坦克之间是否重合，重合不移动
   */
  private boolean canMove() {
    ArrayList<Tank> enemies = gm.getEnemies();
    Tank userTank = gm.getTank();
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
  public void stop() {
    this.moving = false;
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
    }
    move(SPEED);
  }

  /**
   * 开炮
   */
  public void fire() {
    fs.fire();
  }

  private static final int AUTO_FILE_LIMIT = 99;
  private static final int AUTO_FILE_LIMIT_SEED = 3;

  /**
   * 自动开炮 1/3概率
   */
  public void autoFire() {
    if (random.nextInt(AUTO_FILE_LIMIT) == AUTO_FILE_LIMIT_SEED) {
      this.fs.fire();
    }
  }


}
