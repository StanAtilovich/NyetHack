package com.bignerdranch.nyethack

import Key
import LootBox
import java.io.File
import kotlin.random.Random
import kotlin.random.nextInt

private const val TAVERN_MASTER = "Tavern!"
private const val TAVERN_NAME = "$TAVERN_MASTER's Folly"

private val firstNames = setOf("Alex", "Mordoc", "Sophie", "Tariq")
private val lastNames = setOf("Ironfoot", "Fernsworth", "Baggins", "Downstrider")

private val menuData = File("data/tavern-menu-data.txt")
    .readText()
    .split("\n")
    .map { it.split(",") }


private val menuItems = menuData.map { (_, name, _) ->
    name
}

private val menuItemTypes = menuData.associate { (type, name, _) ->
    name to type
}

class Tavern : Room(TAVERN_NAME) {
    //val itemOfDay = patrons.flatMap { getFavoriteMenuItems(it) }.random()
    override val status = "Busy"
    override val lootBox: LootBox<Key> =
        LootBox(Key("ключ к логову зла Ногарце"))
    override fun enterRoom() {
        narrate("${player.name} enters $TAVERN_NAME")
        narrate("There are several items for sale:")
        narrate(menuItems.joinToString())
        //narrate("The item of the day is $itemOfDay")
        narrate("${player.name} sees several patrons in the tavern:")
        narrate("There are still some patrons in the tavern")


    }

    private fun placeOrder(
        patronName: String,
        menuItemName: String,

        ) {
        val itemPrice = menuItemPrices.getValue(menuItemName)
        narrate("$patronName speaks with $TAVERN_MASTER to place an order")

        narrate("$TAVERN_MASTER says, \"You need more coin for a $menuItemName\"")
    }
}


private val menuItemPrices = menuData.associate { (_, name, price) ->
    name to price.toDouble()
}


private fun Any.fold(
    initial: Pair<Int, Int>,
    operation: (acc: Pair<Int, Int>, Map.Entry<String, List<String>>) -> Unit
) {

}


private fun getFavoriteMenuItems(patron: String): List<String> {
    return when (patron) {
        "Alex Ironfoot" -> menuItems.filter { menuItem ->
            menuItemTypes[menuItem]?.contains("dessert") == true
        }

        else -> menuItems.shuffled().take(Random.nextInt(1..2))
    }
}


