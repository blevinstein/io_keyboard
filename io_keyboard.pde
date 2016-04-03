import java.util.function.BiConsumer;
import java.util.List;

String buffer = "";
char lastChar = ' ';

ChordInput chordInput = new ChordInput(new BiConsumer<Integer, Character>() {
  public void accept(Integer keyCode, Character keyChar) {
    if (keyChar > 0) {
      buffer += keyChar;
      lastChar = keyChar;
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
  noStroke();
  fill(0);
  rect(0, 0, width, height);
  fill(255);
  textAlign(LEFT);
  textSize(12);
  text(buffer, 0, 15);
  drawKey(lastChar);
}

void drawKey(char keyChar) {
  List<Boolean> fingers = chordInput.reverseLookup(keyChar);
  for (int i = 0; i < 8; i++) {
    stroke(0, 255, 0);
    if (fingers.get(i)) {
      fill(0, 255, 0);
    } else {
      fill(0);
    }
    ellipseMode(CENTER);
    ellipse(width * (i + 1f) / 9, height / 2, width / 18, width / 18);
  }
  fill(0, 255, 0);
  textAlign(CENTER);
  textSize(26);
  text("\"" + keyChar + "\"", width / 18, height / 2);
}

void keyPressed() {
  chordInput.keyPressed(keyCode);
}

void keyReleased() {
  chordInput.keyReleased(keyCode);
}