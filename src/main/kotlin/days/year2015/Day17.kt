package days.year2015

import days.Day

fun main() {
    println(Day17().solve())
}

class Day17 : Day(17, 2015) { //to0 low 1010
    val containers = inputListInt
    val n = containers.size
    val goal = 150

    fun solve(): Int {
        return check(0, 0, 0)
    }

    fun check(soFar: Int, i: Int, containersN: Int): Int {
        if (soFar == goal) return 1
        if (containersN == 4) return 0
        if (i == n) return 0
        if (soFar > goal) return 0
        return check(soFar + containers[i], i + 1, containersN + 1) + check(soFar, i + 1, containersN)
    }
}

