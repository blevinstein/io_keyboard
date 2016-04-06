bool pressed[8] = {false, false, false, false, false, false, false, false};
bool state[8] = {false, false, false, false, false, false, false, false};
struct ChordInput *chordInput;
String buffer = "";

void setup() {
  pinMode(A0, INPUT);
  pinMode(A1, INPUT);
  pinMode(A2, INPUT);
  pinMode(A3, INPUT);
  pinMode(A4, INPUT);
  pinMode(A5, INPUT);
  pinMode(2, INPUT);
  pinMode(3, INPUT);
  int i;
  for (i = 4; i < 12; i++) {
    pinMode(i, OUTPUT);
  }
  // TODO: Instead of printing each char to serial, add to a buffer, and print whole buffer
  chordInput = newChordInput(&alertKey);
  Serial.begin(9600);
}

void keyPressed(int i) {
  state[i] = true;
  setBuzzer(i, HIGH);
  chordKeyPressed(chordInput, i);
}

void keyReleased(int i) {
  state[i] = false;
  setBuzzer(i, LOW);
  chordKeyReleased(chordInput, i);
}

void alertKey(char c) {
  if (c == '\n') {
    buffer = "";
  } else if (c == '\b') {
    buffer = buffer.substring(0, buffer.length() - 1);
  } else {
    buffer += c;
  }
  Serial.println(buffer + "_");
}

void loop() {
  int i;
  for (i = 0; i < 8; i++) {
    bool now = readButton(i);
    if (state[i] && !now) {
      keyReleased(i);
    } else if (!state[i] && now) {
      keyPressed(i);
    }
  }
  if (Serial.available() > 0) {
    buzzString(Serial.readString().c_str(), 800, 200);
  }
}

void buzzString(const char *str, int on, int off) {
  int i = 0;
  while (str[i]) {
    buzzCharacter(str[i], on);
    delay(off);
    i++;
  }
}

void buzzCharacter(char ch, int mill) {
  int inputValue = reverseLookup(chordInput, ch);
  if (inputValue) {
    int i;
    for (i = 0; i < 8; i++) {
      if (1 << (7 - i) & inputValue) {
        setBuzzer(i, HIGH);
      }
    }
    delay(mill);
    for (i = 0; i < 8; i++) {
      setBuzzer(i, LOW);
    }
  }
}

void setBuzzer(int i, int state) {
  digitalWrite(i + 4, state);
}

bool readButton(int i) {
  switch (i) {
    case 0:
        return digitalRead(A0);
    case 1:
        return digitalRead(A1);
    case 2:
        return digitalRead(A2);
    case 3:
        return digitalRead(A3);
    case 4:
        return digitalRead(A4);
    case 5:
        return digitalRead(A5);
    case 6:
        return digitalRead(2);
    case 7:
        return digitalRead(3);
  }
}

