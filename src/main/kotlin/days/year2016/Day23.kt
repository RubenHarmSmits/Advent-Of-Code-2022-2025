package days.year2016

import days.Day

fun main() {
    println(Day23().partOne())
}

class Day23 : Day(23, 2016) {

    enum class Operation { CPY, DEC, INC, JNZ, TGL }
    data class Action(val from: String, val to: String?, var op: Operation)

    val mapper = mutableMapOf("a" to 0, "b" to 1, "c" to 2, "d" to 3)
    val scores = arrayOf(12, 0, 0, 0)
    val size = inputList.size
    val list = inputList.map { line ->
        val list = line.split(" ")
        when (list[0]) {
            "inc" -> Action(list[1], null, Operation.INC)
            "dec" -> Action(list[1], null, Operation.DEC)
            "cpy" -> Action(list[1], list[2], Operation.CPY)
            "jnz" -> Action(list[1], list[2], Operation.JNZ)
            "tgl" -> Action(list[1], null, Operation.TGL)
            else -> throw Exception("unknown operation")
        }
    }

    fun partOne(): Any {
        var i = 0
        while (i < size) {
            val action = list[i];
            when (action.op) {
                Operation.INC -> scores[mapper[action.from]!!]++
                Operation.DEC -> scores[mapper[action.from]!!]--
                Operation.CPY -> scores[mapper[action.to]!!] = getValue(action.from)
                Operation.JNZ -> if (getValue(action.from) != 0) i += (getValue(action.to!!) - 1)
                Operation.TGL -> {
                    val target = getValue(action.from) + i
                    if (target in list.indices) {
                        val targetInstruction = list[target]
                        targetInstruction.op = toggleResult(targetInstruction)
                    }
                }
            }
            i++
        }
        return scores[0]
    }


    private fun getValue(source: String): Int {
        return source.toIntOrNull() ?: scores[mapper[source]!!]
    }

    private fun toggleResult(instruction: Action) =
        when (instruction.op) {
            Operation.INC -> Operation.DEC
            Operation.DEC -> Operation.INC
            Operation.TGL -> Operation.INC
            Operation.JNZ -> Operation.CPY
            Operation.CPY -> Operation.JNZ
        }
}