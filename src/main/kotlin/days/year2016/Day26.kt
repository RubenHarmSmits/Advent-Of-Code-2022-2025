package days.year2016

import days.Day
import java.time.Duration
import java.time.LocalTime
import java.time.format.DateTimeFormatter

fun main() {
    println(Day26().solve())
}

class Day26 : Day(26, 2016) {
    var tot = 0

    var input = "";

    var listAuthorized: MutableList<Action> = mutableListOf()
    var listExecuted: MutableList<Action> = mutableListOf()
    var differences: MutableList<Long> = mutableListOf()

    fun solve(): Any {
        var a = inputList
            .forEachIndexed { i, it ->
                var list = it.split(" ");
                val timeString = list[4].substringBefore(",")
                val formatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS")
                val time = LocalTime.parse(timeString, formatter)
                val status = list[10].toInt()

                if(status!=200) return@forEachIndexed

                val transactionId = list[8].substringAfter("transactions/").substringBefore("/")
                if (it.contains("authorization")) {
                    listAuthorized.add(Action(time, transactionId))
                } else {
                    listExecuted.add(Action(time, transactionId))
                }
            }

        listExecuted.forEach { action ->
            val matchAuthorized: Action? = listAuthorized.find { it.transactionId == action.transactionId }
            if (matchAuthorized != null) {
                val difference = Duration.between(matchAuthorized.time, action.time).toMillis()
                differences.add(difference)
            } else {
                println("not found")
            }
        }

        differences = differences.filter { it > 0 }.toMutableList()

        java.io.File("/Users/bu79fg/Developer/personal/Advent-Of-Code-2022/src/main/kotlin/test").printWriter().use { out ->
            differences.forEach { line ->
                out.println(line)
            }
        }
        return tot;
    }


}

data class Action(val time: LocalTime, var transactionId: String)