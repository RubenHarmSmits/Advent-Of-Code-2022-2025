package days.year2025

import days.Day

fun main() {
    println(Day1().solve())
}

class Day1 : Day(1, 2025) {
    var count = 0
    var i = 50

    fun solve(): Any {
        inputList.forEach { line ->
            val isRight = line.startsWith("R")
            val amount = line.substring(1).toInt()
            if (isRight) {
                count += (amount + i) / 100
                i = (i + amount) % 100
            } else {
                if (i == 0) count--
                val n = (100 - i + amount) / 100
                count += n
                i = (i - amount + n * 100) % 100
            }
        }
        return count
    }

}