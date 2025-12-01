package days.year2017

import days.Day

fun main() {
    println(Day16().solve())
}

class Day16 : Day(16, 2017) {
    val list = inputList
    var line = "abcdefghijklmnop"
    val positions = mutableListOf<String>()

    fun solve(): Any {
        repeat(100) {
            inputString.split(",").forEach { move ->
                val numbers = extraxtAllIntsFromString(move)
                when (move.first()) {
                    's' -> {
                        line = line.substring(16 - numbers.first()) + line.substring(0, 16 - numbers.first())
                    }
                    'x' -> {
                        val first = line[numbers.first()]
                        val second = line[numbers[1]]
                        val l = line.toMutableList()
                        l[numbers.first()] = second
                        l[numbers[1]] = first
                        line = l.joinToString("")
                    }
                    'p' -> {
                        val (_, first, _, second) = move.toList()
                        val indexfirst = line.indexOf(first)
                        val indexsecond = line.indexOf(second)
                        val l = line.toMutableList()
                        l[indexfirst] = second
                        l[indexsecond] = first
                        line = l.joinToString("")
                    }
                }
            }
            positions.add(line)
            println(it.toString() + line)
        }
        return line
    }

}
