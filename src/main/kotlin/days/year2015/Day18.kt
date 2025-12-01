package days.year2015

import days.Day

fun main() {
    println(Day18().solve())
}

class Day18 : Day(18, 2015) {
    var grid = matrixOfInput(inputList).map { it.map { it == '#' } }


    fun solve(): Any {
        repeat(100) {
            val tempGrid = grid.toMutableMatrix()
            for(y in grid.indices) {
                for (x in grid[0].indices) {
                    val lightIsOn = grid[y][x]
                    val neighborLights = grid.getSurroundingCoordinates(Point(y,x)).count{grid.get(it)}
                    tempGrid[y][x] = (lightIsOn && neighborLights in 2 ..3) || (!lightIsOn && neighborLights == 3)
                }
            }
            tempGrid[0][0]=true
            tempGrid[0][99]=true
            tempGrid[99][0]=true
            tempGrid[99][99]=true
            grid = tempGrid
        }
        return grid.sumOf { it.count { it } }
    }
}

