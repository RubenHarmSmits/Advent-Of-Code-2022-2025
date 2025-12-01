package days.year2015

import days.Day
import kotlin.math.max

fun main() {
    println(Day15().solve())
}

class Day15 : Day(15, 2015) {

    val ingredients = inputList.map { extraxtAllIntsFromStringIncludingNegative(it) }
    fun solve(): Any {
        var highest = 0
        (0..100).forEach { first ->
            (0..(100 - first)).forEach { second ->
                (0..(100 - first - second)).forEach { third ->
                    val fourth = 100 - first - second - third
                    val calories = listOf(
                        first * ingredients[0][4],
                        second * ingredients[1][4],
                        third * ingredients[2][4],
                        fourth * ingredients[3][4],
                    ).sum()
                    if (calories == 500) highest = max(highest, calculateScore(listOf(first, second, third, fourth)))
                }
            }
        }
        return highest
    }

    fun calculateScore(percentages: List<Int>) =
        (0 until 4)
            .map { ingredientIndex ->
                max(0, ingredients.mapIndexed { i, ingredient ->
                    ingredient[ingredientIndex] * percentages[i]
                }.sum())
            }.product()

}


