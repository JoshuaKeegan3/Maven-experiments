package avranalysis.tests;

import static org.junit.Assert.assertEquals;

import avranalysis.core.StackAnalysis;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javr.io.HexFile;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * Tests for part 5 of the assignment.
 *
 * @author David J. Pearce
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Part5Tests {
  /**
   * Identifies the directory in which the test firmwares are located.
   */
  private final String testsDir;

  /**
   * Constructor for tests.
   */
  public Part5Tests() {
    String dir = "tests/".replace("/", File.separator); //$NON-NLS-1$ //$NON-NLS-2$
    assert dir != null;
    this.testsDir = dir;
  }

  /**
   * A test.
   *
   * @throws IOException If something goes wrong.
   */
  @Test
  public void test_01() throws IOException {
    // Check computation
    assertEquals(12, computeStackUsage("fader.hex")); //$NON-NLS-1$
  }

  /**
   * A test.
   *
   * @throws IOException If something goes wrong.
   */
  @Test
  public void test_02() throws IOException {
    // Check computation
    assertEquals(10, computeStackUsage("blocks_1.hex")); //$NON-NLS-1$
  }

  /**
   * A test.
   *
   * @throws IOException If something goes wrong.
   */
  @Test
  public void test_03() throws IOException {
    // Check computation
    assertEquals(10, computeStackUsage("blocks_2.hex")); //$NON-NLS-1$
  }

  /**
   * A test.
   *
   * @throws IOException If something goes wrong.
   */
  @Test
  public void test_04() throws IOException {
    // Check computation
    assertEquals(10, computeStackUsage("blocks_3.hex")); //$NON-NLS-1$
  }

  /**
   * A test.
   *
   * @throws IOException If something goes wrong.
   */
  @Test
  public void test_05() throws IOException {
    // Check computation
    assertEquals(10, computeStackUsage("blocks_4.hex")); //$NON-NLS-1$
  }

  /**
   * A test.
   *
   * @throws IOException If something goes wrong.
   */
  @Test
  public void test_06() throws IOException {
    // Check computation
    assertEquals(10, computeStackUsage("blocks_5.hex")); //$NON-NLS-1$
  }

  /**
   * A test.
   *
   * @throws IOException If something goes wrong.
   */
  @Test
  public void test_07() throws IOException {
    // Check computation
    assertEquals(10, computeStackUsage("blocks_6.hex")); //$NON-NLS-1$
  }

  /**
   * A test.
   *
   * @throws IOException If something goes wrong.
   */
  @Test
  public void test_08() throws IOException {
    // Check computation
    assertEquals(10, computeStackUsage("blocks_7.hex")); //$NON-NLS-1$
  }

  /**
   * A test.
   *
   * @throws IOException If something goes wrong.
   */
  @Test
  public void test_09() throws IOException {
    // Check computation
    assertEquals(12, computeStackUsage("blocker_1.hex")); //$NON-NLS-1$
  }

  /**
   * A test.
   *
   * @throws IOException If something goes wrong.
   */
  @Test
  public void test_10() throws IOException {
    // Check computation
    assertEquals(12, computeStackUsage("blocker_2.hex")); //$NON-NLS-1$
  }

  /**
   * A test.
   *
   * @throws IOException If something goes wrong.
   */
  @Test
  public void test_11() throws IOException {
    // Check computation
    assertEquals(33, computeStackUsage("numbers_1.hex")); //$NON-NLS-1$
  }

  /**
   * A test.
   *
   * @throws IOException If something goes wrong.
   */
  @Test
  public void test_12() throws IOException {
    // Check computation
    assertEquals(71, computeStackUsage("snake.hex")); //$NON-NLS-1$
  }

  /**
   * A test.
   *
   * @throws IOException If something goes wrong.
   */
  @Test
  public void test_13() throws IOException {
    // Check computation
    assertEquals(41, computeStackUsage("tetris.hex")); //$NON-NLS-1$
  }

  /**
   * For a given sequence of instructions compute the maximum stack usage.
   *
   * @param filename Filename of hexfile representing the program to check.
   * @return The worst-case stack usage (in bytes).
   * @throws IOException If something goes wrong parsing the hexfile.
   */
  private int computeStackUsage(String filename) throws IOException {
    // Read the firmware image
    try (FileReader fr = new FileReader(this.testsDir + filename)) {
      HexFile.Reader hfr = new HexFile.Reader(fr);
      HexFile hf = hfr.readAll();
      //
      assert hf != null;
      // Compute stack usage
      return new StackAnalysis(hf).apply();
    }
  }
}
