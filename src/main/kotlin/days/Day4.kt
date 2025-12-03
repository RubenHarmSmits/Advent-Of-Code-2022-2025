package days

import days.Day

fun main() {
    println(Day4().solve())
}

class Day4 : Day(4, 2025) {

    fun solve(): Any {
        val grid = matrixOfInput(inputList).toMutableMatrix()
        var count = 0
        do {
            var found = false
            grid.indices.forEach { y ->
                grid[0].indices.forEach { x ->
                    val surroundingPapers = grid.getSurroundingCoordinates(Point(y, x)).count { grid.get(it) == '@' }
                    if (grid[y][x] == '@' && surroundingPapers < 4) {
                        count++
                        found = true
                        grid[y][x] = '.'
                    }
                }
            }
        } while (found)

        return count
    }
}