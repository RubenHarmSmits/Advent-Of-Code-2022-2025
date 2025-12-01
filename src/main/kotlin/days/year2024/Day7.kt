package days.year2024

import days.Day

fun main() {
    println(Day7().solve())
}

class Day7 : Day(7, 2024) {

    fun solve(): Any {
        return inputList.filter { isPossible(it) }.sumOf { it.split(':')[0].toLong() }
    }

    private fun isPossible(line: String): Boolean {
        val (value, num) = line.split(':')
        val numbers = num.substring(1).split(' ').ints()
        var expressions = mutableListOf(numbers[0].toLong())
        for (i in numbers.dropLast(1).indices) {
            val newExpression = mutableListOf<Long>()
            expressions.forEach {
                newExpression.add(it + numbers[i + 1])
                newExpression.add(it * numbers[i + 1])
                newExpression.add((it.toString() + numbers[i + 1].toString()).toLong())
            }
            expressions = newExpression
        }
        return expressions.any {
            it == value.toLong()
        }
    }
}