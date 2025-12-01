package days.year2015

import days.Day

fun main() {
    println(Day25().solve())
}

class Day25 : Day(25, 2015) {
//    val x = 3
//    val y = 4
    val x = 3029
    val y = 2947
    fun solve(): Any {
        var number = 0
        var actualNumber = 0
        for (i in 1..x) {
            number++
            actualNumber += number
        }
        for (i in 1 until y) {
            actualNumber += number
            number++
        }

        var num = 20151125L
        repeat(actualNumber - 1) {
            num = (num * 252533L) % 33554393
        }

        return num
    }
}



