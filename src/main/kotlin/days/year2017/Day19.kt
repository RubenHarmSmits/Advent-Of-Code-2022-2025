package days.year2017

import days.Day

fun main() {
    println(Day19().solve())
}

class Day19 : Day(19, 2017) { // 18701 too low
    val grid = matrixOfInput(inputList)
    var count = 0
    var direction = Direction.DOWN

    var point = Point(0, grid[0].indexOf('|'))
    var ans = ""

    fun solve(): Any {
        while (true) {
            point.move(direction)
            val c = grid.get(point)
            if (c !in "-|+") ans += c
            if (c == ' ') {
                return count
            }
            if (c == '+') {
                when (direction) {
                    Direction.DOWN, Direction.UP -> {
                        if (grid.get(Point(point.y, point.x - 1)) == '-') direction = Direction.LEFT
                        if (grid.get(Point(point.y, point.x + 1)) == '-') direction = Direction.RIGHT
                    }
                    Direction.RIGHT, Direction.LEFT -> {
                        if (grid.get(Point(point.y - 1, point.x)) == '|') direction = Direction.UP
                        if (grid.get(Point(point.y + 1, point.x)) == '|') direction = Direction.DOWN
                    }
                }
            }
            count++
        }

        return count
    }
}
