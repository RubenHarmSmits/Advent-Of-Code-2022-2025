package days.year2024

import days.Day

fun main() {
    println(Day15().solve())
}


class Day15 : Day(15, 2024) {

    val split = inputList.splitBy { it == "" }
    val grid = matrixOfInput(split[0].map {
        doubleLine(it)
    }).toMutableMatrix()
    val line = split[1].joinToString().filter { !" ,".contains(it) }

    val boxes = mutableListOf<Point>()

    fun doubleLine(line: String): String {
        var newLine = ""
        line.forEachIndexed { index, c ->
            if (c == '#') newLine += "##"
            if (c == 'O') newLine += "[]"
            if (c == '.') newLine += ".."
            if (c == '@') newLine += "@."
        }
        return newLine
    }


    fun solve(): Any {

        println(line)

        line.forEach { direction ->
//            grid.print()
//            println(direction)
            val person = grid.findChar('@')
            when (direction) {
                'v' -> {
                    val oneDown = grid[person.y + 1][person.x]
                    if (oneDown == '.') {
                        grid[person.y][person.x] = '.'
                        grid[person.y + 1][person.x] = '@'
                    } else if ("[]".contains(oneDown)) {
                        var lines = mutableListOf(listOf(person.x))
                        val other = person.x + if (oneDown == ']') -1 else 1
                        lines.add(listOf(person.x, other).sorted())
                        run loop@{
                            (1..grid.size).forEach { dy ->
                                val lastEntry = lines.last()
                                if (grid[person.y + dy + 1].filterIndexed { i, char -> lastEntry.contains(i) && char == '#' }.isNotEmpty()) return@loop
                                if (grid[person.y + dy + 1].filterIndexed { i, char -> lastEntry.contains(i) && char == '.' }.size == lastEntry.size) {
                                    // move all up
                                    lines.reversed().forEachIndexed { i, line ->
                                        val yoflast = person.y + lines.size - 1 - i
                                        line.forEach { x ->
                                            grid[yoflast + 1][x] = grid[yoflast][x]
                                            grid[yoflast][x] = '.'
                                        }
                                    }
                                    return@loop
                                } else {
                                    val newLine = mutableSetOf<Int>()
                                    // add new line
                                    lastEntry.forEach { x ->
                                        val oneDownbox = grid[person.y + lines.size][x]
                                        if (oneDownbox == '[') {
                                            newLine.add(x)
                                            newLine.add(x + 1)
                                        }
                                        if (oneDownbox == ']') {
                                            newLine.add(x)
                                            newLine.add(x - 1)
                                        }
                                    }
                                    lines.add(newLine.toList())
                                }
                            }
                        }

                    }
                }

                '^' -> {
                    val oneUp = grid[person.y - 1][person.x]
                    if (oneUp == '.') {
                        grid[person.y][person.x] = '.'
                        grid[person.y - 1][person.x] = '@'
                    } else if ("[]".contains(oneUp)) {
                        var lines = mutableListOf(listOf(person.x))
                        val other = person.x + if (oneUp == ']') -1 else 1
                        lines.add(listOf(person.x, other).sorted())
                        run loop@{
                            (1..grid.size).forEach { dy ->
                                val lastEntry = lines.last()
                                if (grid[person.y - dy - 1].filterIndexed { i, char -> lastEntry.contains(i) && char == '#' }
                                        .isNotEmpty()) return@loop
                                if (grid[person.y - dy - 1].filterIndexed { i, char -> lastEntry.contains(i) && char == '.' }.size == lastEntry.size) {
                                    // move all up
                                    lines.reversed().forEachIndexed { i, line ->
                                        val yoflast = person.y - lines.size + 1 + i
                                        line.forEach { x ->
                                            grid[yoflast - 1][x] = grid[yoflast][x]
                                            grid[yoflast][x] = '.'
                                        }
                                    }
                                    return@loop
                                } else {
                                    val newLine = mutableSetOf<Int>()
                                    // add new line
                                    lastEntry.forEach { x ->
                                        val oneUpbox = grid[person.y - lines.size][x]
                                        if (oneUpbox == '[') {
                                            newLine.add(x)
                                            newLine.add(x + 1)
                                        }
                                        if (oneUpbox == ']') {
                                            newLine.add(x)
                                            newLine.add(x - 1)
                                        }
                                    }
                                    lines.add(newLine.toList())
                                }
                            }
                        }

                    }
                }

                '>' -> {
                    val sub = grid[person.y].subList(person.x, grid[0].size)
                    val indexFirstWall = sub.indexOf('#')
                    val indexFirstFree = sub.indexOf('.')
                    if (indexFirstFree != -1 && indexFirstWall > indexFirstFree) {
                        grid[person.y][person.x] = '.'
                        grid[person.y][person.x + indexFirstFree] = '['
                        (person.x..person.x + indexFirstFree).forEach { x ->
                            if (grid[person.y][x] == '[') grid[person.y][x] = ']'
                            else if (grid[person.y][x] == ']') grid[person.y][x] = '['
                        }
                        grid[person.y][person.x + 1] = '@'
                    }
                }

                '<' -> {
                    val sub = grid[person.y].subList(0, person.x + 1).reversed()
                    val indexFirstWall = sub.indexOf('#')
                    val indexFirstFree = sub.indexOf('.')
                    if (indexFirstFree != -1 && indexFirstWall > indexFirstFree) {
                        grid[person.y][person.x] = '.'
                        grid[person.y][person.x - indexFirstFree] = ']'
                        (0..indexFirstFree).forEach { x ->
                            if (grid[person.y][person.x - x] == '[') grid[person.y][person.x - x] = ']'
                            else if (grid[person.y][person.x - x] == ']') grid[person.y][person.x - x] = '['
                        }
                        grid[person.y][person.x - 1] = '@'
                    }
                }
            }
        }

        grid.print()

        grid.indices.forEach { y ->
            grid[0].indices.forEach { x ->
                if (grid[y][x] == '[') boxes.add(Point(y, x))
            }
        }
        return boxes.sumOf { it.y * 100 + it.x }
    }


}