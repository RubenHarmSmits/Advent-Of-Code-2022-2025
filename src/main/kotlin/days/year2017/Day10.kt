package days.year2017

import days.Day
import kotlin.math.max
import kotlin.math.min

fun main() {
    println(Day10().solve())
}

class Day10 : Day(10, 2017) {
    private val lengths = "88,88,211,106,141,1,78,254,2,111,77,255,90,0,54,205".map{it.hashCode()} + listOf(17, 31, 73, 47, 23)
    private var list = (0 until 256).toList()
    val n = list.size

    fun solve(): Any {
        var position = 0
        var skip = 0
        repeat(64){
            lengths.forEach { length ->
                val selectBegin = list.subList(position, min(list.size, position + length))
                val selectEnd = list.subList(0, max(0, position + length - n))
                val reversed = (selectBegin + selectEnd).reversed()
                list = reversed.takeLast(selectEnd.size) + list.subList(selectEnd.size, position)  + reversed.subList(0,selectBegin.size) + list.subList(min(n,position + length), n)
                position = (position + length + skip) % n
                skip++
            }
        }

        return list.chunked(16)
            .map { sixteen -> sixteen.reduce { a, b -> a xor b } }
            .map { it.toString(16) }.joinToString(separator = "") { if (it.length == 2) it else "0$it" }
    }

}
