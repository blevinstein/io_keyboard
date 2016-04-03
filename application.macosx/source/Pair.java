
public class Pair<S, T> {
  private S first;
  private T second;

  // Syntactic sugar
  public static <S1, T1> Pair<S1, T1> of(S1 first, T1 second) {
    return new Pair(first, second);
  }

  public Pair(S first, T second) {
    this.first = first;
    this.second = second;
  }

  public S getFirst() { return first; }
  public T getSecond() { return second; }
}