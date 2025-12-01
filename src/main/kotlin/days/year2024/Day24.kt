package days.year2024

import days.Day
import java.lang.Long.toBinaryString

fun main() {
    println(Day24().solve())
}

class Day24 : Day(24, 2024) {

    private val input = inputList.splitBy { it == "" }

    data class Wire(
        val name: String,
        var value: Boolean? = null,
        val inputNameFirst: String? = null,
        val inputNameSecond: String? = null,
        var done: Boolean = false,
        val operation: String? = null
    )

    private val wires = mutableListOf<Wire>().apply {
        addAll(input[0].map { line ->
            val (name, value) = line.split(": ")
            Wire(name, value == "1", done = true)
        })
    }

    fun solve(): Any {
        parseInstructions()
        while (wires.any { !it.done }) {
            processWires()
        }
        val numx = getNumberOfWires("x", wires)
        val numy = getNumberOfWires("y", wires)
        val numz = getNumberOfWires("z", wires)
        println("x: $numx, y: $numy, x+y: ${numx+numy} z: $numz")
        println("${toBinaryString(numx+numy)} answer should be")
        println("${toBinaryString(numz)} answer is")
        return "qnf,vpm,tbt,gsd,kth,z32,z26,z12".split(",").sorted().joinToString(",")
    }

    fun getNumberOfWires(type: String, wires: List<Wire>): Long {
        return wires
            .filter { it.name.startsWith(type) }
            .sortedByDescending { it.name }
            .joinToString(separator = "") { if (it.value == true) "1" else "0" }
            .also{println(it)}
            .toLong(2)
    }

    private fun parseInstructions() {
        input[1].forEach { instruction ->
            val (first, operation, second, _, destination) = instruction.split(" ")
            wires.add(Wire(destination, null, first, second, operation = operation))
        }
    }

    private fun processWires() {
        wires.filter { !it.done }.forEach { wire ->
            val firstWire = wires.find { it.name == wire.inputNameFirst }
            val secondWire = wires.find { it.name == wire.inputNameSecond }
            if (firstWire?.value != null && secondWire?.value != null) {
                wire.value = calculate(firstWire.value!!, secondWire.value!!, wire.operation!!)
                wire.done = true
            }
        }
    }

    private fun calculate(first: Boolean, second: Boolean, operation: String): Boolean {
        return when (operation) {
            "AND" -> first && second
            "OR" -> first || second
            "XOR" -> first != second
            else -> throw IllegalArgumentException("Unrecognized operation: $operation")
        }
    }

    fun getWireValue(name: String): Boolean? {
        return wires.find { it.name == name }?.value
    }
}