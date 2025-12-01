package days.year2015

import days.Day

fun main() {
    println(Day8().solve())
}

class Day8 : Day(8, 2015) {

    var input = "cqjxxzaa"
    val alphabet = "abcdefghijklmnopqrstuvwxyz"

    fun solve(): Any {
        while (!match(input)) {
            var newInput = ""
            for (i in input.indices) {
                if (i == input.length - 1) newInput += count(input[i])
                else if (input.substring(i + 1).all { it == 'z' }) newInput += count(input[i])
                else newInput += input[i]
            }
            input = newInput
        }
        return input
    }

    fun count(char: Char): Char {
        return alphabet[(alphabet.indexOf(char) + 1) % 26]
    }

    fun match(input: String): Boolean {
        val first = alphabet.windowed(3).any { input.contains(it) }
        val second = !"iol".any { input.contains(it) }
        val third = alphabet.count { input.contains("$it$it") } > 1
        return first && second && third
    }
}

