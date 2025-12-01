package days.year2024

import days.Day

fun main() {
    println(Day3().solve())
}

class Day3 : Day(3, 2024) {

    var enabled: Boolean = true

    fun solve(): Any {
        println(inputString)
        return inputString.indices.sumOf {
            checkScore(inputString.substring(it))
        }
    }

    private fun checkScore(line: String): Int {
        try {
            if(line.substring(0,4)=="do()") enabled=true
            if(line.substring(0,7)=="don't()") enabled=false
            if(line.substring(0,4)!="mul(") return 0
            val num1 = extractFirstNumber(line.substring(4))
            val lennum1 = num1.length
            val num2 = extractFirstNumber(line.substring(4+lennum1+1))
            val lennum2 = num2.length
            if(line[lennum1+lennum2+5]!=')') return 0
            return if (enabled) (num1.toInt() * num2.toInt()) else 0
        } catch (e: Exception){
            return 0
        }

    }

    fun extractFirstNumber(input: String): String {
        val regex = Regex("^\\d+")
        val matchResult = regex.find(input)
        return matchResult?.value ?: throw Exception("Invalid input")
    }


}