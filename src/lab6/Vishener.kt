package lab6

import lab4.toNums

class Vishener {


    fun encode(message: String): String {

        val key = dictionary.random().toNums()

        var encodedMessage = ""

        message
            .toLowerCase()
            .filter { ch -> ch.isLetter() || ch == ' ' }
            .forEachIndexed { index, ch ->
                encodedMessage +=
                        if (ch != ' ')                                              //offset
                            letterByIndex[(indexByLetter[ch]!! + key[index % key.size] - indexByLetter['а']!!) % 32]
                        else
                            ch
            }

        return encodedMessage
    }


    fun decode(){

        //todo доделать

    }

}