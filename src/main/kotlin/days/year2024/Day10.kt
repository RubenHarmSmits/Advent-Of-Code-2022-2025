package days.year2024

import days.Day

fun main() {
    println(Day10().solve())
}

class Day10 : Day(10, 2024) {

    val grid = matrixOfInput(inputList)

    var total = 0

    fun solve(): Any {
        grid.indices.forEach { y ->
            grid[0].indices.forEach { x ->
                if (grid[y][x] == '0') total += checkScore(Point(y, x), 0)
            }
        }

        return total
    }

    private fun checkScore(point: Point, cur: Int): Int {
        if (cur == 9) return 1
        return grid.getAdjacentCoordinates(point)
            .filter { grid.get(it).digitToInt() == cur + 1 }
            .sumOf { checkScore(it, cur + 1) }
    }
}