
import java.awt.event.KeyEvent;
import java.util.function.BiConsumer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ChordInput {
  public static final int L1 = KeyEvent.VK_F;
  public static final int L2 = KeyEvent.VK_D;
  public static final int L3 = KeyEvent.VK_S;
  public static final int L4 = KeyEvent.VK_A;
  public static final int R1 = KeyEvent.VK_J;
  public static final int R2 = KeyEvent.VK_K;
  public static final int R3 = KeyEvent.VK_L;
  public static final int R4 = KeyEvent.VK_SEMICOLON;

  public static final char NONE = (char) 0;

  public static Map<Set<Integer>, Pair<Integer, Character>> buildMapping() {
    HashMap<Set<Integer>, Pair<Integer, Character>> mapping = new HashMap<>();

    addToMapping(mapping, KeyEvent.VK_SPACE, ' ', R1, L1);

    addToMapping(mapping, KeyEvent.VK_PERIOD, '.', R3, R4, L3);

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

    addToMapping(mapping, KeyEvent.VK_E, 'E', R1, L4);
    addToMapping(mapping, KeyEvent.VK_T, 'T', L1, L4);
    addToMapping(mapping, KeyEvent.VK_A, 'A', R2, L4);
    addToMapping(mapping, KeyEvent.VK_O, 'O', L2, L4);
    addToMapping(mapping, KeyEvent.VK_I, 'I', R3, L4);
    addToMapping(mapping, KeyEvent.VK_N, 'N', L3, L4);
    addToMapping(mapping, KeyEvent.VK_S, 'S', R4, L4);
    addToMapping(mapping, KeyEvent.VK_H, 'H', R1, R2, L4);
    addToMapping(mapping, KeyEvent.VK_R, 'R', L1, L2, L4);
    addToMapping(mapping, KeyEvent.VK_D, 'D', R2, R3, L4);
    addToMapping(mapping, KeyEvent.VK_L, 'L', L2, L3, L4);
    addToMapping(mapping, KeyEvent.VK_C, 'C', R3, R4, L4);
    addToMapping(mapping, KeyEvent.VK_U, 'U', L1, L3, L4);
    addToMapping(mapping, KeyEvent.VK_M, 'M', R1, R4, L4);
    addToMapping(mapping, KeyEvent.VK_W, 'W', R1, R3, L4);
    addToMapping(mapping, KeyEvent.VK_F, 'F', R1, L2, L4);
    addToMapping(mapping, KeyEvent.VK_G, 'G', R2, L1, L4);
    addToMapping(mapping, KeyEvent.VK_Y, 'Y', R1, L3, L4);
    addToMapping(mapping, KeyEvent.VK_P, 'P', R3, L1, L4);
    addToMapping(mapping, KeyEvent.VK_B, 'B', R4, L1, L4);
    addToMapping(mapping, KeyEvent.VK_V, 'V', R2, L2, L4);
    addToMapping(mapping, KeyEvent.VK_K, 'K', R2, L3, L4);
    addToMapping(mapping, KeyEvent.VK_J, 'J', R3, L2, L4);
    addToMapping(mapping, KeyEvent.VK_X, 'X', R4, L2, L4);
    addToMapping(mapping, KeyEvent.VK_Q, 'Q', R3, L3, L4);
    addToMapping(mapping, KeyEvent.VK_Z, 'Z', R4, L3, L4);

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

  // Returns list of [L4, L3, L2, L1, R1, R2, R3, R4] states
  public List<Boolean> reverseLookup(char keyChar) {
    for (Map.Entry<Set<Integer>, Pair<Integer, Character>> entry : mapping.entrySet()) {
      if (entry.getValue().getSecond() == keyChar) {
        Set<Integer> chord = entry.getKey();
        List<Boolean> result = new ArrayList<>();
        result.add(chord.contains(L4));
        result.add(chord.contains(L3));
        result.add(chord.contains(L2));
        result.add(chord.contains(L1));
        result.add(chord.contains(R1));
        result.add(chord.contains(R2));
        result.add(chord.contains(R3));
        result.add(chord.contains(R4));
        return result;
      }
    }
    return null;
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
        System.err.println(String.format("Unrecognized chord: %s", chordKeys));
      }
      chordKeys.clear();
    }
  }
}

