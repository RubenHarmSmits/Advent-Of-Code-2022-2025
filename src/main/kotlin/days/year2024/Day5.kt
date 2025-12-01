package days.year2024

import days.Day

fun main() {
    println(Day5().solve())
}

class Day5 : Day(5, 2024) {

    fun solve(): Any {
        var (ruless, pages) = inputList.splitBy { it == "" }
        val rules = ruless.map { it.split('|').ints() }
        return pages.map { it.split(',').ints() }
            .sumOf { page ->
                if (rules.any { rule ->
                        ruleIsInvalid(rule, page)
                    }) checkNewOrdering(page) else 0
            }
    }

    private fun checkNewOrdering(page: List<Int>): Int {
        var (ruless, pages) = inputList.splitBy { it == "" }
        val rules = ruless.map { it.split('|').ints() }.filter { page.contains(it[0]) && page.contains(it[1]) }

        var newPage = page.toMutableList()

        var found = false
        while (!found) {
            rules.forEach { rule ->
                if (ruleIsInvalid(rule, newPage)) {
                    newPage = swapInts(newPage, rule[0], rule[1])
                    if (pageIsCorrect(newPage, rules)) found = true
                }
            }

        }
        return newPage[(newPage.size - 1) / 2]
    }

    private fun pageIsCorrect(page: List<Int>, rules: List<List<Int>>) = !rules.any {
        ruleIsInvalid(it, page)
    }


    private fun ruleIsInvalid(rule: List<Int>, page: List<Int>): Boolean {
        val firstIndex = page.indexOf(rule[0])
        val secondIndex = page.indexOf(rule[1])
        if (firstIndex == -1 || secondIndex == -1) return false
        return firstIndex > secondIndex;
    }
}