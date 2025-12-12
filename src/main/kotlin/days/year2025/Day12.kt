package days.year2025

import days.Day

fun main() {
    println(Day12().solve())
}

class Day12 : Day(12, 2025) {
    var list = inputList.splitBy { it == "" }.toMutableList()
    var presents = list.removeLast()

    val shapes = list.map { it.toMutableList() }
        .also { it.forEach { it.removeFirst() } }
        .map { it.map { it.map { it == '#' } } }
            .map { allRotations(it) }

    fun solve(): Any {
        return presents.count { line ->
            val grid = line.substringBefore(":").split("x")
                .let { p -> Array(p.last().toInt()) { BooleanArray(p.first().toInt()) } }
            val presentCounts = line.substringAfter(": ").split(" ").map { it.toInt() }
            val totalsize = grid.size * grid[0].size
            val totalsizePresents = presentCounts.mapIndexed { i, count ->
                shapes[i].first().sumOf { line -> line.count { it } } * count
            }.sum()
            totalsize > totalsizePresents
            //bruteforce(grid, presentCounts)
        }
    }

    fun bruteforce(grid: Array<BooleanArray>, presentCounts: List<Int>): Boolean {
        if (presentCounts.all { it == 0 }) return true
        for (y in 0 until grid.size - 2) {
            for (x in 0 until grid[0].size - 2) {
                presentCounts.forEachIndexed { i, count ->
                    if (count <= 0) return@forEachIndexed
                    for (shape in shapes[i]) {
                        tryToFit(grid, shape, y, x)?.let { newGrid ->
                            val updatedCounts = presentCounts.toMutableList().apply { this[i]-- }
                            if (bruteforce(newGrid, updatedCounts)) return true
                        }
                    }
                }
            }
        }
        return false
    }

    private fun tryToFit(grid: Array<BooleanArray>, box: List<List<Boolean>>, y: Int, x: Int): Array<BooleanArray>? {
        val copy = grid.copyOf()
        for (yi in 0..2) {
            for (xi in 0..2) {
                if (grid[y + yi][x + xi] && box[yi][xi]) return null
                else copy[y + yi][x + xi] = box[yi][xi] || grid[y + yi][x + xi]
            }
        }
        return copy.toMutableList().toTypedArray()
    }

    private fun allRotations(box: List<List<Boolean>>): Set<List<List<Boolean>>> {
        val rotations = (0..3).map { rotateBooleanMatrixCLockwise(box.toMutableMatrix(), it) }
        val mirrored = rotations.map { it.map { row -> row.reversed() } }
        return (rotations + mirrored).toSet()
    }
}