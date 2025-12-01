package days.year2017

import days.Day

fun main() {
    println(Day23().solve())
}

class Day23 : Day(23, 2017) {

    enum class Instruction {SET, JNZ, MUL, SUB}
    data class Action(val instruction: Instruction, val register: String, val value: String)
    val list = inputList.map{
        val (instruction, register, value) = it.split(" ")
        Action(Instruction.valueOf(instruction.uppercase()), register, value)
    }
    val registers = mutableMapOf("a" to 1, "b" to 0, "c" to 0, "d" to 0, "e" to 0, "f" to 0, "g" to 0, "h" to 0)
    var i = 0
    var count = 0

    fun solve(): Any {
        for(i in 105700 .. 122700 step 17) {

            if (!isPrime(i)) count++
        }
        return count
//        while(i<list.size) {
////            if(i==9){
////                println(9)
////                println(registers["g"])
////            }
//            if(i==10){
//                println(13)
//                println(registers["g"])
//            }
//            if(i==18){
//                println(18)
//                println(registers["g"])
//            }
//            val action = list[i]
//            when(action.instruction){
//                Instruction.SET -> {
//                    registers[action.register] = getValue(action.value)
//                }
//                Instruction.MUL -> {
//                    registers[action.register] = getValue(action.value) * registers[action.register]!!
//                }
//                Instruction.SUB -> {
//                    registers[action.register] = registers[action.register]!! - getValue(action.value)
//                }
//                Instruction.JNZ -> {
//                    if(getValue(action.register) != 0) {
//                        i+= (getValue(action.value) -1)
//                    }
//                }
//            }
//            i++
//        }
//        return registers["h"]!!
    }

    private fun getValue(value: String):Int {
        return value.toIntOrNull()?: registers[value]!!
    }

    private fun isPrime(number: Int): Boolean{
        for(i in 2 .. number / 2){
            if(number % i == 0) return false
        }
        return true
    }
}
