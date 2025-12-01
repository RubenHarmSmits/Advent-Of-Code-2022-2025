package days.year2024

import days.Day

fun main() {
    println(Day25().solve())
}

class Day25 : Day(25, 2024) {

    val input = inputList.splitBy { it == "" }
    val locks = input.filter { it[0] == "#####" }
        .map { it.map { it.toList() } }
        .map { it.toMutableMatrix().transposed(1)
            .map { it.indexOf('.') - 1 } }
    val keys = input.filter { it[6] == "#####" }
        .map { it.map { it.toList() } }
        .map { it.toMutableMatrix().transposed(1)
            .map { 6 - it.indexOf('#') } }

    fun solve(): Any {
        return locks.sumOf { lock ->
            keys.count { key ->checkOverlap(lock, key) }
        }
    }

    private fun checkOverlap(lock: List<Int>, key: List<Int>)=
        lock.indices.all { i -> lock[i] + key[i] <= 5 }



}