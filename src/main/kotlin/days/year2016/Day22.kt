package days.year2016

import days.Day
import kotlin.math.min

fun main() {
    println(Day22().solve())
}

class Day22 : Day(22, 2016) {


    data class Node(val coor: Point, var used: Int, var avail: Int) {
        override fun toString(): String {
            return "$used-$avail"
        }
    }

    val endPoint = Point(0, 0)

    var fastest = 250

    var tot = 0

    val cache = HashMap<String, Int>()

    fun solve(): Any {
        var nodes = inputList.map { it.split(" ").filter { it != " " }.filter { it != "  " }.filter { it != "" } }
                .map {
                    val (notused, x, y) = it[0].split("-")
                    val nPoint = Point(y.removePrefix("y").toInt(), x.removePrefix("x").toInt())
                    val list = it.toMutableList()
                    list.removeFirst()
                    val (size, used, avail, usep) = list.map { it.removeSuffix("T") }.map { it.removeSuffix("%").toInt() }
                    Node(nPoint, used, avail)
                }



        val beginPoint = nodes.map { it.coor }.filter { it.y == 0 }.toMutableList().sortedBy { it.x }.last()


        for(y in 0..24){
            for(x in 0..36){
                val coor = nodes.find{it.coor.y==y&&it.coor.x==x}!!
                if(coor.used==0) print('-')
                else if(coor.used>100) print('#')
                else print('.')
            }
            println()
        }

        move(nodes.toMutableList(), 0, beginPoint)


        println(tot)
        return fastest +1;

        //51 steps to reach left of target data
        //36 steps to reach top left from top right * 5
        //221 < answer < 228 < 231 too high
        // no 224
    }

    fun move(nodes: MutableList<Node>, steps: Int, importantDataPoint: Point) {
        tot++
        if (steps >= fastest) return
        val hash = listToString(nodes, importantDataPoint)

        val oldNum = cache[hash]
        if (oldNum != null &&  oldNum <= steps)  return

        cache[hash] = steps

        val moves = getPossibleMoves(nodes)

        moves.shuffled().forEach {move ->
            var importantDataPointVar = importantDataPoint.copy()
            val copyNodes = nodes.toMutableList().map { it.copy() }

            val firstNode = copyNodes.find { it.coor == move.first.coor }!!
            val secondNode = copyNodes.find { it.coor == move.second.coor }!!

            firstNode.avail += move.first.used
            firstNode.used = 0
            secondNode.used += move.first.used
            secondNode.avail -= move.first.used

            if (firstNode.coor == importantDataPoint) {
                importantDataPointVar = secondNode.coor
            }

            if (endPoint == importantDataPointVar) {
                fastest = min(fastest, steps)
                println(fastest+1)
                return
            }


            move(copyNodes.toMutableList(), steps + 1, importantDataPointVar)
        }

    }

    private fun getPossibleMoves(nodes: List<Node>): MutableList<Pair<Node, Node>> {
        val moves = mutableListOf<Pair<Node, Node>>()
        for (node1 in nodes) {
            for (node2 in nodes) {
                if (nodesAreViablePair(node1, node2)) {
                    moves.add(node1 to node2)
                }
            }
        }
        return moves
    }

    private fun listToString(mem: MutableList<Node>, importantDataPoint: Point): String {
        val hash = mem.toString()
        return hash
    }


    private fun nodesAreViablePair(node1: Node, node2: Node): Boolean {
        if (!areStraightNeighbors(node1.coor, node2.coor)) return false
        if (node1.used == 0) return false
        if (node1 == node2) return false
        return node2.avail >= node1.used
    }


}