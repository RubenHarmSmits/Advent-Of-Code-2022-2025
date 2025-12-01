package days.year2024

import days.Day

fun main() {
    println("ANSWER:")
    println(ING().solve())
    println("END ANSWER:")

}

class ING : Day(26, 2024) {


    fun solve(): Any {
        val input = 3141592
        return (1..input).sumOf {
            getHoles(it)
        }
    }

    fun getHoles(number: Int): Int {
        return number.toString()
            .map { it }
            .filter { it in "04689" }.size + number.toString().count { it == '8' }
    }


}