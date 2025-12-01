package days.year2017

import days.Day
import java.lang.Long.toBinaryString

fun main() {
    println(Day15().solve())
}
class Day15: Day(15, 2017) {
    var a = 699L
    var b = 124L

    var aa = 16807L
    var bb = 48271L

    val remainder = 2147483647L

    var count = 0
    var countPair = 0

    val alist = mutableListOf<Long>()

    fun solve(): Any {
        println(Int.MAX_VALUE)
        while(countPair < 5000000){
            a = a * aa % remainder
            b = b * bb % remainder
            if(a%4L==0L)alist.add(a)
            if(b%8L==0L){
                val abin = toBinaryString(alist[countPair])
                countPair++
                val bbin = toBinaryString(b)
                if(abin.takeLast(16)==bbin.takeLast(16))count++
            }
        }
        return count
    }
}
