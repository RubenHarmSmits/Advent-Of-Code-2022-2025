package days.year2017

import days.Day

fun main() {
    println(Day20().solve())
}

// 10 + 2 + 3 + 4

// p=<-815,-87,-531>, v=<19,-38,105>, a=<5,6,-9>

class Day20 : Day(20, 2017) { // too high 492
    data class Node(val p: Point3L, var vx: Long,var vy: Long,var vz: Long,var ax: Long,var ay: Long,var az: Long){
        fun move(){
            vx+=ax
            vy+=ay
            vz+=az
            p.x+=vx
            p.y+=vy
            p.z+=vz
        }

    }
    var list = inputList.map{line ->
        val (px,py,pz,vx,vy,vz,ax,ay,az) = extraxtAllIntsFromStringIncludingNegative(line)
        Node(Point3L(py.toLong(),px.toLong(),pz.toLong()),vx.toLong(),vy.toLong(),vz.toLong(),ax.toLong(),ay.toLong(),az.toLong())
    }
    var count = 0
    
   

    fun solve(): Any {
        repeat(500000000){
            list = list.groupBy { it.p }.filter{it.value.size==1}.values.flatten()
            list.forEach { it.move() }
            println(list.size)
        }
        return list.size
    }

}
