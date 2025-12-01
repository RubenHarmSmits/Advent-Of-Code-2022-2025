package days.year2017

import days.Day

fun main() {
    println(Day12().solve())
}

class Day12 : Day(12, 2017) {
    private val list = inputList
    private var count = 0

    fun solve(): Any {
        go()
        return count
    }

    private fun go() {
        // ...implementation...
    }
}
