package com.bignerdranch.nyethack

import Sellable
import addEnthusiasm
import move
import orEmptyRoom
import kotlin.system.exitProcess

object Game {

    fun sellLoot() {
        when (val currentRoom = currentRoom) {
            is TownSquare -> {
                player.inventory.forEach { item ->
                    if (item is Sellable) {
                        val sellPrice = currentRoom.sellLoot(item)
                        narrate("Sold ${item.name} for $sellPrice gold")
                        player.gold += sellPrice
                    } else {
                        narrate("Your ${item.name} can't be sold")
                    }
                }
                player.inventory.removeAll { it is Sellable }
            }

            else -> narrate("Здесь вы ничего не сможете продать")
        }

    }

    fun takeLoot() {
        val loot = currentRoom.lootBox.takeLoot()
        if (loot == null) {
            narrate("${player.name} подходит к ящику с добычей, но он пуст")
        } else {
            narrate("${player.name} сейчас имеет ${loot.name}")
            player.inventory += loot
        }
    }

    private val worldMap = listOf(
        listOf(TownSquare(), Tavern(), Room("Back Room")),
        listOf(MonsterRoom("A Long Corridor"), Room("A Generic Room")),
        listOf(MonsterRoom("The Dungeon"))
    )
    private var currentRoom: Room = worldMap[0][0]
    private var currentPosition = Coordinate(0, 0)

    private class GameInput(arg: String?) {
        private val input = arg ?: ""
        val command = input.split(" ")[0]
        val argument = input.split(" ").getOrElse(1) { "" }

        fun processCommand() = when (command.lowercase()) {
            "fight" -> fight()
            "move" -> {
                val direction = Direction.values()
                    .firstOrNull { it.name.equals(argument, ignoreCase = true) }
                if (direction != null) {
                    move(direction)
                } else {
                    narrate("I don't know what direction that is")
                }
            }

            "take" -> {
                if (argument.equals("loot", ignoreCase = true)) {
                    com.bignerdranch.nyethack.Game.takeLoot()
                } else {
                    narrate("Я не знаю, чего ты пытаешься добиться")
                }
            }

            "ring" -> ringBell()
            "quit", "exit" -> quit()
            "sell" -> {
                if (argument.equals("loot", ignoreCase = true)) {
                    sellLoot()
                } else {
                    narrate("Я не знаю, что ты пытаешься продать")
                }
            }

            else -> narrate("I'm not sure what you're trying to do")
        }

    }

    init {
        narrate("Welcome, adventurer")
        val mortality = if (player.isImmortal) "an immortal " else "a mortal"
        narrate("${player.name}, $mortality, has ${player.healthPoints} health points")
    }

    fun play() {
        while (true) {
            narrate("${player.name} of ${player.hometown}, ${player.title}, is in ${currentRoom.description()}")
            currentRoom.enterRoom()

            print("> Enter your command: ")
            GameInput(readlnOrNull()).processCommand()
        }
    }

    fun quit() {
        println("Выход из игры")
        println("Спасибо за игру")
        exitProcess(0)
    }

    fun move(direction: Direction) {
        val newPosition = currentPosition move direction
        val newRoom = worldMap[newPosition].orEmptyRoom()
            narrate("The hero moves ${direction.name}")
            currentPosition = newPosition
            currentRoom = newRoom
    }

    fun fight() {
        val monsterRoom = currentRoom as? MonsterRoom
        val currentMonster = monsterRoom?.monster
        if (currentMonster == null) {
            narrate("There is nothing to fight here")
            return
        }

        var combatRound = 0
        val previousNarrationModifier = narrationModifier
        narrationModifier = { it.addEnthusiasm(enthusiasmLevel = combatRound) }

        while (player.healthPoints > 0 && currentMonster.heathPoints > 0) {
            combatRound++
            player.attack(currentMonster)
            if (currentMonster.heathPoints > 0) {
                currentMonster.attack(player)
            }
            Thread.sleep(1000)
        }
        narrationModifier = previousNarrationModifier

        if (player.healthPoints <= 0) {
            narrate("You have defeated! Thanks for playing")
            exitProcess(0)
        } else {
            narrate("${currentMonster.name} has been defeated")
            monsterRoom.monster = null
        }
    }
}

private operator fun List<List<Room>>.get(coordinate: Coordinate) =
    getOrNull(coordinate.y)?.getOrNull(coordinate.x)
