package days.year2017

import days.Day

fun main() {
    println(Day7().solve())
}
class Day7: Day(7, 2017) {

    data class Node(val name: String, val weight: Int, val children: List<String>){
        fun getWeight(all: List<Node>): Int{
            return weight + children.sumOf{child->all.find{it.name==child}!!.getWeight(all)}
        }

        fun checkChildren(all: List<Node>){
            val weights = children.map{name-> all.find{it.name==name}!!.getWeight(all)}
            if(weights.toSet().size >1){
                val wrongWeight = weights.find{weight -> weights.count { it == weight }==1}!!
                val correctWeight = weights.find{weight -> weights.count { it == weight }>1}!!
                val wrongChildName = children.find{name->all.find{it.name==name}!!.getWeight(all)==wrongWeight}!!
                val wrongChild = all.find{it.name==wrongChildName}!!
                println(wrongChild.weight - (wrongWeight - correctWeight))
            }
        }
    }


    val list = inputList.map{
        val spl = it.split(" ")
        val children = if(it.contains("-> ")){
            it.substringAfter("-> ").split(", ")
        } else listOf()
        Node(spl.first(), extraxtAllIntsFromString(it).first(), children)
    }



    fun solve(): Any {
        list.forEach { node -> node.checkChildren(list) }
        return 0
    }

}
