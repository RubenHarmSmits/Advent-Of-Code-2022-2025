package days.year2015

import days.Day
import kotlin.math.max

fun main() {
    println(Day9().solve())
}

class Day9 : Day(9, 2015) {
    data class Node(val name: String, val routes: MutableMap<String, Int> = mutableMapOf()) {
        fun add(neighbor: String, distance: Int) = apply { routes[neighbor] = distance }
    }

    fun solve(): Any {
        val nodes = buildGraph(inputList)
        return nodes.values.maxOf { startNode ->
            findLongestPath(startNode, nodes, mutableSetOf())
        }
    }

    private fun buildGraph(lines: List<String>): Map<String, Node> {
        val nodes = mutableMapOf<String, Node>()
        for (line in lines) {
            val (from, _, to, _, dist) = line.split(" ")
            val distance = dist.toInt()
            nodes.getOrPut(from) { Node(from) }.add(to, distance)
            nodes.getOrPut(to) { Node(to) }.add(from, distance)
        }
        return nodes
    }

    private fun findLongestPath(
        node: Node,
        nodes: Map<String, Node>,
        visited: MutableSet<String>,
        distance: Int = 0
    ): Int {
        visited.add(node.name)
        if (visited.size == nodes.size) return distance
        return node.routes
            .filterKeys { it !in visited }
            .maxOf { (neighbor, dist) ->
                findLongestPath(nodes[neighbor]!!, nodes, visited.toMutableSet(), distance + dist)
            }
    }
}