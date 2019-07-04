package iostrategy;

import java.io.IOException;

public interface OutputStrategy {

  /**
   * 写入函数.
   *
   * @param string 写入的内容
   * @throws IOException 文件错误
   */
  public void write(String string) throws IOException;

  /**
   * 关闭流.
   */
  public void close() throws IOException;
}
