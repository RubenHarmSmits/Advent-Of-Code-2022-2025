package days.year2024

import days.Day

fun main() {
    println(Day8().solve())
}

class Day8 : Day(8, 2024) {

    val grid = matrixOfInput(inputList)
    val antennes = grid.flatten().toSet().filter { it != '.' }
    val antinodes = mutableSetOf<Point>()

    fun solve(): Any {
        println(antennes)

        antennes.forEach { antenne ->
            checkAntenne(antenne)
        }

        return antinodes.size
    }

    private fun checkAntenne(antenne: Char) {
        val locations = mutableListOf<Point>()
        grid.indices.forEach { y ->
            grid[0].indices.forEach { x ->
                if(grid[y][x]==antenne){
                    locations.add(Point(y, x))
                    antinodes.add(Point(y, x))
                }
            }
        }

        locations.combinations(2).forEach { (first,second) ->
            val dy = first.y-second.y
            val dx = first.x-second.x
            var factor1 =1
            while(true){
                val antinode1 = Point(first.y+(dy*factor1), first.x+(dx*factor1))
                if(grid.containsPoint(antinode1)) antinodes.add(antinode1)
                else break
                factor1+=1
            }
            var factor2 =1

            while(true){
                val antinode1 = Point(first.y-(dy*factor2), first.x-(dx*factor2))
                if(grid.containsPoint(antinode1)) antinodes.add(antinode1)
                else break
                factor2+=1
            }
        }
    }

}