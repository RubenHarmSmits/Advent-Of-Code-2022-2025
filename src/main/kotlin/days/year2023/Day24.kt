package days.year2023

import days.Day
import java.math.BigDecimal
import java.math.RoundingMode


fun main() {
    println(Day24().solve())
}


class Day24 : Day(24, 2023) {

    var stones = inputList.map {
        val split = it.replace(",", "").split(" ").filter { it != "@" && it != "" }.map { BigDecimal(it) }
        Stone(Point3D(split[1], split[0], split[2]), Point3D(split[4], (split[3]), split[5]))
    }.shuffled()

    fun solve(): Any {

        for (vx in -1000..1000) {
            outerLoop@ for (vy in -1000..1000) {
                for (vz in 0..0) {
                    val firstVelocity = stones[0].velocity.copy()
                    val firstPosition = stones[0].position.copy()
                    firstVelocity.adjust(BigDecimal(vx), BigDecimal(vy), BigDecimal(vz))

                    var point: PointD? = null
                    for (i in 1 until stones.size) {
                        val secondVelocity = stones[i].velocity.copy()
                        val secondPosition = stones[i].position.copy()
                        secondVelocity.adjust(BigDecimal(vx), BigDecimal(vy), BigDecimal(vz))

                        val intersectPointxy = intersect(firstPosition.z, firstPosition.y, firstPosition.z+firstVelocity.x, firstPosition.y+firstVelocity.y, secondPosition.z, secondPosition.y, secondPosition.z+secondVelocity.z, secondPosition.y+secondVelocity.y)

                        if (point == null) {
                            point = intersectPointxy
                        } else {
                            val y1 = point.y.setScale(0, RoundingMode.FLOOR)
                            val x1 = point.x.setScale(0, RoundingMode.FLOOR)
                            val y2 = intersectPointxy.y.setScale(0, RoundingMode.FLOOR)
                            val x2 = intersectPointxy.x.setScale(0, RoundingMode.FLOOR)
                            if (y1.minus(y2).abs() > BigDecimal(0.0) || x1.minus(x2).abs() > BigDecimal(0.0)) {
                                if (intersectPointxy != PointD(BigDecimal(0), BigDecimal(0))) {
                                    continue@outerLoop
                                }
                            }
                        }
                    }
                    if (point != null && point != PointD(BigDecimal(0), BigDecimal(0))) {
                        println(point)
                        println(vx); println(vy)
                    }
                }
            }
        }
        return 0;
    }



    fun intersect(x1: BigDecimal, y1: BigDecimal, x2: BigDecimal, y2: BigDecimal, x3: BigDecimal, y3: BigDecimal, x4: BigDecimal, y4: BigDecimal): PointD {
        val denom = (y4 - y3) * (x2 - x1) - (x4 - x3) * (y2 - y1)

        if (denom == BigDecimal(0)) {
            return PointD(BigDecimal(0), BigDecimal(0))
        }

        val ua = ((x4 - x3) * (y1 - y3) - (y4 - y3) * (x1 - x3)) / denom

        val intersectionX = x1 + ua * (x2 - x1)
        val intersectionY = y1 + ua * (y2 - y1)

        return PointD(intersectionY, intersectionX)
    }

}


data class Stone(var position: Point3D, var velocity: Point3D)

data class Point3D(var y: BigDecimal, var x: BigDecimal, var z: BigDecimal) {
    fun adjust(dx: BigDecimal, dy: BigDecimal, dz: BigDecimal) {
        this.x = this.x.minus(dx)
        this.y = this.y.minus(dy)
        this.z = this.z.minus(dz)
    }
}

data class PointD(var y: BigDecimal, var x: BigDecimal)






