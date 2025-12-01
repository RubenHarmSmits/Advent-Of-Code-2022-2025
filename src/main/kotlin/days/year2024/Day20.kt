package days.year2024

import days.Day
import days.Matrix

fun main() {
    println(Day20().solve())
}

class Day20 : Day(20, 2024) {

    val originalGrid = matrixOfInput(inputList)
    val startPoint = originalGrid.findChar('S')
    val endPoint = originalGrid.findChar('E')
    val originalPath: List<Point> = makeOriginalPath(originalGrid)

    private fun makeOriginalPath(originalGrid: Matrix<Char>): List<Point> {
        val path = mutableListOf<Point>()
        var point = startPoint
        var previousPoint = startPoint
        while (point != endPoint) {
            path.add(point)
            val a = originalGrid.getAdjacentCoordinates(point).filter { originalGrid.get(it) in ".E" }
                .find { it != previousPoint }!!
            previousPoint = point
            point = a
        }
        path.add(endPoint)
        return path
    }

    fun solve(): Any {
        return originalPath.sumOf { findCheats(it) }
    }

    private fun findCheats(spoint: Point): Int {
        return originalPath
            .filter { pendoint -> manhattanDistance(spoint, pendoint) <= 20 }
            .count { pendoint ->
                originalPath.indexOf(pendoint) - originalPath.indexOf(spoint) - manhattanDistance(
                    spoint,
                    pendoint
                ) >= 100
            }
    }

}