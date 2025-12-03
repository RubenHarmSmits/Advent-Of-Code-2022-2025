package days.year2025

import days.Day

fun main() {
    println(Day3().solve())
}

class Day3 : Day(3, 2025) {

    fun solve(): Long {
        val n = 12
        return inputList.sumOf { bank ->
            var beginning = 0
            (n downTo 1).map { i ->
                bank.substring(beginning, bank.length - i + 1)
                    .map { it.digitToInt() }
                    .withIndex()
                    .maxBy { it.value }
                    .also { beginning += it.index + 1 }
                    .value
            }.joinToString("").toLong()
        }
    }
}