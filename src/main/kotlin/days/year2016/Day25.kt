package days.year2016

import days.Day

fun main() {
    println(Day25().partOne())
}

class Day25 : Day(25, 2016) {

    enum class Operation { CPY, DEC, INC, JNZ, OUT }
    data class Action(val from: String, val to: String?, var op: Operation)

    val mapper = mutableMapOf("a" to 0, "b" to 1, "c" to 2, "d" to 3)
    val size = inputList.size
    val list = inputList.map { line ->
        val list = line.split(" ")
        when (list[0]) {
            "inc" -> Action(list[1], null, Operation.INC)
            "dec" -> Action(list[1], null, Operation.DEC)
            "cpy" -> Action(list[1], list[2], Operation.CPY)
            "jnz" -> Action(list[1], list[2], Operation.JNZ)
            "out" -> Action(list[1], null, Operation.OUT)
            else -> throw Exception("unknown operation")
        }
    }

    fun partOne(): Any {
        var i = 0
        while(true){
            tryA(i)
            i++
        }
    }

    fun tryA(a: Int){
        println(a)
        var zero = true
        val scores = arrayOf(a, 0, 0, 0)
        var i = 0
        while (i < size) {
            val action = list[i];
            when (action.op) {
                Operation.INC -> scores[mapper[action.from]!!]++
                Operation.DEC -> scores[mapper[action.from]!!]--
                Operation.CPY -> scores[mapper[action.to]!!] = getValue(action.from, scores)
                Operation.JNZ -> if (getValue(action.from, scores) != 0) i += (getValue(action.to!!, scores) - 1)
                Operation.OUT -> {
                    val out = getValue(action.from, scores)
                    if((out==0 && zero)||(out==1&&!zero)){
                        zero = !zero
                    } else {
                        return
                    }
                }
            }
            i++
        }

    }


    private fun getValue(source: String, scores: Array<Int>): Int {
        return source.toIntOrNull() ?: scores[mapper[source]!!]
    }
}