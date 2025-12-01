package days.year2018

import days.Day
import kotlin.collections.first
import kotlin.collections.sorted
import kotlin.math.*

fun main() {
    println(Day16().solve())
}
class Day16: Day(16, 2018) { // 181055 < answer < 236639
    var count = 0

    var grid = matrixOfInput(inputList).toMutableMatrix()

    data class Unit(val isGoblin: Boolean,var position: Point ,var hp: Int=200){
        fun isAlive() = hp>0
    }

    val units = mutableListOf<Unit>()

    fun solve(): Any {
        println(count)
        grid.indices.forEach { y ->
            grid.indices.forEach { x ->
                if(grid[y][x] in "GE"){
                    units.add(Unit(grid[y][x]=='G', Point(y,x)))
                }
            }
        }
        grid = grid.map{it.map{if(it in "GE")'.' else it}}.toMutableMatrix()

        while(true) {
            println(count)
            val sorted = units.sortedBy { it.position }
            sorted.forEach { unit ->
                println(unit)
                if(unit !in units) return@forEach

                val p = findShortestPath(unit)
                if(p!=null) unit.position=p

                val surrounding = grid.getAdjacentCoordinates(unit.position)
                val closeEnemy = units.filter { it.isGoblin != unit.isGoblin }.filter { it.position in surrounding }.sortedBy { it.position }.minByOrNull { it.hp }
                if(closeEnemy!=null) {
                    closeEnemy.hp-=3
                    if(!closeEnemy.isAlive()){
                        units.remove(closeEnemy)
                        if(units.map{it.isGoblin}.toSet().size == 1){
                            if(units.indexOf(unit)==units.size-1)count++
                            return count * units.sumOf { it.hp }
                        };
                    }
                }
            }
            count++
        }
    }

    private fun findShortestPath(unit: Unit): Point? {
        val paths = mutableListOf<MutableList<Point>>()
        var smallest = Int.MAX_VALUE
        val mem = mutableMapOf<Point, Int>()

        fun go(point: Point, seen: MutableList<Point>, lookingForGoblin: Boolean){
            val steps = seen.size
            val m = mem[point]
            if(m!=null && m<steps) return
            mem[point] = steps

            if(seen.size>smallest)return
            if(point in units.filter{it.isGoblin==lookingForGoblin}.map{it.position}){
                paths.add(seen.toMutableList())
                smallest = min(smallest, seen.size)
                return
            }

            val surrounding = grid.getAdjacentCoordinates(point)
            var options = surrounding.filter{grid.get(it)=='.'}
                .filter{it !in seen}
                .filter{it !in units.filter{it.isGoblin!=lookingForGoblin}.map{it.position}}
                .filter{option->seen.none {abs(option.y-it.y)+abs(option.x-it.x)==1  }}

            seen.add(point)

            options.forEach { option ->
                go(option, seen.toMutableList(), lookingForGoblin)
            }

        }

        go(unit.position, mutableListOf(), !unit.isGoblin)
        if(paths.isEmpty())return null
        val shortestSize = paths.minOf{it.size}
        if(shortestSize==1) return null
        return paths.filter{it.size==shortestSize}.sortedWith{ a, b ->
                for (i in 0 until shortestSize) {
                    val cmp = a[i].compareTo(b[i])
                    if (cmp != 0) return@sortedWith cmp
                }
                0
        }.first()[1]

    }
}