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
  Serial.begin(9600);
}

bool pressed[8] = {false, false, false, false, false, false, false, false};
bool state[8] = {false, false, false, false, false, false, false, false};

void keyPressed(int i) {
  state[i] = true;
  setBuzzer(i, HIGH);
  Serial.print("keyPressed ");
  Serial.println(i);
}

void keyReleased(int i) {
  state[i] = false;
  setBuzzer(i, LOW);
  Serial.print("keyReleased ");
  Serial.println(i);
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

