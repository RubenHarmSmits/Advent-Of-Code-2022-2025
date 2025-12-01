package days.year2016

import days.Day
import kotlin.math.min

fun main() {
    println(Day24().solve())
}
class Day24: Day(24, 2016) {

    data class Beacon(val name: Char, val distances: MutableMap<Char, Int>, val location: Point)
    val grid = matrixOfInput(inputList)

    var shortest = Int.MAX_VALUE

    val points = grid.findAll { isNumeric(it.toString()) }.map{ Beacon(grid.get(it), mutableMapOf(), it) }

    fun solve(): Any {
        points.forEach {findNeighbours(it)}
        shortest(points.find { it.name=='0' }!!, mutableSetOf(), 0)
        return shortest
    }

    fun shortest(beacon: Beacon, visited: MutableSet<Char>, steps: Int) {
        visited.add(beacon.name)
        if(visited.size == points.size) shortest = min(shortest, steps + beacon.distances['0']!!)
        beacon.distances.forEach { (k, v) -> if(!visited.contains(k)) shortest(points.find { it.name==k }!!,visited.toMutableSet(), steps+v) }
    }

    fun findNeighbours(op: Beacon) {
        var options = mutableSetOf(op.location)
        val visited = mutableSetOf(op.location)
        var distance = 0
        while (options.isNotEmpty()) {
            distance++
            val newOptions = mutableSetOf<Point>()
            options.forEach { option ->
                grid.getAdjacentCoordinates(option.y, option.x)
                    .filter { !visited.contains(it) }
                    .filter{ grid.get(it)!='#' }
                    .forEach {
                        visited.add(it)
                        val char = grid.get(it)
                        if(char.isNumber()) op.distances[char]=distance
                        newOptions.add(it)
                    }
            }
            options = newOptions
        }


    }


}