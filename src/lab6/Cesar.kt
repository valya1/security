package lab6

import java.util.*

class Cesar {

    val n = 33

    fun encode(message: String): String {
        val random = Random().nextInt(33)

        var result = ""
        message
            .toLowerCase()
            .asSequence()
            .filter { ch -> ch.isLetter() || ch == ' ' }
            .forEach { ch ->
                result += if (ch != ' ') letterByIndex[(indexByLetter[ch]!! + random) % 32] else ch
            }

        return result
    }


    fun decode(encodedMessage: String): String {

        val words = encodedMessage
            .toLowerCase()
            .filter { ch -> ch.isLetter() || ch == ' ' }
            .split(" ")

        var key = -1

        for (word in words) {

            for (i in 0..32) {
                var decodedWord = ""
                word.forEach { ch -> decodedWord += if(ch != ' ') letterByIndex[(indexByLetter[ch]!! + i) % 32] else ch }
                if (dictionary.contains(decodedWord)) {
                    key = i
                    break
                }

            }
        }

        if (key == -1) return ""

        var decodedMessage = ""

        words.forEach { word ->
                word.forEach { letter ->
                    decodedMessage += letterByIndex[(indexByLetter[letter]!! + key) % 32]
                }
                decodedMessage += " "
            }

        return decodedMessage.trim()
    }



}