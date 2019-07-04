package iostrategy;

import java.io.IOException;

public interface InputStrategy {

  /**
   * 当前策略的readline函数.
   *
   * @return readline结果
   * @throws IOException 文件错误
   */
  public String readLine() throws IOException;

  /**
   * 关闭流.
   */
  public void close() throws IOException;
}
