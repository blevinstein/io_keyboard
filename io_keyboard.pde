import processing.serial.*;

import java.io.FileNotFoundException;
import java.util.function.BiConsumer;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

// When the user stops typing, after 10 seconds, show a suggestion for the next character
int DELAY = 10_000;

// The user enters [text]. When they make it match [goal], we will choose a new goal for
// them to practice.
String goal = "";
String text = "";

int lastInputMillis = 0;
// Flag set when the user asks for help
boolean showHelp = false;
List<String> practiceWords = new ArrayList();
Random random = new Random();
Serial port;
String serialBuffer = "";

// This is an abstraction to combine multiple keystrokes into a single output.
ChordInput chordInput = new ChordInput(new BiConsumer<Integer, Character>() {
  public void accept(Integer keyCode, Character keyChar) {
    if (keyChar > 0) {
      text += keyChar;
      lastInputMillis = millis();
      showHelp = false;
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

void keyPressed() {
  if (key == 'h' || key == 'H') {
    showHelp = true;
  }
  if (key == 'n' || key == 'N') {
    nextWord();
  }
  chordInput.keyPressed(keyCode);
}

void keyReleased() {
  chordInput.keyReleased(keyCode);
}

void settings() {
  size(640, 480);
}

void setup() {
  surface.setResizable(true);

  printArray(Serial.list());
  try {
    port = new Serial(this, Serial.list()[2], 9600);
  } catch (Exception e) {
    System.err.println("Failed to connect Serial port");
  }

  try {
    HashSet leftHandLetters = new HashSet();
    leftHandLetters.add('r');
    leftHandLetters.add('n');
    leftHandLetters.add('o');
    leftHandLetters.add('t');
    leftHandLetters.add('l');
    leftHandLetters.add('u');
    HashSet rightHandLetters = new HashSet();
    rightHandLetters.add('e');
    rightHandLetters.add('a');
    rightHandLetters.add('i');
    rightHandLetters.add('s');
    rightHandLetters.add('h');
    rightHandLetters.add('d');
    rightHandLetters.add('c');
    rightHandLetters.add('w');
    rightHandLetters.add('m');
    //practiceWords = Words.getPracticeWords(rightHandLetters);
    //practiceWords = Words.getPracticeWords(leftHandLetters);
    //practiceWords = Words.getAllWords();
    practiceWords = Words.getEasyWords(8);
  } catch (FileNotFoundException e) {
    throw new RuntimeException(e);
  }
  // add some numbers as well, skewed towards smaller numbers
  for (int i = 0; i < practiceWords.size() / 3; i++) {
    practiceWords.add("" + round(exp(i / 10000)));
  }
}

// Choose a new word for the user to practice
void nextWord() {
  goal = practiceWords.get(random.nextInt(practiceWords.size()));
  text = "";
  if (port != null) {
    port.write(goal);
  }
}

void draw() {
  // Check for success
  if (text.equals(goal)) {
    nextWord();
  }

  noStroke();
  fill(0);
  rect(0, 0, width, height);
  fill(255);
  textAlign(LEFT);
  textSize(20);
  text(goal, 0, 15);
  text(text + "_", 0, 35);

  int nextIndex = 0;
  while (nextIndex < goal.length()
      && nextIndex < text.length()
      && goal.charAt(nextIndex) == text.charAt(nextIndex)) nextIndex++;
  if (showHelp || millis() - lastInputMillis > DELAY) {
    drawKey(goal.charAt(nextIndex));
  }

  if (port.available() > 0) {
    String inputString = port.readString();
    serialBuffer += inputString;
    while (serialBuffer.indexOf("\n") >= 0) {
      int newline = serialBuffer.indexOf("\n");
      if (newline > 0 && serialBuffer.charAt(newline - 2) == '_') {
        text = serialBuffer.substring(0, newline - 2);
        lastInputMillis = millis();
        showHelp = false;
      } else {
        System.err.println(
            String.format("Invalid line: '%s'", serialBuffer.substring(0, newline - 2)));
      }
      serialBuffer = serialBuffer.substring(newline + 1);
    }
  }
}

// Displays on the screen the button combination necessary to generate the given character
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
    ellipse(width * (i + (i < 4 ? 1f : 2f)) / 10, height / 2, width / 20, width / 20);
  }
}
