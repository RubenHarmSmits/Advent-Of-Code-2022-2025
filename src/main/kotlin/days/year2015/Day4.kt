package days.year2015

import days.Day

fun main() {
    println(Day4().solve())
}

class Day4: Day(4, 2015) {
    val input = "yzbqklnj"

    fun solve(): Any {
        (0..999999999999).forEach { i ->
            val hash = md5("$input$i")
            if (hash.startsWith("000000")) {
                return i
            }
        }
        return false
    }

}