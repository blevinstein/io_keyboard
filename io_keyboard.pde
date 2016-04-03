import java.io.FileNotFoundException;
import java.util.function.BiConsumer;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

String goal = "";
String text = "";

List<String> practiceWords;
Random random = new Random();

ChordInput chordInput = new ChordInput(new BiConsumer<Integer, Character>() {
  public void accept(Integer keyCode, Character keyChar) {
    if (keyChar > 0) {
      text += keyChar;
    } else {
      switch ((int)keyCode) {
        case BACKSPACE:
        case DELETE:
          if (text.length() > 0) {
            text = text.substring(0, text.length() - 1);
          }
          break;
      }
    }
  }
});

void settings() {
  size(640, 480);
}

void setup() {
  HashSet practiceChars = new HashSet();
  practiceChars.add('a');
  practiceChars.add('e');
  practiceChars.add('i');
  practiceChars.add('o');
  practiceChars.add('u');
  practiceChars.add('t');
  practiceChars.add('n');
  practiceChars.add('s');
  try {
    practiceWords = Words.getPracticeWords(practiceChars);
    //practiceWords = Words.getAllWords();
  } catch (FileNotFoundException e) {
    throw new RuntimeException(e);
  }
}

void draw() {
  if (text.equals(goal)) {
    goal = practiceWords.get(random.nextInt(practiceWords.size()));
    text = "";
  }

  noStroke();
  fill(0);
  rect(0, 0, width, height);
  fill(255);
  textAlign(LEFT);
  textSize(20);
  text(goal, 0, 15);
  text(text, 0, 30);

  int i = 0;
  while (i < goal.length() && i < text.length() && goal.charAt(i) == text.charAt(i)) i++;
  drawKey(goal.charAt(i));
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
