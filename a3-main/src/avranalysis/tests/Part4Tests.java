package avranalysis.tests;

import static avranalysis.tests.Part1Tests.computeStackUsage;
import static org.junit.Assert.assertEquals;

import javr.core.AvrInstruction;
import javr.core.AvrInstruction.BREQ;
import javr.core.AvrInstruction.BRGE;
import javr.core.AvrInstruction.CALL;
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
 * Tests for part 4 of the assignment.
 *
 * @author David J. Pearce
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Part4Tests {
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
   * A dummy constant representing MAX. This is used to prevent Eclipse errors
   * being reported on the test methods.
   */
  private final int max = Integer.MAX_VALUE;

  /**
   * A test.
   */
  @Test
  public void test_01() {
    AvrInstruction[] instructions = new AvrInstruction[] {
        new BREQ(2),
        new NOP(),
        new RJMP(-3),
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
        new BREQ(4),
        new PUSH(16),
        new NOP(),
        new POP(16),
        new RJMP(-5),
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
        new RJMP(3),
        new PUSH(16),
        new NOP(),
        new POP(16),
        new BRGE(-4),
        new RJMP(-1)
    };
    // Check computation
    assertEquals(this.one, computeStackUsage(instructions));
  }

  /**
   * A test.
   */
  @Test
  public void test_04() {
    AvrInstruction[] instructions = new AvrInstruction[] {
        new RJMP(2),
        new PUSH(16),
        new NOP(),
        new BRGE(-3),
        new RJMP(-1)
    };
    // Check computation
    assertEquals(this.max, computeStackUsage(instructions));
  }

  /**
   * A test.
   */
  @Test
  public void test_05() {
    AvrInstruction[] instructions = new AvrInstruction[] {
        new RJMP(3),
        new PUSH(16),
        new RCALL(3),
        new POP(16),
        new BRGE(-4),
        new RJMP(-1),
        new PUSH(16),
        new NOP(),
        new POP(16),
        new RET()
    };
    // Check computation
    assertEquals(this.two * 2, computeStackUsage(instructions));
  }

  /**
   * A test.
   */
  @Test
  public void test_06() {
    AvrInstruction[] instructions = new AvrInstruction[] {
        new RJMP(4),
        new PUSH(16),
        new SBRS(16, 1),
        new RCALL(3),
        new POP(16),
        new BRGE(-5),
        new RJMP(-1),
        new PUSH(16),
        new NOP(),
        new POP(16),
        new RET()
    };
    // Check computation
    assertEquals(this.two * 2, computeStackUsage(instructions));
  }

  /**
   * A test.
   */
  @Test
  public void test_07() {
    AvrInstruction[] instructions = new AvrInstruction[] {
        new PUSH(16),
        new RCALL(4),
        new POP(16),
        new SBRS(16, 1),
        new RJMP(-5),
        new RJMP(-1),
        new PUSH(16),
        new NOP(),
        new POP(16),
        new RET()
    };
    // Check computation
    assertEquals(this.two * 2, computeStackUsage(instructions));
  }

  /**
   * A test.
   */
  @Test
  public void test_08() {
    AvrInstruction[] instructions = new AvrInstruction[] {
        new CALL(0x0009), // 0x0000
        new CALL(0x0005), // 0x0002
        new RJMP(-1),     // 0x0004
        new RCALL(1),     // 0x0005
        new RET(),        // 0x0006
        new RCALL(1),     // 0x0007
        new RET(),        // 0x0008
        new BREQ(2),      // 0x0009
        new NOP(),        // 0x000A
        new RJMP(-3),     // 0x000B
        new RET(),        // 0x000C
    };
    // Check computation
    assertEquals(this.three * 2, computeStackUsage(instructions));
  }


  /**
   * A test.
   */
  @Test
  public void test_09() {
    AvrInstruction[] instructions = new AvrInstruction[] {
        new CALL(0x0009), // 0x0000
        new CALL(0x0005), // 0x0002
        new RJMP(-1),     // 0x0004
        new RCALL(1),     // 0x0005
        new RET(),        // 0x0006
        new RCALL(1),     // 0x0007
        new RET(),        // 0x0008
        new PUSH(16),     // 0x0009
        new PUSH(17),     // 0x000A
        new PUSH(18),     // 0x000B
        new BREQ(2),      // 0x000C
        new NOP(),        // 0x000D
        new RJMP(-3),     // 0x000E
        new POP(16),      // 0x000F
        new POP(17),      // 0x0010
        new POP(18),      // 0x0011
        new RET(),        // 0x0012
    };
    // Check computation
    assertEquals(this.three * 3, computeStackUsage(instructions));
  }

  /**
   * A test.
   */
  @Test
  public void test_10() {
    AvrInstruction[] instructions = new AvrInstruction[] {
        new RCALL(2),
        new RJMP(-1),
        new PUSH(16),
        new RCALL(-2), // recurse
        new POP(16),
        new RET()
    };
    // Check computation
    assertEquals(this.max, computeStackUsage(instructions));
  }

  /**
   * A test.
   */
  @Test
  public void test_11() {
    AvrInstruction[] instructions = new AvrInstruction[] {
        new RCALL(2),
        new RJMP(-1),
        new PUSH(16),
        new RCALL(2), // mutually recurse
        new POP(16),
        new RET(),
        new PUSH(16),
        new RCALL(-6), // mutually recurse
        new POP(16),
        new RET(),
    };
    // Check computation
    assertEquals(this.max, computeStackUsage(instructions));
  }

  /**
   * A test.
   */
  @Test
  public void test_12() {
    AvrInstruction[] instructions = new AvrInstruction[] {
        new RCALL(2),
        new RJMP(-1),
        new PUSH(16),
        new RCALL(2), // mutually recurse
        new POP(16),
        new RET(),
        new PUSH(16),
        new RCALL(2), // mutually recurse
        new POP(16),
        new RET(),
        new PUSH(16),
        new RCALL(-10), // mutually recurse
        new POP(16),
        new RET(),
    };
    // Check computation
    assertEquals(this.max, computeStackUsage(instructions));
  }
}
