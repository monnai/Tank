package model;

import com.sun.org.apache.xpath.internal.functions.FuncFalse;
import conf.TankConf;
import entity.Bullet;
import entity.Explode;
import entity.Tank;
import enums.Direct;
import enums.Group;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * TankFrame的中介者，大管家 管理tf的吃喝拉撒 提供tf开始调用的门面
 *
 * @author gu.sc
 */
public class GameModel {

  private GameModel() {
  }

  private static volatile GameModel instance;

  public static GameModel getInstance() {
    if (null == instance) {
      synchronized (GameModel.class) {
        if (null == instance) {
          instance = new GameModel();
        }
      }
    }
    return instance;
  }


  /**
   * 子弹相关
   */
  private ArrayList<Bullet> bullets = new ArrayList<>();

  public ArrayList<Bullet> getBullets() {
    return bullets;
  }

  private void rejectBullets() {
    bullets.removeIf(bullet -> !bullet.living);
  }

  /**
   * 我方坦克
   */
  private Tank tank;

  public Tank getTank() {
    return this.tank;
  }

  /**
   * 敌方坦克
   */
  public ArrayList<Tank> enemies = new ArrayList<>();

  public ArrayList<Tank> getEnemies() {
    return this.enemies;
  }

  private void rejectTanks() {
    enemies.removeIf(enemy -> !enemy.living);
  }

  /**
   * 爆炸相关
   */
  private ArrayList<Explode> explodes = new ArrayList<>();

  private void rejectExplode() {
    explodes.removeIf(explode -> explode.getIndex() == -1);
  }

  /**
   * 初始化完成标识
   */
  private AtomicBoolean initialized = new AtomicBoolean(false);

  public void init() {
    if (!initialized.get()) {
      tank = new Tank(200, 400, Direct.down, Group.GOOD);
      tank.moving = false;
      for (int i = 0; i < TankConf.getInt("tank.number"); i++) {
        this.enemies.add(new Tank(50 + i * 80, 200, Direct.down, Group.BAD));
      }
      initialized.set(true);
    }
  }

  public void paint(Graphics g) {
    Color before = g.getColor();
    g.setColor(Color.RED);
    //sum
//    g.drawString("子弹数：\t" + this.bullets.size(), 10, 60);
//    g.drawString("敌人数：\t" + enemies.size(), 10, 80);
//    g.drawString("爆炸数：\t" + explodes.size(), 10, 100);
    g.setColor(before);
    //my tank paint
    tank.paint(g);
    //tanks paint
    enemies.forEach(tank -> tank.paint(g));
    //bullets paint
    bullets.forEach(x -> x.paint(g));
    //explodes paint
    explodes.forEach(explode -> explode.paint(g));
    for (Tank enemy : enemies) {
      //敌人随机打子弹，运动
      enemy.autoChangeDirection();
      enemy.autoFire();
      //碰撞检验
      for (Bullet bullet : bullets) {
        //todo 拆子弹和敌方坦克比较
        if (bullet.collideWith(enemy)) {
          //如果碰撞，形成爆炸
          explodes.add(new Explode(enemy.x + enemy.WIDTH / 2 - Explode.WIDTH / 2,
              enemy.y + enemy.HEIGHT / 2 - Explode.HEIGHT / 2));
          break;
        }
      }
    }
    //剔除越界的子弹
    this.rejectBullets();
    this.rejectTanks();
    this.rejectExplode();
  }
}
