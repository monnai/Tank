package conf;

import java.io.IOException;
import java.util.Properties;

/**
 * @author gu.sc
 */
public class TankConf {

  private static Properties properties;

  private TankConf() {

  }

  static {
    properties = new Properties();
    try {
      properties.load(TankConf.class.getClassLoader().getResourceAsStream("conf/conf"));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static int getInt(String key) {
    return Integer.parseInt(properties.getProperty(key));
  }

}
