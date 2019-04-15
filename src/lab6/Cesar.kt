package lab6

import lab4.alphabet
import java.util.*

class CesarCoder {

    val n = 33

    fun cesar(message: String, decode: Boolean): String{
        val random = Random().nextInt(33)

        var result = ""
        message.toLowerCase().forEach { ch ->
            result += if (ch != ' ') alphabetByIndex[(alphabetByChar[ch]!! + random) % 33] else ch
        }

        return result
    }

}