package exception;

public class illegalTextGrammarException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * 构造方法
	 * 
	 * @param info 错误信息
	 */
	public illegalTextGrammarException(String info) {
		super(info);
	}

}
