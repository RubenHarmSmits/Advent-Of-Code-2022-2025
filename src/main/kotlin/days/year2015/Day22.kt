package days.year2015

import days.Day
import kotlin.math.max

fun main() {
    println(Day22().solve())
}

class Day22 : Day(22, 2015) { //too low 491  1249  1362   1269

    data class Person(var hp: Int, var mana: Int)

    var lowestManaSpend = Int.MAX_VALUE

    val me = Person(50,  500)
    val boss = Person(58, 0)

    fun solve(): Any {
        move(me, boss, 0, 0, 0, 0, true)
        return lowestManaSpend
    }

    fun move(me: Person, boss: Person, manaSpend: Int, shield: Int, poison: Int, recharge: Int, isMyTurn: Boolean) {
        if(poison>0)boss.hp-=3
        if(recharge>0)me.mana+=101
        if(isMyTurn)me.hp-=1

        if(me.hp<=0 || manaSpend >=lowestManaSpend) return
        if(boss.hp<=0){
            lowestManaSpend = manaSpend
            return
        }

        if(isMyTurn){
            if(me.mana>=53) move(me.copy(mana =  me.mana-53), boss.copy(hp = boss.hp - 4), manaSpend +53, dec(shield),dec(poison),dec(recharge), false)

            if(me.mana>=73) move(me.copy(mana =  me.mana-73, hp = me.hp +2), boss.copy(hp = boss.hp - 2), manaSpend +73, dec(shield),dec(poison),dec(recharge), false)

            if(me.mana>=113 && shield<=1) move(me.copy(mana =  me.mana-113), boss.copy(), manaSpend + 113, 6,dec(poison),dec(recharge), false)

            if(me.mana>=173 && poison<=1) move(me.copy(mana =  me.mana-173), boss.copy(), manaSpend +173, dec(shield),6,dec(recharge), false)

            if(me.mana>=229 && recharge<=1) move(me.copy(mana =  me.mana-229), boss.copy(), manaSpend +229, dec(shield),dec(poison),5, false)
        } else {
            move(me.copy(hp = if(shield>0) me.hp-2 else me.hp-9), boss.copy(), manaSpend, dec(shield),dec(poison),dec(recharge), true)
        }
    }

    fun dec(i:Int) = max(0,i-1)
}

