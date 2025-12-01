package days.year2017

import days.Day

fun main() {
    println(Day5().solve())
}

class Day5 : Day(5, 2017) {
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
