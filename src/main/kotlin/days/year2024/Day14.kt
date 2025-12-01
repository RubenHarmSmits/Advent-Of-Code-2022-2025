package days.year2024

import days.Day
import kotlin.math.abs

fun main() {
    println(Day14().solve())
}

val maxx = 101


class Day14 : Day(14, 2024) { //217893888 too high

    data class Robot(val position: Point, val velocity: Point) {
        val maxy = 103
        val maxx = 101

        fun move(steps: Int) {
            this.position.x = checkPosition(this.position.x, velocity.x, maxx, steps)
            this.position.y = checkPosition(this.position.y, velocity.y, maxy, steps)
        }

        private fun checkPosition(position: Int, velocity: Int, max: Int, steps: Int): Int {
            val newPosition = position + (velocity * steps)
            if (newPosition < 0) {
                if (abs(newPosition) % max == 0) return 0
                else return max - (abs(newPosition) % max)
            }
            if (newPosition >= max) return newPosition % max
            return newPosition
        }
    }

    val robots = inputList.map { line ->
        val (px, py, vx, vy) = extraxtAllIntsFromStringIncludingNegative(line)
        Robot(Point(py, px), Point(vy, vx))
    }


    fun solve(): Any { //7309  <--> 17712

        var move = 99
        robots.forEach { it.move(move) }
        printRobots()
        val steps = 103
        repeat(75) {
            println(it)
            robots.forEach { it.move(steps) }
            printRobots()
            move+=steps
            println(move)

        }
        return 0
    }

    fun printRobots() {
            (22 until 55).forEach { y ->
                repeat(maxx) { x ->
                    val num = robots.count { it.position.y == y && it.position.x == x }
                    if (num == 0) print('.') else print(num)
                }
                println()
            }
        println()
        println()
    }


}