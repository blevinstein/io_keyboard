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
    HashSet bothHandLetters = new HashSet();
    bothHandLetters.add('f');
    bothHandLetters.add('g');
    bothHandLetters.add('y');
    bothHandLetters.add('p');
    bothHandLetters.add('b');
    bothHandLetters.add('v');
    bothHandLetters.add('k');
    bothHandLetters.add('j');
    bothHandLetters.add('x');
    bothHandLetters.add('q');
    bothHandLetters.add('z');
    practiceWords = Words.getPracticeWords(bothHandLetters);
    //practiceWords = Words.getPracticeWords(rightHandLetters);
    //practiceWords = Words.getPracticeWords(leftHandLetters);
    //practiceWords = Words.getAllWords();
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
  text(text, 0, 35);

  int i = 0;
  while (i < goal.length() && i < text.length() && goal.charAt(i) == text.charAt(i)) i++;
  if (showHelp || millis() - lastInputMillis > DELAY) {
    drawKey(goal.charAt(i));
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