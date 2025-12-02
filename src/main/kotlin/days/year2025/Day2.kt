package days.year2025

import days.Day

fun main() {
    println(Day2().solve())
}

class Day2 : Day(2, 2025) {

    fun solve(): Any {
        return inputString
            .split(",")
            .map { it.split("-").map { it.toLong() } }
            .sumOf { (first, second) ->
                (first..second).sumOf { i ->
                    if (isInvalid(i.toString())) i else 0
                }
            }
    }

    fun isInvalid(num: String): Boolean {
        val l = num.length
        for (n in 1..(l / 2)) {
            if (l % n == 0 && num.chunked(n).toSet().size == 1) return true
        }
        return false
    }
}