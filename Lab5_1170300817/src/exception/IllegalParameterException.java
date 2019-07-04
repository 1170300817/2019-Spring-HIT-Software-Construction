package exception;

public class IllegalParameterException extends Exception {
  private static final long serialVersionUID = 1L;

  /**
   * 构造方法.
   * 
   * @param info 错误信息
   */
  public IllegalParameterException(String info) {
    super(info);
  }
}
