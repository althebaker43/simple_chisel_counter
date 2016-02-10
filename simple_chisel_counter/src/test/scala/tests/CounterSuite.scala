package tests

import org.junit.Test
import net.botbakery.samples.simple_chisel_counter.Counter
import Chisel._

class CounterSuite {
    
  val testArgs = Array("--targetDir", "output", "--backend", "c", "--vcd", "--genHarness", "--compile", "--test")
  @Test def testBasic() {
    class CounterTester(dut : Counter) extends Tester(dut) {
      poke(dut.io.reset, 0)
      expect(dut.io.out, 0)
      step(1)
      expect(dut.io.out, 1)
      step(1)
      expect(dut.io.out, 2)
      step(1)
      expect(dut.io.out, 3)
      step(12)
      expect(dut.io.out, 0xF)
      step(1)
      expect(dut.io.out, 0)
      step(1)
      expect(dut.io.out, 1)
    }
    
    chiselMainTest(testArgs, () => Module(new Counter())) {
      c => new CounterTester(c)
    }
  }
  
  @Test def testReset() {
    class CounterTester(dut : Counter) extends Tester(dut) {
      expect(dut.io.out, 0)
      step(1)
      expect(dut.io.out, 1)
      poke(dut.io.reset, 1)
      step(1)
      expect(dut.io.out, 0)
      poke(dut.io.reset, 0)
      step(1)
      expect(dut.io.out, 1)
    }
        
    chiselMainTest(testArgs, () => Module(new Counter())) {
      c => new CounterTester(c)
    }
  }
  
  @Test def testWidth() {
    class CounterTester(dut : Counter) extends Tester(dut) {
      expect(dut.io.out, 0)
      step(7)
      expect(dut.io.out, 7)
      step(1)
      expect(dut.io.out, 0)
    }
    
    chiselMainTest(testArgs, () => Module(new Counter(3))) {
      c => new CounterTester(c)
    }
  }
}