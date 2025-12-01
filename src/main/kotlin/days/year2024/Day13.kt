package days.year2024

import days.Day

fun main() {
    println(Day13().solve())
}

class Day13 : Day(13, 2024) {

    data class Machine(
        val buttonA: PointL,
        val buttonB: PointL,
        val price: PointL,
    )

    private val machines = inputList.splitBy { it == "" }.map {
        val (xa, ya) = extraxtAllLongsFromString(it[0])
        val (xb, yb) = extraxtAllLongsFromString(it[1])
        val (xp, yp) = extraxtAllLongsFromString(it[2])
        Machine(PointL(xa, ya), PointL(xb, yb), PointL(xp + 10000000000000, yp + 10000000000000))
    }

    fun solve(): Any {
        return machines.sumOf { checkScore(it) }
    }

    private fun checkScore(machine: Machine): Long {
        val ay = machine.buttonA.y
        val ax = machine.buttonA.x
        val by = machine.buttonB.y
        val bx = machine.buttonB.x
        val py = machine.price.y
        val px = machine.price.x

        val nb = (ay * px - ax * py) / (ay * bx - ax * by)
        val na = (by * px - bx * py) / (by * ax - bx * ay)
        println("nb=$nb na=$na")
        return if (ay * na + by * nb == py && ax * na + bx * nb == px) 3L * na + nb else 0L

    }

}