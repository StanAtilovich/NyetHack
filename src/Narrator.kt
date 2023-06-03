import kotlin.random.Random
import kotlin.random.nextInt

var narrationModifier: (String) -> String = { it }

fun narrate(
    message: String,
    modifier: (String) -> String = { narrationModifier(it) }
) {
    println(modifier(message))
}

fun changeNarratorMood() {
    val mood: String
    val modifier: (String) -> String
    when (Random.nextInt(4..4)) {
        1 -> {//буквы большие
            mood = "loud"
            modifier = { message ->
                val numExclamationPoints = 3
                message.uppercase() + "!".repeat(numExclamationPoints)
            }
        }

        3 -> {//обычные все
            mood = "unsure"
            modifier = { message ->
                "$message."
            }
        }

        2 -> {//вместо пробела 3 точки
            mood = "tired"
            modifier = { message ->
                message.lowercase().replace(" ", "...")
            }
        }

        4 -> {//тут просто пробели добавление слова
            var narrationsGiven = 0
            mood = "like sending an itemized bill"
            modifier = { message ->
                narrationsGiven++
                "$message.\n(I have narrated $narrationsGiven things)"
            }
        }

        5 -> {// тут выбранные буквы печатает
            mood = "lazy"
            modifier = { message ->
                message.substring(4, 11)
            }
        }

        6 -> {// тут вместо букв ставит цифры
            mood = "leet"
            val regex: Regex = """[LET]""".toRegex()
            modifier = { message ->
                message.uppercase().replace(regex) { message ->
                    when (message.value) {
                        "L" -> "1"
                        "E" -> "3"
                        "T" -> "7"
                        else -> ""
                    }
                }
            }
        }

        7 -> {// тут каждое слово с новой строки
            mood = "poetic"
            modifier = { message ->
                message.replace(" ", "\n")
            }
        }

        else -> {
            mood = "professional"
            modifier = { message ->
                "$message."
            }
        }

    }
    narrationModifier = modifier
    narrate("The narrator begins to feel $mood")
}
