package days.year2015

import days.Day
import kotlin.math.max

fun main() {
    println(Day21().solve())
}

class Day21 : Day(21, 2015) {

    val hitPoints = 104
    val damage = 8
    val armor = 1
    data class Item(val name: String, val cost: Int, val damage: Int, val armor: Int)


    val weapons = listOf(
        Item("Dagger", 8, 4, 0),
        Item("Shortsword", 10, 5, 0),
        Item("Warhammer", 25, 6, 0),
        Item("Longsword", 40, 7, 0),
        Item("Greataxe", 74, 8, 0)
    )

    val armors = listOf(
        Item("Leather", 13, 0, 1),
        Item("Chainmail", 31, 0, 2),
        Item("Splintmail", 53, 0, 3),
        Item("Bandedmail", 75, 0, 4),
        Item("Platemail", 102, 0, 5)
    )

    val rings = listOf(
        Item("Damage +1", 25, 1, 0),
        Item("Damage +2", 50, 2, 0),
        Item("Damage +3", 100, 3, 0),
        Item("Defense +1", 20, 0, 1),
        Item("Defense +2", 40, 0, 2),
        Item("Defense +3", 80, 0, 3)
    )

    fun solve(): Any {
        val options = mutableListOf<List<Item>>()
        weapons.forEach { weapon ->
            rings.forEach { ring ->
                options.add(listOf(weapon, ring))
                rings.filter{it!=ring}.forEach { otherring ->
                    options.add(listOf(weapon, ring, otherring))
                }
            }
            armors.forEach { armor ->
                options.add(listOf(weapon, armor))
                rings.forEach { ring ->
                    options.add(listOf(weapon, armor, ring))
                    rings.filter{it!=ring}.forEach { otherring ->
                        options.add(listOf(weapon, armor, ring, otherring))
                    }
                }
            }
        }
        return options.sortedBy{it.sumOf { it.cost }}.reversed().find{!doIWin(it)}!!.sumOf { it.cost }
    }

    fun doIWin(items: List<Item>):Boolean {
        var myHitpoints =100
        var bossHitpoints = hitPoints
        val myDamage = items.sumOf { it.damage }
        val myArmor = items.sumOf { it.armor }
        val myNetDamage = max(myDamage - armor,1)
        val bossNetDamage = max(damage - myArmor,1)
        val myturns = (bossHitpoints + myNetDamage -1) / myNetDamage
        val hiturns = (myHitpoints + bossNetDamage -1) / bossNetDamage
        return myturns <=hiturns

    }
}

