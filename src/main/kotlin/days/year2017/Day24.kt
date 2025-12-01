package days.year2017

import days.Day
import kotlin.math.max

fun main() {
    println(Day24().solve())
}
class Day24: Day(24, 2017) {
    val bridges = inputList.map{it.split("/").map{it.toInt()}.let{it[0] to it[1]}}

    val mem = mutableListOf<List<Int>>()

    var highestSoFar = 0
    var longestSoFar = 0

    fun solve(): Any {
        bridges.forEachIndexed {i, it ->
            if(it.first==0) go(it.second, mutableListOf(i))
        }
        return highestSoFar
    }

    private fun go(end: Int, alreadyUsed: MutableList<Int>) {
        val sor = alreadyUsed.sorted()
        if(sor in mem)return
        mem.add(alreadyUsed.sorted().toMutableList())
        var found =false
        bridges.forEachIndexed { i, bridge ->
            if( i !in alreadyUsed ) {
                if(bridge.first == end){
                    found = true
                    go(bridge.second, (alreadyUsed.toMutableList() + i).toMutableList())
                }
                if(bridge.second == end){
                    found = true
                    go(bridge.first, (alreadyUsed.toMutableList() + i).toMutableList())
                }
            }
        }
        if(!found){
            val l = alreadyUsed.size
            if(l>longestSoFar){
                highestSoFar=0
                longestSoFar=l
            }
            if(l>=longestSoFar){
                val strength = alreadyUsed.sumOf { bridges[it].second +  bridges[it].first}
                highestSoFar = max(highestSoFar, strength)
                println(highestSoFar)
            }


        }
    }
}
