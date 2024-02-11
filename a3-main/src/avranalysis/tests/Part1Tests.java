package avranalysis.tests;

import static org.junit.Assert.assertEquals;

import avranalysis.core.StackAnalysis;
import javr.core.AvrInstruction;
import javr.core.AvrInstruction.ADD;
import javr.core.AvrInstruction.JMP;
import javr.core.AvrInstruction.LDI;
import javr.core.AvrInstruction.NOP;
import javr.core.AvrInstruction.POP;
import javr.core.AvrInstruction.PUSH;
import javr.core.AvrInstruction.RJMP;
import javr.core.AvrInstruction.SUB;
import javr.io.HexFile;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;


/**
 * Tests for part 1 of the assignment.
 *
 * @author David J. Pearce
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Part1Tests {
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
        new NOP()
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
        new LDI(16, 1)
    };
    // Check computation
    assertEquals(this.zero, computeStackUsage(instructions));
  }

  /**
   * A test.
   */
  @Test
  public void test_03() {
    AvrInstruction[] instructions = new AvrInstruction[] {
        new LDI(16, 1),
        new LDI(17, 2),
        new ADD(16, 17)
    };
    // Check computation
    assertEquals(this.zero, computeStackUsage(instructions));
  }

  /**
   * A test.
   */
  @Test
  public void test_04() {
    AvrInstruction[] instructions = new AvrInstruction[] {
        new LDI(16, 1),
        new LDI(17, 2),
        new SUB(16, 17)
    };
    // Check computation
    assertEquals(this.zero, computeStackUsage(instructions));
  }

  /**
   * A test.
   */
  @Test
  public void test_05() {
    AvrInstruction[] instructions = new AvrInstruction[] {
        new RJMP(1),
        new NOP(),
        new LDI(16, 1)
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
        new LDI(16, 1),
        new PUSH(16)
    };
    // Check computation
    assertEquals(this.one, computeStackUsage(instructions));
  }

  /**
   * A test.
   */
  @Test
  public void test_07() {
    AvrInstruction[] instructions = new AvrInstruction[] {
        new LDI(16, 1),
        new PUSH(16),
        new POP(16)
    };
    // Check computation
    assertEquals(this.one, computeStackUsage(instructions));
  }

  /**
   * A test.
   */
  @Test
  public void test_08() {
    AvrInstruction[] instructions = new AvrInstruction[] {
        new LDI(16, 1),
        new LDI(17, 1),
        new PUSH(16),
        new PUSH(17),
        new POP(17),
        new POP(16)
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
        new LDI(16, 1),
        new PUSH(16),
        new LDI(17, 1),
        new PUSH(17),
        new LDI(18, 1),
        new PUSH(18),
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
  public void test_10() {
    // NOTE: JMP is a 32-bit instruction.
    AvrInstruction[] instructions = new AvrInstruction[] {
        new JMP(0x0004), // 0x0000
        new LDI(16, 1),  // 0x0002
        new PUSH(16),    // 0x0003
        new LDI(16, 1)   // 0x0004
    };
    // Check computation
    assertEquals(this.zero, computeStackUsage(instructions));
  }

  /**
   * A test.
   */
  @Test
  public void test_11() {
    AvrInstruction[] instructions = new AvrInstruction[] {
        new RJMP(2),
        new LDI(16, 1),
        new POP(16),
        new LDI(16, 1)
    };
    // Check computation
    assertEquals(this.zero, computeStackUsage(instructions));
  }

  /**
   * A test.
   */
  @Test
  public void test_12() {
    AvrInstruction[] instructions = new AvrInstruction[] {
        new RJMP(2),
        new LDI(16, 1),
        new PUSH(16),
        new RJMP(2),
        new LDI(16, 1),
        new POP(16),
        new NOP()
    };
    // Check computation
    assertEquals(this.zero, computeStackUsage(instructions));
  }

  /**
   * For a given sequence of instructions compute the maximum stack usage.
   *
   * @param instructions List of instructions to compute stack usage on.
   * @return Stack usage (in bytes).
   */
  public static int computeStackUsage(AvrInstruction... instructions) {
    // Assemble instructions into hexfile
    HexFile hf = assemble(instructions);
    // Compute stack usage
    return new StackAnalysis(hf).apply();
  }

  /**
   * Responsible for turning a given sequence of instructions into a hexfile, so
   * that it can in turn be uploaded to the stack analysis.
   *
   * @param instructions List of instructions to assemble.
   * @return The assembled hexfile.
   */
  private static HexFile assemble(AvrInstruction... instructions) {
    byte[][] chunks = new byte[instructions.length][];
    int total = 0;
    // Encode each instruction into a byte sequence
    for (int i = 0; i != instructions.length; ++i) {
      byte[] bytes = instructions[i].getBytes();
      chunks[i] = bytes;
      total = total + bytes.length;
    }
    // Flatten the chunks into a sequence
    byte[] sequence = new byte[total];
    //
    for (int i = 0, j = 0; i != chunks.length; ++i) {
      byte[] chunk = chunks[i];
      System.arraycopy(chunk, 0, sequence, j, chunk.length);
      j = j + chunk.length;
    }
    // Finally, create the hex file!
    HexFile hf = HexFile.toHexFile(sequence, 16);
    assert hf != null;
    return hf;
  }
}
