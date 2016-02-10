package net.botbakery.samples.simple_chisel_counter

import Chisel._

class Counter(width : Int = 4) extends Module {
  val io = new Bundle {
    val out = UInt(dir = OUTPUT, width = width)
    val reset = Bool(dir = INPUT)
  }
  val countReg = Reg(init = UInt(0))
  when (io.reset) {
    countReg := UInt(0, width = width)
  } .otherwise {
    countReg := UInt(countReg + UInt(1))
  }
  io.out := countReg
}
