package days.year2017

import days.Day

fun main() {
    println(Day18().solve())
}

class Day18 : Day(18, 2017) {
    val list = inputList
    var count = 0

    val registers0 = "qwertyuiopasdfghjklzxcvbnm".map{it.toString() to 0L}.toMap().toMutableMap()
    val registers1 = "qwertyuiopasdfghjklzxcvbnm".map{it.toString() to 0L}.toMap().toMutableMap()
    val que0 = mutableListOf<Long>()
    val que1 = mutableListOf<Long>()

    fun solve(): Any {
        registers0["p"]=0L
        registers1["p"]=1L
        var turn1 = true
        var i0 =0
        var i1 =0
        
        fun run0() {
            while(i0 in list.indices){
                val instr = list[i0].split(" ")
                when(instr[0]){
                    "set" -> registers0[instr[1]] = getValue0(instr[2])
                    "add" -> registers0[instr[1]] = registers0[instr[1]]!! + getValue0(instr[2])
                    "mul" -> registers0[instr[1]] = registers0[instr[1]]!! * getValue0(instr[2])
                    "mod" -> registers0[instr[1]] = registers0[instr[1]]!! % getValue0(instr[2])
                    "jgz" -> if(getValue0(instr[1])>0) i0+=(getValue0(instr[2]).toInt() -1)
                    "snd" -> que1.add(getValue0(instr[1]))
                    "rcv" -> {
                        if(que0.isNotEmpty()){
                            val next = que0.removeAt(0)
                            registers0[instr[1]] = next
                        }
                        else return
                    }
                }
                i0++
            }
        }

        fun run1() {
            while(i1 in list.indices){
                val instr = list[i1].split(" ")
                when(instr[0]){
                    "set" -> registers1[instr[1]] = getValue1(instr[2])
                    "add" -> registers1[instr[1]] = registers1[instr[1]]!! + getValue1(instr[2])
                    "mul" -> registers1[instr[1]] = registers1[instr[1]]!! * getValue1(instr[2])
                    "mod" -> registers1[instr[1]] = registers1[instr[1]]!! % getValue1(instr[2])
                    "jgz" -> if(getValue1(instr[1])>0) i1+=(getValue1(instr[2]).toInt() -1)
                    "snd" -> que0.add(getValue1(instr[1])).also{count++}.also{println(count)}
                    "rcv" -> {
                        if(que1.isNotEmpty()){
                            val next = que1.removeAt(0)
                            registers1[instr[1]] = next
                        }
                        else return
                    }
                }
                i1++
            }

        }
        
        while(true){
            if(turn1) run1() else run0()
            turn1 = !turn1
        }
        return count
    }



    private fun getValue0(c: String): Long {
        if(isNumeric(c)) return c.toLong() else return registers0[c]!!
    }

    private fun getValue1(c: String): Long {
        if(isNumeric(c)) return c.toLong() else return registers1[c]!!
    }
}
