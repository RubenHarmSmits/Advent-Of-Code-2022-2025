package days.year2015

import days.Day

fun main() {
    println(Day13().solve())
}

class Day13 : Day(13, 2015) {

    private val happiness = mutableMapOf<Pair<String, String>, Int>()
    private val people = mutableSetOf<String>()

    fun solve(): Any {
        val a  = listOf(1,3,5).flatMap { listOf(it) }
        inputList.forEach { line ->
            val (name, _, isGain, amount, _, _, _, _, _, _, otherRaw) = line.split(" ")
            val other = otherRaw.removeSuffix(".")
            val value = if (isGain == "gain") amount.toInt() else -amount.toInt()
            happiness[name to other] = value
            people.add(name)
        }

        return people.toList().permutations().maxOf { arrangement ->
            arrangement.indices.sumOf { i ->
                val left = arrangement[i]
                val right = arrangement[(i + 1) % arrangement.size]
                (happiness[left to right] ?: 0) + (happiness[right to left] ?: 0)
            }
        }
    }

}
