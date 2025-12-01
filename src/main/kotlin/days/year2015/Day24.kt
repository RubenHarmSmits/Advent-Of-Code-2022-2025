package days.year2015

import days.Day

fun main() {
    println(Day24().solve())
}

class Day24 : Day(24, 2015) { // too low 7559203

    val part = inputListInt.sum() / 4
    fun solve(): Any {
        val smallestN = (2..8).find { inputListInt.combinations(it).any { it.sum() == part } }!!
        return inputListInt.combinations(smallestN)
            .filter { it.sum() == part }
            .minOf { it.map { it.toLong() }.product() }
    }
}

