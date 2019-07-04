package applications.AtomStructure;

import java.util.Stack;

/**
 * @author Administrator 管理备忘录的类
 */
public class TransitCareTaker {
	private Stack<Memento> mementos = new Stack<>();

	/**
	 * 构造方法
	 * 
	 * @param e
	 */
	public void addMemento(Memento e) {
		mementos.push(e);
	}

	/**
	 * 恢复，出栈第一个mementos
	 * 
	 * @return
	 */
	public Memento getMemento() {
		if (!mementos.empty()) {
			return mementos.pop();
		} else {
			return null;
		}
	}

}
