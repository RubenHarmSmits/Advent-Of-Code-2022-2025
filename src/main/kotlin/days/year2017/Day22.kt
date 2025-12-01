package days.year2017

import days.Day

fun main() {
    println(Day22().solve())
}

class Day22 : Day(22, 2017) { // too low 2142871
    val g = matrixOfInput(inputList).toMutableMatrix()
    val states = sortedMapOf<Point, Int>()
    var count = 0

    val point = Point(12,12)
    var direction = Direction.UP

    fun solve(): Any {
        g.forEachIndexed { y, row->
            row.forEachIndexed { x, char->
                if(char == '#') {
                    states[Point(y,x)] = 2
                }
            }
        }
        repeat(10000000){
            println(it)
            when(states[point]){
                null -> {
                    states[point.copy()] = 1
                    direction = moveLeft(direction)
                }
                1 -> {
                    states[point.copy()] = 2
                    count++
                }
                2 -> {
                    states[point.copy()] = 3
                    direction = moveRight(direction)
                }
                3 -> {
                    states.remove(point)
                    direction = moveReverse(direction)
                }
            }
            point.move(direction)
        }
        return count
    }

    private fun moveRight(direction: Direction): Direction {
            if(direction == Direction.UP) return Direction.RIGHT
            if(direction == Direction.RIGHT) return Direction.DOWN
            if(direction == Direction.DOWN) return Direction.LEFT
            if(direction == Direction.LEFT) return Direction.UP
        throw IllegalArgumentException("Invalid direction")
    }

    private fun moveReverse(direction: Direction): Direction {
        if(direction == Direction.UP) return Direction.DOWN
        if(direction == Direction.RIGHT) return Direction.LEFT
        if(direction == Direction.DOWN) return Direction.UP
        if(direction == Direction.LEFT) return Direction.RIGHT
        throw IllegalArgumentException("Invalid direction")
    }

    private fun moveLeft(direction: Direction): Direction {
        if(direction == Direction.UP) return Direction.LEFT
        if(direction == Direction.RIGHT) return Direction.UP
        if(direction == Direction.DOWN) return Direction.RIGHT
        if(direction == Direction.LEFT) return Direction.DOWN
        throw IllegalArgumentException("Invalid direction")
    }

}
