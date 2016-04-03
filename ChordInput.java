
import java.awt.event.KeyEvent;
import java.util.function.BiConsumer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ChordInput {
  public static final int L1 = KeyEvent.VK_F;
  public static final int L2 = KeyEvent.VK_D;
  public static final int L3 = KeyEvent.VK_S;
  public static final int SHIFT = KeyEvent.VK_A;
  public static final int R1 = KeyEvent.VK_J;
  public static final int R2 = KeyEvent.VK_K;
  public static final int R3 = KeyEvent.VK_L;
  public static final int R4 = KeyEvent.VK_SEMICOLON;
  
  public static final char NONE = (char) 0;
  
  public static Map<Set<Integer>, Pair<Integer, Character>> buildMapping() {
    HashMap<Set<Integer>, Pair<Integer, Character>> mapping = new HashMap<>();
    
    addToMapping(mapping, KeyEvent.VK_SPACE, ' ', R1, L1);
    
    addToMapping(mapping, KeyEvent.VK_E, 'e', R1);
    addToMapping(mapping, KeyEvent.VK_T, 't', L1);
    addToMapping(mapping, KeyEvent.VK_A, 'a', R2);
    addToMapping(mapping, KeyEvent.VK_O, 'o', L2);
    addToMapping(mapping, KeyEvent.VK_I, 'i', R3);
    addToMapping(mapping, KeyEvent.VK_N, 'n', L3);
    addToMapping(mapping, KeyEvent.VK_S, 's', R4);
    addToMapping(mapping, KeyEvent.VK_H, 'h', R1, R2);
    addToMapping(mapping, KeyEvent.VK_R, 'r', L1, L2);
    addToMapping(mapping, KeyEvent.VK_D, 'd', R2, R3);
    addToMapping(mapping, KeyEvent.VK_L, 'l', L2, L3);
    addToMapping(mapping, KeyEvent.VK_C, 'c', R3, R4);
    addToMapping(mapping, KeyEvent.VK_U, 'u', L1, L3);
    addToMapping(mapping, KeyEvent.VK_M, 'm', R1, R4);
    addToMapping(mapping, KeyEvent.VK_W, 'w', R1, R3);
    addToMapping(mapping, KeyEvent.VK_F, 'f', R1, L2);
    addToMapping(mapping, KeyEvent.VK_G, 'g', R2, L1);
    addToMapping(mapping, KeyEvent.VK_Y, 'y', R1, L3);
    addToMapping(mapping, KeyEvent.VK_P, 'p', R3, L1);
    addToMapping(mapping, KeyEvent.VK_B, 'b', R4, L1);
    addToMapping(mapping, KeyEvent.VK_V, 'v', R2, L2);
    addToMapping(mapping, KeyEvent.VK_K, 'k', R2, L3);
    addToMapping(mapping, KeyEvent.VK_J, 'j', R3, L2);
    addToMapping(mapping, KeyEvent.VK_X, 'x', R4, L2);
    addToMapping(mapping, KeyEvent.VK_Q, 'q', R3, L3);
    addToMapping(mapping, KeyEvent.VK_Z, 'z', R4, L3);
    
    addToMapping(mapping, KeyEvent.VK_0, '0', R2, R4);
    addToMapping(mapping, KeyEvent.VK_1, '1', R1, R2, R3);
    addToMapping(mapping, KeyEvent.VK_2, '2', L1, L2, L3);
    addToMapping(mapping, KeyEvent.VK_3, '3', R2, R3, R4);
    addToMapping(mapping, KeyEvent.VK_4, '4', R1, L1, L2);
    addToMapping(mapping, KeyEvent.VK_5, '5', R1, R2, L1);
    addToMapping(mapping, KeyEvent.VK_6, '6', R1, L1, L3);
    addToMapping(mapping, KeyEvent.VK_7, '7', R1, R3, L1);
    addToMapping(mapping, KeyEvent.VK_8, '8', R2, L2, L3);
    addToMapping(mapping, KeyEvent.VK_9, '9', R2, R3, L2);
    
    addToMapping(mapping, KeyEvent.VK_DELETE, NONE, KeyEvent.VK_DELETE);
    addToMapping(mapping, KeyEvent.VK_BACK_SPACE, NONE, KeyEvent.VK_BACK_SPACE);
    
    return mapping;
  }
  
  private static void addToMapping(
      HashMap<Set<Integer>, Pair<Integer, Character>> mapping,
      int keyCode,
      char keyChar,
      int... keyCodes) {
    Set<Integer> input = new HashSet<>();
    for (int inputCode : keyCodes) {
      input.add(inputCode);
    }
    if (mapping.containsKey(input)) {
      throw new IllegalArgumentException("" + keyChar);
    }
    mapping.put(input, Pair.of(keyCode, keyChar));
  }
  
  private Map<Set<Integer>, Pair<Integer, Character>> mapping;
  private BiConsumer<Integer, Character> callback;
  
  private HashSet<Integer> pressedKeys = new HashSet<>();
  private HashSet<Integer> chordKeys = new HashSet<>();
  
  public ChordInput(BiConsumer<Integer, Character> callback) {
    this.mapping = buildMapping();
    this.callback = callback;
  }
  
  public void keyPressed(int keyCode) {
    pressedKeys.add(keyCode);
    chordKeys.add(keyCode);
  }
  
  public void keyReleased(int keyCode) {
    pressedKeys.remove(keyCode);
    if (pressedKeys.isEmpty()) {
      if (mapping.containsKey(chordKeys)) {
        Pair<Integer, Character> output = mapping.get(chordKeys);
        callback.accept(output.getFirst(), output.getSecond());
      } else {
        System.out.println(String.format("Unrecognized chord: %s", chordKeys));
      }
      chordKeys.clear();
    }
  }
}