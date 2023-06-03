var heroName: String = ""
fun main() {
    heroName = promptHeroName()
    narrate("$heroName, ${createTitle(heroName)}, heads to the town square")
    visitTavern()
}

fun promptHeroName(): String {
    narrate("A hero enters the town of Kronstadt. What is their name?\"")
    println("Madrigal")
    return "Madrigal"
}

fun makeYellow(message: String): String = "\u001b[33;1m$message\u001b[0m"
private fun createTitle(name: String): String {
    return when {
        name.all { it.isDigit() } -> "The  Identifiable"
        name.none { it.isLetter() } -> "The Witness Protection Member"
        name.count { it.lowercase() in "aeiou" } > 4 -> "The Master of Vowel"
        else -> "The Renowned Hero"
    }
}