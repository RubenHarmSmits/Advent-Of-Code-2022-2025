package days.year2015

import days.Day
import kotlin.math.min

fun main() {
    println(Day14().solve())
}

class Day14 : Day(14, 2015) { // too high 2502

    data class Commet(val time: Int, val speed: Int, val restTime: Int, var points: Int = 0)

    val seconds = 2503
    val comets = inputList.map { line ->
        val (_, _, _, speed, _, _, time, _, _, _, _, _, _, rest, _) = line.split(" ")
        Commet(time.toInt(), speed.toInt(), rest.toInt())
    }

    fun solve(): Any {
        (1..seconds).forEach { i ->
            val scores = comets.map { comet ->
                val secondsFlying =
                    (i / (comet.time + comet.restTime)) * comet.time + min(
                        comet.time,
                        i % (comet.time + comet.restTime)
                    )
                secondsFlying * comet.speed
            }
            val highest = scores.max()
            scores.forEachIndexed { i, score ->
                if (score == highest) comets[i].points += 1
            }
        }
        return comets.maxOf { it.points }
    }

}