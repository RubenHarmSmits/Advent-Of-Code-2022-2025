package days.year2024

import days.Day

fun main() {
    println(Day6().solve())
}

class Day6 : Day(6, 2024) {

    val grid = matrixOfInput(inputList)
    var total =0
    val startinPoint = grid.findChar('^')
    val direction = Direction.UP

    fun solve(): Any {
        grid.indices.forEach { y ->
            grid[1].indices.forEach { x ->
                if(grid.get(Point(y,x))=='.'){
                    val newgrid = grid.toMutableMatrix()
                    newgrid[y][x]='#'
                    if(guardInLoop(newgrid)) total+=1
                }
            }
        }
        return total

    }

    private fun guardInLoop(newgrid: MutableList<MutableList<Char>>): Boolean {
        try {
            return move(newgrid, startinPoint, direction, mutableListOf())
        } catch (e: Exception) {
            return false
        }
    }

    private fun move(
        newgrid: MutableList<MutableList<Char>>,
        point: Point,
        direction: Direction,
        visited: MutableList<Pair<Point, Direction>>
    ): Boolean {
        if(point to direction in visited){
            println(total)
            return true
        }
        visited.add(point to direction)
        when (direction) {
            Direction.UP -> {
                if(newgrid[point.y-1][point.x]=='#') {
                    return move(newgrid, point, Direction.RIGHT, visited)
                } else {
                    return move(newgrid, Point(point.y-1,point.x), Direction.UP, visited)
                }
            }
            Direction.DOWN -> {
                if(newgrid[point.y+1][point.x]=='#') {
                    return move(newgrid, point, Direction.LEFT, visited)
                } else {
                    return move(newgrid, Point(point.y+1,point.x), Direction.DOWN, visited)
                }
            }
            Direction.LEFT -> {
                if(newgrid[point.y][point.x-1]=='#') {
                    return move(newgrid, point, Direction.UP, visited)
                } else {
                    return move(newgrid, Point(point.y,point.x-1), Direction.LEFT, visited)
                }
            }
            Direction.RIGHT -> {
                if(newgrid[point.y][point.x+1]=='#') {
                    return move(newgrid, point, Direction.DOWN, visited)
                } else {
                    return move(newgrid, Point(point.y,point.x+1), Direction.RIGHT, visited)
                }
            }
        }
    }

}