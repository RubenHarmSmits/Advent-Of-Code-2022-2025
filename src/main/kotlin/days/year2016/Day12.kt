package days.year2016

import days.Day

fun main() {
    val start = System.currentTimeMillis()
    println(Day12().partOne())
    println("MILISECONDS: " + (System.currentTimeMillis() - start))
}

data class Register(val name: String, var amount:Int )

class Day12 : Day(12, 2016) {

    val registers = listOf(
            Register("a", 0),
            Register("b", 0),
            Register("c", 1),
            Register("d", 0)
    )

    private fun getRegisterByName(name: String): Register{
        return registers.find{it.name == name}!!
    }
    fun partOne(): Any {
        var i = 0
        while(i<inputList.size){
            val line = inputList.get(i);
            i++
            val list = line.split(" ")
            when(list[0]){
                "inc"-> getRegisterByName(list[1]).amount++
                "dec"-> getRegisterByName(list[1]).amount--
                "cpy"-> getRegisterByName(list[2]).amount = getValue(list[1])
                "jnz"-> {
                    if(getValue(list[1])!=0) i += list[2].toInt()-1
                }
            }
        }
        return getRegisterByName("a").amount
    }

    private fun getValue(source: String): Int {
        if(isNumeric(source)) return source.toInt()
        return getRegisterByName(source).amount
    }

}




