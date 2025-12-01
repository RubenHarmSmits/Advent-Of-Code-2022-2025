package days

import util.InputReader
import java.math.BigInteger
import java.security.MessageDigest
import java.util.LinkedList
import java.util.Locale
import kotlin.math.abs
import kotlin.math.pow

typealias Matrix<T> = List<List<T>>
typealias MutableMatrix<T> = MutableList<MutableList<T>>


abstract class Day(dayNumber: Int, year: Int = 2022) {

    data class Node(
        val point: Point,
        var options: MutableList<Point> = mutableListOf(),
        var minimalCost: Int = Int.MAX_VALUE,
        var visited: Boolean = false
    )


    fun List<String>.ints(radix: Int = 10) = this.map { it.toInt(radix) }
    fun List<Int>.product() = this.reduce { acc, i -> acc * i }
    fun List<Long>.product() = this.reduce { acc, i -> acc * i }

    fun List<Long>.closestValue(value: Long) = minBy { abs(value - it) }


    fun <E> List<E>.splitBy(predicate: (E) -> Boolean): List<List<E>> =
            this.fold(mutableListOf(mutableListOf<E>())) { acc, element ->
                if (predicate.invoke(element)) {
                    acc += mutableListOf<E>()
                } else {
                    acc.last() += element
                }
                acc
            }

    open fun <T> transpose(table: List<List<T>>): List<List<T>> {
        val ret: MutableList<List<T>> = ArrayList()
        val N = table[0].size
        for (i in 0 until N) {
            val col: MutableList<T> = ArrayList()
            for (row in table) {
                col.add(row[i])
            }
            ret.add(col)
        }
        return ret
    }

    // lazy delegate ensures the property gets computed only on first access
    protected val inputList: List<String> by lazy { InputReader.getInputAsList(dayNumber, year) }
    protected val inputListInt: List<Int> by lazy { InputReader.getInputAsListInt(dayNumber, year) }

    protected val inputString: String by lazy { InputReader.getInputAsString(dayNumber, year) }

    fun sign(num: Int): Int = if (num > 0) 1 else -1
    fun sign(positive: Boolean) = if (positive) 1 else -1

    data class PointL(var y: Long, var x: Long)

    data class Range(val begin: Int, val end: Int)



    fun extraxtAllIntsFromString(string: String): List<Int> {
        val regex = Regex("\\d+")
        val resultList = regex.findAll(string).map { it.value }.toList().ints()
        return resultList
    }
    fun extraxtAllIntsFromStringIncludingNegative(string: String): List<Int> {
        val regex = Regex("-?\\d+")
        val resultList = regex.findAll(string).map { it.value }.toList().ints()
        return resultList
    }

    fun extraxtAllLongsFromString(string: String): List<Long> {
        val regex = Regex("\\d+")
        val resultList = regex.findAll(string).map { it.value }.toList().map { it.toLong() }
        return resultList
    }

    fun isNumeric(str: String) = str.matches(Regex("-?\\d+"))
    fun String.isNumber() = this.matches(Regex("-?\\d+"))
    fun Char.isNumber() = this.toString().isNumber()

    enum class Direction {
        UP, DOWN, RIGHT, LEFT
    }

    fun directionIsOpposite(dir1: Direction?, dir2: Direction?): Boolean {
        if (dir1 == Direction.DOWN && dir2 == Direction.UP) return true
        if (dir2 == Direction.DOWN && dir1 == Direction.UP) return true
        if (dir1 == Direction.RIGHT && dir2 == Direction.LEFT) return true
        if (dir2 == Direction.RIGHT && dir1 == Direction.LEFT) return true
        return false
    }

    fun calculateDirection(begin: Point, end: Point): Direction {
        if(begin.y>end.y) return Direction.UP
        if(begin.y<end.y) return Direction.DOWN
        if(begin.x<end.x) return Direction.RIGHT
        if(begin.x>end.x) return Direction.LEFT
        throw Exception("opeesei")
    }

    fun isTurn(begin: Point, end: Point, direction: Direction): Boolean {
        val newDirection = calculateDirection(begin, end)
        if(newDirection==direction||directionIsOpposite(newDirection, direction))return false
        return true
    }

    data class Point(var y: Int, var x: Int): Comparable<Point> {

        fun move(direction: Any) {
            when (direction) {
                'D', 'v', Direction.DOWN -> this.y++
                'U', '^', Direction.UP -> this.y--
                'L', '<', Direction.LEFT -> this.x--
                'R', '>', Direction.RIGHT -> this.x++
                else -> throw IllegalArgumentException("$direction is not a valid direction")
            }
        }

        override fun compareTo(other: Point): Int {
            return compareValuesBy(this, other, Point::y, Point::x)
        }
    }

    fun manhattanDistance(p1: Point, p2: Point): Int {
        return abs(p1.x - p2.x) + abs(p1.y - p2.y)
    }

    fun manhattanDistance3(p1: List<Int>, p2: List<Int>, p3: List<Int>): Int {
        return abs(p1[0] - p2[1]) + abs(p1[0] - p2[1]) + abs(p1[0] - p2[1])
    }

    fun manhattanDistance(p1: PointL, p2: PointL): Long {
        return abs(p1.x - p2.x) + abs(p1.y - p2.y)
    }


    fun <T> matrixOf(vararg rows: List<T>): Matrix<T> = List(rows.size) { i -> rows[i] }
    fun matrixOfInput(inputList: List<String>): Matrix<Char> {
        return matrixOf(inputList.map { it.map { it } })
    }

    fun <T> matrixOf(rows: List<List<T>>): Matrix<T> = List(rows.size) { i -> rows[i] }
    fun <T> Matrix<T>.toMutableMatrix(): MutableMatrix<T> = this.map { it.toMutableList() }.toMutableList()
    fun <T> Matrix<T>.getColumn(col: Int): List<T> = getCol(this, col)
    fun <T, R> Matrix<T>.mapMatrix(transform: (T) -> R): Matrix<R> = this.map { it.map(transform) }
    fun <T, R> Matrix<T>.mapMatrixIndexed(transform: (Int, Int, T) -> R): Matrix<R> =
            this.mapIndexed { i, row -> row.mapIndexed { j, col -> transform(i, j, col) } }

    fun <T> Matrix<T>.matrixToString(): String = this.joinToString("\n") { it.joinToString(", ") }
    fun <T : Comparable<T>> Matrix<T>.matrixMax(): T = this.mapNotNull { it.maxOrNull() }.maxOrNull()!!
    fun <T : Comparable<T>> Matrix<T>.matrixMin(): T = this.mapNotNull { it.minOrNull() }.minOrNull()!!
    fun <T> Matrix<T>.getColNum(): Int = this[0].size
    fun <T> Matrix<T>.getRowNum(): Int = this.size
    fun <T> Matrix<T>.transposed(times: Int = 1): Matrix<T> = transposeMatrix(this, times)
    fun <T> emptyMatrixOf(rows: Int, columns: Int, default: T) = MutableList(rows) { MutableList(columns) { default } }
//    fun <T> Matrix<T>.count(predicate: (T) -> Boolean) = this.sumOf { it.count(predicate) }
    fun <T> Matrix<T>.getAdjacent(row: Int, col: Int): List<T> =
            this.getAdjacentCoordinates(row, col).map { it -> this[it.y][it.x] }
    fun <T> Matrix<T>.getAdjacent(point: Point): List<T> =
        this.getAdjacentCoordinates(point.y, point.x).map { it -> this[it.y][it.x] }

    fun <T> Matrix<T>.containsPoint(point: Point): Boolean {
        if(point.x < 0 || point.y < 0) return false
        if(point.y>=this.size)return false
        if(point.x>=this[0].size)return false
        return true;
    }



    fun <T> Matrix<T>.findChar(char: Char): Point {
        this.indices.forEach { y ->
            this[0].indices.forEach { x ->
                if (this[y][x] == char) return Point(y, x)
            }
        }
        throw Exception("Could not find $char")
    }

    fun <T> Matrix<T>.findAll(predicate : (T) -> Boolean): List<Point> {
        val list = mutableListOf<Point>()
        this.indices.forEach { y ->
            this[0].indices.forEach { x ->
                if (predicate(this[y][x])) list.add(Point(y, x))
            }
        }
        return list
    }

    fun <T> Matrix<T>.getAdjacentCoordinates(row: Int, col: Int): List<Point> {
        val adjacent = mutableListOf<Point>()
        if (col != 0) adjacent.add(Point(row, col - 1))
        if (col != this.getColNum() - 1) adjacent.add(Point(row, col + 1))
        if (row != 0) adjacent.add(Point(row - 1, col))
        if (row != this.getRowNum() - 1) adjacent.add(Point(row + 1, col))
        return adjacent
    }

    fun <T> Matrix<T>.getAdjacentCoordinates2(point: Pair<Point,Point>): List<Pair<Point,Point>> {
        val adjacent = mutableListOf<Pair<Point,Point>>()


        if (point.first.x != 0) adjacent.add(Point(point.first.y, point.first.x - 1) to point.second);
        else adjacent.add(Point(point.first.y, this.getRowNum()-1) to Point(point.second.y,point.second.x-1))

        if (point.first.x != this.getColNum() - 1) adjacent.add(Point(point.first.y, point.first.x + 1) to point.second)
        else adjacent.add(Point(point.first.y, 0) to Point(point.second.y,point.second.x+1))


        if (point.first.y != 0) adjacent.add(Point(point.first.y - 1, point.first.x) to point.second)
        else adjacent.add(Point(this.getColNum()-1, point.first.x) to Point(point.second.y -1,point.second.x))


        if (point.first.y != this.getRowNum() - 1) adjacent.add(Point(point.first.y + 1, point.first.x) to point.second)
        else adjacent.add(Point(0, point.first.x) to Point(point.second.y +1,point.second.x))


        return adjacent
    }

    fun <T> Matrix<T>.getAdjacentCoordinates(point: Point): List<Point> = getAdjacentCoordinates(point.y, point.x)
//    fun <T> Matrix<T>.getAdjacentCoordinates2(point: Point): List<Point> = getAdjacentCoordinates2(point.y, point.x)
    fun <T> Matrix<T>.getRangesToEdge(point: Point) = getRangesToEdge(point.y, point.x)
    fun <T> Matrix<T>.getRangesToEdge(row: Int, col: Int) = getColumnToEdge(row, col) + getRowToEdge(row, col)
    fun <T> Matrix<T>.getColumnToEdge(row: Int, col: Int): List<List<T>> =
            this.getColumn(col).let { listOf(it.subList(0, row), it.subList(row + 1, it.size)) }

    fun <T> Matrix<T>.getRowToEdge(row: Int, col: Int): List<List<T>> =
            this[row].let { listOf(it.subList(0, col), it.subList(col + 1, it.size)) }


    fun <T> Matrix<T>.getSurroundingCoordinates(row: Int, col: Int): List<Point> {
        val adjacent = getAdjacentCoordinates(row, col).toMutableList()
        if (col != 0 && row != 0) adjacent.add(Point(row - 1, col - 1))
        if (col != 0 && row != this.getRowNum() - 1) adjacent.add(Point(row + 1, col - 1))
        if (col != this.getColNum() - 1 && row != 0) adjacent.add(Point(row - 1, col + 1))
        if (col != this.getColNum() - 1 && row != this.getRowNum() - 1) adjacent.add(Point(row + 1, col + 1))
        return adjacent
    }

    fun <T> Matrix<T>.getSurroundingCoordinates(point: Point): List<Point> =
            this.getSurroundingCoordinates(point.y, point.x)

    fun <T> Matrix<T>.bfs(start: Point, end: Point): List<Point> {
        val queue = LinkedList<Point>()
        val visited = mutableSetOf<Point>()
        val path = mutableMapOf<Point, Point>()

        queue.add(start)
        visited.add(start)

        while (queue.isNotEmpty()) {
            val current = queue.removeFirst()

            if (current == end) {
                // path found, reconstruct it using the path map
                val shortestPath = mutableListOf<Point>()
                var cur = end
                while (cur != start) {
                    shortestPath.add(cur)
                    cur = path[cur]!!
                }
                shortestPath.add(start)
                return shortestPath.reversed()
            }

            val neighbors = this.getAdjacentCoordinates(current).filter { this.getInt(it) - this.getInt(current) < 2 }

            for (neighbor in neighbors) {
                if (neighbor !in visited) {
                    queue.add(neighbor)
                    visited.add(neighbor)
                    path[neighbor] = current
                }
            }
        }


        // no path found
        return List(5000) { start }
    }

    fun <T> Matrix<T>.print() {
        for (row in this) {
            for (element in row) {
                print(element)
                print(" ")
            }
            println() // Move to the next line after printing each row
        }
        println()
    }

    fun reverseColumns(matrix: MutableList<MutableList<Char>>): MutableList<MutableList<Char>> {
        val SIZE = matrix.size
        for (x in 0 until SIZE) {
            val temp = matrix.map { it[x] }.reversed()
            for (y in 0 until SIZE) {
                matrix[y][x] = temp[y]
            }
        }
        return matrix
    }

    fun rotateMatrixCLockwise(matrix: Matrix<Char>, amount: Int): Matrix<Char> {
        var rotatedMatrix: MutableMatrix<Char> = matrix.toMutableMatrix();
        repeat(amount) {
            val rows = rotatedMatrix.size
            val cols = rotatedMatrix[0].size
            val newRotatedMatrix = MutableList(cols) { MutableList(rows) { ' ' } }
            for (i in 0 until rows) {
                for (j in 0 until cols) {
                    newRotatedMatrix[j][rows - 1 - i] = rotatedMatrix[i][j]
                }
            }
            rotatedMatrix = newRotatedMatrix
        }
        return rotatedMatrix
    }

    fun rotateBooleanMatrixCLockwise(matrix: Matrix<Boolean>, amount: Int): Matrix<Boolean> {
        var rotatedMatrix: MutableMatrix<Boolean> = matrix.toMutableMatrix();
        repeat(amount) {
            val rows = rotatedMatrix.size
            val cols = rotatedMatrix[0].size
            val newRotatedMatrix = MutableList(cols) { MutableList(rows) { false } }
            for (i in 0 until rows) {
                for (j in 0 until cols) {
                    newRotatedMatrix[j][rows - 1 - i] = rotatedMatrix[i][j]
                }
            }
            rotatedMatrix = newRotatedMatrix
        }
        return rotatedMatrix
    }

    fun <T> Matrix<T>.print(p: Point, dir: Char) {
        for ((y, row) in this.withIndex()) {
            for ((x, element) in row.withIndex()) {
                if (y == p.y && x == p.x) print(dir)
                else print(element)
            }
            println() // Move to the next line after printing each row
        }
        println()
    }

    fun <T> Matrix<T>.get(point: Point): T {
        return this[point.y][point.x]
    }

    fun <T> Matrix<T>.getInt(point: Point): Int {
        return this[point.y][point.x] as Int
    }


    fun Point.moveInDirection(direction: Any, step: Int = 1): Point = when (direction) {
        'D', Direction.DOWN -> Point(this.y + step, this.x)
        'U', Direction.UP -> Point(this.y - step, this.x)
        'L', Direction.LEFT -> Point(this.y, this.x - step)
        'R', Direction.RIGHT -> Point(this.y, this.x + step)
        else -> throw IllegalArgumentException("$direction is not a valid direction")
    }


    data class Point3(val y: Int, val x: Int, val z: Int)
    data class Point3L(var y: Long, var x: Long, var z: Long)
    data class Cube(val y: Int, val x: Int, val z: Int)

    fun String.hexToBinaryString(): String {
        val num = this.uppercase(Locale.getDefault())
        var binary = ""
        for (hex in num) {
            when (hex) {
                '0' -> binary += "0000"
                '1' -> binary += "0001"
                '2' -> binary += "0010"
                '3' -> binary += "0011"
                '4' -> binary += "0100"
                '5' -> binary += "0101"
                '6' -> binary += "0110"
                '7' -> binary += "0111"
                '8' -> binary += "1000"
                '9' -> binary += "1001"
                'A' -> binary += "1010"
                'B' -> binary += "1011"
                'C' -> binary += "1100"
                'D' -> binary += "1101"
                'E' -> binary += "1110"
                'F' -> binary += "1111"
            }
        }
        return binary
    }


    fun perm(input: String): List<String> {
        if (input.isEmpty()) return listOf("")
        val result = mutableListOf<String>()
        input.forEachIndexed { index, char ->
            val remaining = input.removeRange(index, index + 1)
            perm(remaining).forEach { permutation ->
                result.add(char + permutation)
            }
        }
        return result
    }


    fun <E> permutations(list: List<E>, length: Int? = null): Sequence<List<E>> = sequence {
        val n = list.size
        val r = length ?: list.size

        val indices = list.indices.toMutableList()
        val cycles = (n downTo (n - r)).toMutableList()
        yield(indices.take(r).map { list[it] })

        while (true) {
            var broke = false
            for (i in (r - 1) downTo 0) {
                cycles[i]--
                if (cycles[i] == 0) {
                    val end = indices[i]
                    for (j in i until indices.size - 1) {
                        indices[j] = indices[j + 1]
                    }
                    indices[indices.size - 1] = end
                    cycles[i] = n - i
                } else {
                    val j = cycles[i]
                    val tmp = indices[i]
                    indices[i] = indices[-j + indices.size]
                    indices[-j + indices.size] = tmp
                    yield(indices.take(r).map { list[it] })
                    broke = true
                    break
                }
            }
            if (!broke) {
                break
            }
        }
    }
    /*----- Helper Functions -----*/

    fun <T> transposeMatrix(matrix: Matrix<T>): Matrix<T> =
            List(matrix.getColNum()) { i -> matrix.getColumn(i) }

    fun <T> transposeMatrix(matrix: Matrix<T>, times: Int): Matrix<T> {
        var newMatrix = matrix
        repeat(times) {
            newMatrix = transposeMatrix(newMatrix)
        }
        return newMatrix
    }

    fun <T> getCol(array: List<List<T>>, col: Int): List<T> {
        val rows = array.size
        val column = mutableListOf<T>()
        (0 until rows).forEach {
            column.add(array[it][col])
        }
        return column
    }

    fun md5(input: String): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
    }

    infix fun IntRange.overlaps(other: IntRange): Boolean =
            first in other || last in other || other.first in this || other.last in this

    infix fun IntRange.containsRange(other: IntRange): Boolean = other.first in this && other.last in this

    fun <T> List<T>.combinations(size: Int): List<List<T>> {
        if (size == 0) return listOf(emptyList())
        if (size > this.size) return emptyList()
        if (size == this.size) return listOf(this)

        val result = mutableListOf<List<T>>()
        for (i in this.indices) {
            val head = this[i]
            val tail = this.drop(i + 1)
            result.addAll(tail.combinations(size - 1).map { it + head })
        }
        return result
    }

    fun <T> List<T>.indexOfSubList(subList: List<T>): Int {
        if (subList.isEmpty() || this.size < subList.size) return -1
        return (0..this.size - subList.size).firstOrNull { this.subList(it, it + subList.size) == subList } ?: -1
    }


    // sum of x eg 10 + 9 + 8 + 7 .. + 1
    fun summation(x: Long): Long{
        return x * (x + 1L) / 2L
    }

    fun summation(x: Int): Int{
        return x * (x + 1) / 2
    }

    // find lcm of a list of longs eg [3,4,6] = 12
    fun leastCommonMultiple(numbers: List<Long>): Long {
        var result = numbers[0]
        for (i in 1 until numbers.size) {
            result = leastCommonMultiple(result, numbers[i])
        }
        return result
    }
    // Function to calculate the least common multiple (LCM) of 2 longs
    fun leastCommonMultiple(a: Long, b: Long): Long = abs(a * b) / greatestCommonDevisor(a, b)

    // find the GCD of 2 long's
    fun greatestCommonDevisor(a: Long, b: Long): Long = if (b == 0L) a else greatestCommonDevisor(b, a % b)


    operator fun <T> List<T>.component6(): T = get(5)
    operator fun <T> List<T>.component7(): T = get(6)
    operator fun <T> List<T>.component8(): T = get(7)
    operator fun <T> List<T>.component9(): T = get(8)
    operator fun <T> List<T>.component10(): T = get(9)
    operator fun <T> List<T>.component11(): T = get(10)
    operator fun <T> List<T>.component12(): T = get(11)
    operator fun <T> List<T>.component13(): T = get(12)
    operator fun <T> List<T>.component14(): T = get(13)
    operator fun <T> List<T>.component15(): T = get(14)


    fun intersectRanges(range1: LongRange, range2: LongRange): LongRange? {
        val start = maxOf(range1.start, range2.start)
        val endInclusive = minOf(range1.endInclusive, range2.endInclusive)

        // Check if there is a valid intersection
        if (start <= endInclusive) {
            return start..endInclusive
        } else {
            return null // No intersection
        }
    }


    fun outersectRanges(range1: LongRange, range2: LongRange): MutableList<LongRange> {
        val intersectionStart = maxOf(range1.start, range2.start)
        val intersectionEnd = minOf(range1.endInclusive, range2.endInclusive)

        val outersects = mutableListOf<LongRange>()

        // Check if there is a valid intersection
        if (intersectionStart <= intersectionEnd) {
            if (range1.start < intersectionStart) {
                outersects.add(range1.start until intersectionStart)
            }
            if (intersectionEnd < range1.endInclusive) {
                outersects.add((intersectionEnd + 1)..range1.endInclusive)
            }

            if (range2.start < intersectionStart) {
                outersects.add(range2.start until intersectionStart)
            }
            if (intersectionEnd < range2.endInclusive) {
                outersects.add((intersectionEnd + 1)..range2.endInclusive)
            }
        } else {
            // No intersection, add both ranges as outersects
            outersects.add(range1)
            outersects.add(range2)
        }

        return outersects
    }

    fun outersectWithList(current: LongRange, innerRanges: List<LongRange>): List<LongRange> {
        var outersects = mutableListOf(current)
        innerRanges.forEach { innerRange ->
            var newOutersects = mutableListOf<LongRange>()
            outersects.forEach { outersect ->
                var inner = intersectRanges(outersect, innerRange)
                if (inner == null) {
                    newOutersects.add(outersect)
                } else {
                    newOutersects.addAll(outersectRanges(outersect, innerRange))
                }
            }
            outersects = newOutersects
        }
        return outersects
    }

    fun areStraightNeighbors(point1: Point, point2: Point): Boolean {
        val xDifference = Math.abs(point1.x - point2.x)
        val yDifference = Math.abs(point1.y - point2.y)
        // Check if points are adjacent along the x-axis or y-axis (not diagonally)
        return (xDifference == 1 && yDifference == 0) ||   // Adjacent along x-axis
                (xDifference == 0 && yDifference == 1)      // Adjacent along y-axis
    }
    fun areBothPositiveOrNegative(a: Int, b: Int): Boolean {
        return (a > 0 && b > 0) || (a < 0 && b < 0)
    }

    fun swapInts(list: MutableList<Int>, int1: Int, int2: Int):MutableList<Int> {
        val index1 = list.indexOf(int1)
        val index2 = list.indexOf(int2)
        val temp = list[index1]
        list[index1] = list[index2]
        list[index2] = temp
        return list
    }

    /**
     * Integer power using [Double.pow]
     */
    infix fun Int.`**`(exponent: Int): Int = toDouble().pow(exponent).toInt()

    /**
     * Long power using [Double.pow]
     * Note: it may be preferable to use a BigInteger instead of toDouble()
     * to prevent a loss of precision - use whichever makes sense
     * for the number you have at hand, and the precision you need.
     */
    infix fun Long.`**`(exponent: Int): Long = toDouble().pow(exponent).toLong()
    infix fun Long.`**`(exponent: Long): Long = toDouble().pow(exponent.toInt()).toLong()
// infix fun Long.`**`(exponent: Int): Long = toBigInteger().pow(exponent).toLong()

    /**
     * Double power using [Double.pow]
     */
    infix fun Float.`**`(exponent: Int) : Float = this.pow(exponent)

    /**
     * Float power using [Float.pow]
     */
    infix fun Double.`**`(exponent: Int) : Double = this.pow(exponent)


    fun <T> T.log(tag: String = ""): T {
        println("$tag$this")
        return this
    }

    // Helper function to generate all permutations/combinations of a list
    fun <T> List<T>.permutations(): List<List<T>> {
        if (size <= 1) return listOf(this)
        return flatMap { element ->
            (this - element).permutations().map { listOf(element) + it }
        }
    }





}


