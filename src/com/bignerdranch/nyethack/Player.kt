package com.bignerdranch.nyethack

import Loot
import numVowels


class Player(
    initialName: String,
    val hometown: String = "Neversummer",
    override var healthPoints: Int,
    val isImmortal: Boolean
    ) : Fightable {
    override var name = initialName
        get() = field.replaceFirstChar { it.uppercase() }
        private set(value) {
            field = value.trim()
        }
    override val diceSides: Int = 4

    override fun takeDamage(damage: Int) {
        if (!isImmortal){
            healthPoints -= damage
        }
    }


    override val diceCount: Int = 3
    val title: String
        get() = when {
            name.all { it.isDigit() } -> "Идентифицируемый"
            name.none { it.isLetter() } -> "Сотрудник службы защиты свидетелей"
            name.numVowels > 4 -> "Мастер гласных"
            else -> "Прославленный герой"
        }

    val prophece by lazy {
        narrate("$name отправляется на трудные поиски гадалки")
        Thread.sleep(3000)
        narrate("The fortune taller дарует пророчество $name")
        "Бесстрашный герой из $hometown должен ли когда-нибудь" + listOf(
            "сформируйте маловероятную связь между двумя враждующими группировками",
            "завладейте потусторонним клинком",
            "верните миру дар созидания",
            "лучший в мире едок"
        ).random()
    }

    val inventory = mutableListOf<Loot>()

    var gold = 0

    fun prophesize() {
        narrate("$name thinks about their future")
        narrate("A fortune teller told Madrigal, \"$prophece\"")
    }

    init {
        require(healthPoints > 0) { "health Points must be greater than zero" }
        require(name.isNotBlank()) { "Player must have a name" }
    }

    constructor(name: String) : this(
        initialName = name,
        healthPoints = 100,
        isImmortal = false
    ) {
        if (name.equals("Jason", ignoreCase = true)) {
            healthPoints = 500
        }
    }

    fun castFireball(numFireballs: Int = 2) {
        narrate("A glass of Fireball springs into existence(x$numFireballs)")
    }

    fun changeName(newName: String) {
        narrate("$name legally changes their name to $newName")
        name = newName
    }
}