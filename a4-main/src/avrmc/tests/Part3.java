package avrmc.tests;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javr.core.AvrInstruction;
import javr.io.HexFile;
import org.eclipse.jdt.annotation.Nullable;
import org.junit.jupiter.api.Test;

/**
 * Test cases for Part 2 of the assignment.
 *
 * @author David J. Pearce
 *
 */
public class Part3 {
  /**
   * A dummy constant representing one. This is used to prevent Eclipse errors
   * being reported on the test methods.
   */
  private final int one = 1;
  /**
   * A dummy constant representing three. This is used to prevent Eclipse errors
   * being reported on the test methods.
   */
  private final int three = 3;
  /**
   * A dummy constant representing four. This is used to prevent Eclipse errors
   * being reported on the test methods.
   */
  private final int four = 4;
  /**
   * A dummy constant representing ten. This is used to prevent Eclipse errors
   * being reported on the test methods.
   */
  private final int ten = 10;
  /**
   * A dummy constant representing eighteen. This is used to prevent Eclipse errors
   * being reported on the test methods.
   */
  private final int eighteen = 18;
  /**
   * A dummy constant representing twenty two. This is used to prevent Eclipse errors
   * being reported on the test methods.
   */
  private final int twentytwo = 22;
  /**
   * A dummy constant representing thirty six. This is used to prevent Eclipse
   * errors being reported on the test methods.
   */
  private final int thirtysix = 36;
  /**
   * Identifies the directory in which the test firmwares are located.
   */
  private @Nullable String dir = "tests/".replace("/", File.separator);  //$NON-NLS-1$//$NON-NLS-2$

  /**
   * A test.
   */
  @Test
  public void test_01() {
    AvrInstruction[] instructions = new AvrInstruction[] {
        // Initialize SP
        new AvrInstruction.LDI(28, 0x5f), new AvrInstruction.LDI(29, 0x2),
        new AvrInstruction.OUT(0x3e, 29), new AvrInstruction.OUT(0x3d, 28),
        // Initialise loop for 8 iterations
        new AvrInstruction.LDI(29, 0x8),
        // Loop condition
        new AvrInstruction.CPI(29, 0), new AvrInstruction.BREQ(4), new AvrInstruction.PUSH(16),
        new AvrInstruction.DEC(29), new AvrInstruction.POP(16), new AvrInstruction.RJMP(-6),
        new AvrInstruction.RJMP(-1) };
    // Check computation
    assertEquals(this.one, Part1.computeStackUsage(instructions));
  }

  /**
   * A test.
   */
  @Test
  public void test_02() {
    AvrInstruction[] instructions = new AvrInstruction[] {
        // Initialize SP
        new AvrInstruction.LDI(28, 0x5f), new AvrInstruction.LDI(29, 0x2),
        new AvrInstruction.OUT(0x3e, 29), new AvrInstruction.OUT(0x3d, 28),
        // Branch over method
        new AvrInstruction.RJMP(5),
        // Recursive method
        new AvrInstruction.CPI(29, 0), new AvrInstruction.BREQ(2), new AvrInstruction.DEC(29),
        new AvrInstruction.RCALL(-4), new AvrInstruction.RET(),
        // Initialise for 8 recursive calls
        new AvrInstruction.LDI(29, 0x8), new AvrInstruction.RCALL(-7),
        new AvrInstruction.RJMP(-1) };
    // Check computation
    assertEquals(this.eighteen, Part1.computeStackUsage(instructions));
  }

  /**
   * A test.
   */
  @Test
  public void test_03() {
    AvrInstruction[] instructions = new AvrInstruction[] {
        // Initialize SP
        new AvrInstruction.LDI(28, 0x5f), new AvrInstruction.LDI(29, 0x2),
        new AvrInstruction.OUT(0x3e, 29), new AvrInstruction.OUT(0x3d, 28),
        // Branch over method
        new AvrInstruction.RJMP(4),
        // Non-recursive method
        new AvrInstruction.PUSH(29), new AvrInstruction.DEC(29), new AvrInstruction.POP(29),
        new AvrInstruction.RET(),
        // Infinite (e.g. game) loop
        new AvrInstruction.LDI(29, 0x8), new AvrInstruction.CPI(29, 0x8),
        new AvrInstruction.BREQ(1), new AvrInstruction.RJMP(-1), new AvrInstruction.RCALL(-9),
        new AvrInstruction.RJMP(-2) };
    // Check computation
    assertEquals(this.three, Part1.computeStackUsage(instructions));
  }

  /**
   * A test.
   *
   * @throws IOException If a problem occurrs reading the firmware file.
   */
  @Test
  public void test_04() throws IOException {
    // Check computation
    assertEquals(this.four, computeStackUsage("fader.hex")); //$NON-NLS-1$
  }

  /**
   * A test.
   *
   * @throws IOException If a problem occurrs reading the firmware file.
   */
  @Test
  public void test_05() throws IOException {
    // Check computation
    assertEquals(this.ten, computeStackUsage("blocks_1.hex")); //$NON-NLS-1$
  }

  /**
   * A test.
   *
   * @throws IOException If a problem occurrs reading the firmware file.
   */
  @Test
  public void test_06() throws IOException {
    // Check computation
    assertEquals(this.ten, computeStackUsage("blocks_2.hex")); //$NON-NLS-1$
  }

  /**
   * A test.
   *
   * @throws IOException If a problem occurrs reading the firmware file.
   */
  @Test
  public void test_07() throws IOException {
    // Check computation
    assertEquals(this.ten, computeStackUsage("blocks_3.hex")); //$NON-NLS-1$
  }

  /**
   * A test.
   *
   * @throws IOException If a problem occurrs reading the firmware file.
   */
  @Test
  public void test_08() throws IOException {
    // Check computation
    assertEquals(this.ten, computeStackUsage("blocks_7.hex")); //$NON-NLS-1$
  }

  /**
   * A test.
   *
   * @throws IOException If a problem occurrs reading the firmware file.
   */
  @Test
  public void test_09() throws IOException {
    // Check computation
    assertEquals(this.ten, computeStackUsage("blocks_4.hex")); //$NON-NLS-1$
  }

  /**
   * A test.
   *
   * @throws IOException If a problem occurrs reading the firmware file.
   */
  @Test
  public void test_10() throws IOException {
    // Check computation
    assertEquals(this.ten, computeStackUsage("blocks_5.hex")); //$NON-NLS-1$
  }

  /**
   * A test.
   *
   * @throws IOException If a problem occurrs reading the firmware file.
   */
  @Test
  public void test_11() throws IOException {
    // Check computation
    assertEquals(this.ten, computeStackUsage("blocks_6.hex")); //$NON-NLS-1$
  }

  /**
   * A test.
   *
   * @throws IOException If a problem occurrs reading the firmware file.
   */
  @Test
  public void test_12() throws IOException {
    // Check computation
    assertEquals(this.thirtysix, computeStackUsage("numbers_r.hex")); //$NON-NLS-1$
  }

  /**
   * A test.
   *
   * @throws IOException If a problem occurrs reading the firmware file.
   */
  @Test
  public void test_13() throws IOException {
    // Check computation
    assertEquals(this.twentytwo, computeStackUsage("blocker_1.hex")); //$NON-NLS-1$
  }

  /**
   * A test.
   *
   * @throws IOException If a problem occurrs reading the firmware file.
   */
  @Test
  public void test_14() throws IOException {
    // Check computation
    assertEquals(this.twentytwo, computeStackUsage("blocker_2.hex")); //$NON-NLS-1$
  }


  /**
   * For a given sequence of instructions compute the maximum stack usage.
   *
   * @param filename Filename of firmware to load from disk.
   * @return Calculated maximum stack height
   * @throws IOException If something goes wrong loading the firmware (e.g.
   *                     <code>FileNotFound</code>).
   */
  private int computeStackUsage(String filename) throws IOException {
    try (FileReader fr = new FileReader(this.dir + filename)) {
      // Read the firmware image
      HexFile.Reader hfr = new HexFile.Reader(fr);
      HexFile hf = hfr.readAll();
      assert hf != null;
      return Part1.computeStackUsage(hf);
    }
  }
}
