package days.year2025

import days.Day


fun main() {
    println(Day11().solve("svr", false, false))
}


class Day11 : Day(11, 2025) {
    val nodes = inputList.associate {
        it.substringBefore(":") to it.substringAfter(": ").split(" ")
    }

    val memory = hashMapOf<String, Long>()

    fun solve(node: String, dac: Boolean, fft: Boolean): Long {
        val hash = "$node$dac$fft"

        return memory.getOrPut(hash) {
            if (node == "out") {
                return if (dac && fft) 1L else 0L
            }
            nodes[node]!!.sumOf { other ->
                solve(other, dac || node == "dac", fft || node == "fft")
            }
        }
    }
}