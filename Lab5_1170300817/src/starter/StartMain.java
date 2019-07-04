package starter;

import applications.atomstructure.AtomGame;
import applications.socialnetworkcircle.SocialNetworkGame;
import applications.trackgamecircular.TrackGame;
import exception.ObjectNoFoundException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class StartMain {
  /**
   * 菜单函数.
   */
  public static void gamemenu() {
    System.out.println("1.\tTrackGame");
    System.out.println("2.\tAtomStructure");
    System.out.println("3.\tSocialNetworkCircle");
    System.out.println("end.\t 结束");
  }

  /**
   * 启动三个具体功能的函数.
   * 
   * @param args args
   * @throws IOException 读写异常
   * @throws ObjectNoFoundException 未找到文件错误
   */
  public static void main(String[] args) throws IOException, ObjectNoFoundException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    boolean exitflag = false;
    while (true) {
      gamemenu();
      String input = reader.readLine();
      if (input != null) {
        input = input.trim();
      } else {
        continue;
      }
      switch (input) {
        case "1":
          new TrackGame().gameMain();
          break;
        case "2":
          new AtomGame().gameMain();
          break;
        case "3":
          new SocialNetworkGame().gameMain();
          break;
        case "end":// 结束游戏
          exitflag = true;
          break;
        default:
          System.out.println("输入错误");
          break;
      }
      if (exitflag) {
        break;
      }
    }
  }

}
