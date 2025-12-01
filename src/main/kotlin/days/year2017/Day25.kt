package days.year2017

import days.Day

fun main() {
    println(Day25().solve())
}

class Day25: Day(25, 2017) {

    data class State(val ifNotOn: Move, val ifOn: Move)
    data class Move(val turnOn: Boolean, val moveToRight: Boolean, val continueWith: Int)

    val tapee = BooleanArray(100000){false}

    val states = listOf(
        State(Move(true, true, 1), Move(false, false, 2)), // A
        State(Move(true, false, 0), Move(true, true, 3)),  // B
        State(Move(true, true, 0), Move(false, false, 4)), // C
        State(Move(true, true, 0), Move(false, true, 1)),  // D
        State(Move(true, false, 5), Move(true, false, 2)), // E
        State(Move(true, true, 3), Move(true, true, 0))    // F
    )

    var cursor = 50000
    var stateI = 0

    fun solve(): Any {
        repeat(12919244){
            val state = states[stateI]
            val currentIsOn = tapee[cursor]
            val move = if(currentIsOn) state.ifOn else state.ifNotOn
            tapee[cursor] = move.turnOn
            cursor += if(move.moveToRight) 1 else -1
            stateI = move.continueWith
        }
        return tapee.count { it }
    }
}
