package avrmc.core;

/**
 * Avr Hasher.
 *
 * @author Joshua Keegan 300523483 
 *
 */
public class AvrHasher {
  /**
   * Avr.
   */
  AbstractAvr avr;
  /**
   * mem.
   */
  AbstractMemory mem;
  
  /**
   * Constructor.
   *
   * @param avr - to avr
   * @param mem - to mem
   */
  AvrHasher(AbstractAvr avr, AbstractMemory mem){
    this.avr = avr;
    this.mem = mem;
  }
}
