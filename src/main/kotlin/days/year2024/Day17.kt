package days.year2024

import days.Day

fun main() {
    println(Day17().solve())
}

class Day17 : Day(17, 2024) {  // too low 37221274271216

    val program = listOf(2, 4, 1, 2, 7, 5, 4, 7, 1, 3, 5, 5, 0, 3, 3, 0)
    val beginningRightDigits = 35184372088832L
    fun solve(): Any {
        var a = beginningRightDigits
        var n = 14
        while (n >= 0) {
            var found = false
            while (!found){
                val output = getOutput(a)
                if(output.takeLast(15-n)==program.takeLast(15-n).map{it.toLong()}){
                    found=true
                    n-=1
                } else {
                    a+=(8L `**` n.toLong())
                }
            }
        }
        return a
    }


    var b = 0L
    var c = 0L
    var a = 0L

    fun getOutput(aa: Long): List<Long> {
        var output = listOf<Long>()
        var pointer = 0
        a = aa
        b = 0L
        c = 0L
        while (pointer < program.size) {
            val opcode = program[pointer]
            val operand = program[pointer + 1].toLong()
            when (opcode) {
                0 -> a = a / (2L `**` getCombo(operand))
                1 -> b = b xor operand
                2 -> b = getCombo(operand) % 8
                3 -> if (a != 0L) pointer = (operand - 2).toInt()
                4 -> b = b xor c
                5 -> output += getCombo(operand) % 8L
                6 -> b = a / (2L `**` getCombo(operand))
                7 -> c = a / (2L `**` getCombo(operand))
            }

            pointer += 2
        }

        return output
    }

    private fun getCombo(operand: Long): Long {
        when (operand) {
            0L, 1L, 2L, 3L -> return operand
            4L -> return a
            5L -> return b
            6L -> return c
        }
        throw IllegalStateException("Unreachable")
    }

}