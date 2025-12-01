package days.year2017

import days.Day
import kotlin.math.abs

fun main() {
    println(Day3().solve())
}
class Day3: Day(3, 2017) { // high 393216
    val input = 325489

    fun solve(): Any {
        var number = 1
        var point = Point(0, 0)
        var points = mutableListOf(point to number)
        var direction = Direction.DOWN
        while(number < input) {
            when(direction){
                Direction.DOWN -> {
                    if(Point(point.y, point.x+1) in points.map{it.first}){
                        point = point.copy(y = point.y+1)
                    } else {
                        point = point.copy(x = point.x+1)
                        direction = Direction.RIGHT
                    }
                }
                Direction.RIGHT -> {
                    if(Point(point.y-1, point.x) in points.map{it.first}){
                        point = point.copy(x = point.x+1)
                    } else {
                        point = point.copy(y = point.y-1)
                        direction = Direction.UP
                    }
                }
                Direction.UP -> {
                    if(Point(point.y, point.x-1) in points.map{it.first}){
                        point = point.copy(y = point.y-1)
                    } else {
                        point = point.copy(x = point.x-1)
                        direction = Direction.LEFT
                    }
                }
                Direction.LEFT -> {
                    if(Point(point.y+1, point.x) in points.map{it.first}){
                        point = point.copy(x = point.x-1)
                    } else {
                        point = point.copy(y = point.y+1)
                        direction = Direction.DOWN
                    }
                }
            }
            number = points.filter {abs(it.first.y-point.y)<=1 && abs(it.first.x-point.x)<=1}.sumOf { it.second }
            println(number)
            points.add(point to number)
        }
        return number
    }
}
