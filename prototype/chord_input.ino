typedef struct ChordInput {
  // TODO: Think about compressing [pressed] and [chord] into a single byte each
  bool pressed[8];
  bool chord[8];
  char mapping[256];
  void (*callback)(char);
} ChordInput;

struct ChordInput *newChordInput(void callback(char)) {
  ChordInput *chordInput = (ChordInput*) malloc(sizeof(ChordInput));
  int i;
  for (i = 0; i < 8; i++) {
    chordInput->pressed[i] = false;
    chordInput->chord[i] = false;
  }
  for (i = 0; i < 256; i++) {
    chordInput->mapping[i] = 0;
  }
  chordInput->callback = callback;

  int L4 = 0x80, L3 = 0x40, L2 = 0x20, L1 = 0x10, R1 = 0x08, R2 = 0x04, R3 = 0x02, R4 = 0x01;

  chordInput->mapping[R1] = 'e';
  chordInput->mapping[L1] = 't';
  chordInput->mapping[R2] = 'a';
  chordInput->mapping[L2] = 'o';
  chordInput->mapping[R3] = 'i';
  chordInput->mapping[L3] = 'n';
  chordInput->mapping[R4] = 's';
  chordInput->mapping[R1 | R2] = 'h';
  chordInput->mapping[L1 | L2] = 'r';
  chordInput->mapping[R2 | R3] = 'd';
  chordInput->mapping[L2 | L3] = 'l';
  chordInput->mapping[R3 | R4] = 'c';
  chordInput->mapping[L1 | L3] = 'u';
  chordInput->mapping[R1 | R4] = 'm';
  chordInput->mapping[R1 | R3] = 'w';
  chordInput->mapping[R1 | L2] = 'f';
  chordInput->mapping[R2 | L1] = 'g';
  chordInput->mapping[R1 | L3] = 'y';
  chordInput->mapping[R3 | L1] = 'p';
  chordInput->mapping[R4 | L1] = 'b';
  chordInput->mapping[R2 | L2] = 'v';
  chordInput->mapping[R2 | L3] = 'k';
  chordInput->mapping[R3 | L2] = 'j';
  chordInput->mapping[R4 | L2] = 'x';
  chordInput->mapping[R3 | L3] = 'q';
  chordInput->mapping[R4 | L3] = 'z';

  chordInput->mapping[R1 | L4] = 'E';
  chordInput->mapping[L1 | L4] = 'T';
  chordInput->mapping[R2 | L4] = 'A';
  chordInput->mapping[L2 | L4] = 'O';
  chordInput->mapping[R3 | L4] = 'I';
  chordInput->mapping[L3 | L4] = 'N';
  chordInput->mapping[R4 | L4] = 'S';
  chordInput->mapping[R1 | R2 | L4] = 'H';
  chordInput->mapping[L1 | L2 | L4] = 'R';
  chordInput->mapping[R2 | R3 | L4] = 'D';
  chordInput->mapping[L2 | L3 | L4] = 'L';
  chordInput->mapping[R3 | R4 | L4] = 'C';
  chordInput->mapping[L1 | L3 | L4] = 'U';
  chordInput->mapping[R1 | R4 | L4] = 'M';
  chordInput->mapping[R1 | R3 | L4] = 'W';
  chordInput->mapping[R1 | L2 | L4] = 'F';
  chordInput->mapping[R2 | L1 | L4] = 'G';
  chordInput->mapping[R1 | L3 | L4] = 'Y';
  chordInput->mapping[R3 | L1 | L4] = 'P';
  chordInput->mapping[R4 | L1 | L4] = 'B';
  chordInput->mapping[R2 | L2 | L4] = 'V';
  chordInput->mapping[R2 | L3 | L4] = 'K';
  chordInput->mapping[R3 | L2 | L4] = 'J';
  chordInput->mapping[R4 | L2 | L4] = 'X';
  chordInput->mapping[R3 | L3 | L4] = 'Q';
  chordInput->mapping[R4 | L3 | L4] = 'Z';

  chordInput->mapping[R1 | R2 | R3 | R4] = '\n';
  chordInput->mapping[R1  | R2 | R3 | L1] = '\b';
  chordInput->mapping[R1 | L1] = ' ';
  chordInput->mapping[R3 | R4 | L3] = '.';

  chordInput->mapping[R2 | R4] = '0';
  chordInput->mapping[R1 | R2 | R3] = '1';
  chordInput->mapping[L1 | L2 | L3] = '2';
  chordInput->mapping[R2 | R3 | R4] = '3';
  chordInput->mapping[R1 | L1 | L2] = '4';
  chordInput->mapping[R1 | R2 | L1] = '5';
  chordInput->mapping[R1 | L1 | L3] = '6';
  chordInput->mapping[R1 | R3 | L1] = '7';
  chordInput->mapping[R2 | L2 | L3] = '8';
  chordInput->mapping[R2 | R3 | L2] = '9';

  return chordInput;
}

void dumpState(ChordInput *chordInput) {
  int i;
  Serial.print("pressed ");
  for (i = 0; i < 8; i++) {
    Serial.print(chordInput->pressed[i]);
    Serial.print(" ");
  }
  Serial.println();
  Serial.print("chord   ");
  for (i = 0; i < 8; i++) {
    Serial.print(chordInput->chord[i]);
    Serial.print(" ");
  }
  Serial.println();
}

void chordKeyPressed(ChordInput *chordInput, int key) {
  chordInput->pressed[key] = true;
  chordInput->chord[key] = true;
}

void chordKeyReleased(ChordInput *chordInput, int key) {
  chordInput->pressed[key] = false;

  bool pressedEmpty = true;
  int i;
  for (i = 0; i < 8; i++) {
    if (chordInput->pressed[i]) {
      pressedEmpty = false;
    }
  }
  if (pressedEmpty) {
    int input =
        chordInput->chord[0] * 128 +
        chordInput->chord[1] * 64 +
        chordInput->chord[2] * 32 +
        chordInput->chord[3] * 16 +
        chordInput->chord[4] * 8 +
        chordInput->chord[5] * 4 +
        chordInput->chord[6] * 2 +
        chordInput->chord[7] * 1;
    char output = chordInput->mapping[input];
    if (output) {
      chordInput->callback(output);
    }
    chordReset(chordInput);
    delay(100); // debounce
  }
}

int reverseLookup(ChordInput *chordInput, char ch) {
  int i;
  for (i = 0; i < 256; i++) {
    if (chordInput->mapping[i] == ch) {
      return i;
    }
  }
  return 0;
}

void chordReset(ChordInput *chordInput) {
  int i;
  for (i = 0; i < 8; i++) {
    chordInput->chord[i] = 0;
  }
}

