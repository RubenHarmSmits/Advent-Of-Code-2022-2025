package days.year2025

import days.Day

fun main() {
    println(Day8().solve())
}

class Day8 : Day(8, 2025) {


    var list = inputList.map{it.split(",").map{it.toLong()}}.map{ Node(Point3L(it[1], it[0], it[2])) }
    data class Node(val p:Point3L, val connections: MutableSet<Int> = mutableSetOf())

    val circuits = list.map{mutableSetOf(it.p)}.toMutableList()

    fun solve():Any {
        while(true) {
            var closestNumber = Long.MAX_VALUE
            var closestF = list[0]
            var closestS = list[0]
            var closestFi = 0
            var closestSi = 0
            for(fi in list.indices){
                for(si in list.indices){
                    val first = list[fi]
                    val second = list[si]
                    if(fi != si && fi !in second.connections){
                        val distance = straightDistance3(first.p, second.p)
                        if(distance < closestNumber){
                            closestNumber = distance
                            closestF = first
                            closestS = second
                            closestFi = fi
                            closestSi = si
                        }
                    }
                }
            }
            closestF.connections.add(closestSi)
            closestS.connections.add(closestFi)
            val firstCircuit = circuits.find{closestF.p in it}!!
            val secondCircuit = circuits.find{closestS.p in it}!!
            if(firstCircuit != secondCircuit){
                firstCircuit.addAll(secondCircuit)
                circuits.remove(secondCircuit)
            }
            if(circuits.size == 1){
                return closestF.p.x * closestS.p.x
            }
        }
    }
}
