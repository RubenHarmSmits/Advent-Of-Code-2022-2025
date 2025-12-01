package days.year2024

import days.Day
import org.jgrapht.alg.clique.BronKerboschCliqueFinder
import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.SimpleGraph

fun main() {
    println(Day23().solve())
}

class Day23 : Day(23, 2024) {

    class Computer(val name: String, val connected: MutableList<Computer> = mutableListOf())

    var computers = mutableListOf<Computer>()

    fun solve(): Any {
        val list = inputList.map { it.split("-").let { Pair(it[0], it[1])  } }
        list.forEach {
            computers.add(Computer(it.first))
            computers.add(Computer(it.second))
        }

        computers = computers.distinctBy { it.name }.toMutableList()

        list.forEach { item ->
            val f = computers.find { it.name == item.first }!!
            val s = computers.find { it.name == item.second }!!
            f.connected.add(s)
            s.connected.add(f)
        }


        val graph = SimpleGraph<String, DefaultEdge>(DefaultEdge::class.java)



        computers.forEach {computer ->
            computer.connected.forEach { connectedComputer ->
                graph.addVertex(computer.name)
                graph.addVertex(connectedComputer.name)
                graph.addEdge(connectedComputer.name, computer.name)
            }
        }

        val finder: BronKerboschCliqueFinder<String, DefaultEdge> = BronKerboschCliqueFinder(graph)
        finder.maximumIterator().forEach { println(it) }

        return computers.combinations(3).filter { combinationIsCorrect(it) }.size
    }

    fun combinationIsCorrect(combination: List<Computer>): Boolean {
        val (first, second, third) = combination
        return first in second.connected && first in third.connected &&
                second in first.connected && second in third.connected &&
                third in first.connected && third in second.connected &&
                (first.name.startsWith("t")||second.name.startsWith("t")||third.name.startsWith("t"))
    }


}