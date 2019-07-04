package applications.atomstructure;

import java.util.Stack;

// 备忘录模式实现之一
public class TransitCareTaker {
  private Stack<Memento> mementos = new Stack<>();

  /**
   * 构造方法.
   * 
   * @param e 增加的备忘录
   */
  public void addMemento(Memento e) {
    mementos.push(e);
  }

  /**
   * 恢复，出栈第一个mementos.
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
