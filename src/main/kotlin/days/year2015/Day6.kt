package days.year2015

import days.Day
import kotlin.math.max
import kotlin.math.min

fun main() {
    println(Day6().solve())
}

class Day6 : Day(6, 2015) { //2482890 too low
    val grid = MutableList(1000) { MutableList(1000) { 0 } }.toMutableMatrix()
    fun solve(): Any {
        inputList.forEach { instruction ->
            val (firstx, firsty, secondx, secondy) = extraxtAllIntsFromString(instruction)
            val (first, second) = instruction.split(" ")

            (min(firsty, secondy)..max(firsty, secondy)).forEach { y ->
                (min(firstx, secondx)..max(firstx, secondx)).forEach { x ->
                    if (first == "toggle") grid[y][x] = grid[y][x] + 2
                    else if (second == "on") grid[y][x] = grid[y][x] + 1
                    else if (second == "off") grid[y][x] = max(grid[y][x] - 1, 0)
                    else println("SOmething went wrong")
                }
            }
        }
        return grid.flatten().sum()
    }
}