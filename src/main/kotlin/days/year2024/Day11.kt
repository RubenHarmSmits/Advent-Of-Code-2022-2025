package days.year2024

import days.Day

fun main() {
    println(Day11().solve())
}

class Day11 : Day(11, 2024) {

    val input = inputString.split(" ")

    private var counts = mutableMapOf<String, Long>()
    private val transPosing = mutableMapOf<String, List<String>>()

    fun solve(): Any {

        input.forEach { num ->
            counts[num] = 1
        }

        repeat(75) {
            val newCounts = mutableMapOf<String, Long>()
            counts.forEach { (num, count) ->
                val transposesTo: List<String> = transPosing.getOrElse(num) {
                    val a = transposesTo(num)
                    transPosing[num] = a
                    a
                }
                transposesTo.forEach { newCounts[it] = newCounts.getOrPut(it) { 0 } + count }
            }
            counts = newCounts

        }

        return counts.values.sum()
    }

    private fun transposesTo(number: String): List<String> {
        if (number == "0") return listOf("1")
        else if (number.length % 2 == 0) {
            return listOf(
                number.substring(0, number.length / 2),
                number.substring(number.length / 2).toLong().toString()
            )
        } else {
            return listOf((number.toLong() * 2024L).toString())
        }
    }

}