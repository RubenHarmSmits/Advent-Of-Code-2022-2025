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
                    if ((1..(i.toString().length / 2)).any {
                            i.toString().length % it == 0 && i.toString().chunked(it).toSet().size == 1
                        }) i else 0
                }
            }
    }
}