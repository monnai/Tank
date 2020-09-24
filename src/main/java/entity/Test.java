package entity;

/**
 * @author gu.sc
 */
public class Test {
  @org.junit.Test
  public void test() {
    outer:
    for (int i = 0; i < 10; i++) {

      for (int j = 0; j < 3; j++) {
        if(j==0) {
          break outer;
        }
        System.out.println(1);
      }
      System.out.println(2);
    }
  }

  public void x() {
    if (1 == 2) {
      return ;
    }
  }

}
