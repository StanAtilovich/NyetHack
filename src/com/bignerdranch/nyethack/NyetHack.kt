package com.bignerdranch.nyethack

import Fedora
import Gemstones
import LootBox
import com.bignerdranch.nyethack.Game.quit

lateinit var player: Player

fun main() {
    narrate("Welcome to NyetHack!")
    val playerName = promptHeroName()
    player = Player(playerName)
    changeNarratorMood()
    Game.play()

}

fun promptHeroName(): String {
    narrate("A hero enters the town of Kronstadt. What is their name?\"")
    { message
        ->
        // Выводит сообщение желтым цветом
        "\u001b[33;1m$message\u001b[0m"
    }

    val input = readLine()
    require(input != null && input.isNotEmpty()) {
        "The hero must have a name."
    }
    return input
}


fun makeYellow(message: String): String = "\u001b[33;1m$message\u001b[0m"


fun ringBell() {
    println("Вы ударили в колокол и теперь все знают, что вы в городе.")
}

