package days.year2024

import days.Day
import kotlin.math.max

fun main() {
    println(Day22().solve())
}

class Day22 : Day(22, 2024) {

    fun solve(): Any {
        val returns =  inputList.map {
            getLastNumber(it.toLong())
        }

        val differences = returns.map { getDifferce(it) }

        var highest =0L

        (-9L..9L).forEach { first ->
            (-9L..9L).forEach { second ->
                (-9L..9L).forEach { third ->
                    (-9L..9L).forEach { fourth ->
                        val profit = differences.mapIndexed { i,difference ->
                            val index = difference.windowed(4).indexOf(listOf(first, second, third, fourth))
                            if(index == -1) 0L else returns[i][index+3]
                        }.sum()
                        highest = max(highest, profit)
                    }
                }
            }
        }
        return highest
    }

    fun getLastNumber(n: Long): List<Long>  = (0..2000).toList().map { output(n,it) % 10 }
    fun getDifferce(last: List<Long>)= last.mapIndexed { i, number -> if (i==0) 0 else number - last[i-1] }
    fun mix(first : Long, second: Long) = first xor second
    fun prune(first : Long) = first % 16777216L
    fun proces(s: Long): Long {
        var secret = s
        secret = prune(mix(secret * 64L, secret))
        secret = prune(mix(secret / 32L, secret))
        secret = prune(mix(secret * 2048L, secret))
        return secret
    }

    fun output(s: Long, n: Int): Long{
        var secret = s
        repeat(n){
            secret = proces(secret)
        }
        return secret
    }
}