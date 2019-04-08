package lab3.rsa

import java.lang.Math.pow

class Receiver(val e: Int, val n: Int) {

    //в функцию так же должно передаваться исходное сообщение T', но его опустим, т.к h(T') заранее известно
    fun verifySender(s: Int): Boolean {
        val h2 = 18 // результат выполнения h(T')

        val h3 = pow(s.toDouble(), e.toDouble()).rem(n).toInt()

        return h3 == h2
    }


}
