package avranalysis.tests;

import static avranalysis.tests.Part1Tests.computeStackUsage;
import static org.junit.Assert.assertEquals;

import javr.core.AvrInstruction;
import javr.core.AvrInstruction.ADD;
import javr.core.AvrInstruction.BREQ;
import javr.core.AvrInstruction.BRGE;
import javr.core.AvrInstruction.BRLT;
import javr.core.AvrInstruction.JMP;
import javr.core.AvrInstruction.LDI;
import javr.core.AvrInstruction.NOP;
import javr.core.AvrInstruction.POP;
import javr.core.AvrInstruction.PUSH;
import javr.core.AvrInstruction.RCALL;
import javr.core.AvrInstruction.RET;
import javr.core.AvrInstruction.RJMP;
import javr.core.AvrInstruction.SBRS;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * Tests for part 3 of the assignment.
 *
 * @author David J. Pearce
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Part3Tests {
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
   * A dummy constant representing three. This is used to prevent Eclipse errors
   * being reported on the test methods.
   */
  private final int three = 3;

  /**
   * A test.
   */
  @Test
  public void test_01() {
    AvrInstruction[] instructions = new AvrInstruction[] {
        new BREQ(1),
        new NOP(),
        new RJMP(-1)
    };
    // Check computation
    assertEquals(this.zero, computeStackUsage(instructions));
  }

  /**
   * A test.
   */
  @Test
  public void test_02() {
    AvrInstruction[] instructions = new AvrInstruction[] {
        new BRGE(2),
        new PUSH(16),
        new POP(16),
        new RJMP(-1)
    };
    // Check computation
    assertEquals(this.one, computeStackUsage(instructions));
  }

  /**
   * A test.
   */
  @Test
  public void test_03() {
    AvrInstruction[] instructions = new AvrInstruction[] {
        new BRGE(3),
        new PUSH(16),
        new POP(16),
        new RJMP(1),
        new RCALL(1),
        new RJMP(-1),
        new PUSH(16),
        new PUSH(17),
        new PUSH(18),
        new LDI(16, 1),
        new LDI(17, 2),
        new ADD(17, 18),
        new POP(18),
        new POP(17),
        new POP(16),
        new RET(),
    };
    // Check computation
    assertEquals(this.two + this.three, computeStackUsage(instructions));
  }

  /**
   * A test.
   */
  @Test
  public void test_04() {
    AvrInstruction[] instructions = new AvrInstruction[] {
        new BRGE(3),
        new PUSH(16),
        new POP(16),
        new RJMP(3),
        new BRLT(2),
        new PUSH(16),
        new POP(16),
        new RJMP(-1),
    };
    // Check computation
    assertEquals(this.one, computeStackUsage(instructions));
  }

  /**
   * A test.
   */
  @Test
  public void test_05() {
    AvrInstruction[] instructions = new AvrInstruction[] {
        new SBRS(16, 1),
        new NOP(),
        new RJMP(-1)
    };
    // Check computation
    assertEquals(this.zero, computeStackUsage(instructions));
  }

  /**
   * A test.
   */
  @Test
  public void test_06() {
    AvrInstruction[] instructions = new AvrInstruction[] {
        new SBRS(16, 1),
        new RCALL(1),
        new RJMP(-1),
        new PUSH(16),
        new PUSH(17),
        new PUSH(18),
        new LDI(16, 1),
        new LDI(17, 2),
        new ADD(17, 18),
        new POP(18),
        new POP(17),
        new POP(16),
        new RET(),
    };
    // Check computation
    assertEquals(this.two + this.three, computeStackUsage(instructions));
  }

  /**
   * A test.
   */
  @Test
  public void test_07() {
    AvrInstruction[] instructions = new AvrInstruction[] {
        new SBRS(16, 1),
        new RJMP(1),
        new RJMP(-1),
        new PUSH(16),
        new PUSH(17),
        new PUSH(18),
        new LDI(16, 1),
        new LDI(17, 2),
        new ADD(17, 18),
        new POP(18),
        new POP(17),
        new POP(16)
    };
    // Check computation
    assertEquals(this.three, computeStackUsage(instructions));
  }

  /**
   * A test.
   */
  @Test
  public void test_08() {
    AvrInstruction[] instructions = new AvrInstruction[] {
        new SBRS(16, 1),
        new RJMP(3),
        new PUSH(16),
        new POP(16),
        new RJMP(-1),
        new PUSH(16),
        new POP(16),
        new RJMP(-1),
    };
    // Check computation
    assertEquals(this.one, computeStackUsage(instructions));
  }

  /**
   * A test.
   */
  @Test
  public void test_09() {
    AvrInstruction[] instructions = new AvrInstruction[] {
        new SBRS(16, 1),
        new JMP(0x92FF << 6), // hehe :)
        new NOP()
    };
    // Check computation
    assertEquals(this.zero, computeStackUsage(instructions));
  }

  /**
   * A test.
   */
  @Test
  public void test_10() {
    AvrInstruction[] instructions = new AvrInstruction[] {
        new SBRS(16, 1),
        new RJMP(3),
        new PUSH(16),
        new POP(16),
        new RJMP(-1),
    };
    // Check computation
    assertEquals(this.one, computeStackUsage(instructions));
  }
}