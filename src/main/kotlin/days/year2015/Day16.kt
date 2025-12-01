package days.year2015

import days.Day

fun main() {
    println(Day16().solve())
}

class Day16 : Day(16, 2015) {

    val attributes = mapOf(
        "children" to 3,
        "cats" to 7,
        "samoyeds" to 2,
        "pomeranians" to 3,
        "akitas" to 0,
        "vizslas" to 0,
        "goldfish" to 5,
        "trees" to 3,
        "cars" to 2,
        "perfumes" to 1
    )

    fun solve(): Any? {
        return inputList.find { auntLine ->
            val numbers = extraxtAllIntsFromStringIncludingNegative(auntLine)
            val (_, _, first, _, second, _, third) = auntLine.split(" ").map { it.removeSuffix(":") }
            isCorrect(first, numbers[1]) && isCorrect(second, numbers[2]) && isCorrect(third, numbers[3])
        }
    }

    fun isCorrect(type: String, value: Int) =
        when (type) {
            "cats", "trees" -> value > attributes[type]!!
            "pomeranians", "goldfish" -> value < attributes[type]!!
            else -> attributes[type]!! == value
        }
}

