import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.io.FileNotFoundException; 
import java.util.function.BiConsumer; 
import java.util.HashSet; 
import java.util.List; 
import java.util.Random; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class io_keyboard extends PApplet {







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

public void keyPressed() {
  if (key == 'h' || key == 'H') {
    showHelp = true;
  }
  if (key == 'n' || key == 'N') {
    nextWord();
  }
  chordInput.keyPressed(keyCode);
}

public void keyReleased() {
  chordInput.keyReleased(keyCode);
}

public void settings() {
  size(640, 480);
}

public void setup() {
  surface.setResizable(true);

  try {
    /*
    HashSet practiceChars = new HashSet();
    practiceChars.add('a');
    practiceChars.add('e');
    practiceChars.add('i');
    practiceChars.add('o');
    practiceChars.add('u');
    practiceChars.add('t');
    practiceChars.add('n');
    practiceChars.add('s');
    practiceWords = Words.getPracticeWords(practiceChars);
    */
    practiceWords = Words.getAllWords();
  } catch (FileNotFoundException e) {
    throw new RuntimeException(e);
  }
  // add some numbers as well, skewed towards smaller numbers
  for (int i = 0; i < 100000; i++) {
    practiceWords.add("" + round(exp(i / 10000)));
  }
}

// Choose a new word for the user to practice
public void nextWord() {
  goal = practiceWords.get(random.nextInt(practiceWords.size()));
  text = "";
}

public void draw() {
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
public void drawKey(char keyChar) {
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
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "io_keyboard" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
