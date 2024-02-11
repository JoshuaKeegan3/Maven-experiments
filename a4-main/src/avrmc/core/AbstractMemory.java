package avrmc.core;

import java.util.Arrays;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

/**
 * Provides an abstract version of javr.core.Memory which holds abstract values,
 * rather than just concrete bytes. This interface attempts to reduce memory
 * allocation / deallocation for the common case of writing a known value.
 *
 * @author David J. Pearce
 *
 */
public class AbstractMemory {
  /**
   * Array of (abstract) bytes which make up this memory.
   */
  private final @NonNull Byte[] bytes;

  /**
   * Construct a new abstract memory of a given size. All locations are initially
   * set to zero.
   *
   * @param size Size (in bytes) of memory to construct.
   */
  public AbstractMemory(int size) {
    this.bytes = new @NonNull Byte[size];
    for (int i = 0; i != this.getBytes().length; ++i) {
      this.getBytes()[i] = Byte.from((byte) 0);
    }
  }

  /**
   * Construct an exact copy of this abstract memory.
   *
   * @param mem Abstract memory to copy.
   */
  public AbstractMemory(AbstractMemory mem) {
    final int n = mem.getBytes().length;
    this.bytes = new @NonNull Byte[n];
    for (int i = 0; i != n; ++i) {
      this.getBytes()[i] = mem.getBytes()[i];
    }
  }

  /**
   * Read an abstract byte from a given address. This will throw an exception is
   * the byte at the given address has unknown value.
   *
   * @param address Address to read from.
   * @return Value read from given address.
   */
  public Byte read(int address) {
    return this.getBytes()[address];
  }

  /**
   * Write an abstract byte to a given address.
   *
   * @param address Address to write to.
   * @param value   Value to be written.
   */
  public void write(int address, Byte value) {
    this.getBytes()[address] = value;
  }

  /**
   * Get size of this memory.
   *
   * @return Size in bytes.
   */
  public int size() {
    return this.getBytes().length;
  }

  /*@Override
  public int hashCode() {
    int hash = 7;
    final int n = this.getBytes().length;
    for (int i = 0; i < n; i++) {
      hash *= 31 * hash + this.getBytes()[i].hashCode();
    }
    return hash;
  }*/



  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + Arrays.hashCode(this.bytes);
    return result;
  }

  @Override
  public boolean equals(@Nullable Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    AbstractMemory other = (AbstractMemory) obj;
    if (!Arrays.equals(this.bytes, other.bytes)) {
      return false;
    }
    return true;
  }

  /**
   * Represents a byte in memory which has a potentially <i>unknown</i> value.
   *
   * @author David J. Pearce
   *
   */
  public static final class Byte {
    /**
     * Represents the special unknown byte.
     */
    public static final Byte UNKNOWN = new Byte();

    /**
     * Represents a concrete value within this byte.
     */
    private final byte value;
    /**
     * Indicates whether this byte is unknown or not.
     */
    private final boolean unknown;

    /**
     * Construct a concrete byte.
     *
     * @param value Value to be instantiated.
     */
    private Byte(byte value) {
      this.value = value;
      this.unknown = false;
    }

    /**
     * Construct the unknown byte.
     */
    private Byte() {
      this.value = 0;
      this.unknown = true;
    }

    @Override
    public int hashCode() {
      if (this.unknown) {
        return 500;
      }
      return this.value;
    }

    @Override
    public boolean equals(@Nullable Object o) {
      if (o == null || !(o instanceof Byte)) {
        return false;
      }
      Byte o2 = (Byte) o;

      return o2.hashCode() == this.hashCode();
    }

    /**
     * Perform a arithmetic ADDITION with another byte.
     *
     * @param rhs Right hand parameter for this operation.
     * @return Resulting byte.
     */
    public Byte add(Byte rhs) {
      if (this.isUnknown() || rhs.isUnknown()) {
        return UNKNOWN;
      }
      return new Byte((byte) (this.value + rhs.value));
    }

    /**
     * Perform a bitwise AND against another byte.
     *
     * @param rhs Right hand parameter for this operation.
     * @return Resulting byte.
     */
    public Byte and(Byte rhs) {
      if (this.isUnknown() || rhs.isUnknown()) {
        return UNKNOWN;
      }
      return new Byte((byte) (this.value & rhs.value));
    }

    /**
     * Clear a specific bit in this abstract byte.
     *
     * @param index Index between <code>0</code> and <code>7</code>.
     * @return Updated byte.
     */
    public Byte clear(int index) {
      if (this.isUnknown()) {
        return UNKNOWN;
      }
      int mask = ~(1 << index);
      return new Byte((byte) (this.value & mask));
    }

    /**
     * Check whether this abstract byte is equal to another abstract byte.
     *
     * @param rhs Right hand parameter for this operation.
     * @return Resulting byte.
     */
    public Bit eq(Byte rhs) {
      if (this.isUnknown()) {
        return Bit.UNKNOWN;
      }
      return this.value == rhs.value ? Bit.TRUE : Bit.FALSE;
    }

    /**
     * Get a given bit within this byte.
     *
     * @param index between <code>0</code> and <code>7</code>.
     * @return Bit at given index.
     */
    public Bit get(int index) {
      if (this.unknown) {
        return Bit.UNKNOWN;
      } else if ((this.value & (1 << index)) != 0) {
        return Bit.TRUE;
      } else {
        return Bit.FALSE;
      }
    }

    /**
     * Increment this abstract byte by one.
     *
     * @return Updated byte.
     */
    public Byte inc() {
      if (this.isUnknown()) {
        return UNKNOWN;
      }
      return new Byte((byte) (this.value + 1));
    }

    /**
     * Check whether this value is unknown or not. That is, whether this abstract
     * value has a known value or not.
     *
     * @return True if this byte has an unknown value, or false otherwise.
     */
    public boolean isUnknown() {
      return this.unknown;
    }

    /**
     * Determine whether this abstract byte is equal to zero or not.
     *
     * @return Result of test against zero (which may be unknown).
     */
    public Bit isZero() {
      if (this.isUnknown()) {
        return Bit.UNKNOWN;
      }
      if (this.value == 0) {
        return Bit.TRUE;
      }
      return Bit.FALSE;
    }

    /**
     * Determine whether this abstract byte is not equal to zero.
     *
     * @return Result of test against zero (which may be unknown).
     */
    public Bit isNotZero() {
      if (this.isUnknown()) {
        return Bit.UNKNOWN;
      }
      if (this.value != 0) {
        return Bit.TRUE;
      }
      return Bit.FALSE;
    }

    /**
     * Determine whether this abstract byte is equal the least representable
     * (signed) value which is <code>0x80</code>.
     *
     * @return Result of test (which may be unknown).
     */
    public Bit isLeast() {
      if (this.isUnknown()) {
        return Bit.UNKNOWN;
      }
      if (this.value == -128) {
        return Bit.TRUE;
      }
      return Bit.FALSE;
    }

    /**
     * Perform two's compliment negation on this byte.
     *
     * @return Negated byte.
     */
    public Byte neg() {
      if (this.isUnknown()) {
        return UNKNOWN;
      }
      return new Byte((byte) -this.value);
    }

    /**
     * Perform one's compliment inversion on this byte.
     *
     * @return Inverted byte.
     */
    public Byte inv() {
      if (this.isUnknown()) {
        return UNKNOWN;
      }
      return new Byte((byte) (0xFF - this.value));
    }

    /**
     * Perform a bitwise OR operation against another abstract byte.
     *
     * @param rhs Right hand parameter for this operation.
     * @return Resulting byte.
     */
    public Byte or(Byte rhs) {
      if (this.isUnknown() || rhs.isUnknown()) {
        return UNKNOWN;
      }
      return new Byte((byte) (this.value | rhs.value));
    }

    /**
     * Set a specific bit in this abstract byte.
     *
     * @param index between <code>0</code> and <code>7</code>.
     * @return Updated byte.
     */
    public Byte set(int index) {
      if (this.isUnknown()) {
        return UNKNOWN;
      }
      int mask = 1 << index;
      return new Byte((byte) (this.value | mask));
    }

    /**
     * Set a specific bit in this abstract byte to a given value. Observe that, if
     * the bit being set is unknown, then the result byte is unknown.
     *
     * @param index Index between <code>0</code> and <code>7</code>.
     * @param bit   Value to set.
     * @return Updated byte.
     */
    public Byte set(int index, Bit bit) {
      if (this.isUnknown()) {
        return UNKNOWN;
      }
      if (bit == Bit.TRUE) {
        return set(index);
      }
      return clear(index);
    }

    /**
     * Perform a signed shift-right operation on this abstract byte.
     *
     * @param rhs Right hand parameter for this operation.
     * @return Resulting byte.
     */
    public Byte shr(int rhs) {
      if (this.isUnknown()) {
        return UNKNOWN;
      }
      return new Byte((byte) (this.value >> rhs));
    }

    /**
     * Subtract another abstract byte from this one.
     *
     * @param rhs Right hand parameter for this operation.
     * @return Resulting byte.
     */
    public Byte sub(Byte rhs) {
      if (this.isUnknown() || rhs.isUnknown()) {
        return UNKNOWN;
      }
      return new Byte((byte) (this.value - rhs.value));
    }

    /**
     * Subtract a constant from this abstract byte.
     *
     * @param rhs Right hand parameter for this operation.
     * @return Resulting byte.
     */
    public Byte sub(byte rhs) {
      return new Byte((byte) (this.value - rhs));
    }

    /**
     * Perform an unsigned shift-right operation on this abstract byte.
     *
     * @param rhs Right hand parameter for this operation.
     * @return Resulting byte.
     */
    public Byte ushr(int rhs) {
      if (this.isUnknown()) {
        return UNKNOWN;
      }
      int v = (0xff & this.value) >>> rhs;
      return new Byte((byte) v);
    }

    /**
     * Perform a bitwise XOR operation against another abstract byte.
     *
     * @param rhs Right hand parameter for this operation.
     * @return Resulting byte.
     */
    public Byte xor(Byte rhs) {
      if (this.isUnknown() || rhs.isUnknown()) {
        return UNKNOWN;
      }
      return new Byte((byte) (this.value ^ rhs.value));
    }

    /**
     * Swaps high and low nibbles in a register.
     *
     * @return Resulting byte.
     */
    public Byte swap() {
      if (this.isUnknown()) {
        return UNKNOWN;
      }
      int lsn = this.value & 0b0000_1111;
      int msn = this.value & 0b1111_0000;
      return new Byte((byte) ((lsn << 4) | (msn >> 4)));
    }

    @Override
    public String toString() {
      if (this.unknown) {
        return "??"; //$NON-NLS-1$
      }
      String r = String.format("%02X", Integer.valueOf(this.value)); //$NON-NLS-1$
      assert r != null;
      return r;
    }

    /**
     * byte value of class.
     *
     * @return byte - value
     */
    public byte toByte() {
      if (this.unknown) {
        throw new IllegalArgumentException("Cannot concretize unknown value"); //$NON-NLS-1$
      }
      return this.value;
    }

    /**
     * Construct an abstract byte from a concrete byte.
     *
     * @param v Value to instantiate from.
     * @return Byte instance.
     */
    public static Byte from(byte v) {
      return new Byte(v);
    }

    /**
     * Construct an abstract byte from eight abstract bits.
     *
     * @param b7 Bit 7 (Most significant)
     * @param b6 Bit 6
     * @param b5 Bit 5
     * @param b4 Bit 4
     * @param b3 Bit 3
     * @param b2 Bit 2
     * @param b1 Bit 1
     * @param b0 Bit 0 (least significant).
     * @return Byte instance.
     */
    public static Byte from(Bit b7, Bit b6, Bit b5, Bit b4, Bit b3, Bit b2, Bit b1, Bit b0) {
      if (b7 == Bit.UNKNOWN || b6 == Bit.UNKNOWN || b5 == Bit.UNKNOWN || b4 == Bit.UNKNOWN
          || b3 == Bit.UNKNOWN || b2 == Bit.UNKNOWN || b1 == Bit.UNKNOWN || b0 == Bit.UNKNOWN) {
        return UNKNOWN;
      }
      int v = 0;
      v |= (b7 == Bit.TRUE) ? 0b1000_0000 : 0;
      v |= (b6 == Bit.TRUE) ? 0b0100_0000 : 0;
      v |= (b5 == Bit.TRUE) ? 0b0010_0000 : 0;
      v |= (b4 == Bit.TRUE) ? 0b0001_0000 : 0;
      v |= (b3 == Bit.TRUE) ? 0b0000_1000 : 0;
      v |= (b2 == Bit.TRUE) ? 0b0000_0100 : 0;
      v |= (b1 == Bit.TRUE) ? 0b0000_0010 : 0;
      v |= (b0 == Bit.TRUE) ? 0b0000_0001 : 0;
      return new Byte((byte) v);
    }
  }

  /**
   * Represents two bytes packaged together to form a 16-bit word. These are
   * typically used for addressing memory.
   *
   * @author David J. Pearce
   *
   */
  public static final class Word {
    /**
     * Represents the special unknown word.
     */
    private static final Word UNKNOWN = new Word();

    /**
     * Current value for this word.
     */
    private final int value;
    /**
     * Flag indicating whether or not this value is unknown.
     */
    private final boolean unknown;

    /**
     * Construct a new word with the initial value zero.
     */
    private Word() {
      this.value = 0;
      this.unknown = true;
    }

    /**
     * Construct a new word with a given initial value.
     *
     * @param v The initial value for this word.
     */
    private Word(int v) {
      this.value = v;
      this.unknown = false;
    }

    /**
     * Add a constant onto this word.
     *
     * @param rhs Right hand parameter for this operation.
     * @return Resulting word.
     */
    public Word add(byte rhs) {
      if (this.unknown) {
        return UNKNOWN;
      }
      return new Word(this.value + rhs);
    }

    /**
     * Subtract a constant onto this word.
     *
     * @param rhs Right hand parameter for this operation.
     * @return Resulting word.
     */
    public Word sub(byte rhs) {
      if (this.unknown) {
        return UNKNOWN;
      }
      return new Word(this.value - rhs);
    }

    /**
     * Increment this word by one.
     *
     * @return Resulting word.
     */
    public Word dec() {
      if (this.unknown) {
        return UNKNOWN;
      }
      return add((byte) -1);
    }

    /**
     * Get a given bit within this word.
     *
     * @param index Bit index between <code>0</code> and <code>15</code>.
     * @return Bit value.
     */
    public Bit get(int index) {
      if (this.unknown) {
        return Bit.UNKNOWN;
      } else if ((this.value & (1 << index)) != 0) {
        return Bit.TRUE;
      } else {
        return Bit.FALSE;
      }
    }

    /**
     * Get the low byte of this word.
     *
     * @return Low (i.e. least significant) byte.
     */
    public Byte getLow() {
      if (this.unknown) {
        return Byte.UNKNOWN;
      }
      return Byte.from((byte) (this.value & 0xFF));
    }

    /**
     * Get the high byte of this word.
     *
     * @return Hight (i.e. most significant) byte.
     */
    public Byte getHigh() {
      if (this.unknown) {
        return Byte.UNKNOWN;
      }
      return Byte.from((byte) (this.value >> 8));
    }

    /**
     * Increment this word by one.
     *
     * @return Resulting word.
     */
    public Word inc() {
      return add((byte) 1);
    }

    /**
     * Check whether this value is unknown or not. That is, whether this abstract
     * value has a known value or not.
     *
     * @return True if this value is unknown, or false otherwise.
     */
    public boolean isUnknown() {
      return this.unknown;
    }

    /**
     * Determine whether this abstract word is equal to zero or not.
     *
     * @return Result of test against zero (which may be unknown).
     */
    public Bit isZero() {
      if (this.unknown) {
        return Bit.UNKNOWN;
      } else if (this.value == 0) {
        return Bit.TRUE;
      } else {
        return Bit.FALSE;
      }
    }

    /**
     * Get the concrete value associated with this abstract value. This assumes that
     * <code>isConcrete()</code> holds, otherwise an exception is thrown.
     *
     * @return Concrete integer value.
     */
    public int toInt() {
      if (this.unknown) {
        throw new IllegalArgumentException("cannot concretize an unknown value"); //$NON-NLS-1$
      }
      return this.value;
    }

    /**
     * Construct an abstract word from a concrete value.
     *
     * @param word Concrete word to construct from.
     * @return Word instance.
     */
    public static Word from(int word) {
      return new Word(word);
    }

    /**
     * Construct an abstract word from two abstract bytes.
     *
     * @param msb Most significant byte.
     * @param lsb Least significant byte.
     * @return Word instance.
     */
    public static Word from(Byte msb, Byte lsb) {
      if (msb.isUnknown() || lsb.isUnknown()) {
        return UNKNOWN;
      }
      int m = (msb.value & 0xFF) << 8;
      int l = lsb.value & 0xFF;
      return new Word(m | l);
    }
  }

  /**
   * Represents a single abstract bit which has three possible states:
   * <i>true</i>, <i>false</i> and <i>unknown</i>. The latter signals the bit is
   * either <i>true</i> or <i>false</i> (but we don't know which).
   *
   * @author David J. Pearce
   *
   */
  public static class Bit {
    /**
     * Singleton instance representing a bit with <i>unknown</i> value.
     */
    public static final Bit UNKNOWN = new Bit();
    /**
     * Singleton instance representing a bit with <i>true</i> value.
     */
    public static final Bit TRUE = new Bit();
    /**
     * Singleton instance representing a bit with <i>false</i> value.
     */
    public static final Bit FALSE = new Bit();

    /**
     * Private construct to ensure singleton fields above are used.
     */
    private Bit() {

    }
  }

  // =========================================================================
  // Testing
  // =========================================================================

  /**
   * Main function used for testing.
   *
   * @param args - empty
   */
  public static void main(String[] args) {
    // Unary byte operations first
    for (int i = 0; i < 256; ++i) {
      byte b = (byte) i;
      Byte bb = Byte.from(b);
      // isZer0
      check(b == 0, bb.isZero());
      check(b != 0, bb.isNotZero());
      check(b == -128, bb.isLeast());
      check((byte) -b, bb.neg());
      check((byte) (0xFF - b), bb.inv());
      check(swap(b), bb.swap());
      check((byte) (b + 1), bb.inc());
      for (int j = 0; j < 8; ++j) {
        check(clear(b, j), bb.clear(j));
        check(get(b, j), bb.get(j));
        check(set(b, j), bb.set(j));
        check(set(b, j), bb.set(j, Bit.TRUE));
        check(clear(b, j), bb.set(j, Bit.FALSE));
        check((byte) (b >> j), bb.shr(j));
        check((byte) ((b & 0xFF) >>> j), bb.ushr(j));
      }
    }
    // Binary byte operations
    for (int i = 0; i < 256; ++i) {
      byte b1 = (byte) i;
      Byte bb1 = Byte.from(b1);
      for (int j = 0; j < 256; ++j) {
        byte b2 = (byte) j;
        Byte bb2 = Byte.from(b2);
        check((byte) (b1 + b2), bb1.add(bb2));
        check((byte) (b1 & b2), bb1.and(bb2));
        check(b1 == b2, bb1.eq(bb2));
        check((byte) (b1 | b2), bb1.or(bb2));
        check((byte) (b1 - b2), bb1.sub(bb2));
        check((byte) (b1 - b2), bb1.sub(b2));
        check((byte) (b1 ^ b2), bb1.xor(bb2));
        //
        check(toWord(b1, b2), Word.from(bb1, bb2));
      }
    }
    // unary word operations
    for (int i = 0; i < 65536; ++i) {
      Word w = Word.from(i);
      check(i - 1, w.dec());
      check(getLow(i), w.getLow());
      check(getHigh(i), w.getHigh());
      check(i + 1, w.inc());
      check(i == 0, w.isZero());
      for (int j = 0; j < 16; ++j) {
        check(get(i, j), w.get(j));
      }
    }
    // binary word operations
    for (int i = 0; i < 65536; ++i) {
      Word w = Word.from(i);
      for (int j = 0; j < 256; ++j) {
        byte b = (byte) j;
        check(i + b, w.add(b));
        check(i - b, w.sub(b));
      }
    }
  }

  /**
   * Applies a bit mask to get low part of word.
   *
   * @param word int
   * @return byte conversation
   */
  public static byte getLow(int word) {
    return (byte) (word & 0xFF);
  }

  /**
   * Applies a bit mask to get high part of word.
   *
   * @param word int
   * @return byte conversation
   */
  public static byte getHigh(int word) {
    return (byte) (word >> 8);
  }

  /**
   * Combines the 2 bytes into a word.
   *
   * @param m lower byte
   * @param l upper byte
   * @return newly created word
   */
  public static int toWord(byte m, byte l) {
    int msb = m & 0xFF;
    int lsb = l & 0xFF;
    return (msb << 8) | lsb;
  }

  /**
   * Clears an bit in a byte.
   *
   * @param b byte to change
   * @param index to clear
   * @return new byte
   */
  public static byte clear(byte b, int index) {
    int mask = (1 << index);
    return (byte) (b & ~mask);
  }

  /**
   * Gets an bit in a word.
   *
   * @param w word to change
   * @param index to get
   * @return new word
   */
  public static boolean get(int w, int index) {
    int mask = (1 << index);
    return (w & mask) != 0;
  }

  /**
   * Gets an bit in a byte.
   *
   * @param b byte to change
   * @param index to get
   * @return new byte
   */
  public static boolean get(byte b, int index) {
    int mask = (1 << index);
    return (b & mask) != 0;
  }

  /**
   * Sets an bit in a byte.
   *
   * @param b byte to change
   * @param index to get
   * @return new byte
   */
  public static byte set(byte b, int index) {
    int mask = (1 << index);
    return (byte) (b | mask);
  }

  /**
   * Flips a byte.
   *
   * @param b byte to swap
   * @return flipped byte
   */
  public static byte swap(byte b) {
    // Perform the operation
    int lsn = b & 0b0000_1111;
    int msn = b & 0b1111_0000;
    return (byte) ((lsn << 4) | (msn >> 4));
  }

  /**
   * Checks a word with a Word.
   *
   * @param l word
   * @param r Word
   */
  public static void check(int l, Word r) {
    if (l != r.toInt()) {
      throw new IllegalArgumentException();
    }
  }

  /**
   * Checks a byte with a Byte.
   *
   * @param l byte
   * @param r Byte
   */
  public static void check(byte l, Byte r) {
    if (l != r.toByte()) {
      throw new IllegalArgumentException();
    }
  }

  /**
   * Checks a bit with a Bit.
   *
   * @param l byte
   * @param r Byte
   */
  public static void check(boolean l, Bit r) {
    check(l, r == Bit.TRUE);
  }

  /**
   * checks two booleans are equal.
   *
   * @param l left
   * @param r right
   */
  public static void check(boolean l, boolean r) {
    if (l != r) {
      throw new IllegalArgumentException();
    }
  }

  /**
   * Get bytes.
   *
   * @return bytes array
   */
  @NonNull
  protected Byte[] getBytes() {
    return this.bytes;
  }
}