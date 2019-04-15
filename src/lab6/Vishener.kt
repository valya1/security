package lab6

import lab4.alphabet
import lab4.toNums

class Vishener {


    fun encode(message: String): String {

        val key = dictionary.random().toLowerCase().toNums()

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


    fun decode(encodedMessage: String) {

        val words = encodedMessage
            .toLowerCase()
            .filter { ch -> ch.isLetter() || ch == ' ' }
            .split(" ")

        var key = arrayListOf<Int>()

         for(encodedWord in words){

             for (word in dictionary) {
                 val potentialKey = word.toLowerCase().toNums() //сдвиги

                 var decodedWord = ""
                 encodedWord.forEachIndexed { index, ch ->
//                     decodedWord += letterByIndex[(indexByLetter[ch]!! + potentialKey[index % potentialKey.size] - indexByLetter['а']!!) % 32]
                     decodedWord += letterByIndex[(indexByLetter[ch]!! + alphabet.size - (potentialKey[index % potentialKey.size] - indexByLetter['а']!!)) % 32]

                 }
                 if(dictionary.contains(decodedWord)){ //ключ найден
                     key = potentialKey
                     break
                 }
             }
         }

        var decodedMessage = ""
        for(encodedWord in words){
            encodedWord.forEachIndexed { index, ch ->
                decodedMessage += letterByIndex[(indexByLetter[ch]!! + key[index % key.size] - indexByLetter['а']!!) % 32]
            }
        }

    }

}