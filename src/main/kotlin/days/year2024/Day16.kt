package days.year2024

import days.Day

fun main() {
    println(Day16().solve())
}

class Day16 : Day(16, 2024) {  //417 too low

    data class Node(
        val point: Point,
        var options: MutableMap<Point, Pair<Int, List<Point>>> = mutableMapOf(),
        var minimalCost: Pair<Int, List<Point>> = Int.MAX_VALUE to mutableListOf(),
        var visited: Boolean = false
    ) {}

    val grid = matrixOfInput(inputList)
    val turningPoints = mutableListOf<Node>()
    val startingNode = Node(grid.findChar('S'))
    val endNode = Node(grid.findChar('E'))


    fun solve(): Any {
        turningPoints.add(startingNode)
        turningPoints.add(endNode)
        grid.indices.forEach { y ->
            grid.indices.forEach { x ->
                if (grid[y][x] == '.' && grid.getAdjacentCoordinates(y, x).filter { grid.get(it) == '.' }.size > 2) {
                    turningPoints.add(Node(Point(y, x)))
                }
            }
        }

        turningPoints.forEach { point ->
            findOptions(point)
        }
        dijkstra()
        return answer.size + 1
    }


    var answer: MutableSet<Point> = mutableSetOf()
    val max = 75416

    private fun dijkstra() {
        startingNode.minimalCost = 0 to startingNode.minimalCost.second.toMutableList()
        while (turningPoints.any { !it.visited }) {
            val smallest = turningPoints.filter { !it.visited }.minByOrNull { it.minimalCost.first }!!
            smallest.visited = true
            smallest.options.forEach { optionScore ->
                val option = findNode(optionScore.key)

                val currentLowest = option.minimalCost.first
                val currentScore = smallest.minimalCost.first + optionScore.value.first

                if (currentScore == currentLowest) {
                    option.minimalCost =
                        currentScore to (optionScore.value.second + smallest.minimalCost.second + option.minimalCost.second)
                }
                if (currentScore < currentLowest) {
                    option.minimalCost =
                        currentScore to (smallest.minimalCost.second + optionScore.value.second).toMutableList()
                }
                if (option == endNode && option.minimalCost.first == max) {
                    println(option.minimalCost.second)
                    answer += option.minimalCost.second
                }

            }
        }
    }

    private fun findNode(point: Point): Node {
        return turningPoints.find { it.point == point } ?: throw Exception("Not found")
    }

    private fun findOptions(node: Node) {
        val neighbors = grid.getAdjacentCoordinates(node.point).filter { grid.get(it) == '.' }
        neighbors.forEach {
            traverse(calculateDirection(node.point, it), node, it, 1, mutableListOf(), mutableSetOf())
        }
    }

    private fun traverse(
        direction: Direction,
        startNode: Node,
        point: Point,
        score: Int,
        mem: MutableList<String>,
        steps: MutableSet<Point>
    ) {
        val hash = "$direction $point"
        if (mem.contains(hash)) return
        mem.add(hash)
        steps.add(point)
        val turningPoint = turningPoints.find { it.point == point }
        val iamturningpoint = turningPoint != null
        if (turningPoint != null) {
            val temp = startNode.options[turningPoint.point]
            val currentScore = temp?.first ?: (score + 1000)
            if (currentScore > score + 100) {
                startNode.options[turningPoint.point] = score + 1000 to steps.toMutableList()
            }
        }
        val neighbors = grid.getAdjacentCoordinates(point).filter { ".ES".contains(grid.get(it)) }
        neighbors.forEach { neighbour ->
            val newDirection = calculateDirection(point, neighbour)
            if (!directionIsOpposite(newDirection, direction)) {
                if (!(iamturningpoint && isTurn(point, neighbour, direction))) {
                    traverse(
                        newDirection,
                        startNode,
                        neighbour,
                        score + 1 + if (isTurn(point, neighbour, direction)) 1000 else 0,
                        mem,
                        steps.toMutableSet()
                    )
                }
            }
        }
    }

}