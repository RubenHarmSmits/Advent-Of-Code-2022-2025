package days.year2025

import days.Day
import java.util.SortedSet
import kotlin.math.*
import kotlin.math.max

fun main() {
    println(Day9().solve())
}

class Day9 : Day(9, 2025) {
    var corners = inputList.map {
        val l = extraxtAllIntsFromString(it)
        Point(l[1], l[0])
    }.toMutableList()

    val ys = corners.map { it.y }.sorted().toSet()
    val xs = corners.map { it.x }.sorted().toSet()


    var outer = mutableListOf<Point>()
    var outerS: SortedSet<Point> = sortedSetOf()

    val maxy = corners.maxOf { it.y }
    var highest = 0L

    var all = mutableSetOf<Point>()
    val allRanges = Array<MutableList<IntRange>>(maxy + 1) {
        mutableListOf()
    }


    fun solve(): Any {
        corners.add(corners.first())
        corners.zipWithNext { first, second ->
            for (y in min(first.y, second.y)..max(first.y, second.y)) {
                for (x in min(first.x, second.x)..max(first.x, second.x)) {
                    outer.add(Point(y, x))
                }
            }
        }
        outerS = outer.toSortedSet()

        fun findH(y: Int): MutableList<IntRange> {
            val inter = outerS.filter { op ->
                y == op.y
            }.map { it.x }

            var on = true
            var previousx = inter.first()
            var beginonx = inter.first()

            val ranges = mutableListOf<IntRange>()

            inter.takeLast(inter.size - 1).forEachIndexed { i, x ->
                if (x - previousx == 1) {
                    previousx = x
                    if (i == inter.takeLast(inter.size - 1).size - 1) {
                        if (!on) throw Exception("Ruben")
                        ranges.add(beginonx..x)
                    }
                } else {
                    if (on) {
                        ranges.add(beginonx..x)
                    } else {
                        beginonx = x
                    }
                    on = !on
                }

            }
            return ranges
        }


        ys.zipWithNext { firsty, secondy ->
            println(firsty)
            var ranges = findH(firsty)
            allRanges[firsty] = ranges
            var rangesBelow = findH(firsty + 1)
            for (y in firsty + 1 until secondy) {
                allRanges[y] = rangesBelow
            }
        }

        var r = findH(ys.last())
        allRanges[ys.last()] = r
        println("done")

        for (first in corners) {
            println(first)
            for (second in corners) {
                val size = (abs(first.y - second.y) + 1).toLong() * (abs(first.x - second.x) + 1).toLong()
                if (size > highest && possible(first, second)) {
                    highest = size
                    println(highest)
                }

            }
        }
        return highest
    }

    private fun possible(first: Point, second: Point): Boolean {
        (min(first.y, second.y)..max(first.y, second.y)).filter{it in ys}.forEach { y ->
            val ranges = allRanges[y]
            (min(first.x, second.x)..max(first.x, second.x)).filter{it in xs}.forEach { x ->
                val xinside = ranges.any { x in it }
                if (!xinside) return false
            }
        }
        return true
    }

}