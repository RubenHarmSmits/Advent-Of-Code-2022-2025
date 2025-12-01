package days.year2017

import days.Day
import days.Matrix
import kotlin.math.pow

fun main() {
    println(Day21().solve()) //too high 127
}
class Day21: Day(21, 2017) {

    data class Rule(val from: List<List<Boolean>>, val to: List<List<Boolean>>)

//    var grid  = matrixOf(mutableListOf(false, true, false,false, true, false),mutableListOf(false, false, true,false, true, false),mutableListOf(true, true, true,false, true, false))
    var grid  = matrixOf(mutableListOf(false, true, false),mutableListOf(false, false, true),mutableListOf(true, true, true))
    val rules2 = inputList.filter{it.indexOf('/')==2}.map{
        val (from, to) = it.split(" => ")
        Rule(from.split("/").map{it.map{it=='#'}}, to.split("/").map{it.map{it=='#'}})
    }.map{rule ->
        val allRotations = (lists(rule.from)+
                lists(rotateBooleanMatrixCLockwise(rule.from.toMutableMatrix(),1)) +
                lists(rotateBooleanMatrixCLockwise(rule.from.toMutableMatrix(),2)) +
                lists(rotateBooleanMatrixCLockwise(rule.from.toMutableMatrix(),3))).toSet()
        allRotations.map{ Rule(it, rule.to) }
    }.flatten()
    val rules3 = inputList.filter{it.indexOf('/')==3}.map{
        val (from, to) = it.split(" => ")
        Rule(from.split("/").map{it.map{it=='#'}}, to.split("/").map{it.map{it=='#'}})
    }.map{rule ->
        val allRotations = (lists(rule.from)+
                lists(rotateBooleanMatrixCLockwise(rule.from.toMutableMatrix(),1)) +
                lists(rotateBooleanMatrixCLockwise(rule.from.toMutableMatrix(),2)) +
                lists(rotateBooleanMatrixCLockwise(rule.from.toMutableMatrix(),3))).toSet()
        allRotations.map{ Rule(it, rule.to) }
    }.flatten()
    val list = inputList
    var count = 0

    val n = 5

    fun solve(): Any {
        repeat(18){
            println(it)
            if(grid.size%2==0){
                val boxes = divideBoxes2(grid, 2)
                val translatedBoxes: List<List<List<Boolean>>> = tranlateBoxes(boxes)
                grid = createGrid(translatedBoxes)
            } else {
                val boxes = divideBoxes(grid, 3)
                val translatedBoxes: List<List<List<Boolean>>> = tranlateBoxes(boxes)
                grid = createGrid(translatedBoxes)
            }

        }
        return grid.flatten().count{it}
    }

    private fun divideBoxes(grid: Matrix<Boolean>, n: Int):List<List<List<Boolean>>> {
        return grid.chunked(n).map{rows ->
            val first = rows.first().chunked(n)
            val second = rows[1].chunked(n)
            val third = rows[2].chunked(n)
            first.mapIndexed { i, firstTree ->
                listOf(firstTree, second[i], third[i])
            }
        }.flatten()
    }

    private fun divideBoxes2(grid: Matrix<Boolean>, n: Int):List<List<List<Boolean>>> {
        return grid.chunked(n).map{rows ->
            val first = rows.first().chunked(n)
            val second = rows[1].chunked(n)
            first.mapIndexed { i, firstTree ->
                listOf(firstTree, second[i])
            }
        }.flatten()
    }

    private fun tranlateBoxes(boxes: List<List<List<Boolean>>>): List<List<List<Boolean>>> {
        return boxes.map{translateBox(it)}
    }

    private fun translateBox(box: List<List<Boolean>>): List<List<Boolean>> {
        if(box.size==3){
            val all = rules3.filter { rule3 ->
                rule3.from == box
            }
            return all.first().to
        } else {
            val all =  rules2.filter { rule2 ->
                rule2.from == box
            }
            return all.first().to
        }
    }


    private fun lists(box: List<List<Boolean>>): MutableList<List<List<Boolean>>> {
        val possibleRotations = mutableListOf(box)
        val allTrees = box.toMutableList().map { it.toMutableList().reversed() }
        possibleRotations.add(allTrees)
        return possibleRotations
    }

    private fun createGrid(translatedBoxes: List<List<List<Boolean>>>): Matrix<Boolean> {
        val newGrid = mutableListOf<List<Boolean>>()
        val n = translatedBoxes.size.toDouble().pow(0.5).toInt()
        val size = translatedBoxes.first().size
        for(i in 0 until n){
            val boxes = translatedBoxes.subList(i* n, (i*n)+n)
            for(ii in 0 until size){
                val row = boxes.map{it[ii]}.flatten()
                newGrid.add(row)
            }
        }
        return newGrid.toMutableMatrix()
    }

}
