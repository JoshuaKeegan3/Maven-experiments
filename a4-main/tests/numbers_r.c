#include <assert.h>
#include <avr/io.h>
#include "tinyboy.h"

// A method which pushes up the stack depth.
int gadget(int v) {
  int tmp[16] = {0,0,0,-1,0,0,0,-1};
  return tmp[v];
}

void setup() {
  // set SCLK, MOSI, MISO, SS to be output
  DDRB = 0b00001111;
  PORTB = 0b00000000;
}

int value = 0;
int base = 1;

int get_digit(int code, int b) {
  int base = 10000;
  //
  for(int i=0;i<5;++i) {
    int d = code / base;
    if(base == b) {
      return d;
    }
    code = code - (d * base);
    base = base / 10;
  }
  assert(1 != 1);
  // Should be impossible to get here
  return 0;
}

void update_value() {
  int b = read_buttons();
  //
  if(b == BUTTON_DOWN) {
    if(get_digit(value,base) == 0) {
      value = value + (9 * base);
    } else {
      value = value - base;
    }
    assert(value >= 0 && value < 1000);
  } else if(b == BUTTON_UP) { 
    if(get_digit(value,base) == 9) {
      value = value - (9 * base);
    } else {
      value = value + base;
    }
  } else if(base > 1 && (b == BUTTON_RIGHT)) {
    base = base / 10;
  } else if(base < 100 && (b == BUTTON_LEFT)) { 
    base = base * 10;
  }
  //assert(0 <= value && value <= 10000);
}

int main() {
  // Setup stuff
  setup();
  //
  while(value != 99) {
    // read buttons and change value
    update_value();
  }
  //
  return gadget(value%8);
}
