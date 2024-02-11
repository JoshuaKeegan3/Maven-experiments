package avranalysis.core;

import java.util.HashMap;
import java.util.Map;
import javr.core.AvrDecoder;
import javr.core.AvrInstruction;
import javr.core.AvrInstruction.BREQ;
import javr.core.AvrInstruction.BRGE;
import javr.core.AvrInstruction.BRLT;
import javr.core.AvrInstruction.BRNE;
import javr.core.AvrInstruction.CALL;
import javr.core.AvrInstruction.JMP;
import javr.core.AvrInstruction.RelativeAddress;
import javr.io.HexFile;
import javr.memory.ElasticByteMemory;
import org.eclipse.jdt.annotation.Nullable;

/**
 * Responsible for determining the worst-case stack analysis for a given AVR
 * program.
 *
 * @author David J. Pearce
 *
 */
public class StackAnalysis {
  /**
   * Contains the raw bytes of the given firmware image being analysed.
   */
  private ElasticByteMemory firmware;

  /**
   * The decoder is used for actually decoding an instruction.
   */
  private AvrDecoder decoder = new AvrDecoder();

  /**
   * Records the maximum height seen so far.
   */
  private int maxHeight;

  /**
   * Construct a new analysis instance for a given hex file.
   *
   * @param hf Hexfile on which the analysis will be run.
   */
  public StackAnalysis(HexFile hf) {
    // Create firmware memory
    this.firmware = new ElasticByteMemory();
    // Upload image to firmware memory
    hf.uploadTo(this.firmware);
  }

  /**
   * Apply the stack analysis to the given firmware image producing a maximum
   * stack usage (in bytes).
   *
   * @return The maximum height observed thus far.
   */
  public int apply() {
    // Reset the maximum, height
    this.maxHeight = 0;
    // Traverse instructions starting at beginning
    traverse(0, 0, new HashMap<>());
    // Return the maximum height observed
    return this.maxHeight;
  }

  /**
   * Traverse the instruction at a given pc address, assuming the stack has a
   * given height on entry.
   *
   * @param pc            Program Counter of instruction to traverse
   * @param currentHeight Current height of the stack at this point (in bytes)
   */
  private void traverse(int pc, int currentHeight,
      Map<@Nullable Integer, @Nullable Integer> stackHeight) {
    // Check whether current stack height is maximum
    this.maxHeight = Math.max(this.maxHeight, currentHeight);
    // Check whether we have terminated or not
    if ((pc * 2) >= this.firmware.size()) {
      // We've gone over end of instruction sequence, so stop.
      return;
    }
    // Process instruction at this address
    AvrInstruction instruction = decodeInstructionAt(pc);
    // Move to the next logical instruction as this is always the starting point.
    int next = pc + instruction.getWidth();
    //
    try {
      process(instruction, next, currentHeight, new HashMap<>(stackHeight));
    } catch (Error e) {
      e.equals(e);
      this.maxHeight = Integer.MAX_VALUE;
    }
  }

  /**
   * Process the effect of a given instruction.
   *
   * @param instruction   Instruction to process
   * @param pc            Program counter of following instruction
   * @param currentHeight Current height of the stack at this point (in bytes)
   */

  private void process(AvrInstruction instruction, int pc, int currentHeight,
      Map<@Nullable Integer, @Nullable Integer> stackHeight) {
    Integer i = Integer.valueOf(pc);
    if (!stackHeight.containsKey(i)) {
      stackHeight.put(i, Integer.valueOf(-1));
    }

    Integer i2 = stackHeight.get(i);

    if (i2 != null && i2.intValue() == currentHeight) {
      return;
    }

    stackHeight.put(i, Integer.valueOf(currentHeight));

    switch (instruction.getOpcode()) {
      case BREQ: {
        BREQ branch = (BREQ) instruction;
        traverse(pc, currentHeight, new HashMap<>(stackHeight));
        traverse(pc + branch.k, currentHeight, new HashMap<>(stackHeight));
        break;
      }
      case BRGE: {
        BRGE branch = (BRGE) instruction;
        traverse(pc, currentHeight, new HashMap<>(stackHeight));
        traverse(pc + branch.k, currentHeight, new HashMap<>(stackHeight));
        break;
      }
      case BRNE: {
        BRNE branch = (BRNE) instruction;
        traverse(pc, currentHeight, new HashMap<>(stackHeight));
        traverse(pc + branch.k, currentHeight, new HashMap<>(stackHeight));
        break;
      }
      case BRLT: {
        BRLT branch = (BRLT) instruction;
        traverse(pc, currentHeight, new HashMap<>(stackHeight));
        traverse(pc + branch.k, currentHeight, new HashMap<>(stackHeight));
        break;
      }
      case SBRS: {
        traverse(pc, currentHeight, new HashMap<>(stackHeight));
        traverse(pc + decodeInstructionAt(pc + 1).getWidth(), currentHeight,
            new HashMap<>(stackHeight));
        break;
      }
      case SBRC: {
        traverse(pc, currentHeight, new HashMap<>(stackHeight));
        traverse(pc + decodeInstructionAt(pc + 1).getWidth(), currentHeight,
            new HashMap<>(stackHeight));
        break;
      }
      case CALL: {
        CALL branch = (CALL) instruction;
        // Check whether infinite loop; if so, terminate.
        if (branch.k != -1) {
          // Explore the branch target
          traverse(pc, currentHeight, new HashMap<>(stackHeight));
          traverse(branch.k, currentHeight + 2, new HashMap<>(stackHeight));
        }
        break;
      }
      case RCALL: {
        RelativeAddress branch = (RelativeAddress) instruction;
        // Check whether infinite loop; if so, terminate.
        if (branch.k != -1) {
          // Explore the branch target
          traverse(pc, currentHeight, new HashMap<>(stackHeight));
          traverse(pc + branch.k, currentHeight + 2, new HashMap<>(stackHeight));
  
        }
        break;
      }
      case JMP: {
        JMP branch = (AvrInstruction.JMP) instruction;
        // Check whether infinite loop; if so, terminate.
        if (branch.k != -1) {
          // Explore the branch target
          traverse(branch.k, currentHeight, new HashMap<>(stackHeight));
        }
        break;
      }
      case RJMP: {
        RelativeAddress branch = (RelativeAddress) instruction;
        // Check whether infinite loop; if so, terminate.
        if (branch.k != -1) {
          // Explore the branch target
          traverse(pc + branch.k, currentHeight, new HashMap<>(stackHeight));
        }
        break;
      }
      case RET: {
        break;
      }
      case RETI:
        break;
      case PUSH: {
        traverse(pc, currentHeight + 1, new HashMap<>(stackHeight)); // push to memory u nut
        break;
      }
      case POP: {
        traverse(pc, currentHeight - 1, new HashMap<>(stackHeight));
        break;
      }
      default:
        // Indicates a standard instruction where control is transferred to the
        // following instruction.
        // System.out.println(instruction.getOpcode());
        traverse(pc, currentHeight, new HashMap<>(stackHeight));
    }
  }

  /**
   * Decode the instruction at a given PC location.
   *
   * @param pc Address of instruction to decode.
   * @return Instruction which has been decoded.
   */
  private AvrInstruction decodeInstructionAt(int pc) {
    AvrInstruction insn = this.decoder.decode(this.firmware, pc);
    assert insn != null;
    return insn;
  }
}