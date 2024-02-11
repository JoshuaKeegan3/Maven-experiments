package avrmc.tests;

import static avrmc.tests.Part1.computeStackUsage;
import static org.junit.Assert.assertEquals;

import javr.core.AvrInstruction;
import org.junit.jupiter.api.Test;

/**
 * Test cases for Part 1 of the assignment.
 *
 * @author David J. Pearce
 *
 */
public class Part2 {
  /**
   * A dummy constant representing zero. This is used to prevent Eclipse errors
   * being reported on the test methods.
   */
  private final int zero = 0;
  /**
   * A dummy constant representing one. This is used to prevent Eclipse errors
   * being reported on the test methods.
   */
  private final int one = 1;
  /**
   * A dummy constant representing two. This is used to prevent Eclipse errors
   * being reported on the test methods.
   */
  private final int two = 2;
  /**
   * A dummy constant representing six. This is used to prevent Eclipse errors
   * being reported on the test methods.
   */
  private final int six = 6;

  /**
   * A Test.
   */
  @Test
  public void test_01() {
    AvrInstruction[] instructions = new AvrInstruction[] {
        // Initialize SP
        new AvrInstruction.LDI(28, 0x5f),
        new AvrInstruction.LDI(29, 0x2),
        new AvrInstruction.OUT(0x3e, 29),
        new AvrInstruction.OUT(0x3d, 28),
        // Read (Unknown) Input
        new AvrInstruction.IN(16, 0x16),
        // Compare against zero
        new AvrInstruction.CPI(16, 0),
        // Conditional Branch
        new AvrInstruction.BRLT(2),
        new AvrInstruction.NOP(),
        new AvrInstruction.NOP(),
        new AvrInstruction.RJMP(-1) };
    // Check computation
    assertEquals(this.zero, computeStackUsage(instructions));
  }

  /**
   * A Test.
   */
  @Test
  public void test_02() {
    AvrInstruction[] instructions = new AvrInstruction[] {
        // Initialize SP
        new AvrInstruction.LDI(28, 0x5f),
        new AvrInstruction.LDI(29, 0x2),
        new AvrInstruction.OUT(0x3e, 29),
        new AvrInstruction.OUT(0x3d, 28),
        // Read (Unknown) Input
        new AvrInstruction.IN(16, 0x16),
        // Compare against zero
        new AvrInstruction.CPI(16, 0),
        // Conditional Branch
        new AvrInstruction.BRLT(3),
        new AvrInstruction.NOP(),
        new AvrInstruction.NOP(),
        new AvrInstruction.RJMP(-1),
        new AvrInstruction.PUSH(0x16),
        new AvrInstruction.POP(0x16),
        new AvrInstruction.RJMP(-1) };
    // Check computation
    assertEquals(this.one, computeStackUsage(instructions));
  }

  /**
   * A Test.
   */
  @Test
  public void test_03() {
    AvrInstruction[] instructions = new AvrInstruction[] {
        // Initialize SP
        new AvrInstruction.LDI(28, 0x5f),
        new AvrInstruction.LDI(29, 0x2),
        new AvrInstruction.OUT(0x3e, 29),
        new AvrInstruction.OUT(0x3d, 28),
        // Read (Unknown) Input
        new AvrInstruction.IN(16, 0x16),
        // Compare against zero
        new AvrInstruction.CPI(16, 0),
        // Conditional Branch
        new AvrInstruction.BRLT(3),
        new AvrInstruction.PUSH(0x16),
        new AvrInstruction.POP(0x16),
        new AvrInstruction.RJMP(-1),
        new AvrInstruction.RJMP(-1) };
    // Check computation
    assertEquals(this.one, computeStackUsage(instructions));
  }

  /**
   * A Test.
   */
  @Test
  public void test_04() {
    AvrInstruction[] instructions = new AvrInstruction[] {
        // Initialize SP
        new AvrInstruction.LDI(28, 0x5f),
        new AvrInstruction.LDI(29, 0x2),
        new AvrInstruction.OUT(0x3e, 29),
        new AvrInstruction.OUT(0x3d, 28),
        // Read (Unknown) Input
        new AvrInstruction.IN(16, 0x16),
        // Compare against zero
        new AvrInstruction.CP(16, 0),
        // Conditional Branch
        new AvrInstruction.BRLT(3),
        new AvrInstruction.NOP(),
        new AvrInstruction.NOP(),
        new AvrInstruction.RJMP(-1),
        new AvrInstruction.PUSH(0x16),
        new AvrInstruction.POP(0x16),
        new AvrInstruction.RJMP(-1) };
    // Check computation
    assertEquals(this.one, computeStackUsage(instructions));
  }

  /**
   * A Test.
   */
  @Test
  public void test_05() {
    AvrInstruction[] instructions = new AvrInstruction[] {
        // Initialize SP
        new AvrInstruction.LDI(28, 0x5f),
        new AvrInstruction.LDI(29, 0x2),
        new AvrInstruction.OUT(0x3e, 29),
        new AvrInstruction.OUT(0x3d, 28),
        // Read (Unknown) Input
        new AvrInstruction.IN(16, 0x16),
        // Compare against zero
        new AvrInstruction.CP(16, 0),
        // Conditional Branch
        new AvrInstruction.BRLT(3),
        new AvrInstruction.PUSH(0x16),
        new AvrInstruction.POP(0x16),
        new AvrInstruction.RJMP(-1),
        new AvrInstruction.RJMP(-1) };
    // Check computation
    assertEquals(this.one, computeStackUsage(instructions));
  }

  /**
   * A Test.
   */
  @Test
  public void test_06() {
    AvrInstruction[] instructions = new AvrInstruction[] {
        // Initialize SP
        new AvrInstruction.LDI(28, 0x5f),
        new AvrInstruction.LDI(29, 0x2),
        new AvrInstruction.OUT(0x3e, 29),
        new AvrInstruction.OUT(0x3d, 28),
        // Read (Unknown) Input
        new AvrInstruction.IN(16, 0x16),
        new AvrInstruction.NEG(16),
        // Compare against zero
        new AvrInstruction.CPI(16, 0),
        // Conditional Branch
        new AvrInstruction.BRLT(3),
        new AvrInstruction.NOP(),
        new AvrInstruction.NOP(),
        new AvrInstruction.RJMP(-1),
        new AvrInstruction.PUSH(0x16),
        new AvrInstruction.POP(0x16),
        new AvrInstruction.RJMP(-1) };
    // Check computation
    assertEquals(this.one, computeStackUsage(instructions));
  }

  /**
   * A Test.
   */
  @Test
  public void test_07() {
    AvrInstruction[] instructions = new AvrInstruction[] {
        // Initialize SP
        new AvrInstruction.LDI(28, 0x5f),
        new AvrInstruction.LDI(29, 0x2),
        new AvrInstruction.OUT(0x3e, 29),
        new AvrInstruction.OUT(0x3d, 28),
        // Read (Unknown) Input
        new AvrInstruction.IN(16, 0x16),
        new AvrInstruction.NEG(16),
        // Compare against zero
        new AvrInstruction.CPI(16, 0),
        // Conditional Branch
        new AvrInstruction.BRLT(3),
        new AvrInstruction.PUSH(0x16),
        new AvrInstruction.POP(0x16),
        new AvrInstruction.RJMP(-1),
        new AvrInstruction.RJMP(-1) };
    // Check computation
    assertEquals(this.one, computeStackUsage(instructions));
  }

  /**
   * A Test.
   */
  @Test
  public void test_08() {
    AvrInstruction[] instructions = new AvrInstruction[] {
        // Initialize SP
        new AvrInstruction.LDI(28, 0x5f),
        new AvrInstruction.LDI(29, 0x2),
        new AvrInstruction.OUT(0x3e, 29),
        new AvrInstruction.OUT(0x3d, 28),
        // Read (Unknown) Input
        new AvrInstruction.IN(16, 0x16),
        new AvrInstruction.COM(16),
        // Compare against zero
        new AvrInstruction.CPI(16, 0),
        // Conditional Branch
        new AvrInstruction.BRLT(3),
        new AvrInstruction.NOP(),
        new AvrInstruction.NOP(),
        new AvrInstruction.RJMP(-1),
        new AvrInstruction.PUSH(0x16),
        new AvrInstruction.POP(0x16),
        new AvrInstruction.RJMP(-1) };
    // Check computation
    assertEquals(this.one, computeStackUsage(instructions));
  }

  /**
   * A Test.
   */
  @Test
  public void test_09() {
    AvrInstruction[] instructions = new AvrInstruction[] {
        // Initialize SP
        new AvrInstruction.LDI(28, 0x5f),
        new AvrInstruction.LDI(29, 0x2),
        new AvrInstruction.OUT(0x3e, 29),
        new AvrInstruction.OUT(0x3d, 28),
        // Read (Unknown) Input
        new AvrInstruction.IN(16, 0x16),
        new AvrInstruction.COM(16),
        // Compare against zero
        new AvrInstruction.CPI(16, 0),
        // Conditional Branch
        new AvrInstruction.BRLT(3),
        new AvrInstruction.PUSH(0x16),
        new AvrInstruction.POP(0x16),
        new AvrInstruction.RJMP(-1),
        new AvrInstruction.RJMP(-1) };
    // Check computation
    assertEquals(this.one, computeStackUsage(instructions));
  }

  /**
   * A Test.
   */
  @Test
  public void test_10() {
    AvrInstruction[] instructions = new AvrInstruction[] {
        // Initialize SP
        new AvrInstruction.LDI(28, 0x5f),
        new AvrInstruction.LDI(29, 0x2),
        new AvrInstruction.OUT(0x3e, 29),
        new AvrInstruction.OUT(0x3d, 28),
        // Read (Unknown) Input
        new AvrInstruction.IN(16, 0x16),
        new AvrInstruction.ADD(16, 16),
        // Compare against zero
        new AvrInstruction.CPI(16, 0),
        // Conditional Branch
        new AvrInstruction.BRLT(3),
        new AvrInstruction.NOP(),
        new AvrInstruction.NOP(),
        new AvrInstruction.RJMP(-1),
        new AvrInstruction.PUSH(0x16),
        new AvrInstruction.POP(0x16),
        new AvrInstruction.RJMP(-1) };
    // Check computation
    assertEquals(this.one, computeStackUsage(instructions));
  }

  /**
   * A Test.
   */
  @Test
  public void test_11() {
    AvrInstruction[] instructions = new AvrInstruction[] {
        // Initialize SP
        new AvrInstruction.LDI(28, 0x5f),
        new AvrInstruction.LDI(29, 0x2),
        new AvrInstruction.OUT(0x3e, 29),
        new AvrInstruction.OUT(0x3d, 28),
        // Read (Unknown) Input
        new AvrInstruction.IN(16, 0x16),
        new AvrInstruction.ADD(16, 16),
        // Compare against zero
        new AvrInstruction.CPI(16, 0),
        // Conditional Branch
        new AvrInstruction.BRLT(3),
        new AvrInstruction.PUSH(0x16),
        new AvrInstruction.POP(0x16),
        new AvrInstruction.RJMP(-1),
        new AvrInstruction.RJMP(-1) };
    // Check computation
    assertEquals(this.one, computeStackUsage(instructions));
  }

  /**
   * A Test.
   */
  @Test
  public void test_12() {
    AvrInstruction[] instructions = new AvrInstruction[] {
        // Initialize SP
        new AvrInstruction.LDI(28, 0x5f),
        new AvrInstruction.LDI(29, 0x2),
        new AvrInstruction.OUT(0x3e, 29),
        new AvrInstruction.OUT(0x3d, 28),
        // Read (Unknown) Input
        new AvrInstruction.IN(28, 0x16),
        new AvrInstruction.ADIW(28, 1),
        // Compare against zero
        new AvrInstruction.CPI(28, 0),
        // Conditional Branch
        new AvrInstruction.BRLT(3),
        new AvrInstruction.NOP(),
        new AvrInstruction.NOP(),
        new AvrInstruction.RJMP(-1),
        new AvrInstruction.PUSH(0x16),
        new AvrInstruction.POP(0x16),
        new AvrInstruction.RJMP(-1) };
    // Check computation
    assertEquals(this.one, computeStackUsage(instructions));
  }

  /**
   * A Test.
   */
  @Test
  public void test_13() {
    AvrInstruction[] instructions = new AvrInstruction[] {
        // Initialize SP
        new AvrInstruction.LDI(28, 0x5f),
        new AvrInstruction.LDI(29, 0x2),
        new AvrInstruction.OUT(0x3e, 29),
        new AvrInstruction.OUT(0x3d, 28),
        // Read (Unknown) Input
        new AvrInstruction.IN(28, 0x16),
        new AvrInstruction.ADIW(28, 1),
        // Compare against zero
        new AvrInstruction.CPI(28, 0),
        // Conditional Branch
        new AvrInstruction.BRLT(3),
        new AvrInstruction.PUSH(0x16),
        new AvrInstruction.POP(0x16),
        new AvrInstruction.RJMP(-1),
        new AvrInstruction.RJMP(-1) };
    // Check computation
    assertEquals(this.one, computeStackUsage(instructions));
  }

  /**
   * A Test.
   */
  @Test
  public void test_14() {
    AvrInstruction[] instructions = new AvrInstruction[] {
        // Initialize SP
        new AvrInstruction.LDI(28, 0x5f),
        new AvrInstruction.LDI(29, 0x2),
        new AvrInstruction.OUT(0x3e, 29),
        new AvrInstruction.OUT(0x3d, 28),
        // Read (Unknown) Input
        new AvrInstruction.IN(28, 0x16),
        new AvrInstruction.SBIW(28, 1),
        // Compare against zero
        new AvrInstruction.CPI(28, 0),
        // Conditional Branch
        new AvrInstruction.BRLT(3),
        new AvrInstruction.NOP(),
        new AvrInstruction.NOP(),
        new AvrInstruction.RJMP(-1),
        new AvrInstruction.PUSH(0x16),
        new AvrInstruction.POP(0x16),
        new AvrInstruction.RJMP(-1) };
    // Check computation
    assertEquals(this.one, computeStackUsage(instructions));
  }

  /**
   * A Test.
   */
  @Test
  public void test_15() {
    AvrInstruction[] instructions = new AvrInstruction[] {
        // Initialize SP
        new AvrInstruction.LDI(28, 0x5f),
        new AvrInstruction.LDI(29, 0x2),
        new AvrInstruction.OUT(0x3e, 29),
        new AvrInstruction.OUT(0x3d, 28),
        // Read (Unknown) Input
        new AvrInstruction.IN(28, 0x16),
        new AvrInstruction.SBIW(28, 1),
        // Compare against zero
        new AvrInstruction.CPI(28, 1),
        // Conditional Branch
        new AvrInstruction.BREQ(3),
        new AvrInstruction.NOP(),
        new AvrInstruction.NOP(),
        new AvrInstruction.RJMP(-1),
        new AvrInstruction.PUSH(0x16),
        new AvrInstruction.POP(0x16),
        new AvrInstruction.RJMP(-1) };
    // Check computation
    assertEquals(this.one, computeStackUsage(instructions));
  }

  /**
   * A Test.
   */
  @Test
  public void test_16() {
    AvrInstruction[] instructions = new AvrInstruction[] {
        // Initialize SP
        new AvrInstruction.LDI(28, 0x5f),
        new AvrInstruction.LDI(29, 0x2),
        new AvrInstruction.OUT(0x3e, 29),
        new AvrInstruction.OUT(0x3d, 28),
        // Read (Unknown) Input
        new AvrInstruction.IN(28, 0x16),
        new AvrInstruction.SUB(28, 29),
        // Compare against zero
        new AvrInstruction.CPI(28, 0),
        // Conditional Branch
        new AvrInstruction.BRLT(3),
        new AvrInstruction.PUSH(0x16),
        new AvrInstruction.POP(0x16),
        new AvrInstruction.RJMP(-1),
        new AvrInstruction.RJMP(-1) };
    // Check computation
    assertEquals(this.one, computeStackUsage(instructions));
  }

  /**
   * A Test.
   */
  @Test
  public void test_17() {
    AvrInstruction[] instructions = new AvrInstruction[] {
        // Initialize SP
        new AvrInstruction.LDI(28, 0x5f),
        new AvrInstruction.LDI(29, 0x2),
        new AvrInstruction.OUT(0x3e, 29),
        new AvrInstruction.OUT(0x3d, 28),
        // Read (Unknown) Input
        new AvrInstruction.IN(29, 0x16),
        new AvrInstruction.AND(29, 28),
        // Compare against zero
        new AvrInstruction.CPI(29, 0),
        // Conditional Branch
        new AvrInstruction.BRLT(3),
        new AvrInstruction.NOP(),
        new AvrInstruction.NOP(),
        new AvrInstruction.RJMP(-1),
        new AvrInstruction.PUSH(0x16),
        new AvrInstruction.POP(0x16),
        new AvrInstruction.RJMP(-1) };
    // Check computation
    assertEquals(this.one, computeStackUsage(instructions));
  }

  /**
   * A Test.
   */
  @Test
  public void test_18() {
    AvrInstruction[] instructions = new AvrInstruction[] {
        // Initialize SP
        new AvrInstruction.LDI(28, 0x5f),
        new AvrInstruction.LDI(29, 0x2),
        new AvrInstruction.OUT(0x3e, 29),
        new AvrInstruction.OUT(0x3d, 28),
        // Read (Unknown) Input
        new AvrInstruction.IN(29, 0x16),
        new AvrInstruction.ANDI(29, 0xFF),
        // Compare against zero
        new AvrInstruction.CPI(29, 0),
        // Conditional Branch
        new AvrInstruction.BRLT(3),
        new AvrInstruction.NOP(),
        new AvrInstruction.NOP(),
        new AvrInstruction.RJMP(-1),
        new AvrInstruction.PUSH(0x16),
        new AvrInstruction.POP(0x16),
        new AvrInstruction.RJMP(-1) };
    // Check computation
    assertEquals(this.one, computeStackUsage(instructions));
  }

  /**
   * A Test.
   */
  @Test
  public void test_19() {
    AvrInstruction[] instructions = new AvrInstruction[] {
        // Initialize SP
        new AvrInstruction.LDI(28, 0x5f),
        new AvrInstruction.LDI(29, 0x2),
        new AvrInstruction.OUT(0x3e, 29),
        new AvrInstruction.OUT(0x3d, 28),
        // Read (Unknown) Input
        new AvrInstruction.IN(29, 0x16),
        new AvrInstruction.AND(29, 28),
        // Compare against zero
        new AvrInstruction.CPI(29, 0),
        // Conditional Branch
        new AvrInstruction.BREQ(3),
        new AvrInstruction.PUSH(0x16),
        new AvrInstruction.POP(0x16),
        new AvrInstruction.RJMP(-1),
        new AvrInstruction.RJMP(-1) };
    // Check computation
    assertEquals(this.one, computeStackUsage(instructions));
  }

  /**
   * A Test.
   */
  @Test
  public void test_20() {
    AvrInstruction[] instructions = new AvrInstruction[] {
        // Initialize SP
        new AvrInstruction.LDI(28, 0x5f),
        new AvrInstruction.LDI(29, 0x2),
        new AvrInstruction.OUT(0x3e, 29),
        new AvrInstruction.OUT(0x3d, 28),
        // Read (Unknown) Input
        new AvrInstruction.IN(16, 0x16),
        new AvrInstruction.LSR(16),
        // Compare against zero
        new AvrInstruction.CPI(16, 1),
        // Conditional Branch
        new AvrInstruction.BRLO(4),
        new AvrInstruction.PUSH(16),
        new AvrInstruction.PUSH(17),
        new AvrInstruction.POP(17),
        new AvrInstruction.POP(16),
        new AvrInstruction.RJMP(-1) };
    // Check computation
    assertEquals(this.two, computeStackUsage(instructions));
  }

  /**
   * A Test.
   */
  @Test
  public void test_21() {
    AvrInstruction[] instructions = new AvrInstruction[] {
        // Initialize SP
        new AvrInstruction.LDI(28, 0x5f),
        new AvrInstruction.LDI(29, 0x2),
        new AvrInstruction.OUT(0x3e, 29),
        new AvrInstruction.OUT(0x3d, 28),
        // Read (Unknown) Input
        new AvrInstruction.IN(16, 0x16),
        new AvrInstruction.SBRC(16, 1),
        // Compare against zero
        new AvrInstruction.CPI(16, 0),
        // Conditional Branch
        new AvrInstruction.BREQ(1),
        new AvrInstruction.RJMP(-1),
        new AvrInstruction.PUSH(16),
        new AvrInstruction.PUSH(17),
        new AvrInstruction.POP(17),
        new AvrInstruction.POP(16),
        new AvrInstruction.RJMP(-1) };
    // Check computation
    assertEquals(this.two, computeStackUsage(instructions));
  }

  /**
   * A Test.
   */
  @Test
  public void test_22() {
    AvrInstruction[] instructions = new AvrInstruction[] {
        // Initialize SP
        new AvrInstruction.LDI(28, 0x5f),
        new AvrInstruction.LDI(29, 0x2),
        new AvrInstruction.OUT(0x3e, 29),
        new AvrInstruction.OUT(0x3d, 28),
        // Read (Unknown) Input
        new AvrInstruction.IN(16, 0x16),
        new AvrInstruction.BST(16, 1),
        // Conditional Branch
        new AvrInstruction.BRTS(1),
        new AvrInstruction.RJMP(-1),
        new AvrInstruction.PUSH(16),
        new AvrInstruction.PUSH(17),
        new AvrInstruction.POP(17),
        new AvrInstruction.POP(16),
        new AvrInstruction.RJMP(-1) };
    // Check computation
    assertEquals(this.two, computeStackUsage(instructions));
  }

  /**
   * A Test.
   */
  @Test
  public void test_23() {
    AvrInstruction[] instructions = new AvrInstruction[] {
        // Initialize SP
        new AvrInstruction.LDI(28, 0x5f),
        new AvrInstruction.LDI(29, 0x2),
        new AvrInstruction.OUT(0x3e, 29),
        new AvrInstruction.OUT(0x3d, 28),
        // Read (Unknown) Input
        new AvrInstruction.IN(16, 0x16),
        new AvrInstruction.BST(16, 1),
        // Conditional Branch
        new AvrInstruction.BRTC(1),
        new AvrInstruction.RJMP(-1),
        new AvrInstruction.PUSH(16),
        new AvrInstruction.PUSH(17),
        new AvrInstruction.POP(17),
        new AvrInstruction.POP(16),
        new AvrInstruction.RJMP(-1) };
    // Check computation
    assertEquals(this.two, computeStackUsage(instructions));
  }

  /**
   * A Test.
   */
  @Test
  public void test_24() {
    AvrInstruction[] instructions = new AvrInstruction[] {
        // Initialize SP
        new AvrInstruction.LDI(28, 0x5f),
        new AvrInstruction.LDI(29, 0x2),
        new AvrInstruction.OUT(0x3e, 29),
        new AvrInstruction.OUT(0x3d, 28),
        // Read (Unknown) Input
        new AvrInstruction.IN(16, 0x16),
        new AvrInstruction.BST(16, 1),
        new AvrInstruction.BLD(16, 1),
        // Compare against zero
        new AvrInstruction.CPI(16, 0),
        // Conditional Branch
        new AvrInstruction.BRNE(1),
        new AvrInstruction.RJMP(-1),
        new AvrInstruction.PUSH(16),
        new AvrInstruction.PUSH(17),
        new AvrInstruction.POP(17),
        new AvrInstruction.POP(16),
        new AvrInstruction.RJMP(-1) };
    // Check computation
    assertEquals(this.two, computeStackUsage(instructions));
  }

  /**
   * A Test.
   */
  @Test
  public void test_25() {
    AvrInstruction[] instructions = new AvrInstruction[] {
        // Initialize SP
        new AvrInstruction.LDI(28, 0x5f),
        new AvrInstruction.LDI(29, 0x2),
        new AvrInstruction.OUT(0x3e, 29),
        new AvrInstruction.OUT(0x3d, 28),
        // Read (Unknown) Input
        new AvrInstruction.IN(16, 0x16),
        // Compare two registers and skip
        new AvrInstruction.CPSE(16, 29),
        // Conditional Branch
        new AvrInstruction.RJMP(-1),
        new AvrInstruction.PUSH(16),
        new AvrInstruction.PUSH(17),
        new AvrInstruction.POP(17),
        new AvrInstruction.POP(16),
        new AvrInstruction.RJMP(-1) };
    // Check computation
    assertEquals(this.two, computeStackUsage(instructions));
  }

  /**
   * A Test.
   */
  @Test
  public void test_26() {
    AvrInstruction[] instructions = new AvrInstruction[] {
        // Initialize SP
        new AvrInstruction.LDI(28, 0x5f),
        new AvrInstruction.LDI(29, 0x2),
        new AvrInstruction.OUT(0x3e, 29),
        new AvrInstruction.OUT(0x3d, 28),
        // Read (Unknown) Input
        new AvrInstruction.IN(16, 0x16),
        // Compare against zero
        new AvrInstruction.CPI(16, 0),
        // Conditional Branch
        new AvrInstruction.BRHC(3),
        new AvrInstruction.PUSH(16),
        new AvrInstruction.POP(16),
        new AvrInstruction.RJMP(1),
        new AvrInstruction.RCALL(1),
        new AvrInstruction.RJMP(-1),
        new AvrInstruction.PUSH(16),
        new AvrInstruction.PUSH(17),
        new AvrInstruction.PUSH(18),
        new AvrInstruction.PUSH(19),
        new AvrInstruction.LDI(16, 1),
        new AvrInstruction.LDI(17, 2),
        new AvrInstruction.ADD(17, 18),
        new AvrInstruction.POP(19),
        new AvrInstruction.POP(18),
        new AvrInstruction.POP(17),
        new AvrInstruction.POP(16),
        new AvrInstruction.RET(), };
    // Check computation
    assertEquals(this.six, computeStackUsage(instructions));
  }

  /**
   * A Test.
   */
  @Test
  public void test_27() {
    AvrInstruction[] instructions = new AvrInstruction[] {
        // Initialize SP
        new AvrInstruction.LDI(28, 0x5f),
        new AvrInstruction.LDI(29, 0x2),
        new AvrInstruction.OUT(0x3e, 29),
        new AvrInstruction.OUT(0x3d, 28),
        // Read (Unknown) Input
        new AvrInstruction.IN(16, 0x16),
        // Compare against zero
        new AvrInstruction.CPI(16, 0),
        // Conditional Branch
        new AvrInstruction.BRHC(1),
        new AvrInstruction.RCALL(1),
        new AvrInstruction.RJMP(-1),
        new AvrInstruction.PUSH(16),
        new AvrInstruction.PUSH(17),
        new AvrInstruction.PUSH(18),
        new AvrInstruction.PUSH(19),
        new AvrInstruction.LDI(16, 1),
        new AvrInstruction.LDI(17, 2),
        new AvrInstruction.ADD(17, 18),
        new AvrInstruction.POP(19),
        new AvrInstruction.POP(18),
        new AvrInstruction.POP(17),
        new AvrInstruction.POP(16),
        new AvrInstruction.RET(), };
    // Check computation
    assertEquals(this.six, computeStackUsage(instructions));
  }

  /**
   * A Test.
   */
  @Test
  public void test_28() {
    AvrInstruction[] instructions = new AvrInstruction[] {
        // Initialize SP
        new AvrInstruction.LDI(28, 0x5f),
        new AvrInstruction.LDI(29, 0x2),
        new AvrInstruction.OUT(0x3e, 29),
        new AvrInstruction.OUT(0x3d, 28),
        // Read (Unknown) Input
        new AvrInstruction.IN(16, 0x16),
        // Compare against one
        new AvrInstruction.CPI(16, 0xff),
        // Conditional Branch
        new AvrInstruction.BRGE(5),
        new AvrInstruction.PUSH(16),
        new AvrInstruction.PUSH(17),
        new AvrInstruction.POP(17),
        new AvrInstruction.POP(16),
        new AvrInstruction.RJMP(2),
        new AvrInstruction.PUSH(16),
        new AvrInstruction.POP(16),
        new AvrInstruction.RJMP(-1), };
    // Check computation
    assertEquals(this.two, computeStackUsage(instructions));
  }

  /**
   * A Test.
   */
  @Test
  public void test_29() {
    AvrInstruction[] instructions = new AvrInstruction[] {
        // Initialize SP
        new AvrInstruction.LDI(28, 0x5f),
        new AvrInstruction.LDI(29, 0x2),
        new AvrInstruction.OUT(0x3e, 29),
        new AvrInstruction.OUT(0x3d, 28),
        // Read (Unknown) Input
        new AvrInstruction.IN(16, 0x16),
        // Compare against zero
        new AvrInstruction.CPI(16, 0),
        // Conditional Branch
        new AvrInstruction.BRGE(3),
        new AvrInstruction.PUSH(16),
        new AvrInstruction.POP(16),
        new AvrInstruction.RJMP(4),
        new AvrInstruction.PUSH(16),
        new AvrInstruction.PUSH(17),
        new AvrInstruction.POP(17),
        new AvrInstruction.POP(16),
        new AvrInstruction.RJMP(-1), };
    // Check computation
    assertEquals(this.two, computeStackUsage(instructions));
  }

  /**
   * A Test.
   */
  @Test
  public void test_30() {
    AvrInstruction[] instructions = new AvrInstruction[] {
        // Initialize SP
        new AvrInstruction.LDI(28, 0x5f),
        new AvrInstruction.LDI(29, 0x2),
        new AvrInstruction.OUT(0x3e, 29),
        new AvrInstruction.OUT(0x3d, 28),
        // Read (Unknown) Input
        new AvrInstruction.IN(16, 0x16),
        // Compare against zero
        new AvrInstruction.CPI(16, 0),
        // Clear zero flag
        new AvrInstruction.CLZ(),
        // Conditional Branch
        new AvrInstruction.BRBC(1, 4),
        // Dead code
        new AvrInstruction.PUSH(16),
        new AvrInstruction.PUSH(17),
        new AvrInstruction.POP(17),
        new AvrInstruction.POP(16),
        // Live code
        new AvrInstruction.RJMP(-1) };
    // Check computation
    assertEquals(this.zero, computeStackUsage(instructions));
  }

  /**
   * A Test.
   */
  @Test
  public void test_31() {
    AvrInstruction[] instructions = new AvrInstruction[] {
        // Initialize SP
        new AvrInstruction.LDI(28, 0x5f),
        new AvrInstruction.LDI(29, 0x2),
        new AvrInstruction.OUT(0x3e, 29),
        new AvrInstruction.OUT(0x3d, 28),
        // Read (Unknown) Input
        new AvrInstruction.IN(16, 0x16),
        // Compare against zero
        new AvrInstruction.CPI(16, 0),
        // Clear zero flag
        new AvrInstruction.SEZ(),
        // Conditional Branch
        new AvrInstruction.BRBC(1, 4),
        new AvrInstruction.PUSH(16),
        new AvrInstruction.PUSH(17),
        new AvrInstruction.POP(17),
        new AvrInstruction.POP(16),
        new AvrInstruction.RJMP(-1) };
    // Check computation
    assertEquals(this.two, computeStackUsage(instructions));
  }

  /**
   * A Test.
   */
  @Test
  public void test_32() {
    AvrInstruction[] instructions = new AvrInstruction[] {
        // Initialize SP
        new AvrInstruction.LDI(28, 0x5f),
        new AvrInstruction.LDI(29, 0x2),
        new AvrInstruction.OUT(0x3e, 29),
        new AvrInstruction.OUT(0x3d, 28),
        // Read (Unknown) Input
        new AvrInstruction.IN(16, 0x16),
        // Compare against zero
        new AvrInstruction.CPI(16, 0),
        // Clear zero flag
        new AvrInstruction.CLZ(),
        // Conditional Branch
        new AvrInstruction.BRBS(1, 4),
        // Dead code
        new AvrInstruction.PUSH(16),
        new AvrInstruction.PUSH(17),
        new AvrInstruction.POP(17),
        new AvrInstruction.POP(16),
        // Live code
        new AvrInstruction.RJMP(-1) };
    // Check computation
    assertEquals(this.two, computeStackUsage(instructions));
  }

  /**
   * A Test.
   */
  @Test
  public void test_33() {
    AvrInstruction[] instructions = new AvrInstruction[] {
        // Initialize SP
        new AvrInstruction.LDI(28, 0x5f),
        new AvrInstruction.LDI(29, 0x2),
        new AvrInstruction.OUT(0x3e, 29),
        new AvrInstruction.OUT(0x3d, 28),
        // Read (Unknown) Input
        new AvrInstruction.IN(16, 0x16),
        // Compare against zero
        new AvrInstruction.CPI(16, 0),
        // Clear zero flag
        new AvrInstruction.CLC(),
        // Conditional Branch
        new AvrInstruction.BRBC(1, 4),
        // Dead code
        new AvrInstruction.PUSH(16),
        new AvrInstruction.PUSH(17),
        new AvrInstruction.POP(17),
        new AvrInstruction.POP(16),
        // Live code
        new AvrInstruction.RJMP(-1) };
    // Check computation
    assertEquals(this.two, computeStackUsage(instructions));
  }

  /**
   * A Test.
   */
  @Test
  public void test_34() {
    AvrInstruction[] instructions = new AvrInstruction[] {
        // Initialize SP
        new AvrInstruction.LDI(28, 0x5f),
        new AvrInstruction.LDI(29, 0x2),
        new AvrInstruction.OUT(0x3e, 29),
        new AvrInstruction.OUT(0x3d, 28),
        // Read (Unknown) Input
        new AvrInstruction.IN(16, 0x16),
        // Compare against zero
        new AvrInstruction.CPI(16, 0),
        // Conditional Branch
        new AvrInstruction.BREQ(5),
        new AvrInstruction.BRNE(4),
        // Dead code
        new AvrInstruction.PUSH(16),
        new AvrInstruction.PUSH(17),
        new AvrInstruction.POP(17),
        new AvrInstruction.POP(16),
        // Live code
        new AvrInstruction.RJMP(-1) };
    // Check computation
    assertEquals(this.zero, computeStackUsage(instructions));
  }

  /**
   * A Test.
   */
  @Test
  public void test_35() {
    AvrInstruction[] instructions = new AvrInstruction[] {
        // Initialize SP
        new AvrInstruction.LDI(28, 0x5f),
        new AvrInstruction.LDI(29, 0x2),
        new AvrInstruction.OUT(0x3e, 29),
        new AvrInstruction.OUT(0x3d, 28),
        // Read (Unknown) Input
        new AvrInstruction.IN(16, 0x16),
        // Compare against zero
        new AvrInstruction.CPI(16, 0),
        // Conditional Branch
        new AvrInstruction.BREQ(6),
        new AvrInstruction.BREQ(1),
        new AvrInstruction.RJMP(-1),
        // Dead code
        new AvrInstruction.PUSH(16),
        new AvrInstruction.PUSH(17),
        new AvrInstruction.POP(17),
        new AvrInstruction.POP(16),
        // Live code
        new AvrInstruction.PUSH(16),
        new AvrInstruction.POP(16),
        new AvrInstruction.RJMP(-1) };
    // Check computation
    assertEquals(this.one, computeStackUsage(instructions));
  }

  /**
   * A Test.
   */
  @Test
  public void test_36() {
    AvrInstruction[] instructions = new AvrInstruction[] {
        // Initialize SP
        new AvrInstruction.LDI(28, 0x5f),
        new AvrInstruction.LDI(29, 0x2),
        new AvrInstruction.OUT(0x3e, 29),
        new AvrInstruction.OUT(0x3d, 28),
        // Read (Unknown) Input
        new AvrInstruction.IN(16, 0x16),
        // Compare against zero
        new AvrInstruction.CPI(16, 0),
        // Conditional Branch
        new AvrInstruction.BRNE(1),
        new AvrInstruction.RJMP(5),
        new AvrInstruction.BRNE(4),
        // Dead code
        new AvrInstruction.PUSH(16),
        new AvrInstruction.PUSH(17),
        new AvrInstruction.POP(17),
        new AvrInstruction.POP(16),
        // Live code
        new AvrInstruction.RJMP(-1) };
    // Check computation
    assertEquals(this.zero, computeStackUsage(instructions));
  }

  /**
   * A Test.
   */
  @Test
  public void test_37() {
    AvrInstruction[] instructions = new AvrInstruction[] {
        // Initialize SP
        new AvrInstruction.LDI(28, 0x5f),
        new AvrInstruction.LDI(29, 0x2),
        new AvrInstruction.OUT(0x3e, 29),
        new AvrInstruction.OUT(0x3d, 28),
        // Read (Unknown) Input
        new AvrInstruction.IN(16, 0x16),
        // Compare against zero
        new AvrInstruction.CPI(16, 0),
        // Conditional Branch
        new AvrInstruction.BRNE(6),
        new AvrInstruction.BRNE(1),
        new AvrInstruction.RJMP(-1),
        // Dead code
        new AvrInstruction.PUSH(16),
        new AvrInstruction.PUSH(17),
        new AvrInstruction.POP(17),
        new AvrInstruction.POP(16),
        // Live code
        new AvrInstruction.PUSH(17),
        new AvrInstruction.POP(17),
        new AvrInstruction.RJMP(-1) };
    // Check computation
    assertEquals(this.one, computeStackUsage(instructions));
  }
}
