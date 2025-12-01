package days.year2017

import days.Day

fun main() {
    println(Day17().solve())
}

class Day17 : Day(17, 2017) {
    val steps = 382
    var position = 0
    var size = 1
    var after0 = 1

    fun solve(): Any {
        repeat(50000000){
            position = (position + steps) % size + 1
            if(position==1) after0 = it+1
            size++
        }
        return after0
    }

}
