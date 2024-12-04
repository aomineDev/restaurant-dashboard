package aomine.utils;

import java.util.Iterator;
import java.util.Stack;

public class PrevNext<E> implements Iterable<E> {
  private final Stack<E> stack1;
  private final Stack<E> stack2;
  
  public PrevNext() {
    stack1 = new Stack<>();
    stack2 = new Stack<>();
  }

  public void add(E item) {
    stack1.push(item);
    stack2.clear();
  }

  public E prev() {
    if (stack1.size() > 1) {
      stack2.push(stack1.pop());

      return stack1.get(stack1.size() - 1);
    }

    return null;
  }

  public E next() {
    if (stack2.isEmpty()) return null;

    E item = stack2.pop();
    stack1.push(item);

    return item;
  }

  public E getCurrent() {
    if (stack1.isEmpty()) return null;
    
    return stack1.get(stack1.size() - 1);
  }

  public boolean isPrevAble() {
    return stack1.size() > 1;
  }

  public boolean isNextAble() {
    return !stack2.isEmpty();
  }

  public void clear() {
    stack1.clear();
    stack2.clear();
  }

  public void clearNext() {
    stack2.clear();
  }

  @Override
  public Iterator<E> iterator() {
    return new MyIterator();
  }

  private class MyIterator implements Iterator<E> {
    private int index = 0;

    @Override
    public boolean hasNext() {
      if (index < stack1.size()) return true;
      else if (index < stack1.size() + stack2.size())  return true;
      else return false;
    }

    @Override
    public E next() {
      if (index < stack1.size()) {
        return stack1.elementAt(index++);
      } else {
        return stack2.elementAt((index++) - stack1.size());
      }
    }
  }
}
