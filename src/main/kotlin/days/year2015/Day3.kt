package days.year2015

import days.Day

fun main() {
    println(Day3().solve())
}

class Day3: Day(3, 2015) {
    private var point1 = Point(0, 0)
    private var point2 = Point(0, 0)
    private val visited: MutableSet<Point> = mutableSetOf(point1, point2)

    private fun move(point: Point, direction: Char): Point = when (direction) {
        '>' -> Point(point.x + 1, point.y)
        '<' -> Point(point.x - 1, point.y)
        '^' -> Point(point.x, point.y + 1)
        'v' -> Point(point.x, point.y - 1)
        else -> point
    }

    fun solve(): Any {
        inputString.forEachIndexed { index, dir ->
            if (index % 2 == 0) {
                point1 = move(point1, dir)
                visited.add(point1)
            } else {
                point2 = move(point2, dir)
                visited.add(point2)
            }
        }
        return visited.size
    }

}