package days.year2024

import days.Day
import kotlin.math.abs

fun main() {
    println(Day2().solve())
}

class Day2 : Day(2, 2024) {

    fun solve(): Any {
        return inputList
            .map{it.split(" ").ints()}
            .filter{ list ->
                list.indices.any {
                    val copy = list.toMutableList()
                    copy.removeAt(it)
                    reportIsSafe(copy)
                }
        }.size
    }

    private fun reportIsSafe(list: List<Int>): Boolean {
        val first = list[1] - list[0]
        var previous = list[0]
        val ans = list.drop(1).all{
            if(!areBothPositiveOrNegative(it-previous,first)) return@all false
            val ab = abs(it-previous)
            if(ab>3||ab<0) return@all false
            previous = it
            return@all true;
        }
        return ans
    }


}