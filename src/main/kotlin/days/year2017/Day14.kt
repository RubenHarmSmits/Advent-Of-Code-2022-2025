package days.year2017

import days.Day
import kotlin.math.max
import kotlin.math.min

fun main() {
    println(Day14().solve())
}

class Day14 : Day(14, 2017) {

    val input = "jzgqcdpd-"

    var regions = mutableListOf<MutableList<Point>>()

    fun solve(): Any {
        val grid = (0 until 128).map {
            getKnot((input + it).map { it.hashCode() } + listOf(17, 31, 73, 47, 23)).hexToBinaryString()
                .map { it == '1' }
        }
        for (y in grid.indices) {
            for (x in grid[0].indices) {
                if (grid[y][x]) {
                    val point = Point(y, x)
                    val adjacentRegions = regions.filter { region -> grid.getAdjacentCoordinates(point).any { region.contains(it) } }
                    val newRegion = adjacentRegions.flatten() + point
                    adjacentRegions.forEach { regions.remove(it) }
                    regions.add(newRegion.toMutableList())
                }
            }
        }
        return regions.size
    }

    fun getKnot(lengths: List<Int>): String {
        var list = (0 until 256).toList()
        val n = list.size
        var position = 0
        var skip = 0
        repeat(64) {
            lengths.forEach { length ->
                val selectBegin = list.subList(position, min(list.size, position + length))
                val selectEnd = list.subList(0, max(0, position + length - n))
                val reversed = (selectBegin + selectEnd).reversed()
                list = reversed.takeLast(selectEnd.size) + list.subList(selectEnd.size, position) + reversed.subList(
                    0,
                    selectBegin.size
                ) + list.subList(min(n, position + length), n)
                position = (position + length + skip) % n
                skip++
            }
        }

        return list.chunked(16)
            .map { sixteen -> sixteen.reduce { a, b -> a xor b } }
            .map { it.toString(16) }.joinToString(separator = "") { if (it.length == 2) it else "0$it" }
    }
}
