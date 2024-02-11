# Makefile for programming the ATtiny85 via micronucleous USB
# bootloader
DEVICE      = attiny85
CLOCK      = 16000000UL
PROGRAMMER = -c usbtiny 
AVRDUDE = avrdude $(PROGRAMMER) -p $(DEVICE)
COMPILE = avr-gcc -Wall -Os -std=c11 -DF_CPU=$(CLOCK) -mmcu=$(DEVICE)

TARGETS = $(shell ls *.c | sed -e "s/\.c/\.hex/")

all: $(TARGETS)

%.s : %.c
	$(COMPILE) -S -o $@ $<

%.o : %.c
	$(COMPILE) -c -o $@ $<

%.elf: %.o
	$(COMPILE) -o $@ $< 

%.hex: %.elf
	avr-objcopy -j .text -j .data -O ihex $< $@

clean:
	rm -f *.hex *.elf *.o
