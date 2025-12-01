package days.year2016

import days.Day

fun main() {
    println(Day21().solve())
}

class Day21 : Day(21, 2016) {

    fun solve(): Any{
        while (true){
            var input = "fbgdceah".toCharArray().toMutableList().shuffled();
            var output = solveInput(input.toCharArray())
            if (output == "fbgdceah") return input.joinToString("")
        }

    }


    fun solveInput(inp: CharArray): String {
        var input = inp
        inputList.map { it }
                .forEachIndexed { i, it ->
                    var list = it.split(" ");
                    if (list.contains("reverse")) {
                        val (x, xx, firstIndex, xxx, secondIndex) = list
                        var str = input.joinToString("")
                        val begin = str.substring(0, firstIndex.toInt()) + str.substring(firstIndex.toInt(), secondIndex.toInt() + 1).reversed() + str.substring(secondIndex.toInt() + 1)
                        input = begin.toCharArray()

                    }
                    if (list.contains("rotate") && list.contains("based")) {
                        val letter = list[6]
                        var steps = input.indexOf(letter[0])
                        if(steps>=4)steps++
                        val division = (steps +1) % input.size
                        val str = input.joinToString("").substring(input.size - division) + input.joinToString("").substring(0 until input.size - division)
                        input = str.toCharArray()
                    }
                    if (list.contains("rotate") && list.contains("left")) {
                        val steps = list[2].toInt()
                        val division = steps % input.size
                        val str = input.joinToString("").substring(division) + input.joinToString("").substring(0 until division)
                        input = str.toCharArray()
                    }
                    if (list.contains("rotate") && list.contains("right")) {
                        val steps = list[2].toInt()
                        val division = steps % input.size
                        val str = input.joinToString("").substring(input.size - division) + input.joinToString("").substring(0 until input.size - division)
                        input = str.toCharArray()
                    }
                    if (list.contains("move")) {
                        val toMove = list[2].toInt()
                        val destination = list[5].toInt()
                        var strL = input.joinToString("").toMutableList()
                        strL.removeAt(toMove)
                        val str = strL.joinToString("")
                        val before = str.substring(0, destination)
                        val inbetween = input[toMove]
                        val after = str.substring(destination)

                        input = (before + inbetween + after).toCharArray()
                    }
                    if (list.contains("swap") && list.contains("position")) {
                        // swap position 4 with position 0
                        val temp = input[list[2].toInt()]
                        input[list[2].toInt()] = input[list[5].toInt()]
                        input[list[5].toInt()] = temp
                    }
                    if (list.contains("swap") && list.contains("letter")) {
                        val indexoffirst = input.indexOf(list[2][0])
                        val indexofsecond = input.indexOf(list[5][0])
                        val temp = input[indexoffirst]
                        input[indexoffirst] = input[indexofsecond]
                        input[indexofsecond] = temp
                    }
                }
        return input.joinToString("");
    }


}