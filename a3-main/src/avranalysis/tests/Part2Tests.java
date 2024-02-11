package avranalysis.tests;

import static avranalysis.tests.Part1Tests.computeStackUsage;
import static org.junit.Assert.assertEquals;

import javr.core.AvrInstruction;
import javr.core.AvrInstruction.CALL;
import javr.core.AvrInstruction.MOV;
import javr.core.AvrInstruction.NOP;
import javr.core.AvrInstruction.POP;
import javr.core.AvrInstruction.PUSH;
import javr.core.AvrInstruction.RCALL;
import javr.core.AvrInstruction.RET;
import javr.core.AvrInstruction.RJMP;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * Tests for part 2 of the assignment.
 *
 * @author David J. Pearce
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Part2Tests {
  /**
   * A dummy constant representing one. This is used to prevent Eclipse errors
   * being reported on the test methods.
   */
  private final int two = 2;
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
   * A test.
   */
  @Test
  public void test_01() {
    AvrInstruction[] instructions = new AvrInstruction[] {
        new RCALL(1),
        new RJMP(-1),
        new RET()
    };
    // Check computation
    assertEquals(this.two, computeStackUsage(instructions));
  }

  /**
   * A test.
   */
  @Test
  public void test_02() {
    AvrInstruction[] instructions = new AvrInstruction[] {
        new RCALL(1),
        new RJMP(-1),
        new PUSH(16),
        new NOP(),
        new POP(16),
        new RET()
    };
    // Check computation
    assertEquals(this.three, computeStackUsage(instructions));
  }

  /**
   * A test.
   */
  @Test
  public void test_03() {
    AvrInstruction[] instructions = new AvrInstruction[] {
        new CALL(0x0003), // 0x0000
        new RJMP(-1),     // 0x0002
        new RCALL(1),     // 0x0003
        new RET(),        // 0x0004
        new RET()         // 0x0005
    };
    // Check computation
    assertEquals(this.four, computeStackUsage(instructions));
  }

  /**
   * A test.
   */
  @Test
  public void test_04() {
    AvrInstruction[] instructions = new AvrInstruction[] {
        new CALL(0x0005), // 0x0000
        new CALL(0x0009), // 0x0002
        new RJMP(-1),     // 0x0004
        new RCALL(1),     // 0x0005
        new RET(),        // 0x0006
        new RCALL(1),     // 0x0007
        new RET(),        // 0x0008
        new RET(),        // 0x0009
    };
    // Check computation
    assertEquals(this.three * 2, computeStackUsage(instructions));
  }

  /**
   * A test.
   */
  @Test
  public void test_05() {
    AvrInstruction[] instructions = new AvrInstruction[] {
        new CALL(0x0009), // 0x0000
        new CALL(0x0005), // 0x0002
        new RJMP(-1),     // 0x0004
        new RCALL(1),     // 0x0005
        new RET(),        // 0x0006
        new RCALL(1),     // 0x0007
        new RET(),        // 0x0008
        new RET(),        // 0x0009
    };
    // Check computation
    assertEquals(this.three * 2, computeStackUsage(instructions));
  }

  /**
   * A test.
   */
  @Test
  public void test_06() {
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
        new MOV(18, 16),   // 0x000C
        new POP(16),      // 0x000D
        new POP(17),      // 0x000E
        new POP(18),      // 0x000F
        new RET(),        // 0x0010
    };
    // Check computation
    assertEquals(this.three * 3, computeStackUsage(instructions));
  }

  /**
   * A test.
   */
  @Test
  public void test_07() {
    AvrInstruction[] instructions = new AvrInstruction[] {
        new RJMP(1),
        new RET(),
        new RCALL(-2),
    };
    // Check computation
    assertEquals(this.two, computeStackUsage(instructions));
  }

  /**
   * A test.
   */
  @Test
  public void test_08() {
    AvrInstruction[] instructions = new AvrInstruction[] {
        new RJMP(1),      // 0x0000
        new RET(),        // 0x0001
        new CALL(0x0001), // 0x0002
    };
    // Check computation
    assertEquals(this.two, computeStackUsage(instructions));
  }

  /**
   * A test.
   */
  @Test
  public void test_09() {
    AvrInstruction[] instructions = new AvrInstruction[] {
        new RCALL(1),
        new RJMP(-1),
        new RCALL(1),
        new RET(),
        new RCALL(1),
        new RET(),
        new RCALL(1),
        new RET(),
        new RCALL(1),
        new RET(),
        new RCALL(1),
        new RET(),
        new RCALL(1),
        new RET(),
        new RCALL(1),
        new RET(),
        new RCALL(1),
        new RET(),
        new RET()
    };
    // Check computation
    assertEquals(this.three * 6, computeStackUsage(instructions));
  }

  /**
   * A test.
   */
  @Test
  public void test_10() {
    AvrInstruction[] instructions = new AvrInstruction[] {
        new RCALL(1),
        new RJMP(-1),
        new RCALL(2),
        new RCALL(2),
        new RET(),
        new RCALL(2),
        new RCALL(2),
        new RET(),
        new RCALL(2),
        new RCALL(2),
        new RET(),
        new RCALL(1),
        new RET(),
        new RET()
    };
    // Check computation
    assertEquals(this.two * 5, computeStackUsage(instructions));
  }
}
