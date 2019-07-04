package exception;

public class illegalParameterException extends Exception{
	private static final long serialVersionUID = 1L;

	/**
	 * 构造方法
	 * 
	 * @param info 错误信息
	 */
	public illegalParameterException(String info) {
		super(info);
	}
}
