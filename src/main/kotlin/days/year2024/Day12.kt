package days.year2024

import days.Day

fun main() {
    println(Day12().solve())
}

class Day12 : Day(12, 2024) {

    data class Region(
        val startingPoint: Point,
        val startingLetter: Char,
        val region: MutableList<Point>,
        var fences: Int
    )

    val grid = matrixOfInput(inputList)
    val memory = mutableListOf<Point>()
    val regions = mutableListOf<Region>()

    fun solve(): Any {
        grid.indices.forEach { y ->
            grid.indices.forEach { x ->
                val p = Point(y, x)
                if (p !in memory) {
                    val newRegion = Region(p, grid.get(p), mutableListOf(), 0)
                    regions.add(newRegion)
                    checkPlots(newRegion, Point(y, x))
                }
            }
        }
        println(regions.map{it.startingLetter})
        return regions.sumOf {
            (it.fences - getScore(it.region)) * it.region.size
        }
    }

    private fun getScore(region: MutableList<Point>):Int {
        var score = 0
        grid.indices.forEach { y->
            grid[0].indices.forEach { x ->
                if(Point(y, x) in region && Point(y, x+1) in region && Point(y-1, x) !in region && Point(y-1, x+1) !in region) score++
                if(Point(y, x) in region && Point(y, x+1) in region && Point(y+1, x) !in region && Point(y+1, x+1) !in region) score++
                if(Point(y, x) in region && Point(y+1, x) in region && Point(y, x-1) !in region && Point(y+1, x-1) !in region) score++
                if(Point(y, x) in region && Point(y+1, x) in region && Point(y, x+1) !in region && Point(y+1, x+1) !in region) score++
            }
        }
        return score
    }

    private fun checkPlots(region: Region, point: Point) {
        if (point in memory) return
        memory.add(point)
        region.region.add(point)
        val neighbours = grid.getAdjacentCoordinates(point).filter { grid.get(it) == region.startingLetter }
        region.fences += 4 - neighbours.size
        neighbours.filter { it !in memory }.forEach {
            checkPlots(region, it)
        }
    }

}