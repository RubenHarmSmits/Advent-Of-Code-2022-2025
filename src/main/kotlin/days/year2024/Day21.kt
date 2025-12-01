package days.year2024

import days.Day

fun main() {
    println(Day21().solve())
}

class Day21 : Day(21, 2024) { // too high 207517476003690, 147386330975014, 129551515895690,

    data class Map(val name: Char, val list: List<Pair<Char, String>>)
    data class Translate(val from: String, val to: String)
    data class Pattern(val from: String, val to: List<String>)

    val patterns = mutableListOf<Pattern>()
    val translations = mutableListOf<Translate>()
    val mapsButtons = mutableListOf<Map>()

    fun solve(): Any {
        puzzle()
        return 0
//        fillMaps()
//        return inputList.sumOf { calculateComplexity(it) }
    }

    fun puzzle() {
        (0..9).forEach { A ->
            (0..9).forEach { B ->
                (0..9).forEach { C ->
                    (0..9).forEach { D ->
                        val newNumberString = "$A$B$C$D"
                        val newnumber = newNumberString.toInt()
                        val sumLower = summation(newnumber - 1)
                        val perm = perm(newNumberString).toSet()
//                            .map { addNumber(it) }
//                            .flatten()
                        perm.forEach { oldNumberString ->
                            val oldnumber = oldNumberString.toInt()
                            val sumHigher = summation(oldnumber) - summation(newnumber)
                            if (sumHigher == sumLower){
                                println("$oldnumber $newnumber")
                            }
                        }
                    }
                }
            }
        }
    }

    fun addNumber(oldNumberString: String): List<String> {
        val newList = mutableListOf<String>()
        (0..9).map{it.toString()}.forEach { newChar->
            newList.add(newChar + oldNumberString)
            newList.add(oldNumberString[0] + newChar + oldNumberString.substring(1))
            newList.add(oldNumberString.substring(0,2) + newChar + oldNumberString.substring(2))
            newList.add(oldNumberString.substring(0,3) + newChar + oldNumberString.substring(3))
            newList.add(oldNumberString.substring(0,4) + newChar + oldNumberString.substring(4))
        }

        return newList.toSet().toList()
    }

    fun puzzle2() {
        (1..9).forEach { A ->
            (1..9).forEach { B ->
                (1..9).forEach { C ->
                    (1..9).forEach { D ->
                        val first = "$A$B$C$D".toInt()
                        val second = first * 3
                        val all = "$A$B$C$D" + second.toString()
                        if (all.toSet().size == 9 && !all.contains("0")) {
                            println(all)
                        }
                    }
                }
            }
        }
    }

    private fun fillMaps() {
        mapsButtons.addAll(
            listOf(
                Map(
                    'A', listOf(
                        '0' to "<",
                        '1' to "^<<",
                        '2' to "<^,^<",
                        '3' to "^",
                        '4' to "^^<<",
                        '6' to "^^",
                        '7' to "^^^<<",
                        '8' to "<^^^",
                        '9' to "^^^",
                    )
                ),
                Map(
                    '0', listOf(
                        'A' to ">",
                        '1' to "^<",
                        '2' to "^",
                        '3' to "^>",
                        '4' to "^^<",
                        '5' to "^^",
                        '7' to "^^^<",
                        '8' to "^^^",
                        '9' to "^^^>",
                    )
                ),
                Map(
                    '1', listOf(
                        '7' to "^^",
                        '2' to ">",
                        '4' to "^",
                        '6' to "^>>",
                    )
                ),
                Map(
                    '2', listOf(
                        '9' to "^^>", //!!!!
                    )
                ),
                Map(
                    '3', listOf(
                        'A' to "v",
                        '7' to "^^<<",
                    )
                ),
                Map(
                    '4', listOf(
                        '0' to ">vv",
                        '5' to ">",
                    )
                ),
                Map(
                    '5', listOf(
                        '6' to ">",
                    )
                ),
                Map(
                    '6', listOf(
                        'A' to "vv",
                        '9' to "^",
                    )
                ),
                Map(
                    '7', listOf(
                        '0' to ">vvv",
                        '9' to ">>",
                    )
                ),
                Map(
                    '8', listOf(
                        '0' to "vvv",
                    )
                ),
                Map(
                    '9', listOf(
                        'A' to "vvv",
                        '8' to "<",
                    )
                )
            )
        )

        translations.addAll(
            listOf(
                Translate("A^", "<"),
                Translate("Av", "<v"),
                Translate("A<", "v<<"),
                Translate("A>", "v"),
                Translate("AA", ""),
                Translate("v^", "^"),
                Translate("vA", "^>"),
                Translate("v<", "<"),
                Translate("v>", ">"),
                Translate("vv", ""),
                Translate("^A", ">"),
                Translate("^v", "v"),
                Translate("^<", "v<"),
                Translate("^>", "v>"),
                Translate("^^", ""),
                Translate(">^", "<^"),
                Translate(">v", "<"),
                Translate("><", "<<"),
                Translate(">A", "^"),
                Translate(">>", ""),
                Translate("<^", ">^"),
                Translate("<v", ">"),
                Translate("<A", ">>^"),
                Translate("<>", ">>"),
                Translate("<<", "")
            )
        )

    }


    private fun calculateComplexity(code: String): Long {
        val number = code.substring(0, 3).toLong()
        val shortestSequence = calculateShortestSequence("A" + code)
        return number * shortestSequence
    }

    private fun calculateShortestSequence(code: String): Long {
        var sequence = ""
        code.windowed(2).forEach {
            val (start, end) = it[0] to it[1]
            val option = mapsButtons.find { it.name == start }!!.list.find { it.first == end }!!.second
            sequence += option + "A"
        }

        var counts = mutableMapOf<Pattern, Long>()
        sequence.split("A").dropLast(1).forEach { pat ->
            val pattern = getAndAddPattern(pat)
            counts[pattern] = counts.getOrDefault(pattern, 0) + 1
        }
        calculateN(counts)
        repeat(25) {
            counts = translateCounts(counts)
        }

        return calculateN(counts)
    }

    private fun calculateN(counts: MutableMap<Pattern, Long>): Long {
        return counts.map { count ->
            (count.key.from.length + 1) * count.value
        }.sum()
    }

    private fun getAndAddPattern(path: String): Pattern {
        return patterns.find { it.from == path } ?: Pattern(path, getPatterns(path)).also {
            patterns.add(it)
        }
    }

    private fun translateCounts(counts: MutableMap<Pattern, Long>): MutableMap<Pattern, Long> {
        val newCounts = mutableMapOf<Pattern, Long>()
        counts.forEach { (pattern, count) ->
            pattern.to.forEach { to ->
                val patO = getAndAddPattern(to)
                newCounts[patO] = newCounts.getOrDefault(patO, 0L) + count
            }
        }
        return newCounts
    }

    private fun getPatterns(directions: String): List<String> {
        return shortestByPath("A${directions}A").split("A").dropLast(1)
    }

    private fun shortestByPath(directions: String): String {
        var sequence = ""
        directions.windowed(2).forEach {
            val option = translations.find { t -> t.from == it }!!.to
            sequence += option + "A"
        }
        return sequence
    }

}