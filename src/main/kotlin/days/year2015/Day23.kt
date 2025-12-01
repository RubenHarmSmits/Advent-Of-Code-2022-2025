package days.year2015

import days.Day

fun main() {
    println(Day23().solve())
}

class Day23 : Day(23, 2015) {
    val instructions = inputList
    var a = 1
    var b = 0
    fun solve(): Any {
        var i = 0
        while (i < instructions.size) {
            val spl = instructions[i].split(" ")
            val (instruction, second) = spl
            val isA = second.contains("a")
            when (instruction) {
                "hlf" -> if (isA) a /= 2 else b /= 2
                "tpl" -> if (isA) a *= 3 else b *= 3
                "inc" -> if (isA) a++ else b++
                "jmp" -> i += extraxtAllIntsFromStringIncludingNegative(second)[0] - 1
                "jie" -> if ((isA && a % 2 == 0) || (!isA && b % 2 == 0)) i += (extraxtAllIntsFromStringIncludingNegative(
                    spl[2]
                )[0] - 1)

                "jio" -> if ((isA && a == 1) || (!isA && b == 1)) i += (extraxtAllIntsFromStringIncludingNegative(spl[2])[0] - 1)
                else -> println("OOPSIE")
            }
            i++
        }
        return b
    }
}

