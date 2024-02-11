package avrmc.core;

import avrmc.core.AbstractMemory.Byte;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javr.core.AVR;
import org.eclipse.jdt.annotation.Nullable;

/**
 * Responsible for managing the model checking process for a given
 * <i>property</i> of the AVR machine. For example, this property might be the
 * maximum stack height observed in any reachable state; or, it could be a
 * signal as to whether any assertion's were violated, etc. The key, however, is
 * that this must properly manage a potentially large number of states.
 *
 * @author David J. Pearce
 *
 * @param <T> Concrete property value to be checked.
 */
public class AvrModelChecker<T> {
  /**
   * Property to use for checking concrete value.
   */
  private final Property<T> property;

  /**
   * Construct a new model checker instance to check a given property.
   *
   * @param property The property which this checker will check.
   */
  public AvrModelChecker(Property<T> property) {
    this.property = property;
  }

  /**
   * maps all the states occurred at each pc.
   */
  private final Set<Integer> memory = new HashSet<>();

  /**
   * Pc map.
   */
  private final Map<Integer, @Nullable Integer> pcMap = new HashMap<>();

  /**
   * Apply this model checker to a given starting state and compute the final
   * property. For example, if we're computing the maximum stack height then this
   * would return that value.
   *
   * @param seed Machine state to start checking from.
   * @return Computed property value.
   */
  public T apply(AbstractAvr seed) {

    //int count = 0;
    ArrayList<AbstractAvr> worklist = new ArrayList<>();
    // See the worklist
    worklist.add(seed);
    // Compute initial value for our starting state
    T value = this.property.map(seed);
    //
    while (worklist.size() > 0) {
      // Get next state to process
      AbstractAvr state = pop(worklist);

      AbstractAvr fork = null;
      //
      try {
        // Execute state until fork encountered
        while (fork == null) {

          // Reset I/O port unknown value(s).
          resetIoPort(state);
          // Execute one step the state
          fork = state.clock();

          // Determine property for updated state
          T nvalue = this.property.map(state);
          // Join with accumulated value
          value = this.property.join(value, nvalue);

          Integer pc = Integer.valueOf(state.getProgramCounter());
          assert pc != null;

          if (!this.pcMap.containsKey(pc)) {
            Integer one = Integer.valueOf(0);
            assert one != null;
            this.pcMap.put(pc, one);
          }

          this.pcMap.compute(pc, (k, v) -> {
            assert v != null;
            Integer v2 = Integer.valueOf(v.intValue() + 1);
            assert v2 != null;
            return v2;
          });


          Integer count = this.pcMap.get(pc);
          assert count != null;
          if (count.intValue() > 100) {
            this.pcMap.compute(pc, (k, v) -> {
              assert v != null;
              Integer zero = Integer.valueOf(0);
              assert zero != null;
              return zero;
            });

            break;
          }
        }
        // Add state and fork back on worklist
        Integer hash = AvrModelChecker.assertNotNull(Integer.valueOf(state.hashCode()));
        if (!this.memory.contains(hash)) {
          assert hash != null;
          this.memory.add(hash);
          worklist.add(state);
        }
        if (fork != null
            && !this.memory.contains(
                AvrModelChecker.assertNotNull(Integer.valueOf(fork.hashCode())))) {
          Integer forkhash = AvrModelChecker.assertNotNull(Integer.valueOf(fork.hashCode()));
          assert forkhash != null;
          worklist.add(fork);
        }


      } catch (AVR.HaltedException e) {
        assert e != null;
        // Indicates current state has halted. In such case, we don't need to put it
        // back on the worklist. However, we do still want to extract its property value
        // (e.g. as this might tell us the exit code, etc).
        T nvalue = this.property.map(state);
        // Join with accumulated value
        value = this.property.join(value, nvalue);
      }
    }
    return value;
  }

  /**
   * Represents a property which is checked over all states encountered during
   * model checking. This could be a simply safety property (e.g. no assertions
   * fail) or some other property of the state (e.g. maximum stack height).
   *
   * @author David J. Pearce
   * @param <T> Value which this property represents.
   *
   */
  public interface Property<T> {
    /**
     * Compute the given property for a single AVR state. For example, the current
     * stack height of the machine.
     *
     * @param state AVR state over which to compute the given property.
     * @return The computed property value
     */
    public T map(AbstractAvr state);

    /**
     * Join two property values together to form a single property value. For
     * example, if we're computing the maximum stack height then we would keep the
     * largest value.
     *
     * @param left  Leftmost value to join.
     * @param right Rightmost value to join.
     * @return Result of join.
     */
    public T join(T left, T right);
  }

  // ===============================================================
  // Helpers
  // ===============================================================

  /**
   * This resets the IO port value to unknown, which may be necessary if the
   * AbstractAVR writes concrete values to the IO port.
   *
   * @param state Abstract AVR state.
   */
  private static void resetIoPort(AbstractAvr state) {
    state.getData().write(32 + 0x16, Byte.UNKNOWN);
  }


  /**
   * Simple helper function for pulling things off the worklist efficiently.
   *
   * @param states Worklist of states to pop from.
   * @return Abstract state popped from list.
   */
  private static AbstractAvr pop(List<AbstractAvr> states) {
    int last = states.size() - 1;
    @Nullable AbstractAvr st = states.get(last);
    states.remove(last);
    return st;
  }

  /**
   * Check that an nullable is not null.
   *
   * @param <T> any type
   * @param o object to check
   * @return checkded object
   */
  private static <T> T assertNotNull(@Nullable T o) {
    assert o != null;
    return o;
  }
}
