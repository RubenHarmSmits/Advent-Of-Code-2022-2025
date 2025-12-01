package days.year2015

import com.fasterxml.jackson.databind.node.ArrayNode
import com.fasterxml.jackson.databind.node.ObjectNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import days.Day
import java.io.File

fun main() {
    println(Day12().solve())
}

class Day12 : Day(12, 2015) {
    val mapper = jacksonObjectMapper()
    val root = mapper.readTree(File("src/main/resources/2015/harING.json"))

    val listNodes = mutableListOf(root)
    var answer = 0

    fun solve(): Any {
        while (listNodes.isNotEmpty()) {
            val node = listNodes.removeAt(0)
            if (node is ObjectNode && node.any { it.textValue() == "red" }) continue
            node.forEach {
                if (it.isInt) answer += it.asInt()
                if (it is ObjectNode || it is ArrayNode) listNodes.add(it)
            }
        }
        return answer
    }
}

