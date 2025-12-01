package days.year2024

import days.Day

fun main() {
    println(Day4().solve())
}

class Day4 : Day(4, 2024) {

    val grid = matrixOfInput(inputList)
    var total =0

    val a = listOf(3,4,5).count{it==3}

    fun solve(): Any {
        grid.indices.forEach { y->
            grid[0].indices.forEach { x->
                checkXmas(Point(y,x))
            }
        }
        return total
    }

    private fun checkXmas(point: Point): Int {
        if(grid.get(point)!='A') return 0
        try {
            val first = "${grid.get(Point(point.y-1, point.x+1))} + ${grid.get(Point(point.y+1, point.x-1))}"
            val second = "${grid.get(Point(point.y+1, point.x+1))} + ${grid.get(Point(point.y-1, point.x-1))}"
            if(first.contains('M')&&first.contains('S')&&second.contains('M')&&second.contains('S')) total+=1
        } catch (e: Exception) {
            return 0
        }

        return 0

    }

    private fun checkLetter(letterInt: Int, point: Point, direction: Point): Int {
        val newpoint = Point(direction.y + point.y,direction.x + point.x)

        try {
            val letter = grid[newpoint.y][newpoint.x]
            when(letterInt){
                1 -> if(letter != 'M') return 0
                2 -> if(letter != 'A') return 0
                3 -> return if(letter != 'S') 0 else 1
            }
            return checkLetter(letterInt+1, newpoint, direction)

        } catch (e: Exception){
            return 0
        }

    }


}