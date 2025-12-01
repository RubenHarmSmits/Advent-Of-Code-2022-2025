package days.year2015

import days.Day

fun main() {
    println(Day10().solve())
}

class Day10 : Day(10, 2015) {
    var input = "1113222113"
    fun solve(): Any {
        repeat(50){
            val list = createSequenceList(input)
            input = list.joinToString("") { "${it.length}${it.first()}" }
        }
        return input.length
    }

    private fun createSequenceList(input: String):List<String> {
        var current = input.first()
        var counter = 0
        val answer = mutableListOf<String>()
        input.forEach { char ->
            if(char==current) counter++
            else {
                answer.add(current.toString().repeat(counter))
                current = char
                counter = 1
            }
        }
        answer.add(current.toString().repeat(counter))
        return answer.also{println(it)}
    }
}

