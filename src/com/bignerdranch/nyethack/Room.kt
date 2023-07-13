package com.bignerdranch.nyethack

import DropOffBox
import Gemstones
import Hat
import Loot
import LootBox
import Sellable

open class Room(val name: String) {
    protected open val status = "Calm"
    open val lootBox: LootBox<Loot> = LootBox.random()
    open fun description() = "$name (Currentlt: $status)"
    open fun enterRoom() {
        narrate("There is nothing to do here")
    }
}

class TownSquare : Room("The Town Square") {
    override val status = "Bustling"
    private var bellSound = "GWONG"
    val hatDropOffBox = DropOffBox<Hat>()
    val gemDropOffBox = DropOffBox<Gemstones>()
    final override fun enterRoom() {
        narrate(
            "\n" +
                    "Жители деревни сплачиваются и приветствуют появление героя"
        )
        ringBell()
    }

    private fun ringBell() {
        narrate("Колокольня возвестила о прибытии героя: $bellSound")
    }

    fun <T> sellLoot(
        loot: T
    ): Int where T : Loot, T : Sellable {
        return when (loot) {
            is Hat -> hatDropOffBox.sellLoot(loot)
            is Gemstones -> gemDropOffBox.sellLoot(loot)
            else -> 0
        }
    }
}

open class MonsterRoom(
    name: String,
    var monster: Monster? = Goblin()
) : Room(name) {
    override fun description() =
        super.description() + "(Creature: ${monster?.description ?: "None"}"

    override fun enterRoom() {
        if (monster == null) {
            super.enterRoom()
        } else {
            narrate("В этой комнате таится опасность")
        }
    }
}