package aomine.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public class GoatList<T> extends ArrayList<T> {
  public GoatList() {
  }

  public GoatList(List<T> list) {
    super(list);
  }

  @SafeVarargs
  public GoatList(T... items) {
    for (T item : items)
      add(item);
  }

  public GoatList<T> filter(Predicate<T> predicate) {
    GoatList<T> filteredList = new GoatList<>();

    for (T item : this) {
      if (predicate.test(item))
        filteredList.add(item);
    }

    return filteredList;
  }

  public GoatList<T> filter(BiPredicate<T, Integer> predicate) {
    GoatList<T> filteredList = new GoatList<>();

    for (int i = 0; i < size(); i++) {
      T item = get(i);

      if (predicate.test(item, i))
        filteredList.add(item);
    }

    return filteredList;
  }

  public GoatList<T> map(UnaryOperator<T> operator) {
    GoatList<T> mappedList = new GoatList<>();

    for (T item : this) {
      mappedList.add(operator.apply(item));
    }

    return mappedList;
  }

  public GoatList<T> map(BiFunction<T, Integer, T> fucntion) {
    GoatList<T> mappedList = new GoatList<>();

    for (int i = 0; i < size(); i++) {
      mappedList.add(fucntion.apply(get(i), i));
    }

    return mappedList;
  }

  public <U> GoatList<U> mapDiff(Function<T, U> fucntion) {
    GoatList<U> mappedList = new GoatList<>();

    for (T item : this) {
      mappedList.add(fucntion.apply(item));
    }

    return mappedList;
  }

  public <U> GoatList<U> mapDiff(BiFunction<T, Integer, U> fucntion) {
    GoatList<U> mappedList = new GoatList<>();

    for (int i = 0; i < size(); i++) {
      mappedList.add(fucntion.apply(get(i), i));
    }

    return mappedList;
  }

  public void forEach(BiConsumer<T, Integer> consumer) {
    for (int i = 0; i < size(); i++) {
      consumer.accept(get(i), i);
    }
  }
}
