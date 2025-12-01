package days.year2015

import days.Day

fun main() {
    Day7().solve()
}

class Day7 : Day(7, 2015) {

    val wires = mutableMapOf<String, Int>()

    val visited = BooleanArray(inputList.size)

    fun solve(): Any {
        while (visited.any { !it }) {
            inputList.forEachIndexed { i, line ->
                if (!visited[i]) {
                    val (from, to) = line.split(" -> ")
                    val spl = from.split(" ")
                    try {
                        val amoount = getAmount(spl)
                        wires.getOrPut(to) { amoount }
                        visited[i] = true
                    } catch (e: Exception) {
                        println(e)
                    }
                }
            }
        }
        return wires["a"]!!
    }

    private fun getAmount(spl: List<String>): Int {
        if (spl.contains("AND")) {
            val (first, second, third) = spl
            return getValue(first) and getValue(third)
        } else if (spl.contains("OR")) {
            val (first, second, third) = spl
            return getValue(first) or getValue(third)
        } else if (spl.contains("LSHIFT")) {
            val (first, second, third) = spl
            return getValue(first) shl third.toInt()
        } else if (spl.contains("RSHIFT")) {
            val (first, second, third) = spl
            return getValue(first) shr third.toInt()
        } else if (spl.contains("NOT")) {
            return 65535 - getValue(spl[1])
        } else {
            return getValue(spl.first())
        }
    }

    private fun getValue(v: String) = if (isNumeric(v)) v.toInt() else wires[v]!!

}




