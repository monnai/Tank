package factory;

import entity.Bullet;
import entity.Explode;
import entity.abst.AbstractTank;
import enums.Direct;
import enums.Group;
import t1.TankFrame;

/**
 * @author gu.sc
 */
public abstract class AbstractFactory {

  AbstractFactory(TankFrame tf) {
    this.tf = tf;
  }

  /**
   * 创建子弹
   *
   * @return 子弹
   */
  public abstract Bullet createBullet();

  TankFrame tf;

  /**
   * tank的工厂
   *
   * @param x 横坐标
   * @param y 纵坐标
   * @param direct 方向
   * @param group 派别
   * @return 坦克
   */
  public abstract AbstractTank createTank(int x, int y, Direct direct, Group group);

  /**
   * 创建爆炸
   *
   * @return 爆炸
   */
  public abstract Explode createExplode();
}
