package days.year2015

import days.Day

fun main() {
    println(Day11().solve())
}

class Day11 : Day(11, 2015) {
    fun solve(): Any {
        return inputList.count {
            val between = it.windowed(3).any { it[0] == it[2] }
            val same = it.windowed(2).toSet().any {
                it.split(it).size > 2
            }
            same && between
        }
    }
}

