package days.year2025

import days.Day

fun main() {
    println(Day5().solve())
}

class Day5 : Day(5, 2025) {

    val list = inputList.splitBy { it == "" }

    val ranges: List<LongRange> = list.first().map {
        val spl = it.split("-")
        spl.first().toLong()..spl.last().toLong()
    }

    val intersectedRanges = mutableListOf<LongRange>()

    fun solve(): Any {
        ranges.forEach { range ->
            val takingOutRanges = mutableListOf<LongRange>()
            intersectedRanges.forEach { ir ->
                val interSected = intersectRanges(range, ir)
                if (interSected != null) takingOutRanges.add(interSected)
            }
            val outersected = outersectWithList(range, takingOutRanges)

            intersectedRanges.addAll(outersected)
        }

        return intersectedRanges.sumOf { it.last + 1 - it.first }
    }
}

