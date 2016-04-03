IO Keyboard
===========

From left to right, your fingers:

- L4 (left pinky)
- L3 (left ring finger)
- L2 (left middle finger)
- L1 (left index finger)
- R1 (right index finger)
- R2 (right middle finger
- R3 (right ring finger)
- R4 (right pinky)

To use the keyboard, place your hands on the home row: [ASDF] and [JKL;]

For each stroke, press down one or more keys, then release them all. The
order in which you press the different keys should* have no effect, and will
not take effect until you release ALL keys.

TODO: Ignore when L4 is pressed, allow it to be held over multiple strokes.

Codes:

In approximately decreasing order by letter frequency
https://en.wikipedia.org/wiki/Letter_frequency

Common letters get the (approximately) simplest key codes. The left pinky (L4)
is reserved as a modifier, which makes letters uppercase (more uses to come?).

NOTE: My definition of [simple] is totally arbitrary. This scheme is plausible,
in that the most common letters require the least work, and it can permit
letters (lower and upper case), numbers, and some amount of punctuation.
However, I made some *very* questionable decisions that could be improved. In
particular, it would be easier to remember the numbers if the sequence 0-9 had a
recognizable pattern or sequence, but it looks basically arbitrary to me.
TODO: Make the numbers look morel like binary?

Excluding L4 as a modifier, we have the following:

    1-finger codes (7)
    2-finger codes (7 choose 2 = 21)
    3-finger codes (7 choose 3 = 35)
    *** Unreliable past this point
    4-finger codes (7 choose 4 = 35)
    5-finger codes (7 choose 5 = 21)
    6-finger codes (7 choose 6 = 7)

*** Awkward limitation: when you try to press more than 3 keys at a time on a
normal keyboard, you may not get accurate key events. So codes requiring more
than 3 fingers may not work, in this POC.

So, we have 7 + 21 + 35 = 63 usable encodings

    26 letters
    10 digits

    e = R1           [ ]  [ ][ ][ ]    [X][ ][ ][ ]
    t = L1           [ ]  [ ][ ][X]    [ ][ ][ ][ ]
    a = R2           [ ]  [ ][ ][ ]    [ ][X][ ][ ]
    o = L2           [ ]  [ ][X][ ]    [ ][ ][ ][ ]
    i = R3           [ ]  [ ][ ][ ]    [ ][ ][X][ ]
    n = L3           [ ]  [X][ ][ ]    [ ][ ][ ][ ]
    s = R4           [ ]  [ ][ ][ ]    [ ][ ][ ][X]
    h = R1 R2        [ ]  [ ][ ][ ]    [X][X][ ][ ]
    r = L1 L2        [ ]  [ ][X][X]    [ ][ ][ ][ ]
    d = R2 R3        [ ]  [ ][ ][ ]    [ ][X][X][ ]
    l = L2 L3        [ ]  [X][X][ ]    [ ][ ][ ][ ]
    c = R3 R4        [ ]  [ ][ ][ ]    [ ][ ][X][X]
    u = L1 L3        [ ]  [X][ ][X]    [ ][ ][ ][ ]
    m = R1 R4        [ ]  [ ][ ][ ]    [X][ ][ ][X]
    w = R1 R3        [ ]  [ ][ ][ ]    [X][ ][X][ ]
    f = R1 L2        [ ]  [ ][X][ ]    [X][ ][ ][ ]
    g = R2 L1        [ ]  [ ][ ][X]    [ ][X][ ][ ]
    y = R1 L3        [ ]  [X][ ][ ]    [X][ ][ ][ ]
    p = R3 L1        [ ]  [ ][ ][X]    [ ][ ][X][ ]
    b = R4 L1        [ ]  [ ][ ][X]    [ ][ ][ ][X]
    v = R2 L2        [ ]  [ ][X][ ]    [ ][X][ ][ ]
    k = R2 L3        [ ]  [X][ ][ ]    [ ][X][ ][ ]
    j = R3 L2        [ ]  [ ][X][ ]    [ ][ ][X][ ]
    x = R4 L2        [ ]  [ ][X][ ]    [ ][ ][ ][X]
    q = R3 L3        [ ]  [X][ ][ ]    [ ][ ][X][ ]
    z = R4 L3        [ ]  [X][ ][ ]    [ ][ ][ ][X]

    (for upper case, add left pinky L4)

    0 = R2 R4        [ ]  [ ][ ][ ]    [ ][X][ ][X]
    1 = R1 R2 R3     [ ]  [ ][ ][ ]    [X][X][X][ ]
    2 = L1 L2 L3     [ ]  [X][X][X]    [ ][ ][ ][ ]
    3 = R2 R3 R4     [ ]  [ ][ ][ ]    [ ][X][X][X]
    4 = R1 L1 L2     [ ]  [ ][X][X]    [X][ ][ ][ ]
    5 = R1 R2 L1     [ ]  [ ][ ][X]    [X][X][ ][ ]
    6 = R1 L1 L3     [ ]  [X][ ][X]    [X][ ][ ][ ]
    7 = R1 R3 L1     [ ]  [ ][ ][X]    [X][ ][X][ ]
    8 = R2 L2 L3     [ ]  [X][X][ ]    [ ][X][ ][ ]
    9 = R2 R3 L2     [ ]  [ ][X][ ]    [ ][X][X][ ]

    TODO: Add modifier + numbers => symbols

    space = R1 L1    [ ]  [ ][ ][X]    [X][ ][ ][ ]
    .     = R3 R4 L3 [ ]  [X][ ][ ]    [ ][ ][X][X]

    TODO: add more punctuation

Backspace/delete works normally
TODO: reassign to a 3-finger code

