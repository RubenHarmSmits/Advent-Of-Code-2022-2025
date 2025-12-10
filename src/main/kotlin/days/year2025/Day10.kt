package days.year2025

import days.Day
import org.chocosolver.solver.Model
import org.chocosolver.solver.search.strategy.Search
import org.chocosolver.solver.variables.IntVar


fun main() {
    println(Day10().solve())
}


class Day10 : Day(10, 2025) {

    fun solve(): Any {
        return inputList
            .sumOf { line -> choco(line) }
    }

    fun choco(line: String):Int{
        val goal = extraxtAllIntsFromString(line.substringAfter("{").substringBefore("}"))
        val buttons = line.substringAfter("]").substringBefore("{").split("(").map { extraxtAllIntsFromString(it) }.filter { it.size != 0 }

        val maxCLicks = goal.max()
        val model = Model(line)

        val variables = buttons.mapIndexed { i, button ->
            model.intVar(i.toString(), 0, maxCLicks)
        }
        goal.forEachIndexed { goali, goalAmount ->
            val a = buttons.mapIndexedNotNull {i,b -> i.takeIf {goali in b  }}.map{variables[it]}.toTypedArray()
            model.scalar(a, IntArray(a.size){1}, "=", goalAmount).post()
        }

        val totalSum: IntVar = model.intVar("totalSum", 0, goal.sum())
        model.sum(variables.toTypedArray(), "=", totalSum).post()
        model.setObjective(Model.MINIMIZE, totalSum)

        model.solver.setSearch(Search.minDomLBSearch(*variables.toTypedArray()))

        var ans = 0
        while(model.solver.solve()){
            ans = variables.sumOf { it.value }
        }
        return ans
    }

}