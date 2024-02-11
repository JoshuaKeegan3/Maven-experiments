#include <avr/io.h>
#include "tinyboy.h"

#define WIDTH (SCREEN_WIDTH >> 3)
#define HEIGHT (SCREEN_HEIGHT >> 3)

#define TELEPORT_X 5
#define TELEPORT_Y 5

// =========================================================
// STATE
// =========================================================

#define NROCKS 2

int player_x;
int player_y;
int rock_x[4] = {1,2,6,4};
int rock_y[4] = {1,4,2,4};

// =========================================================
// IO Functions
// =========================================================

#define WHITE 0x00
#define GREY  0b10000001
#define BLACK 0xFF

int getRock(int x, int y) {
  for(int i=0;i!=NROCKS;++i) {
    if(rock_x[i] == x && rock_y[i] == y) {
      return i;
    }
  }
  return -1;
}

// A method which pushes up the stack depth.
int gadget(int v) {
  int tmp[8] = {0,0,0,0,0,-1,0,0};
  return tmp[v];
}

int withinBounds(int x, int y) {
  return (x >= 0 && x < WIDTH) && (y >= 0 && y < HEIGHT);
}

void setup() {
  // set SCLK, MOSI, MISO, SS to be output
  DDRB = 0b00001111;
  PORTB = 0b00000000;
  //
  player_x = 2;
  player_y = 2;
}

int main() {
  // Setup stuff
  setup();
  // Run
  while(1) {
    int dx = 0;
    int dy = 0;
    // Read user input
    int buttons = read_buttons();
    //
    if(buttons == BUTTON_UP) {
      dy = -1;
    } else if(buttons == BUTTON_DOWN) {
      dy = +1;
    } else if(buttons == BUTTON_LEFT) {
      dx = -1;      
    } else if(buttons == BUTTON_RIGHT) {
      dx = +1;
    }
    //
    int nx = player_x + dx;
    int ny = player_y + dy;    
    // Attempt to make the move
    if(withinBounds(nx,ny)) {
      // Check whether pushing rock
      int r = getRock(nx,ny);
      //
      if(r >= 0) {
	// Am attempting to move this rock
	int rx = rock_x[r] + dx;
	int ry = rock_y[r] + dy;    	
	// Attemp to push rock
	if(withinBounds(rx,ry) && getRock(rx,ry) < 0) {
	  // All looks good.
	  player_x = nx;
	  player_y = ny;
	  //
	  if(rx == TELEPORT_X && ry == TELEPORT_Y) {
	    // Move rock off the board
	    rock_x[r] = gadget(rx);
	    rock_y[r] = gadget(ry);
	  } else {
	    rock_x[r] = rx;
	    rock_y[r] = ry;
	  }
	} else {
	  // cannot push rock
	}
      } else {
	// No rock being pushed
	player_x = nx;
	player_y = ny;
      }
    }
  }
}
