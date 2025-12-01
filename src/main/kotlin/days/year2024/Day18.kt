package days.year2024

import days.Day

fun main() {
    println(Day18().solve())
}

class Day18 : Day(18, 2024) {  // 35,55

    data class Node(
        val point: Point,
        var options: MutableList<Point> = mutableListOf(),
        var minimalCost: Int = Int.MAX_VALUE,
        var visited: Boolean = false
    )

    val endpoint = Point(70, 70)
    val grid = MutableList(71) { MutableList(71) { '.' } }

    fun solve(): Any {
        inputList.indices.forEach {
            if (!isPossible(1024 + it)) return it
        }
        return 0
    }

    private fun isPossible(bytes: Int): Boolean {
        val nodes = mutableListOf<Node>()
        val corrupt = inputList.subList(0, bytes).map {
            extraxtAllIntsFromString(it).let {
                Point(it[1], it[0])
            }
        }
        grid.indices.forEach { y ->
            grid[0].indices.forEach { x ->
                if (Point(y, x) !in corrupt) {
                    nodes.add(Node(Point(y, x)))
                }
            }
        }
        nodes.forEach { it.options = grid.getAdjacentCoordinates(it.point).filter { it !in corrupt }.toMutableList() }
        val found = false
        nodes[0].minimalCost = 0
        while (!found) {
            val closest = nodes.filter { !it.visited }.minBy { it.minimalCost }
            if (closest.minimalCost == Int.MAX_VALUE) return false
            if (closest.point == endpoint) {
                return true
            }
            closest.visited = true
            closest.options.forEach { option ->
                nodes.find { it.point == option }!!.minimalCost = closest.minimalCost + 1
            }
        }
        throw Exception("")
    }
}