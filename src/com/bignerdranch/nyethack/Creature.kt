package com.bignerdranch.nyethack

import kotlin.random.Random

interface Fightable {
    val name: String
    val healthPoints: Int
    val diceCount: Int
    val diceSides: Int

    fun takeDamage(damage: Int)

    fun attack(opponent: Fightable) {
        val damageRoll = (0 until diceCount).sumOf {
            Random.nextInt(diceSides + 1)
        }
        narrate("$name deals $damageRoll to ${opponent.name}")
        opponent.takeDamage(damageRoll)
    }
}

abstract class Monster(
    override val name: String,
    val description: String,
    var heathPoints: Int
) : Fightable {
    override fun takeDamage(damage: Int) {
        heathPoints -= damage
    }
}

class Goblin(
    name: String = "Goblin",
    description: String = "A nasty-looking goblin",
    heathPoints: Int = 30, override val healthPoints: Int = 30
) : Monster(name, description, heathPoints) {
    override val diceCount = 2
    override val diceSides = 8
}