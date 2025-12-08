package days.year2025

import days.Day

fun main() {
    println(Day7().solve())
}

class Day7 : Day(7, 2025) {

    val grid = matrixOfInput(inputList).toMutableMatrix()
    val memory = mutableMapOf<Point, Long>()

    fun solve() = check(grid.findChar('S'))

    fun check(p: Point) = memory.getOrPut(p) {
        checkScore(p)
            .also { memory[p] = it }
    }

    fun checkScore(p: Point): Long = when {
        p.y == grid.size - 1 -> 1L
        grid.get(p) == '^' -> check(Point(p.y + 1, p.x + 1)) + check(Point(p.y + 1, p.x - 1))
        else -> check(Point(p.y + 1, p.x))
    }
}
