import java.util.function.BiConsumer;

String buffer = "";

ChordInput chordInput = new ChordInput(new BiConsumer<Integer, Character>() {
  public void accept(Integer keyCode, Character keyChar) {
    if (keyChar > 0) {
      buffer += keyChar;
    } else {
      switch ((int)keyCode) {
        case BACKSPACE:
        case DELETE:
          if (buffer.length() > 0) {
            buffer = buffer.substring(0, buffer.length() - 1);
          }
          break;
      }
    }
  }
});

void settings() {
  size(640, 480);
}

void draw() {
  fill(0);
  rect(0, 0, width, height);
  fill(255);
  text(buffer, 0, 15);
}

void keyPressed() {
  chordInput.keyPressed(keyCode);
}

void keyReleased() {
  chordInput.keyReleased(keyCode);
}