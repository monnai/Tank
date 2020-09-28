package model;

import collider.BulletTankCollider;
import collider.ColliderChain;
import collider.TankTankCollider;
import collider.WallBulletCollider;
import conf.TankConf;
import entity.BaseGameObject;
import entity.Tank;
import entity.Wall;
import enums.Direct;
import enums.Group;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * TankFrame的中介者，大管家 管理tf的吃喝拉撒 提供tf开始调用的门面
 *
 * @author gu.sc
 */
public class GameModel {

  /**
   * 单例GameModel
   */
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

  private Tank tank;

  public Tank getTank() {
    return this.tank;
  }

  /**
   * 存放所有的物体包括tank bullet explode wall等等
   */
  private ArrayList<BaseGameObject> gos = new ArrayList<>();

  /**
   * 为其他模块提供gos的访问
   *
   * @return gos
   */
  public ArrayList<BaseGameObject> getGos() {
    return this.gos;
  }

  /**
   * 初始化完成标识
   */
  private AtomicBoolean initialized = new AtomicBoolean(false);
  /**
   * 判断碰撞的责任链
   */
  private ColliderChain colliderChain = new ColliderChain();

  public void init() {
    if (!initialized.get()) {
      tank = new Tank(200, 400, Direct.down, Group.GOOD);
      tank.moving = false;
      String number = "tank.number";
      for (int i = 0; i < TankConf.getInt(number); i++) {
        this.gos.add(new Tank(50 + i * 80, 200, Direct.down, Group.BAD));
      }
      //加入墙
//      Wall wall = new Wall(33, 55, 124, 20);
//      this.gos.add(wall);
      this.gos.add(tank);
      initialized.set(true);
      colliderChain.add(new BulletTankCollider());
      colliderChain.add(new TankTankCollider());
//      colliderChain.add(new WallBulletCollider());
    }
  }

  /**
   * update的时候会自动调用，用于画出物体。并在其中加入了碰撞和移除控制
   *
   * @param g 默认传入的graphics
   */
  public void paint(Graphics g) {
    Color before = g.getColor();
    g.setColor(Color.RED);
    g.setColor(before);
    //画出主战tank
    //画出所有的敌人，子弹，墙等，他们在new的时候会自动加入gos或者在gm初始化时手动add的
    for (int i = 0; i < gos.size(); i++) {
      gos.get(i).paint(g);
    }
    //碰撞验证
    for (int i = 0; i < gos.size() - 1; i++) {
      for (int j = i + 1; j < gos.size(); j++) {
        colliderChain.collide(gos.get(i), gos.get(j));
      }
    }
    //去除失效的物体：包括 tank bullet explode
    reject();
  }


  /**
   * 移除失效的物体
   */
  private void reject() {
    this.gos.removeIf(BaseGameObject::showReject);
  }
}
