package days.year2015

import days.Day

fun main() {
    println(Day19().solve())
}

class Day19 : Day(19, 2015) {
    var molecule = "CRnCaSiRnBSiRnFArTiBPTiTiBFArPBCaSiThSiRnTiBPBPMgArCaSiRnTiMgArCaSiThCaSiRnFArRnSiRnFArTiTiBFArCaCaSiRnSiThCaCaSiRnMgArFYSiRnFYCaFArSiThCaSiThPBPTiMgArCaPRnSiAlArPBCaCaSiRnFYSiThCaRnFArArCaCaSiRnPBSiRnFArMgYCaCaCaCaSiThCaCaSiAlArCaCaSiRnPBSiAlArBCaCaCaCaSiThCaPBSiThPBPBCaSiRnFYFArSiThCaSiRnFArBCaCaSiRnFYFArSiThCaPBSiThCaSiRnPMgArRnFArPTiBCaPRnFArCaCaCaCaSiRnCaCaSiRnFYFArFArBCaSiThFArThSiThSiRnTiRnPMgArFArCaSiThCaPBCaSiRnBFArCaCaPRnCaCaPMgArSiRnFYFArCaSiThRnPBPMgAr"

//    val molecule = "HOH"
    val changes = inputList.map { it.split(" => ").let { it[0] to it[1] } }

    val memory = mutableSetOf<String>()

    fun solve(): Any {
        check(molecule, 0)
        return 0
    }

    fun check(molecule: String, steps: Int) {
        if(molecule in memory)return
        memory.add(molecule)
        if (molecule == "e") println(steps)
        val options = mutableSetOf<Pair<Int, Pair<String, String>>>()
        for (i in molecule.indices) {
            val change = changes.find { molecule.substring(i).startsWith(it.second) }
            if (change != null) options.add(i to change)
        }
        options.shuffled().forEach { option ->
            check(
                molecule.replaceRange(option.first, option.first + option.second.second.length, option.second.first),
                steps + 1
            )
        }
    }

//    fun check(initial: String, left: String) {
//        if (left.isEmpty()){
//            options.add(initial)
//            return
//        }
//        changes.forEach {
//            if (left.startsWith(it.first)) {
//                check(initial + it.second, left.removePrefix(it.first))
//            }
//        }
//        check(initial + left.first(), left.removeRange(0,1))
//    }

//    fun check(initial: String, left: String) {
//        if (left.isEmpty()) {
//            options.add(initial)
//            return
//        }
//        changes.forEach {
//            if (left.startsWith(it.first)) {
//                check(initial + it.second, left.removePrefix(it.first))
//            }
//        }
//        check(initial + left.first(), left.removeRange(0, 1))
//    }

    fun getOptions(molecule: String): MutableSet<String> {
        val options = mutableSetOf<String>()
        for (i in molecule.indices) {
            changes.forEach { change ->
                if (molecule.substring(i).startsWith(change.first)) {
                    val option = molecule.take(i) + change.second + molecule.substring(i + change.first.length)
                    if (option !in memory) {
                        memory.add(option)
                        options.add(option)
                    }
                }
            }
        }
        return options
    }
}

