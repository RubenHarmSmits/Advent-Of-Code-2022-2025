package days.year2015

import days.Day

fun main() {
    println(Day20().solve())
}

class Day20 : Day(20, 2015) { // too high 1081080
    val primes = mutableListOf(2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71)
    val input = 36000000
    fun solve(): Any {
        var number = 0
        var realNumber = 1
        while (true) {
            realNumber *= primes[number]
            val presents = calcPresents(number)
            println(presents)
            println("-------")
            if (presents >= input) return number
            number++
        }
    }

    private fun calcPresents(number: Int): Int {
        val dividedBy = mutableListOf<Int>()
        (1 ..number).forEach{
            if(number % it == 0 && number/it<=50) dividedBy.add(it)
        }
        return dividedBy.sumOf { it * 11 }
    }
}

